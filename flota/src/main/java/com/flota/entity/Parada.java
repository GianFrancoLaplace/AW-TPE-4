package com.flota.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parada")
@Getter @Setter
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParada;

    private String nombre;
    private Double latitudCentro;
    private Double longitudCentro;
    private Double radioMetros; // Para definir el geofence
    private Boolean activa;

    public Parada() {}
}