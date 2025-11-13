/*
package org.example.cuentafacturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @ManyToOne
    @JoinColumn(name = "idCuenta", nullable = false)
    private Cuenta cuenta;

    private float monto;

    private String descripcion;

    private LocalDateTime fechaMovimiento;

    private String referencia;

    public Movimiento() {}

    public Movimiento(Cuenta cuentaNoEncontrada, float monto, String s, String s1, LocalDateTime now) {
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public float getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}

 */
