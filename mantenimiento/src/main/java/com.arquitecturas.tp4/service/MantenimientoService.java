package com.arquitecturas.tp4.service;

import com.arquitecturas.tp4.entities.Mantenimiento;
import com.arquitecturas.tp4.repository.MantenimientoRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data


public class MantenimientoService {
    @Autowired
    private MantenimientoRepository mantenimientoRepository;


    public Mantenimiento addMantenimiento(Mantenimiento mantenimiento){
        mantenimientoRepository.save(mantenimiento);
        return mantenimiento;
    }

    public void terminarMantiniminento(int id){
        Mantenimiento mantenimientoXTerminar = mantenimientoRepository.findByIdMonopatin(id);
        if (mantenimientoXTerminar != null){
           mantenimientoRepository.updateDate(mantenimientoXTerminar.getId_mantenimiento());
        }
    }
}
