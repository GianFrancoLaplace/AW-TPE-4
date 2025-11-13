/*
package org.example.cuentafacturacion.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.cuentafacturacion.dto.MovimientoDTO;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody MovimientoDTO request) {
        Movimiento movimiento = movimientoService.registrarMovimiento(
                request.getIdCuenta(),
                request.getMonto(),
                request.getDescripcion(),
                request.getReferencia()
        );
        return ResponseEntity.ok(movimiento);
    }
}
*/