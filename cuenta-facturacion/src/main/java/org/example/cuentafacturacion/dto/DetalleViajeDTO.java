package org.example.cuentafacturacion.dto;

import lombok.*;

/**
 * DTO para el detalle de un viaje individual en el reporte de uso.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetalleViajeDTO {
    private Long idViaje;
    private String fechaInicio;
    private String fechaFin;
    private float kmRecorridos;
    private float costoTotal;
}