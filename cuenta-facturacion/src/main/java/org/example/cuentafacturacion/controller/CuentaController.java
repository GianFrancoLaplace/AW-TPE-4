package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.dto.ReporteUsoCuentaDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuenta")
@Tag(name = "Cuentas", description = "Operaciones relacionadas con la gestion de cuentas y saldo")
public class CuentaController {

    @Autowired
    private CuentaService serviceCuenta;

    @Operation(
            summary = "Crear una nueva cuenta",
            description = "Da de alta una cuenta en el sistema con saldo inicial y estado activo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta creada correctamente",
                    content = @Content(schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/")
    public Cuenta addCuenta(@RequestBody Cuenta cuenta) {
        return serviceCuenta.addCuenta(cuenta);
    }


    @Operation(summary = "Anular una cuenta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta anulada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/anularCuenta/{id}")
    public ResponseEntity<String> anularCuenta(
            @Parameter(description = "ID de la cuenta a anular", example = "1")
            @PathVariable Long id) {
        serviceCuenta.anularCuenta(id); // <--- AQUI ESTÁ LA NUEVA LÓGICA
        return ResponseEntity.ok("Cuenta TPE " + id + " anulada exitosamente (y fuente de pago bloqueada).");
    }


    @Operation(summary = "Cambiar plan de una cuenta")
    @PutMapping("/cambiarPlan/{id}")
    public void cambiarPlan(
            @Parameter(description = "ID de la cuenta", example = "1")
            @PathVariable int id,
            @Parameter(description = "Nueva categoría del plan", example = "PREMIUM")
            @RequestParam String categoria) {
        serviceCuenta.updatePlan(id, categoria);
    }

    @Operation(summary = "Obtener una cuenta por ID")
    @GetMapping("/{id}")
    public Optional<Cuenta> getCuenta(
            @Parameter(description = "ID de la cuenta", example = "1")
            @PathVariable int id) {
        return serviceCuenta.findCuenta(id);
    }


    @Operation(summary = "Asociar usuario a cuenta")
    @PostMapping("/{id_cuenta}/usuario/{id_usuario}")
    public void addUsuario( @Parameter(description = "ID de la cuenta") @PathVariable int id_cuenta,
                            @Parameter(description = "ID del usuario") @PathVariable int id_usuario) {
        serviceCuenta.addUsuario(id_cuenta, id_usuario);
    }

    @Operation(summary = "Eliminar usuario de una cuenta")
    @DeleteMapping("/{id_cuenta}/usuario/{id_usuario}")
    public void deleteUsuario(@Parameter(description = "ID de la cuenta") @PathVariable int id_cuenta,
                              @Parameter(description = "ID del usuario") @PathVariable int id_usuario) {
        serviceCuenta.deleteUsuario(id_cuenta, id_usuario);
    }

    @Operation(summary = "Obtener usuarios asociados a una cuenta")
    @GetMapping("/usuarios/{id_cuenta}")
    public List<Integer> getUsuarios(@Parameter(description = "ID de la cuenta")
                                     @PathVariable int id_cuenta) {
        return serviceCuenta.getUsuarios(id_cuenta);
    }

    /**
     * GET /api/cuenta/reporte-uso/{idCuenta}?fechaInicio=2025-01-01&fechaFin=2025-12-31
     */
    @Operation(
            summary = "Reporte de uso de una cuenta",
            description = "Devuelve viajes, gastos y kilómetros recorridos entre dos fechas"
    )
    @GetMapping("/reporte-uso/{idCuenta}")
    public ResponseEntity<ReporteUsoCuentaDTO> obtenerReporteUso(
            @Parameter(description = "ID de la cuenta") @PathVariable Long idCuenta,
            @Parameter(description = "Fecha inicio (YYYY-MM-DD)", example = "2025-01-01")
            @RequestParam String fechaInicio,
            @Parameter(description = "Fecha fin (YYYY-MM-DD)", example = "2025-12-31")
            @RequestParam String fechaFin) {

        ReporteUsoCuentaDTO reporte = serviceCuenta.obtenerReporteUso(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @Operation(summary = "Cargar crédito en una cuenta")
    @PostMapping("/{idCuenta}/cargar")
    public ResponseEntity<String> cargarCredito(
            @PathVariable Long idCuenta,
            @Parameter(description = "Monto a cargar", example = "500")
            @RequestParam float monto) {

        serviceCuenta.cargarCredito(idCuenta, monto);
        return ResponseEntity.ok("Credito cargado correctamente: $" + monto);
    }

    @Operation(summary = "Descontar saldo por viaje")
    @PostMapping("/descontar-viaje")
    public ResponseEntity<String> descontarSaldoPorViaje(
            @RequestParam Long idCuenta,
            @RequestParam float costoTotal,
            @RequestParam String idViaje) {

        serviceCuenta.descontarSaldoPorViaje(idCuenta, costoTotal, idViaje);
        return ResponseEntity.ok("Saldo descontado por viaje " + idViaje + ": $" + costoTotal);
    }


}