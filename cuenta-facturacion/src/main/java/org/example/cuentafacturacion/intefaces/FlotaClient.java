package org.example.cuentafacturacion.intefaces;

import org.example.cuentafacturacion.dto.MonopatinInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "flota-service", url = "http://localhost:8086")
public interface FlotaClient {
    @GetMapping("monopatines/disponibles")
    List<MonopatinInfoDTO> obtenerMonopatines();

    @GetMapping("monopatines/reporte-mantenimiento")
    List<MonopatinInfoDTO> obtenerMonopatinesEnMantenimiento();
}
