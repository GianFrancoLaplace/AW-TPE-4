package com.flota.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "monopatin")
@Getter @Setter
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMonopatin;

    @Enumerated(EnumType.STRING)
    private EstadoMonopatin estado;

    private Double latitudActual;
    private Double longitudActual;

    private Double kmTotalesAcumulados = 0.0;
    private Integer tiempoUsoTotalMinutos = 0;

    public Monopatin() {}

    public enum EstadoMonopatin {
        DISPONIBLE, EN_USO, EN_MANTENIMIENTO, BATERIA_BAJA
    }
}