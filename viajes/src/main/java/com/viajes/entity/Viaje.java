package com.viajes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

@Entity
@Table(name = "viaje")
@Getter
@Setter
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idMonopatin; // FK lógica
    private Long idUsuario;     // FK lógica
    private Long idCuenta;      // FK lógica

    private LocalDateTime inicio;
    private LocalDateTime fin;

    private Integer minutosTotales;
    private Integer minutosPausa;

    private Double kmRecorridos;
    private Double costoTotal;

    private boolean pausaExtendida;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estadoViaje;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    private List<Pausa> pausas = new ArrayList<>();


    public Viaje() {
    }


    public enum EstadoViaje {
        EN_CURSO,
        PAUSADO,
        FINALIZADO
    }
}