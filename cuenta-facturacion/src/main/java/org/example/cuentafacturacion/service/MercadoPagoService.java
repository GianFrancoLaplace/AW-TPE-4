package org.example.cuentafacturacion.service;

public interface MercadoPagoService {

    /**
     * Simula la validación y el procesamiento de un monto en la cuenta de MercadoPago.
     * Un monto positivo simula una carga de crédito (cargo a la tarjeta).
     * @param idMercadopago El ID externo de la cuenta de Mercado Pago.
     * @param monto El valor de la transacción (positivo para carga, negativo para consulta/débito).
     * @return true si la transacción es exitosa, false en caso contrario.
     */
    boolean procesarTransaccion(int idMercadopago, float monto);

    /**

     * @param idMercadopago El ID externo de la cuenta de Mercado Pago.
     * @return true si la anulación fue registrada.
     */
    boolean anularFuenteDePago(int idMercadopago);
}