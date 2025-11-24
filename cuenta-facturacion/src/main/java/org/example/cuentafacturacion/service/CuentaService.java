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
    // FIX: Se agrega Autowired a MovimientoService para que funcione correctamente
    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CuentaUsuarioRepository cuentaUsuarioRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ViajeClient viajeClient;

    // NUEVO: Inyección del servicio de pagos mockeado
    @Autowired
    private MercadoPagoService mercadoPagoService;


    public Cuenta addCuenta(Cuenta cuenta) {
        // Asumimos estado ACTIVA al crear
        if (cuenta.getEstado() == null) {
            cuenta.setEstado(Cuenta.EstadoCuenta.ACTIVA);
        }
        return cuentaRepository.save(cuenta);
    }

    // MODIFICADO: Refactorizado para cumplir la consigna 4b / 60
    public void anularCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta TPE no encontrada."));

        // 1. ANULAR CUENTA EN EL SISTEMA EXTERNO (MOCK DE MP)
        // El administrador anula la cuenta, lo que implica bloquear la fuente de pago externa.
        if (mercadoPagoService.anularFuenteDePago(cuenta.getIdMercadopago())) {
            // 2. CAMBIAR ESTADO INTERNO solo si la fuente de pago externa se procesó.
            cuenta.setEstado(Cuenta.EstadoCuenta.ANULADA);
            cuentaRepository.save(cuenta);
            System.out.println("✅ Cuenta TPE " + id + " anulada exitosamente (y fuente de pago bloqueada).");
        } else {
            // Si el servicio de pagos externo falla o reporta un error.
            throw new RuntimeException("⚠️ No se pudo anular la cuenta de pagos externa para Cuenta TPE " + id);
        }
    }

    // Mantenemos el update original con un nombre más descriptivo si es necesario
    public void updateCuenta(int id) {
        cuentaRepository.updateEstado(id); // Asumo que este método original es de cambio de estado.
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

    // MODIFICADO: Ahora usa el Mock de Mercado Pago para simular el cargo externo.
    public void cargarCredito(Long idCuenta, float monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta TPE no encontrada."));

        // 1. SIMULAR TRANSACCIÓN EN MP (Validación de tarjeta/fondos/cargo)
        // El monto es positivo, simulando el cargo a la tarjeta externa de MP.
        if (mercadoPagoService.procesarTransaccion(cuenta.getIdMercadopago(), monto)) {

            // 2. Si la transacción externa es OK, se registra el movimiento interno (CRÉDITO A LA CUENTA TPE)
            movimientoService.registrarMovimiento(
                    idCuenta,
                    monto,
                    "Carga de credito",
                    "CARGA-" + System.currentTimeMillis()
            );

            System.out.println("Carga de crédito exitosa en cuenta TPE " + idCuenta);

        } else {
            // Si el mock falla (ej: ID de tarjeta simulada como inválida o bloqueada)
            throw new RuntimeException("Error al procesar la carga de crédito en MercadoPago (simulado). Verifique logs.");
        }
    }

    // Se mantiene igual ya que el débito es INTERNO contra el saldo cargado.
    public void descontarSaldoPorViaje(Long idCuenta, float costoTotal, String idViaje) {
        if (costoTotal <= 0) {
            throw new IllegalArgumentException("El costo del viaje debe ser mayor que 0");
        }

        // El débito es interno (registrado como movimiento negativo)
        movimientoService.registrarMovimiento(
                idCuenta,
                -costoTotal,
                "Cobro de viaje",
                "VIAJE-" + idViaje
        );
    }

}