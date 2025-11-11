package com.flota.service;

import com.flota.entity.Parada;
import com.flota.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParadaService {

    @Autowired
    private ParadaRepository repository;

    public Parada save(Parada p) {
        return repository.save(p);
    }

    public List<Parada> findAll() {
        return repository.findAll();
    }

    public Parada findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parada no encontrada"));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean estaDentroDeParada(Long idParadaDestino, Double longitudActual, Double latitudActual) {
        Parada parada = findById(idParadaDestino);

        // Conversión aproximada de grados a metros
        // 1 grado de latitud ≈ 111,000 metros (constante en toda la Tierra)
        // 1 grado de longitud ≈ 111,000 * cos(latitud) metros (varía según latitud)

        double deltaLat = Math.abs(latitudActual - parada.getLatitudCentro());
        double deltaLon = Math.abs(longitudActual - parada.getLongitudCentro());

        // Convertir diferencias de grados a metros
        double distanciaLatMetros = deltaLat * 111000; // 1° lat ≈ 111 km
        double distanciaLonMetros = deltaLon * 111000 * Math.cos(Math.toRadians(parada.getLatitudCentro()));

        // Distancia aproximada
        double distanciaTotal = Math.sqrt(
                Math.pow(distanciaLatMetros, 2) + Math.pow(distanciaLonMetros, 2)
        );

        return distanciaTotal <= parada.getRadioMetros();
    }

    public Parada update(Long id, Parada paradaActualizada) {
        return new Parada();
    }

    public List<Parada> getParadasActivas() {
        return repository.findAll(); // TODO: Filtrar activas
    }

    public void cambiarEstado(Long id, Boolean activa) {
    }

    public List<Parada> buscarCercanas(Double lat, Double lon, Double radio) {
        return new ArrayList<>();
    }
}
