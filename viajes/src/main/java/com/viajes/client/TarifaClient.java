package com.viajes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tarifa-service", url = "http://localhost:8086") // Cambiar URL según despliegue
public interface TarifaClient {

    @PostMapping("/tarifa/calcular")
    void calcularCosto(
            @RequestParam("idViaje") Long idViaje,
            @RequestParam("idCuenta") Long idCuenta,
            @RequestParam("duracionMinutos") Long duracionMinutos,
            @RequestParam("tienePausaExcesiva") boolean tienePausaExcesiva
    );
}
