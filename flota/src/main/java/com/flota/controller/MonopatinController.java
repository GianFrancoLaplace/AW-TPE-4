// Archivo: flota/src/main/java/com/arqweb/flota/controller/MonopatinController.java
package com.flota.controller;

import com.flota.entity.Monopatin;
import com.flota.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService service;

    @PostMapping
    public ResponseEntity<Monopatin> createMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevo = service.save(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    // READ: GET /monopatines/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> getMonopatinById(@PathVariable String id) {
        Monopatin monopatin = service.findById(id);
        return ResponseEntity.ok(monopatin);
    }

    // READ: GET /monopatines (ABM listado)
    @GetMapping
    public ResponseEntity<List<Monopatin>> getAllMonopatines() {
        return ResponseEntity.ok(service.findAll());
    }

    // Funcionalidad: Reporte
    // GET /monopatines/reporte-mantenimiento?umbralKm=500
    @GetMapping("/reporte-mantenimiento")
    public ResponseEntity<List<Monopatin>> getMantenimientoReport(@RequestParam double umbralKm) {
        List<Monopatin> aMantener = service.getMonopatinesParaMantenimiento(umbralKm);
        return ResponseEntity.ok(aMantener);
    }
}