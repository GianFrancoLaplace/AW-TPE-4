package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.dto.MovimientoDTO;
import org.example.cuentafacturacion.dto.ReporteFacturacionDTO;
import org.example.cuentafacturacion.dto.ReporteSaldoMovimientosDTO;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * GET /api/movimientos/reporte-facturacion?fechaInicio=2025-01-01&fechaFin=2025-12-31
     */
    @GetMapping("/reporte-facturacion")
    public ResponseEntity<ReporteFacturacionDTO> obtenerReporteFacturacion(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        ReporteFacturacionDTO reporte = movimientoService.obtenerReporteFacturacion(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    /**
     * GET /api/movimientos/reporte-saldo/{idCuenta}?fechaInicio=2025-01-01&fechaFin=2025-12-31
     */
    @GetMapping("/reporte-saldo/{idCuenta}")
    public ResponseEntity<ReporteSaldoMovimientosDTO> obtenerReporteSaldo(
            @PathVariable Long idCuenta,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        ReporteSaldoMovimientosDTO reporte = movimientoService.obtenerReporteSaldoMovimientos(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
