package org.example.tarifa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarifa")
public class Tarifa {
    @Id
    private int id_tarifa;
    private String descripcion;
    private int precio_minuto_normal;
    private int precio_minuto_pausa_extendida;
    private Date fecha_vigencia_desde;
    private boolean activa;
}
