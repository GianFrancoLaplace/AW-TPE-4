package org.example.cuentafacturacion.service;

import jakarta.transaction.Transactional;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.repository.CuentaRepository;
import org.example.cuentafacturacion.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento registrarMovimiento(Long idCuenta, float monto, String descripcion, String referencia) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Actualiza saldo
        cuenta.setSaldoActual(cuenta.getSaldoActual() + monto);
        cuentaRepository.save(cuenta);

        // Registra el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setMonto(monto);
        movimiento.setDescripcion(descripcion);
        movimiento.setReferencia(referencia);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }
}
