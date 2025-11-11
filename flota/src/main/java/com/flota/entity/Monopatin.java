package com.flota.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "monopatin")
@Getter @Setter // Anotación de Lombok para generar Getters y Setters
public class Monopatin {
    @Id
    // El TPE no especifica si es String o INT, usaremos String (ID unívoco)
    private String idMonopatin;

    @Enumerated(EnumType.STRING)
    private EstadoMonopatin estado; // 'disponible', 'en_uso', 'en_mantenimiento'

    private Double latitudActual;
    private Double longitudActual;

    // Para reportes de mantenimiento
    private Double kmTotalesAcumulados = 0.0;
    private Integer tiempoUsoTotalMinutos = 0;

    // Constructor vacío requerido por JPA
    public Monopatin() {}

    // Enum para tipos de estados, más seguro que un String
    public enum EstadoMonopatin {
        DISPONIBLE, EN_USO, EN_MANTENIMIENTO, BATERIA_BAJA
    }
}