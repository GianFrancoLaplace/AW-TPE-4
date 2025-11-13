package com.flota.controller;

import com.flota.entity.Monopatin;
import com.flota.service.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService service;

    // ==================== ABM BÁSICO ====================

    // CREATE: POST /monopatines
    @PostMapping
    public ResponseEntity<Monopatin> createMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevo = service.save(monopatin);
        return ResponseEntity.ok(nuevo);
    }

    // READ: GET /monopatines/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> getMonopatinById(@PathVariable Long id) {
        Monopatin monopatin = service.findById(id);
        return ResponseEntity.ok(monopatin);
    }

    // READ: GET /monopatines (listado completo)
    @GetMapping
    public ResponseEntity<List<Monopatin>> getAllMonopatines() {
        return ResponseEntity.ok(service.findAll());
    }

    // UPDATE: PUT /monopatines/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> updateMonopatin(
            @PathVariable Long id,
            @RequestBody Monopatin monopatinActualizado) {
        Monopatin updated = service.update(id, monopatinActualizado);
        return ResponseEntity.ok(updated);
    }

    // DELETE: DELETE /monopatines/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonopatin(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== FUNCIONALIDADES ESPECÍFICAS ====================

    // Cambiar estado del monopatín
    // PUT /monopatines/{id}/estado
    // Body: { "nuevoEstado": "EN_MANTENIMIENTO" }
    @PutMapping("/{id}/estado")
    public ResponseEntity<Monopatin> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        String nuevoEstado = body.get("nuevoEstado");
        service.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.ok(service.findById(id));
    }

    // Actualizar ubicación GPS (lo llama el monopatín o el servicio de Viajes)
    // PUT /monopatines/{id}/ubicacion
    // Body: { "latitud": -37.3213, "longitud": -59.1234 }
    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<Monopatin> actualizarUbicacion(
            @PathVariable Long id,
            @RequestBody Map<String, Double> ubicacion) {
        Double latitud = ubicacion.get("latitud");
        Double longitud = ubicacion.get("longitud");
        Monopatin actualizado = service.actualizarUbicacion(id, latitud, longitud);
        return ResponseEntity.ok(actualizado);
    }

    // Actualizar km y tiempo de uso (lo llama el servicio de Viajes al finalizar)
    // PUT /monopatines/{id}/uso
    // Body: { "kmRecorridos": 5.2, "minutosUsados": 25 }
    @PutMapping("/{id}/uso")
    public ResponseEntity<Monopatin> actualizarUso(
            @PathVariable Long id,
            @RequestBody Map<String, Number> uso) {
        Double kmRecorridos = uso.get("kmRecorridos").doubleValue();
        Integer minutosUsados = uso.get("minutosUsados").intValue();
        service.actualizarUso(id, kmRecorridos, minutosUsados);
        return ResponseEntity.ok(service.findById(id));
    }

    // ==================== REPORTES Y CONSULTAS ====================

    // Reporte de monopatines que necesitan mantenimiento
    // GET /monopatines/reporte-mantenimiento?umbralKm=500
    @GetMapping("/reporte-mantenimiento")
    public ResponseEntity<List<Monopatin>> getMantenimientoReport(
            @RequestParam double umbralKm) {
        List<Monopatin> aMantener = service.getMonopatinesParaMantenimiento(umbralKm);
        return ResponseEntity.ok(aMantener);
    }

    // Buscar monopatines cercanos a una ubicación
    // GET /monopatines/cercanos?lat=-37.3213&lon=-59.1234&radio=500
    @GetMapping("/cercanos")
    public ResponseEntity<List<Monopatin>> getMonopatinesCercanos(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "1000") Double radio) {
        List<Monopatin> cercanos = service.buscarCercanos(lat, lon, radio);
        return ResponseEntity.ok(cercanos);
    }

    // Obtener monopatines disponibles
    // GET /monopatines/disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Monopatin>> getMonopatinesDisponibles() {
        List<Monopatin> disponibles = service.getMonopatinesDisponibles();
        return ResponseEntity.ok(disponibles);
    }

    // Registrar monopatín en mantenimiento
    // POST /monopatines/{id}/mantenimiento
    @PostMapping("/{id}/mantenimiento")
    public ResponseEntity<Monopatin> registrarEnMantenimiento(@PathVariable Long id) {
        service.cambiarEstado(id, "EN_MANTENIMIENTO");
        return ResponseEntity.ok(service.findById(id));
    }

    // Ubicar monopatín en parada
    // POST /monopatines/{id}/ubicar-parada
    // Body: { "idParada": 1 }
    @PostMapping("/{id}/ubicar-parada")
    public ResponseEntity<Monopatin> ubicarEnParada(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        Long idParada = body.get("idParada");
        Monopatin actualizado = service.ubicarEnParada(id, idParada);
        return ResponseEntity.ok(actualizado);
    }
}