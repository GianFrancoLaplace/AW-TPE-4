package com.viajes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InicioViajeRequest {
    private String idMonopatin;
    private Long idUsuario;
    private Long idCuenta;
    private Long idParadaOrigen;

    public InicioViajeRequest() {}
}