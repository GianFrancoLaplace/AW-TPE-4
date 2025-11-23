package com.flota.controller;

import com.flota.entity.Parada;
import com.flota.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    private ParadaService service;

    // ==================== ABM BÁSICO ====================

    // CREATE: POST /paradas
    @PostMapping
    public ResponseEntity<Parada> createParada(@RequestBody Parada parada) {
        return ResponseEntity.ok(service.save(parada));
    }

    // READ: GET /paradas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Parada> getParadaById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // READ: GET /paradas (listado completo)
    @GetMapping
    public ResponseEntity<List<Parada>> getAllParadas() {
        return ResponseEntity.ok(service.findAll());
    }

    // UPDATE: PUT /paradas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Parada> updateParada(
            @PathVariable String id,
            @RequestBody Parada paradaActualizada) {
        Parada updated = service.update(id, paradaActualizada);
        return ResponseEntity.ok(updated);
    }

    // DELETE: DELETE /paradas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== FUNCIONALIDADES ESPECÍFICAS ====================

    // Validar si una ubicación está dentro de una parada (para finalizar viaje)
    // GET /paradas/{id}/validar-ubicacion?lat=-37.3213&lon=-59.1234
    @GetMapping("/{id}/validar-ubicacion")
    public ResponseEntity<Map<String, Boolean>> validarUbicacion(
            @PathVariable String id,
            @RequestParam Double lat,
            @RequestParam Double lon) {
        boolean dentroDeParada = service.estaDentroDeParada(id, lon, lat);
        return ResponseEntity.ok(Map.of("dentroDeParada", dentroDeParada));
    }

    // Obtener paradas activas
    // GET /paradas/activas
    @GetMapping("/activas")
    public ResponseEntity<List<Parada>> getParadasActivas() {
        List<Parada> activas = service.getParadasActivas();
        return ResponseEntity.ok(activas);
    }

    // Activar/desactivar parada
    // PUT /paradas/{id}/estado
    // Body: { "activa": true }
    @PutMapping("/{id}/estado")
    public ResponseEntity<Parada> cambiarEstadoParada(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> body) {
        Boolean activa = body.get("activa");
        service.cambiarEstado(id, activa);
        return ResponseEntity.ok(service.findById(id));
    }

    // Buscar paradas cercanas a una ubicación
    // GET /paradas/cercanas?lat=-37.3213&lon=-59.1234&radio=500
    @GetMapping("/cercanas")
    public ResponseEntity<List<Parada>> getParadasCercanas(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "1000") Double radio) {
        List<Parada> cercanas = service.buscarCercanas(lat, lon, radio);
        return ResponseEntity.ok(cercanas);
    }
}