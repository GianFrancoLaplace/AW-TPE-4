package com.viajes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viaje")
@Getter @Setter
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaje;

    private String idMonopatin; // FK lógica
    private Long idUsuario;     // FK lógica
    private Long idCuenta;      // FK lógica

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    private Double kmRecorridos = 0.0;
    private Long idParadaOrigen;
    private Long idParadaDestino;

    private Double costoTotal = 0.0;

    @Enumerated(EnumType.STRING)
    private EstadoViaje estadoViaje;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    private List<Pausa> pausas;

    public Viaje() {}

    public enum EstadoViaje {
        EN_CURSO, PAUSADO, FINALIZADO
    }
}