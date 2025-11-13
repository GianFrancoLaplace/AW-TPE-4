package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuenta")
public class cuentaController {
    @Autowired
    private CuentaService serviceCuenta;

    @PostMapping("/")
    public Cuenta addCuenta(Cuenta cuenta) {
       return serviceCuenta.addCuenta(cuenta);
    }
    @PutMapping("/anularCuenta/{id}")
    public void anularCuenta(int id) {
        serviceCuenta.updateCuenta(id);
    }

    @PutMapping("/cambiarPlan/{id}")
    public void cambiarPlan(int id,String categoria) {
        serviceCuenta.updatePlan(id, categoria);
    }

    @GetMapping("/{id}")
    public Optional<Cuenta> getCuenta(int id) {
        return serviceCuenta.findCuenta(id);
    }
    @PostMapping("{id_cuenta}/usuario/id_usuario")
    public void addUsuario(int id_cuenta,int id_usuario) {
        serviceCuenta.addUsuario(id_cuenta,id_usuario);
    }

    @DeleteMapping("{id_cuenta}/usuario/id_usuario")
    public void deleteUsuario(int id_cuenta,int id_usuario) {
        serviceCuenta.deleteUsuario(id_cuenta,id_usuario);
    }

    @GetMapping("usuarios/{id_cuenta}")
    public List<Integer> getUsuarios(int id_cuenta) {
        return serviceCuenta.getUsuarios(id_cuenta);
    }
}
