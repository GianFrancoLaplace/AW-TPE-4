package org.example.microservicioidentidadyauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private String telefono;
}