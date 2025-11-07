package com.flota.service;

import com.flota.entity.Parada;
import com.flota.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public boolean estaDentroDeParada(long idParadaDestion, double longitudActual, double latitudActual) {
        return true; // TODO implementar
    }

    // TODO: Implementar método que verifique si un punto (lat, lon)
    // está dentro del radio de una parada (cálculo de distancia geográfica)
    // public boolean estaDentroDeParada(Long idParada, Double lat, Double lon) { ... }
}
