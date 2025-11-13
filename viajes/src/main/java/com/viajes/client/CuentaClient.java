package com.viajes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cuenta-service", url = "http://localhost:8085") // Cambiar URL según despliegue
public interface CuentaClient {

    @GetMapping("/cuentas/verificar-habilitada")
    boolean verificarCuentaHabilitada(@RequestParam("idCuenta") Long idCuenta);
}
