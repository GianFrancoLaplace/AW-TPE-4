package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.AsistentePremium;
import org.example.cuentafacturacion.dto.PreguntaDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat Premium", description = "Consultas al asistente AI exclusivo para cuentas PREMIUM")
public class ChatContoller {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private AsistentePremium asistentePremium;

    @Operation(
            summary = "Consultar asistente premium",
            description = "Permite realizar preguntas al asistente premium. Solo disponible para cuentas con plan PREMIUM."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta generada por el asistente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada"),
            @ApiResponse(responseCode = "501", description = "Cuenta no premium")
    })
    @PostMapping("/consultar")
    public ResponseEntity<String> consultarAsistentePremium(
            @Parameter(description = "ID de la cuenta", example = "1")
            @RequestParam int idCuenta,
            @RequestBody PreguntaDTO preguntaDTO) {

        String pregunta = preguntaDTO.getPregunta();

        Optional<Cuenta> cuenta = cuentaService.findCuenta(idCuenta);

        if (cuenta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (cuenta.get().getCategoria() != Cuenta.TipoCuenta.PREMIUM){
            return ResponseEntity.status(501)
                    .body("Esta funcionalidad es exclusiva para usuarios PREMIUM. Actualiza tu plan.");
        }
        String respuesta = asistentePremium.chatear("User ID: " + idCuenta + ". Pregunta: " + pregunta);

        return ResponseEntity.ok(respuesta);
    }
}
