package org.example.microservicioidentidadyauth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private String telefono;
//    private Enum rol;
//    private Cuenta cuenta;
}

