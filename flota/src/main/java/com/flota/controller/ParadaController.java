package com.flota.controller;

import com.flota.entity.Parada;
import com.flota.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    private ParadaService service;

    @PostMapping
    public ResponseEntity<Parada> createParada(@RequestBody Parada parada) {
        return ResponseEntity.ok(service.save(parada));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Parada>> getAllParadas() {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}