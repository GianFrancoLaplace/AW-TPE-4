package com.viajes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cuenta-service", url = "${cuenta-service.url}")
public interface CuentaClient {

    @GetMapping("/api/cuenta/verificarCuentaHabilitada/{idCuenta}")
    boolean verificarCuentaHabilitada(@PathVariable("idCuenta") int idCuenta);
}
