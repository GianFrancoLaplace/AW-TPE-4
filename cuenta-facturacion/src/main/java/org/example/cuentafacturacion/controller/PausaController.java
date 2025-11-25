package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.service.PausaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pausas")
@Tag(name = "Pausas", description = "Gestión de descuentos por pausas prolongadas")
public class PausaController {
    @Autowired
    private PausaService pausaService;

    @Operation(
            summary = "Aplicar descuento por pausa",
            description = "Descuenta saldo si la pausa supera los 15 minutos"
    )
    @PostMapping("/descuento")
    public ResponseEntity<String> aplicarDescuentoPorPausa(
            @Parameter(description = "ID de la cuenta", example = "1")
            @RequestParam Long idCuenta,
            @Parameter(description = "Cantidad de minutos en pausa", example = "30")
            @RequestParam int minutosPausa) {

        pausaService.aplicarDescuentoPorPausa(idCuenta, minutosPausa);
        return ResponseEntity.ok("Descuento aplicado correctamente (si correspondía)");
    }
}