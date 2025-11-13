/*
package org.example.cuentafacturacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PausaService {
    @Autowired
    private MovimientoService movimientoService;

    public void aplicarDescuentoPorPausa(Long idCuenta, int minutosPausa) {
        if (minutosPausa > 15) {
            float montoDescuento = calcularDescuento(minutosPausa);

            movimientoService.registrarMovimiento(
                    idCuenta,
                    -montoDescuento,
                    "Descuento por pausa mayor a 15 minutos (" + minutosPausa + " min)",
                    "PAUSA-" + System.currentTimeMillis()
            );
        }
    }

    private float calcularDescuento(int minutosPausa) {
        int minutosExtra = minutosPausa - 15;
        return minutosExtra * 1.0f;
    }
}

 */

