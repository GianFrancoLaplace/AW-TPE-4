package com.flota.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa un monopatín eléctrico de la flota.
 * Almacena información de ubicación GPS, estado actual y métricas de uso.
 */
@Document(collection = "monopatines")
@Getter @Setter
public class Monopatin {

    @Id
    private String id;

    private EstadoMonopatin estado;
    private Double latitudActual;
    private Double longitudActual;
    private Double kmTotalesAcumulados = 0.0;
    private Integer tiempoUsoTotalMinutos = 0;

    public Monopatin() {
        this.estado = EstadoMonopatin.DISPONIBLE;
    }

    public enum EstadoMonopatin {
        DISPONIBLE,
        EN_USO,
        EN_MANTENIMIENTO,
        BATERIA_BAJA
    }
}