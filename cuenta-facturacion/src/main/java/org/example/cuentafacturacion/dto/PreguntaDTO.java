package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "pregunta")
public class PreguntaDTO {
    @Schema(description = "pregunta", example = "pregunta")
    private String pregunta;

    // Getters y Setters
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }
}