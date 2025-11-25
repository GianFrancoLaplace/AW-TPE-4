package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.dto.MovimientoDTO;
import org.example.cuentafacturacion.dto.ReporteFacturacionDTO;
import org.example.cuentafacturacion.dto.ReporteSaldoMovimientosDTO;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/movimientos")
@Tag(name = "Movimientos", description = "Gestión de movimientos financieros y reportes")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Operation(
            summary = "Registrar movimiento",
            description = "Registra un movimiento financiero y actualiza el saldo de la cuenta"
    )
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
    @Operation(
            summary = "Reporte de facturación general",
            description = "Devuelve el total facturado y cantidad de transacciones en un período"
    )
    @GetMapping("/reporte-facturacion")
    public ResponseEntity<ReporteFacturacionDTO> obtenerReporteFacturacion(
            @Parameter(description = "Fecha inicio (YYYY-MM-DD)", example = "2025-01-01")
            @RequestParam String fechaInicio,
            @Parameter(description = "Fecha fin (YYYY-MM-DD)", example = "2025-12-31")
            @RequestParam String fechaFin) {

        ReporteFacturacionDTO reporte = movimientoService.obtenerReporteFacturacion(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    /**
     * GET /api/movimientos/reporte-saldo/{idCuenta}?fechaInicio=2025-01-01&fechaFin=2025-12-31
     */
    @Operation(
            summary = "Reporte de saldo y movimientos de una cuenta",
            description = "Devuelve ingresos, egresos y detalle de movimientos"
    )
    @GetMapping("/reporte-saldo/{idCuenta}")
    public ResponseEntity<ReporteSaldoMovimientosDTO> obtenerReporteSaldo(
            @Parameter(description = "ID de la cuenta", example = "1")
            @PathVariable Long idCuenta,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        ReporteSaldoMovimientosDTO reporte = movimientoService.obtenerReporteSaldoMovimientos(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }
}
