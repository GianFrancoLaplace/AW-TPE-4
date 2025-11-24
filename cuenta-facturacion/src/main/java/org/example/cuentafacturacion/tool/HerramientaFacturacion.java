package org.example.cuentafacturacion.tool;

import dev.langchain4j.agent.tool.Tool;
import org.example.cuentafacturacion.dto.MonopatinInfoDTO;
import org.example.cuentafacturacion.dto.ReporteUsoCuentaDTO;
import org.example.cuentafacturacion.intefaces.FlotaClient;
import org.example.cuentafacturacion.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HerramientaFacturacion {
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private FlotaClient flotaClient;

    @Tool("Obtiene el reporte de uso, saldo y viajes de un usuario en un rango de fechas")
    public ReporteUsoCuentaDTO obtenerEstadoCuenta(Long idCuenta, String fechaInicio, String fechaFin) {
        return cuentaService.obtenerReporteUso(idCuenta, fechaInicio, fechaFin);
    }

    @Tool("Verifica si la cuenta tiene saldo suficiente para un monto dado")
    public boolean verificaSaldo(int idCuenta, float monto) {
        var cuenta = cuentaService.findCuenta(idCuenta);
        float saldoActual = cuenta.get().getSaldoActual();
        return saldoActual >= monto;

    }
    @Tool("Obtiene el saldo")
    public float obtenerSaldo(int idCuenta) {
        var cuenta = cuentaService.findCuenta(idCuenta);
        float saldoActual = cuenta.get().getSaldoActual();
        return saldoActual;
    }
    @Tool("Consulta la lista de monopatines disponibles actualmente, su batería y ubicación")
    public List<MonopatinInfoDTO> consultarMonopatinesDisponibles() {
        try {
            // El Chatbot llama a esta función -> Java llama a Flota -> Flota responde
            return flotaClient.obtenerMonopatines();
        } catch (Exception e) {
            return List.of(); // Manejo de error si el servicio flota está caído
        }
    }

    @Tool("Consulta la lista de monopatines en mantenimiento")
    public List<MonopatinInfoDTO> obtenerMonopatinesEnMantenimiento() {
        try {
            // El Chatbot llama a esta función -> Java llama a Flota -> Flota responde
            return flotaClient.obtenerMonopatinesEnMantenimiento();
        } catch (Exception e) {
            return List.of(); // Manejo de error si el servicio flota está caído
        }
    }

}
