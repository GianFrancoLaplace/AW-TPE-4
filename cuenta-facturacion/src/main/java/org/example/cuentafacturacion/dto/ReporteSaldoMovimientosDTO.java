package org.example.cuentafacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para el reporte de saldo y movimientos de una cuenta.
 * Incluye el detalle completo de transacciones en un período.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteSaldoMovimientosDTO {
    private Long idCuenta;
    private float saldoActual;
    private float totalIngresos;
    private float totalEgresos;
    private List<DetalleMovimientoDTO> movimientos;
}