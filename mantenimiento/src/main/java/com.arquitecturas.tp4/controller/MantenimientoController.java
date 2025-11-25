package com.arquitecturas.tp4.controller;
import com.arquitecturas.tp4.entities.Mantenimiento;
import com.arquitecturas.tp4.service.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/mantenimiento")
@Tag(name = "Mantenimiento", description = "Gestión de mantenimientos de monopatines")
public class MantenimientoController {
@Autowired
    private final MantenimientoService mantenimientoService;

    public MantenimientoController(MantenimientoService mantenimientoService) {
        this.mantenimientoService = mantenimientoService;
    }

    @Operation(
            summary = "Registrar mantenimiento",
            description = "Crea un nuevo mantenimiento para un monopatín"
    )
    @PostMapping()
    public ResponseEntity<Mantenimiento> insertMantenimiento(@RequestBody Mantenimiento mantenimiento){
        Mantenimiento mantenimientoNuevo = mantenimientoService.addMantenimiento(mantenimiento);
        return ResponseEntity.ok(mantenimientoNuevo);
    }

    @Operation(
            summary = "Finalizar mantenimiento",
            description = "Marca como finalizado el mantenimiento de un monopatín"
    )
    @PatchMapping("/terminarMantenimiento/{id_monopatin}")
    public void updateMantenimiento(
            @Parameter(description = "ID del monopatín", example = "101")
            @PathVariable int idMonopatin
    ){
        mantenimientoService.terminarMantiniminento(idMonopatin);
    }
}

