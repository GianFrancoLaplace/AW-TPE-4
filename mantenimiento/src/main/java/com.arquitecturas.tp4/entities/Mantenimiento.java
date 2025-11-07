package com.arquitecturas.tp4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mantenimiento")
public class Mantenimiento {
    @Id
    private int id_mantenimiento;
    private Date fechaInicio;
    private Date fecha_fin;
    private int idEncargado;
    private int idMonopatin;
}
