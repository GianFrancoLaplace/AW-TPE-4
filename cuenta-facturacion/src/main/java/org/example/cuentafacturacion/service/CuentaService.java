package org.example.cuentafacturacion.service;

import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.repository.CuentaRepository;
import org.example.cuentafacturacion.repository.CuentaUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private CuentaUsuarioRepository cuentaUsuarioRepository;

    public Cuenta addCuenta(Cuenta cuenta) {
      return cuentaRepository.save(cuenta);
    }

    public void updateCuenta(int id) {
       cuentaRepository.updateEstado(id);
    }

    public void updatePlan(int id,String categoria) {
        cuentaRepository.updatePlan(id,categoria);
    }

    public Optional<Cuenta> findCuenta(int id) {
        return cuentaRepository.findById((long) id);
    }
    public void addUsuario(int id_cuenta,int id_usuario) {
        cuentaUsuarioRepository.asociarUsuario(id_cuenta,id_usuario);
    }

    public void deleteUsuario(int id_cuenta,int id_usuario) {
        cuentaUsuarioRepository.quitarUsuario(id_cuenta,id_usuario);
    }

    public List<Integer> getUsuarios(int id_cuenta){
        return cuentaUsuarioRepository.obtenerUsuarios(id_cuenta);
    }

}
