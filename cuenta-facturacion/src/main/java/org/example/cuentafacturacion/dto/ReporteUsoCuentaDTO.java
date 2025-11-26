package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para el reporte de uso de una cuenta específica.
 * Incluye información de viajes realizados y consumo de saldo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "reporte uso de una cuenta específica")
public class ReporteUsoCuentaDTO {
    @Schema(description = "ID de la cuenta", example = "2")
    private Long idCuenta;
    @Schema(description = "saldo actual", example = "2000")
    private float saldoActual;
    @Schema(description = "total gastado", example = "15000")
    private float totalGastado;
    @Schema(description = "cantidad de viajes", example = "8")
    private int cantidadViajes;
    @Schema(description = "kilometros totales recorridos", example = "57.6")
    private float kmTotalesRecorridos;
    @Schema(description = "viajes", example = "{viajes}")
    private List<DetalleViajeDTO> viajes;
}