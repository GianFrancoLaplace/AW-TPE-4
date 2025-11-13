package org.example.cuentafacturacion.dto;

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
public class ReporteUsoCuentaDTO {
    private Long idCuenta;
    private float saldoActual;
    private float totalGastado;
    private int cantidadViajes;
    private float kmTotalesRecorridos;
    private List<DetalleViajeDTO> viajes;
}