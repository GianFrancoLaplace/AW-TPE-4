package com.viajes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime; // Usar LocalDateTime o Instant para fechas

@Entity
@Table(name = "viaje")
@Getter @Setter
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaje;

    private String idMonopatin; // FK lógica, no JPA/SQL
    private Long idUsuario;     // FK lógica
    private Long idCuenta;      // FK lógica

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin; // Nulo si está en curso

    private Double kmRecorridos;
    private Long idParadaOrigen; // FK lógica
    private Long idParadaDestino; // FK lógica

    private Double costoTotal;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    private List<Pausa> pausas;

    // ... Constructor y métodos
}