package com.viajes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

@Entity
@Table(name = "pausa")
@Getter @Setter
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_viaje")
    private Viaje viaje;

    private LocalDateTime inicioPausa;
    private LocalDateTime finPausa;

    private boolean activa;
    private boolean extendida;

    public Pausa() {
    }


}
