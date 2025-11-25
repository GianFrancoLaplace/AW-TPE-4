package org.example.cuentafacturacion.service;

import org.springframework.stereotype.Service;

@Service
public class MercadoPagoMockImpl implements MercadoPagoService {

    // IDs para simular condiciones de fallo
    private static final int ID_CUENTA_BLOQUEADA = 999;
    private static final int ID_FALLO_TRANSACCION = 1000;

    @Override
    public boolean procesarTransaccion(int idMercadopago, float monto) {
        if (idMercadopago == ID_CUENTA_BLOQUEADA) {
            System.err.println("[MOCK MP - FALLO] ID de MercadoPago " + idMercadopago + " simulado como bloqueado. Operación cancelada.");
            return false;
        }

        if (idMercadopago == ID_FALLO_TRANSACCION) {
            System.err.println("[MOCK MP - FALLO] Error de conexión simulado para ID " + idMercadopago);
            return false;
        }

        String operacion = (monto > 0) ? "Carga de crédito (Cargo a tarjeta)" : "Débito (Consulta o validación)";
        float valor = Math.abs(monto);

        System.out.println("[MOCK MP - OK] Transacción simulada: " + operacion + " de $" + valor + " en MP ID: " + idMercadopago);
        return true;
    }

    @Override
    public boolean anularFuenteDePago(int idMercadopago) {
        if (idMercadopago == ID_CUENTA_BLOQUEADA) {
            System.out.println("[MOCK MP - INFO] La fuente de pago " + idMercadopago + " ya estaba marcada como anulada.");
            return false;
        }

        System.out.println("[MOCK MP - OK] Fuente de pago " + idMercadopago + " anulada exitosamente en el sistema de pagos.");
        return true;
    }
}