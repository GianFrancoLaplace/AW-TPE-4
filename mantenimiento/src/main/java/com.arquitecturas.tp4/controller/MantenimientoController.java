package com.arquitecturas.tp4.controller;
import com.arquitecturas.tp4.entities.Mantenimiento;
import com.arquitecturas.tp4.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/mantenimiento")

public class MantenimientoController {
@Autowired
    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @PostMapping()
    public ResponseEntity<Mantenimiento> insertMantenimiento(@RequestBody Mantenimiento mantenimiento){
        Mantenimiento mantenimientoNuevo = mantenimientoService.addMantenimiento(mantenimiento);
        return ResponseEntity.ok(mantenimientoNuevo);
    }

    public void updateMantenimiento(@RequestBody int monopatinId){
        mantenimientoService.terminarMantiniminento(monopatinId);
    }
}

