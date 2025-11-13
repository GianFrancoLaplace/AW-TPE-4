package org.example.cuentafacturacion.controller;

import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuenta")
public class cuentaController {
    @Autowired
    private CuentaService serviceCuenta;

    public Cuenta addCuenta(Cuenta cuenta) {
       return serviceCuenta.addCuenta(cuenta);
    }

    public void anularCuenta(int id) {
        serviceCuenta.updateCuenta(id);
    }
    public void cambiarPlan(int id,String categoria) {
        serviceCuenta.updatePlan(id, categoria);
    }
}
