package org.example.microservicioidentidadyauth.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String contrasenia;
}