package org.example.cuentafacturacion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Table(name = "cuenta")
@Getter
@Setter

public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @Enumerated(EnumType.STRING)
    private TipoCuenta categoria;

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
}
