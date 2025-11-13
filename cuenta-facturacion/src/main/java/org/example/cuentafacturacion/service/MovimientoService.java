package org.example.cuentafacturacion.service;

import jakarta.transaction.Transactional;
import org.example.cuentafacturacion.dto.DetalleMovimientoDTO;
import org.example.cuentafacturacion.dto.ReporteFacturacionDTO;
import org.example.cuentafacturacion.dto.ReporteSaldoMovimientosDTO;
import org.example.cuentafacturacion.entity.Cuenta;
import org.example.cuentafacturacion.entity.Movimiento;
import org.example.cuentafacturacion.repository.CuentaRepository;
import org.example.cuentafacturacion.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Genera reporte de facturación total en un período.
     */
    public ReporteFacturacionDTO obtenerReporteFacturacion(String fechaInicio, String fechaFin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio + " 00:00:00", formatter);
        LocalDateTime fin = LocalDateTime.parse(fechaFin + " 23:59:59", formatter);

        List<Movimiento> movimientos = movimientoRepository.findByFecha(inicio, fin);

        float totalFacturado = 0;
        int cantidadTransacciones = 0;

        for (Movimiento m : movimientos) {
            if (m.getMonto() < 0) {
                totalFacturado += Math.abs(m.getMonto());
            }
            cantidadTransacciones++;
        }

        return new ReporteFacturacionDTO(
                totalFacturado,
                cantidadTransacciones,
                fechaInicio,
                fechaFin
        );
    }

    /**
     * Genera reporte de saldo y movimientos de una cuenta.
     */
    public ReporteSaldoMovimientosDTO obtenerReporteSaldoMovimientos(Long idCuenta, String fechaInicio, String fechaFin) {
        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio + " 00:00:00", formatter);
        LocalDateTime fin = LocalDateTime.parse(fechaFin + " 23:59:59", formatter);

        List<Movimiento> movimientos = movimientoRepository.findByCuentaAndFecha(idCuenta, inicio, fin);

        float totalIngresos = 0;
        float totalEgresos = 0;
        List<DetalleMovimientoDTO> detalleMovimientos = new ArrayList<>();

        for (Movimiento m : movimientos) {
            if (m.getMonto() > 0) {
                totalIngresos += m.getMonto();
            } else {
                totalEgresos += Math.abs(m.getMonto());
            }

            detalleMovimientos.add(new DetalleMovimientoDTO(
                    m.getIdMovimiento(),
                    m.getMonto(),
                    m.getDescripcion(),
                    m.getFechaMovimiento().format(formatter),
                    m.getReferencia()
            ));
        }

        return new ReporteSaldoMovimientosDTO(
                idCuenta,
                cuenta.getSaldoActual(),
                totalIngresos,
                totalEgresos,
                detalleMovimientos
        );
    }
}