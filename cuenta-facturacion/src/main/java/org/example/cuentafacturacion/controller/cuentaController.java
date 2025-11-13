package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.dto.ReporteUsoCuentaDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuenta")
public class cuentaController {

    @Autowired
    private CuentaService serviceCuenta;

    @PostMapping("/")
    public Cuenta addCuenta(@RequestBody Cuenta cuenta) {
        return serviceCuenta.addCuenta(cuenta);
    }

    @PutMapping("/anularCuenta/{id}")
    public void anularCuenta(@PathVariable int id) {
        serviceCuenta.updateCuenta(id);
    }

    @PutMapping("/cambiarPlan/{id}")
    public void cambiarPlan(@PathVariable int id, @RequestParam String categoria) {
        serviceCuenta.updatePlan(id, categoria);
    }

    @GetMapping("/{id}")
    public Optional<Cuenta> getCuenta(@PathVariable int id) {
        return serviceCuenta.findCuenta(id);
    }

    @PostMapping("/{id_cuenta}/usuario/{id_usuario}")
    public void addUsuario(@PathVariable int id_cuenta, @PathVariable int id_usuario) {
        serviceCuenta.addUsuario(id_cuenta, id_usuario);
    }

    @DeleteMapping("/{id_cuenta}/usuario/{id_usuario}")
    public void deleteUsuario(@PathVariable int id_cuenta, @PathVariable int id_usuario) {
        serviceCuenta.deleteUsuario(id_cuenta, id_usuario);
    }

    @GetMapping("/usuarios/{id_cuenta}")
    public List<Integer> getUsuarios(@PathVariable int id_cuenta) {
        return serviceCuenta.getUsuarios(id_cuenta);
    }

    /**
     * GET /api/cuenta/reporte-uso/{idCuenta}?fechaInicio=2025-01-01&fechaFin=2025-12-31
     */
    @GetMapping("/reporte-uso/{idCuenta}")
    public ResponseEntity<ReporteUsoCuentaDTO> obtenerReporteUso(
            @PathVariable Long idCuenta,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        ReporteUsoCuentaDTO reporte = serviceCuenta.obtenerReporteUso(idCuenta, fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/{idCuenta}/cargar")
    public ResponseEntity<String> cargarCredito(
            @PathVariable Long idCuenta,
            @RequestParam float monto) {

        serviceCuenta.cargarCredito(idCuenta, monto);
        return ResponseEntity.ok("Credito cargado correctamente: $" + monto);
    }

    @PostMapping("/descontar-viaje")
    public ResponseEntity<String> descontarSaldoPorViaje(
            @RequestParam Long idCuenta,
            @RequestParam float costoTotal,
            @RequestParam String idViaje) {

        serviceCuenta.descontarSaldoPorViaje(idCuenta, costoTotal, idViaje);
        return ResponseEntity.ok("Saldo descontado por viaje " + idViaje + ": $" + costoTotal);
    }


}