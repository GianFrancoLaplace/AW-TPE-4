package com.viajes.service;

import com.viajes.dto.IniciarViajeDTO;
import com.viajes.dto.FinalizarViajeDTO;
import com.viajes.client.TarifaClient;
import com.viajes.client.FlotaClient;
import com.viajes.client.CuentaClient;
import com.viajes.entity.Pausa;
import com.viajes.entity.Viaje;
import com.viajes.repository.PausaRepository;
import com.viajes.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaRepository pausaRepository;

    @Autowired
    private TarifaClient tarifaClient;

    @Autowired
    private FlotaClient flotaClient;

    @Autowired
    private CuentaClient cuentaClient;

    // ---------- INICIAR VIAJE ----------
    public Viaje iniciarViaje(IniciarViajeDTO dto) {

        // Verificar disponibilidad del monopatín
        boolean disponible = flotaClient.verificarDisponibilidad(dto.getIdMonopatin());
        if (!disponible) {
            throw new IllegalStateException("El monopatín no está disponible");
        }

        // Verificar cuenta
        boolean cuentaValida = cuentaClient.verificarCuentaHabilitada(dto.getIdCuenta());
        if (!cuentaValida) {
            throw new IllegalStateException("La cuenta no está habilitada o sin crédito suficiente");
        }

        // Crear nuevo viaje
        Viaje viaje = new Viaje();
        viaje.setIdUsuario(dto.getIdUsuario());
        viaje.setIdMonopatin(dto.getIdMonopatin());
        viaje.setIdCuenta(dto.getIdCuenta());
        viaje.setInicio(LocalDateTime.now());
        viaje.setEstadoViaje(Viaje.EstadoViaje.EN_CURSO);
        viaje.setKmRecorridos(0.0);

        Viaje nuevoViaje = viajeRepository.save(viaje);

        // Marcar monopatín en uso
        flotaClient.marcarEnUso(dto.getIdMonopatin());

        return nuevoViaje;
    }

    // ---------- PAUSAR VIAJE ----------
    public Pausa pausarViaje(Long idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        if (viaje.getEstadoViaje() != Viaje.EstadoViaje.EN_CURSO) {
            throw new RuntimeException("El viaje no está en curso y no puede pausarse");
        }
        // Evitar múltiples pausas activas
        pausaRepository.findByViajeIdAndActivaTrue(idViaje)
                .ifPresent(p -> {
                    throw new RuntimeException("Ya existe una pausa activa para este viaje");
                });

        Pausa pausa = new Pausa();
        pausa.setInicioPausa(LocalDateTime.now());
        pausa.setActiva(true);
        pausa.setExtendida(false);
        pausa.setViaje(viaje);

        return pausaRepository.save(pausa);
    }

    // ---------- REANUDAR VIAJE ----------
    public Viaje reanudarViaje(Long idViaje) {
        Pausa pausaActiva = pausaRepository.findByViajeIdAndActivaTrue(idViaje)
                .orElseThrow(() -> new RuntimeException("No hay pausa activa para este viaje"));

        pausaActiva.setFinPausa(LocalDateTime.now());
        pausaActiva.setActiva(false);
        // Calcular duración de la pausa
        Duration duracion = Duration.between(pausaActiva.getInicioPausa(), pausaActiva.getFinPausa());
        if (duracion.toMinutes() > 15) {
            pausaActiva.setExtendida(true);
        }

        pausaRepository.save(pausaActiva);

        return pausaActiva.getViaje();
    }

    // ---------- FINALIZAR VIAJE ----------
    public Viaje finalizarViaje(FinalizarViajeDTO dto) {
        Viaje viaje = viajeRepository.findById(dto.getIdViaje())
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        if (viaje.getFin() != null) {
            throw new RuntimeException("El viaje ya fue finalizado");
        }

        // Validar ubicación final
        boolean enParada = flotaClient.verificarUbicacionEnParada(
                String.valueOf(viaje.getIdMonopatin()), dto.getLatitud(), dto.getLongitud()
        );
        if (!enParada) {
            throw new RuntimeException("El monopatín no se encuentra en una parada permitida");
        }

        // Finalizar viaje
        viaje.setFin(LocalDateTime.now());
        viaje.setEstadoViaje(Viaje.EstadoViaje.FINALIZADO);

        // Calcular duración total
        long duracionTotalMinutos = Duration.between(viaje.getInicio(), viaje.getFin()).toMinutes();

        // Calcular pausas
        Integer minutosPausa = pausaRepository.calcularTotalMinutosPausas(viaje.getId());
        boolean tienePausaExcesiva = pausaRepository.tienePausaExcesiva(viaje.getId());

        if (minutosPausa == null) minutosPausa = 0;

        viaje.setMinutosTotales((int) duracionTotalMinutos);
        viaje.setMinutosPausa(minutosPausa);
        viaje.setPausaExtendida(tienePausaExcesiva);

        // Calcular kilómetros recorridos
        double kmRecorridos = flotaClient.obtenerKmRecorridos(
                String.valueOf(viaje.getIdMonopatin()),
                viaje.getInicio().toString(),
                viaje.getFin().toString()
        );
        viaje.setKmRecorridos(kmRecorridos);

        viajeRepository.save(viaje);

        // Marcar monopatín disponible nuevamente
        flotaClient.marcarComoDisponible(viaje.getIdMonopatin());

        // Enviar datos al microservicio de tarifa
        tarifaClient.calcularCosto(
                viaje.getId(),
                viaje.getIdCuenta(),
                duracionTotalMinutos,
                tienePausaExcesiva
        );

        return viaje;
    }
}
