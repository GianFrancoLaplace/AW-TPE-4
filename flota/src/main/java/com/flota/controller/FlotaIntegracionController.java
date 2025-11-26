package com.flota.controller;

import com.flota.entity.Monopatin;
import com.flota.service.MonopatinService;
import com.flota.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flota")
public class FlotaIntegracionController {

    @Autowired
    private MonopatinService monopatinService;

    @Autowired
    private ParadaService paradaService;

    // 1. Verificar disponibilidad
    @GetMapping("/disponibilidad")
    public boolean verificarDisponibilidad(@RequestParam String idMonopatin) {
        try {
            Monopatin m = monopatinService.findById(idMonopatin);
            return m.getEstado() == Monopatin.EstadoMonopatin.DISPONIBLE;
        } catch (RuntimeException e) {
            return false;  // Si no existe, no está disponible
        }
    }

    // 2. Marcar en uso
    @PostMapping("/marcar-en-uso")
    public void marcarEnUso(@RequestParam String idMonopatin) {
        monopatinService.cambiarEstado(idMonopatin, "EN_USO");
    }

    // 3. Marcar disponible
    @PostMapping("/marcar-disponible")
    public void marcarComoDisponible(@RequestParam String idMonopatin) {
        monopatinService.cambiarEstado(idMonopatin, "DISPONIBLE");
    }

    // 4. Verificar parada
    @GetMapping("/verificar-parada")
    public boolean verificarUbicacionEnParada(
            @RequestParam String idParada,  // Cambiar lógica en viajes
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        return paradaService.estaDentroDeParada(idParada, longitud, latitud);
    }

    // 5. KM recorridos - retorna total acumulado (no por rango)
    @GetMapping("/km-recorridos")
    public double obtenerKmRecorridos(@RequestParam String idMonopatin) {
        Monopatin m = monopatinService.findById(idMonopatin);
        return m.getKmTotalesAcumulados();
    }
}
