package com.flota.service;

import com.flota.entity.Parada;
import com.flota.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Actualiza los datos de una parada existente.
     * Permite modificar nombre, ubicación, radio o estado activo de la parada.
     *
     * @param id - ID de la parada a actualizar
     * @param paradaActualizada - Objeto con los nuevos datos
     * @return Parada actualizada
     * @throws RuntimeException si la parada no existe
     */
    public Parada update(Long id, Parada paradaActualizada) {
        Parada existente = findById(id);

        // Actualizar solo campos no nulos
        if (paradaActualizada.getNombre() != null) {
            existente.setNombre(paradaActualizada.getNombre());
        }
        if (paradaActualizada.getLatitudCentro() != null) {
            existente.setLatitudCentro(paradaActualizada.getLatitudCentro());
        }
        if (paradaActualizada.getLongitudCentro() != null) {
            existente.setLongitudCentro(paradaActualizada.getLongitudCentro());
        }
        if (paradaActualizada.getRadioMetros() != null) {
            existente.setRadioMetros(paradaActualizada.getRadioMetros());
        }
        if (paradaActualizada.getActiva() != null) {
            existente.setActiva(paradaActualizada.getActiva());
        }

        return repository.save(existente);
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

    /**
     * Busca paradas cercanas a una ubicación dentro de un radio específico.
     * Útil para que usuarios vean dónde pueden dejar el monopatín al finalizar viaje.
     *
     * @param lat - Latitud del usuario
     * @param lon - Longitud del usuario
     * @param radio - Radio de búsqueda en metros
     * @return Lista de paradas dentro del radio
     */
    public List<Parada> buscarCercanas(Double lat, Double lon, Double radio) {
        return repository.findAll().stream()
                .filter(parada -> {
                    // Validar que la parada tenga ubicación configurada
                    if (parada.getLatitudCentro() == null || parada.getLongitudCentro() == null) {
                        return false;
                    }

                    // Calcular distancia usando el mismo método que monopatines
                    double distancia = calcularDistancia(
                            lat, lon,
                            parada.getLatitudCentro(), parada.getLongitudCentro()
                    );

                    // Incluir solo si está dentro del radio
                    return distancia <= radio;
                })
                .collect(Collectors.toList());
    }

    /**
     * Calcula la distancia aproximada entre dos puntos geográficos usando Pitágoras.
     * Conversión aproximada:
     *   - 1 grado de latitud ≈ 111,000 metros (constante)
     *   - 1 grado de longitud ≈ 111,000 * cos(latitud) metros (varía según latitud)
     *
     * @param lat1 - Latitud del primer punto
     * @param lon1 - Longitud del primer punto
     * @param lat2 - Latitud del segundo punto
     * @param lon2 - Longitud del segundo punto
     * @return Distancia en metros
     */
    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        double deltaLat = Math.abs(lat1 - lat2);
        double deltaLon = Math.abs(lon1 - lon2);

        double distanciaLatMetros = deltaLat * 111000;
        double distanciaLonMetros = deltaLon * 111000 * Math.cos(Math.toRadians(lat1));

        return Math.sqrt(
                Math.pow(distanciaLatMetros, 2) + Math.pow(distanciaLonMetros, 2)
        );
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
}
