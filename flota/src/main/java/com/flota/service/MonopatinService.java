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

    @Autowired
    private ParadaService paradaService;

    public Monopatin save(Monopatin m) {
        return repository.save(m);
    }

    public List<Monopatin> findAll() {
        return repository.findAll();
    }

    public Monopatin findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Monopatín no encontrado."));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Monopatin> getMonopatinesParaMantenimiento(double umbralKm) {
        return repository.findAll().stream()
                .filter(m -> m.getKmTotalesAcumulados() > umbralKm)
                .toList();
    }

    public void cambiarEstado(Long idMonopatin, String nuevoEstado) {
        Monopatin monopatin = findById(idMonopatin);
        monopatin.setEstado(Monopatin.EstadoMonopatin.valueOf(nuevoEstado));
        repository.save(monopatin);
    }

    public void actualizarUbicacion(Long id, Double latitud, Double longitud) {
        Monopatin monopatin = findById(id);

        if (monopatin == null)
            throw new RuntimeException("Monopatin no encontrado.");

        monopatin.setLatitudActual(latitud);
        monopatin.setLongitudActual(longitud);

        repository.save(monopatin);
    }

    public void actualizarUso(Long id, Double kmRecorridos, Integer minutosUsados) {
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
     *
     * @param lat         - Latitud del usuario
     * @param lon         - Longitud del usuario
     * @param radioMetros - Radio de búsqueda en metros
     * @return Lista de monopatines dentro del radio
     */
    public List<Monopatin> buscarCercanos(Double lat, Double lon, Double radioMetros) {
        return repository.findAll().stream()
                .filter(monopatin -> {
                    if (monopatin.getLatitudActual() == null || monopatin.getLongitudActual() == null) {
                        return false;
                    }

                    double distancia = calcularDistancia(
                            lat, lon,
                            monopatin.getLatitudActual(), monopatin.getLongitudActual()
                    );

                    return distancia <= radioMetros;
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los monopatines que están en estado DISPONIBLE.
     * Útil para que los usuarios vean qué monopatines pueden usar.
     *
     * @return Lista de monopatines con estado DISPONIBLE
     */
    public List<Monopatin> getMonopatinesDisponibles() {
        return repository.findAll().stream()
                .filter(monopatin -> monopatin.getEstado() == Monopatin.EstadoMonopatin.DISPONIBLE)
                .collect(Collectors.toList());
    }

    private double calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {
        double deltaLat = Math.abs(lat1 - lat2);
        double deltaLon = Math.abs(lon1 - lon2);

        double distanciaLatMetros = deltaLat * 111000;
        double distanciaLonMetros = deltaLon * 111000 * Math.cos(Math.toRadians(lat1));

        return Math.sqrt(
                Math.pow(distanciaLatMetros, 2) + Math.pow(distanciaLonMetros, 2)
        );
    }

    /**
     * Actualiza los datos de un monopatín existente.
     * Permite modificar cualquier campo del monopatín (estado, ubicación, etc.)
     * manteniendo su ID original.
     *
     * @param id                   - ID del monopatín a actualizar
     * @param monopatinActualizado - Objeto con los nuevos datos
     * @return Monopatín actualizado
     * @throws RuntimeException si el monopatín no existe
     */
    public Monopatin update(long id, Monopatin monopatinActualizado) {
        Monopatin existente = findById(id);

        if (monopatinActualizado.getEstado() != null) {
            existente.setEstado(monopatinActualizado.getEstado());
        }
        if (monopatinActualizado.getLatitudActual() != null) {
            existente.setLatitudActual(monopatinActualizado.getLatitudActual());
        }
        if (monopatinActualizado.getLongitudActual() != null) {
            existente.setLongitudActual(monopatinActualizado.getLongitudActual());
        }
        if (monopatinActualizado.getKmTotalesAcumulados() != null) {
            existente.setKmTotalesAcumulados(monopatinActualizado.getKmTotalesAcumulados());
        }
        if (monopatinActualizado.getTiempoUsoTotalMinutos() != null) {
            existente.setTiempoUsoTotalMinutos(monopatinActualizado.getTiempoUsoTotalMinutos());
        }

        return repository.save(existente);
    }

    /**
     * Ubica un monopatín en una parada específica.
     * Actualiza las coordenadas del monopatín a las de la parada.
     * Útil cuando se devuelve un monopatín a una parada o después de mantenimiento.
     *
     * @param idMonopatin - ID del monopatín
     * @param idParada - ID de la parada donde se ubicará
     * @return Monopatín con ubicación actualizada
     */
    public Monopatin ubicarEnParada(Long idMonopatin, Long idParada) {
        Monopatin monopatin = findById(idMonopatin);
        Parada parada = paradaService.findById(idParada);

        monopatin.setLatitudActual(parada.getLatitudCentro());
        monopatin.setLongitudActual(parada.getLongitudCentro());

        return repository.save(monopatin);
    }
}