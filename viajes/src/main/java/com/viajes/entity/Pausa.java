package com.viajes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "pausa")
@Getter @Setter
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPausa;

    @ManyToOne
    @JoinColumn(name = "id_viaje")
    private Viaje viaje;

    private LocalDateTime fechaHoraInicioPausa;
    private LocalDateTime fechaHoraFinPausa; // Nulo si está activa

    public Pausa() {}
}
