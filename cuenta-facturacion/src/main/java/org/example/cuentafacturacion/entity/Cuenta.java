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

    // --- CAMBIO AQUÍ ---
    // En Java se llama "categoria", pero en la DB es "categoria_cuenta"
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_cuenta")
    private TipoCuenta categoria;

    private String nombre;

    private int idMercadopago;

    private float saldoActual;


    @Column(name = "cupo_km_mes")
    private int cupoKMMes;

    private Date fechaRenovacionCupo;

    public enum EstadoCuenta {
        ANULADA, ACTIVA
    }

    public enum TipoCuenta {
        PREMIUM, BASICA
    }
}