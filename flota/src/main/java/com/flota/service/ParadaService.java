package com.flota.service;

import com.flota.entity.Parada;
import com.flota.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Obtiene todas las paradas que están activas (disponibles para uso).
     * Los usuarios solo deben ver paradas activas en el mapa de la app.
     *
     * @return Lista de paradas con activa = true
     */
    public List<Parada> getParadasActivas() {
        return repository.findAll().stream()
                .filter(parada -> parada.getActiva())
                .collect(Collectors.toList());
    }

    /**
     * Cambia el estado activo/inactivo de una parada.
     * Según el TPE: el administrador puede deshabilitar paradas temporalmente
     * cuando sea necesario (ej: mantenimiento de la zona).
     *
     * @param id - ID de la parada a modificar
     * @param activa - true para activar, false para desactivar
     */
    public void cambiarEstado(Long id, Boolean activa) {
        Parada parada = findById(id);
        parada.setActiva(activa);
        repository.save(parada);
    }

    public List<Parada> buscarCercanas(Double lat, Double lon, Double radio) {
        return new ArrayList<>();
    }
}
