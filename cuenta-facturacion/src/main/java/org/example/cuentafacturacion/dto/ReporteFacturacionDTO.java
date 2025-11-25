package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "reporte facturacion")
public class ReporteFacturacionDTO {
    @Schema(description = "total facturado", example = "15000")
    private float totalFacturado;
    @Schema(description = "cantidad de transacciones", example = "20")
    private int cantidadTransacciones;
    @Schema(description = "periodo inicio", example = "periodo inicio")
    private String periodoInicio;
    @Schema(description = "periodo fin", example = "periodo fin")
    private String periodoFin;
}