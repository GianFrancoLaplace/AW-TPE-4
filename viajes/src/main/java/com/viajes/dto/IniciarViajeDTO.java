package com.viajes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IniciarViajeDTO {
    private Long idUsuario;
    private Long idCuenta;
    private String idMonopatin;
}