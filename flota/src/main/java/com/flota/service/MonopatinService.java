package com.flota.service;

import com.flota.entity.Monopatin;
import com.flota.entity.Parada;
import com.flota.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository repository;

    public Monopatin save(Monopatin m) {
        return repository.save(m); // CREATE/UPDATE
    }

    public List<Monopatin> findAll() {
        return repository.findAll(); // READ
    }

    public Monopatin findById(String id) {
        // Lanza excepción si no encuentra
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Monopatín no encontrado."));
    }

    public void deleteById(String id) {
        repository.deleteById(id); // DELETE
    }

    // Reporte por KMs para Mantenimiento
    public List<Monopatin> getMonopatinesParaMantenimiento(double umbralKm) {
        return repository.findAll().stream()
                .filter(m -> m.getKmTotalesAcumulados() > umbralKm)
                .toList();
    }

    public void cambiarEstado(String idMonopatin, String nuevoEstado) {
        Monopatin monopatin = findById(idMonopatin);
        monopatin.setEstado(Monopatin.EstadoMonopatin.valueOf(nuevoEstado));
        repository.save(monopatin);
    }

    public void actualizarUbicacion(String id, Double latitud, Double longitud) {
        Monopatin monopatin = findById(id);

        if (monopatin == null)
            throw new RuntimeException("Monopatin no encontrado.");

        monopatin.setLatitudActual(latitud);
        monopatin.setLongitudActual(longitud);

        repository.save(monopatin);
    }

    public void actualizarUso(String id, Double kmRecorridos, Integer minutosUsados) {
        Monopatin monopatin = findById(id);

        if (monopatin == null)
            throw new RuntimeException("Monopatin no encontrado.");

        double kmRecorridosTotal = monopatin.getKmTotalesAcumulados() + kmRecorridos;
        int minutosUsadosTotal = monopatin.getTiempoUsoTotalMinutos() + minutosUsados;

        monopatin.setKmTotalesAcumulados(kmRecorridosTotal);
        monopatin.setTiempoUsoTotalMinutos(minutosUsadosTotal);

        repository.save(monopatin);
    }

    /**
     * Busca monopatines cercanos a una ubicación dada dentro de un radio específico.
     * Según el TPE (punto 4.g): "Como usuario quiero buscar un listado de los monopatines
     * cercanos a mi zona, para poder encontrar un monopatín cerca de mi ubicación"
     * Debe calcular la distancia entre la ubicación dada y cada monopatín, y devolver
     * solo aquellos que estén dentro del radio especificado (en metros).
     * @param lat - Latitud del usuario
     * @param lon - Longitud del usuario
     * @param radioMetros - Radio de búsqueda en metros
     * @return Lista de monopatines dentro del radio
     */
    public List<Monopatin> buscarCercanos(Double lat, Double lon, Double radioMetros) {
        // Obtener todos los monopatines
        return repository.findAll().stream()
                .filter(monopatin -> {
                    // Validar que el monopatín tenga ubicación
                    if (monopatin.getLatitudActual() == null || monopatin.getLongitudActual() == null) {
                        return false;
                    }

                    // Calcular distancia usando Pitágoras
                    double distancia = calcularDistancia(
                            lat, lon,
                            monopatin.getLatitudActual(), monopatin.getLongitudActual()
                    );

                    // Incluir solo si está dentro del radio
                    return distancia <= radioMetros;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los monopatines que están en estado DISPONIBLE.
     * Útil para que los usuarios vean qué monopatines pueden usar.
     * @return Lista de monopatines con estado DISPONIBLE
     */
    public List<Monopatin> getMonopatinesDisponibles() {
        return repository.findAll().stream()
                .filter(monopatin -> monopatin.getEstado() == Monopatin.EstadoMonopatin.DISPONIBLE)
                .collect(Collectors.toList());
    }

    /**
     * Método auxiliar para calcular la distancia entre dos puntos geográficos usando Pitágoras.
     * Conversión aproximada:
     *   - 1 grado de latitud ≈ 111,000 metros (constante)
     *   - 1 grado de longitud ≈ 111,000 * cos(latitud) metros (varía según latitud)
     * @param lat1 - Latitud del primer punto
     * @param lon1 - Longitud del primer punto
     * @param lat2 - Latitud del segundo punto
     * @param lon2 - Longitud del segundo punto
     * @return Distancia en metros
     */
    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        // Calcular diferencias en grados
        double deltaLat = Math.abs(lat1 - lat2);
        double deltaLon = Math.abs(lon1 - lon2);

        // Convertir diferencias de latitud a metros
        double distanciaLatMetros = deltaLat * 111000;

        // Convertir diferencias de longitud a metros (ajustado por latitud)
        double distanciaLonMetros = deltaLon * 111000 * Math.cos(Math.toRadians(lat1));

        // Aplicar Pitágoras: distancia = √(x² + y²)
        return Math.sqrt(
                Math.pow(distanciaLatMetros, 2) + Math.pow(distanciaLonMetros, 2)
        );
    }

    public Monopatin update(String id, Monopatin monopatinActualizado) {
        return new Monopatin();
    }
}