package org.example.tarifa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifa")
@Tag(name = "Tarifas", description = "Gestión de tarifas del sistema")
public class TarifaController {
    @Autowired
    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @Operation(summary = "Crear tarifa", description = "Registra una nueva tarifa")
    @PostMapping
    public ResponseEntity<Tarifa> insertTarifa(@RequestBody Tarifa tarifa) {
        Tarifa nueva = tarifaService.insertTarifa(tarifa);
        return ResponseEntity.ok(nueva);
    }

    @Operation(summary = "Actualizar tarifa", description = "Modifica una tarifa existente")
    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(
            @PathVariable Long id,
            @RequestBody Tarifa tarifa
    ) {
        Tarifa actualizada = tarifaService.updateTarifa(id, tarifa);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Activar tarifa", description = "Activa una tarifa y desactiva la anterior")
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarTarifa(@PathVariable Long id) {
        tarifaService.activarTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener tarifa activa")
    @GetMapping("/activa")
    public ResponseEntity<Tarifa> getTarifaActiva() {
        Tarifa activa = tarifaService.getTarifaActiva();
        return ResponseEntity.ok(activa);
    }

    @Operation(summary = "Listar todas las tarifas")
    @GetMapping
    public ResponseEntity<List<Tarifa>> getAllTarifas() {
        List<Tarifa> tarifas = tarifaService.getAllTarifas();
        return ResponseEntity.ok(tarifas);
    }

}
