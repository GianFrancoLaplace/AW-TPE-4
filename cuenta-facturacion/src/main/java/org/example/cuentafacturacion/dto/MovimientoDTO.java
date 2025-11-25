package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalles de un movimiento")
public class MovimientoDTO {
    @Schema(description = "ID del movimiento", example = "1")
    private Long idCuenta;
    @Schema(description = "Monto del movimiento", example = "-250.5")
    private float monto;
    @Schema(description = "descripcion del movimiento", example = "descripcion")
    private String descripcion;
    @Schema(description = "referencia movimiento", example = "referencia")
    private String referencia;

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
