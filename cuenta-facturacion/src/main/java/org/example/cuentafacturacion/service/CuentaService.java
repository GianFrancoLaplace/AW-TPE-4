package org.example.cuentafacturacion.service;

import org.example.cuentafacturacion.client.ViajeClient;
import org.example.cuentafacturacion.dto.DetalleViajeDTO;
import org.example.cuentafacturacion.dto.ReporteUsoCuentaDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.repository.CuentaRepository;
import org.example.cuentafacturacion.repository.CuentaUsuarioRepository;
import org.example.cuentafacturacion.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaUsuarioRepository cuentaUsuarioRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ViajeClient viajeClient;

    public Cuenta addCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void updateCuenta(int id) {
        cuentaRepository.updateEstado(id);
    }

    public void updatePlan(int id, String categoria) {
        cuentaRepository.updatePlan(id, categoria);
    }

    public Optional<Cuenta> findCuenta(int id) {
        return cuentaRepository.findById((long) id);
    }

    public void addUsuario(int id_cuenta, int id_usuario) {
        cuentaUsuarioRepository.asociarUsuario(id_cuenta, id_usuario);
    }

    public void deleteUsuario(int id_cuenta,int id_usuario) {
        cuentaUsuarioRepository.quitarUsuario(id_cuenta,id_usuario);
    }

    public List<Integer> getUsuarios(int id_cuenta) {
        return cuentaUsuarioRepository.obtenerUsuarios(id_cuenta);
    }

    /**
     * Genera reporte de uso de una cuenta con detalle de viajes y gastos.
     */
    public ReporteUsoCuentaDTO obtenerReporteUso(Long idCuenta, String fechaInicio, String fechaFin) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio + " 00:00:00", formatter);
        LocalDateTime fin = LocalDateTime.parse(fechaFin + " 23:59:59", formatter);

        List<Movimiento> movimientos = movimientoRepository.findByCuentaAndFecha(idCuenta, inicio, fin);

        float totalGastado = 0;
        for (Movimiento m : movimientos) {
            if (m.getMonto() < 0) {
                totalGastado += Math.abs(m.getMonto());
            }
        }

        List<DetalleViajeDTO> viajes = viajeClient.obtenerViajesPorCuenta(idCuenta, fechaInicio, fechaFin);

        int cantidadViajes = viajes.size();
        float kmTotales = 0;
        for (DetalleViajeDTO viaje : viajes) {
            kmTotales += viaje.getKmRecorridos();
        }

        return new ReporteUsoCuentaDTO(
                idCuenta,
                cuenta.getSaldoActual(),
                totalGastado,
                cantidadViajes,
                kmTotales,
                viajes
        );
    }
}