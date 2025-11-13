package org.example.cuentafacturacion.service;

import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta addCuenta(Cuenta cuenta) {
      return cuentaRepository.save(cuenta);
    }
    public void updateCuenta(int id) {
       cuentaRepository.updateEstado(id);
    }
    public void updatePlan(int id,String categoria) {
        cuentaRepository.updatePlan(id,categoria);
    }

}
