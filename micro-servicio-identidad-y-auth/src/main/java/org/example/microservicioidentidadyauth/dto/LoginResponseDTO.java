package org.example.microservicioidentidadyauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// LoginResponse.java
@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private Long id;
    private String email;
    private String nombre;
}