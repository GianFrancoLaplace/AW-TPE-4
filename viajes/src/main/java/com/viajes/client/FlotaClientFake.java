package com.viajes.client;

import org.springframework.stereotype.Component;

@Component
public class FlotaClientFake implements FlotaClient {

    @Override
    public boolean verificarDisponibilidad(String idMonopatin) {
        return true;
    }

    @Override
    public void marcarEnUso(String idMonopatin) {
        System.out.println("Monopatín " + idMonopatin + " marcado en uso");
    }

    @Override
    public void marcarComoDisponible(String idMonopatin) {
        System.out.println("Monopatín " + idMonopatin + " disponible nuevamente");
    }

    @Override
    public double obtenerKmRecorridos(String idMonopatin, String inicio, String fin) {
        return 3.5; // km simulados
    }

    @Override
    public boolean verificarUbicacionEnParada(String idMonopatin, Double lat, Double lon) {
        return true; // siempre válido
    }
}
