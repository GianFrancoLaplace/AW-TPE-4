package com.viajes.client;

import org.springframework.stereotype.Component;

@Component
public class CuentaClientFake implements CuentaClient {

    @Override
    public boolean verificarCuentaHabilitada(Long idCuenta) {
        return true;
    }
}
