package org.example.tarifa.service;

import lombok.Data;
import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TarifaService {
    @Autowired
    private TarifaRepository tarifaRepository;

    public Tarifa insertTarifa(Tarifa t){
        tarifaRepository.save(t);
        return t;
    }

    public Tarifa updateTarifa(Long id, Tarifa datosNuevos) {
        Tarifa existente = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con ID " + id));

        existente.setDescripcion(datosNuevos.getDescripcion());
        existente.setPrecio_minuto_normal(datosNuevos.getPrecio_minuto_normal());
        existente.setPrecio_minuto_pausa_extendida(datosNuevos.getPrecio_minuto_pausa_extendida());
        existente.setFecha_vigencia_desde(datosNuevos.getFecha_vigencia_desde());
        existente.setActiva(datosNuevos.isActiva());

        return tarifaRepository.save(existente);
    }

    public void activarTarifa(Long id) {
        tarifaRepository.desactivarTodas();
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con ID " + id));
        tarifa.setActiva(true);
        tarifaRepository.save(tarifa);
    }

    public Tarifa getTarifaActiva() {
        return tarifaRepository.findByActivaTrue()
                .orElseThrow(() -> new RuntimeException("No hay tarifa activa"));
    }

    public List<Tarifa> getAllTarifas() {
        return tarifaRepository.findAll();
    }
}
