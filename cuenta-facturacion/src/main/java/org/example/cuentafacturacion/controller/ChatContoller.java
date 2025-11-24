package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.AsistentePremium;
import org.example.cuentafacturacion.dto.PreguntaDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatContoller {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private AsistentePremium asistentePremium;

    @PostMapping("/consultar")
    public ResponseEntity<String> consultarAsistentePremium(@RequestParam int idCuenta, @RequestBody PreguntaDTO preguntaDTO) {

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
