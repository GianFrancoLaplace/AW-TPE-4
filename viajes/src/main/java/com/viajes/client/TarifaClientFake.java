package com.viajes.client;

import org.springframework.stereotype.Component;

@Component
public class TarifaClientFake implements TarifaClient {

    // Ajuste: coincidir exactamente con la interfaz
    @Override
    public void calcularCosto(Long idViaje, Long idCuenta, Long duracionMinutos, boolean tienePausaExcesiva) {
        System.out.println("Costo calculado para viaje " + idViaje);
    }
}
