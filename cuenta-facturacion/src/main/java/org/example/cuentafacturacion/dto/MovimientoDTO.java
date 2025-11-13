package org.example.cuentafacturacion.dto;

import lombok.Data;

@Data
public class MovimientoDTO {
    private Long idCuenta;
    private float monto;
    private String descripcion;
    private String referencia;
}
