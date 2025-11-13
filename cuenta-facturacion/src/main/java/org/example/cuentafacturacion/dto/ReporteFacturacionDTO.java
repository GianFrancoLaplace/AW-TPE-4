package org.example.cuentafacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para el reporte de facturación total en un rango de fechas.
 * Incluye el monto total facturado y el detalle de transacciones.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteFacturacionDTO {
    private float totalFacturado;
    private int cantidadTransacciones;
    private String periodoInicio;
    private String periodoFin;
}