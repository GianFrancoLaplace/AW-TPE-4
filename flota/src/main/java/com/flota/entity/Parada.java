package com.flota.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una parada física donde los usuarios pueden tomar o dejar monopatines.
 * Define un área circular (centro + radio).
 */
@Document(collection = "paradas")
@Getter @Setter
public class Parada {

    @Id
    private String id;

    private String nombre;
    private Double latitudCentro;
    private Double longitudCentro;
    private Double radioMetros;
    private Boolean activa;

    public Parada() {
        this.activa = true;
        this.radioMetros = 50.0;
    }
}