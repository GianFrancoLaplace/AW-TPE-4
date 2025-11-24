package com.flota.hook;

import com.flota.DTO.EstadoCuentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cuenta-service", url = "http://localhost:8085")
public interface CuentaCliente {
    @GetMapping("/api/cuenta/{id}")
    EstadoCuentaDTO obtenerCuenta(@PathVariable("id") int idCuenta);
}
