package com.viajes.service;

import com.flota.entity.Monopatin;
import com.flota.repository.ParadaRepository;
import com.flota.service.MonopatinService;
import com.flota.service.ParadaService;
import com.viajes.dto.InicioViajeRequest;
import com.viajes.entity.Pausa;
import com.viajes.entity.Viaje;
import com.viajes.repository.PausaRepository;
import com.viajes.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private PausaRepository pausaRepository;

    @Autowired
    private ParadaService paradaService;

    @Autowired
    private MonopatinService monopatinService;

    // TODO: Implementar la lógica completa de iniciar viaje
    // 1. Validar que el monopatín existe y está disponible (llamada a servicio Flota)
    // 2. Crear el viaje con fechaHoraInicio = LocalDateTime.now()
    // 3. Actualizar el estado del monopatín a EN_USO
    public Viaje iniciarViaje(InicioViajeRequest request) {
        Viaje viaje = new Viaje();
        viaje.setIdMonopatin(request.getIdMonopatin());
        viaje.setIdUsuario(request.getIdUsuario());
        viaje.setIdCuenta(request.getIdCuenta());
        viaje.setIdParadaOrigen(request.getIdParadaOrigen());
        viaje.setFechaHoraInicio(LocalDateTime.now());
        viaje.setEstadoViaje(Viaje.EstadoViaje.EN_CURSO);

        // TODO: Llamar a servicio Flota para actualizar estado monopatín

        return viajeRepository.save(viaje);
    }

    // TODO: Implementar validación de GPS (parada destino)
    // verificar que la ubicación del monopatín está dentro del radio de la parada
    public Viaje finalizarViaje(Long idViaje, Long idParadaDestino) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // TODO: Validar que el monopatín está en una parada válida (GPS)


        Monopatin monopatin = monopatinService.findById(viaje.getIdMonopatin());

        if (!paradaService.estaDentroDeParada(
                idParadaDestino,
                monopatin.getLatitudActual(),
                monopatin.getLongitudActual())) {
            throw new RuntimeException("El monopatín no está dentro de la parada destino");
        }

        LocalDateTime horarioActual = LocalDateTime.now();
        long duracionTotalMinutos  = Duration.between(viaje.getFechaHoraInicio(), horarioActual).toMinutes();

        List<Pausa> pausas = viaje.getPausas();

        // Detectar si hubo pausa extendida (>15 min)
        boolean huboPausaExtendida = false;
        LocalDateTime momentoPausaExtendida = null;

        for (Pausa pausa : pausas) {
            if (pausa.getFechaHoraFinPausa() != null) { // Solo pausas finalizadas
                long duracionPausa = Duration.between(
                        pausa.getFechaHoraInicioPausa(),
                        pausa.getFechaHoraFinPausa()
                ).toMinutes();

                if (duracionPausa > 15) {
                    huboPausaExtendida = true;
                    momentoPausaExtendida = pausa.getFechaHoraInicioPausa().plusMinutes(15);
                    break; // Con una pausa extendida ya cambia toda la tarifa
                }
            }
        }

        TarifaDTO tarifa = obtenerTarifaActiva(); // Llamada HTTP a microservicio Tarifas

        double costoTotal;

        if (huboPausaExtendida) {
            // Minutos hasta que se activó la tarifa extendida (15 min después del inicio de pausa)
            long minutosNormales = Duration.between(viaje.getFechaHoraInicio(), momentoPausaExtendida).toMinutes();
            // Minutos restantes con tarifa extendida
            long minutosExtendidos = duracionTotalMinutos - minutosNormales;

            costoTotal = (minutosNormales * tarifa.getPrecioMinutoNormal())
                    + (minutosExtendidos * tarifa.getPrecioMinutoPausaExtendida());
        } else {
            // Todo el viaje a tarifa normal
            costoTotal = duracionTotalMinutos * tarifa.getPrecioMinutoNormal();
        }

        // Asignar el costo calculado al viaje
        viaje.setCostoTotal(costoTotal);

        // Registrar transacción en microservicio de Cuentas
        registrarTransaccion(viaje.getIdCuenta(), costoTotal, idViaje);

        // Actualizar estado del monopatín a DISPONIBLE
        monopatinService.cambiarEstado(viaje.getIdMonopatin(), "DISPONIBLE");

        // Finalizar viaje
        viaje.setFechaHoraFin(horarioActual); // Reusar variable
        viaje.setIdParadaDestino(idParadaDestino);
        viaje.setEstadoViaje(Viaje.EstadoViaje.FINALIZADO);

        return viajeRepository.save(viaje);
    }

    private void registrarTransaccion(long idCuenta, double costoTotal, long idViaje) {
        // TODO implementar
        System.out.println("Registrando transaccion...");
        System.out.println("idCuenta: " + idCuenta);
        System.out.println("costoTotal: " + costoTotal);
        System.out.println("idViaje: " + idViaje);
        System.out.println("Transaccion registrada de manera épica");
    }

    private TarifaDTO obtenerTarifaActiva() {
        TarifaDTO tarifa = new TarifaDTO();
        tarifa.setTarifaViaje(0);
        tarifa.setTarifaPausa(0);
        return tarifa;
    }

    public Pausa pausarViaje(Long idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        Pausa pausa = new Pausa();
        pausa.setViaje(viaje);
        pausa.setFechaHoraInicioPausa(LocalDateTime.now());

        viaje.setEstadoViaje(Viaje.EstadoViaje.PAUSADO);
        viajeRepository.save(viaje);

        return pausaRepository.save(pausa);
    }
}

class TarifaDTO {
    int tarifaPausa;
    int tarifaViaje;

    public TarifaDTO() {}

    public void setTarifaPausa(int tarifaPausa) {
        this.tarifaPausa = tarifaPausa;
    }

    public void setTarifaViaje(int tarifaViaje) {
        this.tarifaViaje = tarifaViaje;
    }

    public long getPrecioMinutoNormal() {
        return tarifaViaje / 60;
    }

    public long getPrecioMinutoPausaExtendida() {
        return tarifaViaje / 60;
    }
}