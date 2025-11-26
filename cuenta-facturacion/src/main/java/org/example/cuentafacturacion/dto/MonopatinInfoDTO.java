package org.example.cuentafacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "informacion de monopatin")
public class MonopatinInfoDTO {
    @Schema(description = "ID del monopatin", example = "1")
    private String id;
    @Schema(description = "ubicacion del monopatin", example = "ubicacion")
    private String ubicacion;
    @Schema(description = "bateria del monopatin", example = "34.5")
    private double bateria;
}
