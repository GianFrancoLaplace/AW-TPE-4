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
    private int idCuenta;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @Enumerated(EnumType.STRING)
    private TipoCuenta categoria;

    @ManyToMany
    @JoinTable(
            name = "cuenta_usuario",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<int> usuarios = new HashSet<>();

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
