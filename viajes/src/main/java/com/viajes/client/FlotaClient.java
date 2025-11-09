package com.viajes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "flota-service", url = "http://localhost:8082") // Cambiar URL según despliegue
public interface FlotaClient {

    @GetMapping("/flota/disponibilidad")
    boolean verificarDisponibilidad(@RequestParam("idMonopatin") String idMonopatin);

    @PostMapping("/flota/marcar-en-uso")
    void marcarEnUso(@RequestParam("idMonopatin") String idMonopatin);

    @PostMapping("/flota/marcar-disponible")
    void marcarComoDisponible(@RequestParam("idMonopatin") String idMonopatin);

    @GetMapping("/flota/verificar-parada")
    boolean verificarUbicacionEnParada(
            @RequestParam("idMonopatin") String idMonopatin,
            @RequestParam("latitud") Double latitud,
            @RequestParam("longitud") Double longitud
    );

    @GetMapping("/flota/km-recorridos")
    double obtenerKmRecorridos(
            @RequestParam("idMonopatin") String idMonopatin,
            @RequestParam("inicio") String inicio,
            @RequestParam("fin") String fin
    );
}
