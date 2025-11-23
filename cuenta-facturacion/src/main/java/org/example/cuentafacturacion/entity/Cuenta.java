package org.example.cuentafacturacion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "cuenta")
@Data
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCuenta;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @Enumerated(EnumType.STRING)
    private TipoCuenta categoria;

    
    private String nombre;

    private int idMercadopago;

    private float saldoActual;

    private int cupoKMMes;

    private Date fechaRenovacionCupo;

    public enum EstadoCuenta {
        ANULADA, ACTIVA
    }

    public enum TipoCuenta {
        PREMIUM, BASICA
    }

    // Getters
    public Integer getIdCuenta() {
        return idCuenta;
    }
//
//    public EstadoCuenta getEstado() {
//        return estado;
//    }
//
//    public TipoCuenta getCategoria() {
//        return categoria;
//    }
//
//    public int getIdMercadopago() {
//        return idMercadopago;
//    }
//
//    public float getSaldoActual() {
//        return saldoActual;
//    }
//
//    public int getCupoKMMes() {
//        return cupoKMMes;
//    }
//
//    public Date getFechaRenovacionCupo() {
//        return fechaRenovacionCupo;
//    }

    // Setters
    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

//    public void setEstado(EstadoCuenta estado) {
//        this.estado = estado;
//    }
//
//    public void setCategoria(TipoCuenta categoria) {
//        this.categoria = categoria;
//    }
//
//    public void setIdMercadopago(int idMercadopago) {
//        this.idMercadopago = idMercadopago;
//    }
//
//    public void setSaldoActual(float saldoActual) {
//        this.saldoActual = saldoActual;
//    }
//
//    public void setCupoKMMes(int cupoKMMes) {
//        this.cupoKMMes = cupoKMMes;
//    }
//
//    public void setFechaRenovacionCupo(Date fechaRenovacionCupo) {
//        this.fechaRenovacionCupo = fechaRenovacionCupo;
//    }
}
