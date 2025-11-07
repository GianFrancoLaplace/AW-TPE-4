package com.flota.service;

import com.flota.entity.Monopatin;
import com.flota.repository.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        // Lanza excepción si no encuentra (práctica recomendada)
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Monopatín no encontrado."));
    }

    public void deleteById(String id) {
        repository.deleteById(id); // DELETE
    }

    // Funcionalidad clave: Reporte por KMs para Mantenimiento [cite: 109]
    public List<Monopatin> getMonopatinesParaMantenimiento(double umbralKm) {
        // En el mundo real, esto sería una consulta compleja; aquí lo simplificamos:
        return repository.findAll().stream()
                .filter(m -> m.getKmTotalesAcumulados() > umbralKm)
                .toList();
    }

    public void cambiarEstado(String idMonopatin, String disponible) {
    }
}