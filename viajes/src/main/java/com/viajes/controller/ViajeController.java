package com.viajes.controller;

import com.viajes.dto.IniciarViajeDTO;
import com.viajes.dto.FinalizarViajeDTO;
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
    private ViajeService viajeService;

    @PostMapping("/iniciar")
    public ResponseEntity<Viaje> iniciarViaje(@RequestBody IniciarViajeDTO dto) {
        Viaje viaje = viajeService.iniciarViaje(dto);
        return ResponseEntity.ok(viaje);
    }

    @PostMapping("/finalizar")
    public ResponseEntity<Viaje> finalizarViaje(@RequestBody FinalizarViajeDTO dto) {
        Viaje viaje = viajeService.finalizarViaje(dto);
        return ResponseEntity.ok(viaje);
    }

    @PostMapping("/pausar/{idViaje}")
    public ResponseEntity<Pausa> pausarViaje(@PathVariable Long idViaje) {
        Pausa pausa = viajeService.pausarViaje(idViaje);
        return ResponseEntity.ok(pausa);
    }
    @PostMapping("/reanudar/{idViaje}")
    public ResponseEntity<Viaje> reanudarViaje(@PathVariable Long idViaje) {
        Viaje viaje = viajeService.reanudarViaje(idViaje);
        return ResponseEntity.ok(viaje);
    }
}