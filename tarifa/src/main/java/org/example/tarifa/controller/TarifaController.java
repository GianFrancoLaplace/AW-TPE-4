package org.example.tarifa.controller;

import org.example.tarifa.entity.Tarifa;
import org.example.tarifa.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifa")
public class TarifaController {
@Autowired
private final TarifaService tarifaService;

public TarifaController(TarifaService tarifaService) {
    this.tarifaService = tarifaService;
}

    @PostMapping
    public ResponseEntity<Tarifa> insertTarifa(@RequestBody Tarifa tarifa) {
        Tarifa nueva = tarifaService.insertTarifa(tarifa);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(@PathVariable Long id, @RequestBody Tarifa tarifa) {
        Tarifa actualizada = tarifaService.updateTarifa(id, tarifa);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarTarifa(@PathVariable Long id) {
        tarifaService.activarTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activa")
    public ResponseEntity<Tarifa> getTarifaActiva() {
        Tarifa activa = tarifaService.getTarifaActiva();
        return ResponseEntity.ok(activa);
    }

    @GetMapping
    public ResponseEntity<List<Tarifa>> getAllTarifas() {
        List<Tarifa> tarifas = tarifaService.getAllTarifas();
        return ResponseEntity.ok(tarifas);
    }

}
