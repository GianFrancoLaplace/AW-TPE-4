package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "reporte detalle completo de transacciones en un período")
public class ReporteSaldoMovimientosDTO {
    @Schema(description = "ID de la cuenta", example = "1")
    private Long idCuenta;
    @Schema(description = "saldo actual", example = "240.5")
    private float saldoActual;
    @Schema(description = "ingresos totales", example = "13000")
    private float totalIngresos;
    @Schema(description = "egresos totales", example = "11000")
    private float totalEgresos;
    @Schema(description = "movimientos", example = "{movimientos}")
    private List<DetalleMovimientoDTO> movimientos;
}