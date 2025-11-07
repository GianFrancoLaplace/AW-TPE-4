package com.viajes.controller;

import com.viajes.dto.InicioViajeRequest; // DTO para recibir datos de inicio (idMonopatin, idUsuario)
import com.viajes.entity.Pausa;
import com.viajes.entity.Viaje;
import com.viajes.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService service;

    @PostMapping("/iniciar")
    public ResponseEntity<Viaje> iniciarViaje(@RequestBody InicioViajeRequest request) {
        Viaje nuevoViaje = service.iniciarViaje(request);
        return ResponseEntity.ok(nuevoViaje);
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Viaje> finalizarViaje(@PathVariable Long id, @RequestParam Long idParadaDestino) {
        Viaje viajeFinalizado = service.finalizarViaje(id, idParadaDestino);
        return ResponseEntity.ok(viajeFinalizado);
    }

    @PostMapping("/{id}/pausar")
    public ResponseEntity<Pausa> pausarViaje(@PathVariable Long id) {
        Pausa nuevaPausa = service.pausarViaje(id);
        return ResponseEntity.ok(nuevaPausa);
    }
}