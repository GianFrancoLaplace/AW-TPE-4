package org.example.cuentafacturacion.dto;


import io.swagger.v3.oas.annotations.media.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para el detalle de un movimiento individual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle de un movimiento financiero realizado sobre una cuenta")
public class DetalleMovimientoDTO {
    @Schema(description = "ID del movimiento", example = "15")
    private Long idMovimiento;
    @Schema(description = "Monto del movimiento (positivo carga, negativo gasto)", example = "-250.5")
    private float monto;
    @Schema(description = "Descripción del movimiento", example = "Cobro por viaje")
    private String descripcion;
    @Schema(description = "Fecha del movimiento", example = "2025-05-12 14:30:00")
    private String fechaMovimiento;
    @Schema(description = "Referencia externa del movimiento", example = "VIAJE-1234")
    private String referencia;
}