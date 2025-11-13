/*
package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.service.PausaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pausas")
public class PausaController {
    @Autowired
    private PausaService pausaService;

    @PostMapping("/descuento")
    public ResponseEntity<String> aplicarDescuentoPorPausa(
            @RequestParam Long idCuenta,
            @RequestParam int minutosPausa) {

        pausaService.aplicarDescuentoPorPausa(idCuenta, minutosPausa);
        return ResponseEntity.ok("Descuento aplicado correctamente (si correspondía)");
    }
}

 */
