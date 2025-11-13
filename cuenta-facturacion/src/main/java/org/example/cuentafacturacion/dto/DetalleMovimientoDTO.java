package org.example.cuentafacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para el detalle de un movimiento individual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMovimientoDTO {
    private Long idMovimiento;
    private float monto;
    private String descripcion;
    private String fechaMovimiento;
    private String referencia;
}