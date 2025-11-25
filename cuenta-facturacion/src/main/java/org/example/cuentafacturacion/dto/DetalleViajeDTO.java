package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * DTO para el detalle de un viaje individual en el reporte de uso.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detalle de un viaje")
public class DetalleViajeDTO {
    @Schema(description = "ID del viaje", example = "3")
    private Long idViaje;
    @Schema(description = "Fecha inicio del viaje", example = "2025-05-12 14:10:00")
    private String fechaInicio;
    @Schema(description = "Fecha fin del viaje", example = "2025-05-12 17:20:00")
    private String fechaFin;
    @Schema(description = "kilometros recorridos", example = "13.4")
    private float kmRecorridos;
    @Schema(description = "costo total del viaje", example = "3500")
    private float costoTotal;
}