package org.example.cuentafacturacion.client;

import org.example.cuentafacturacion.dto.DetalleViajeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Cliente REST para comunicarse con el microservicio de Viajes.
 * Permite obtener información de viajes de una cuenta específica.
 */
@Component
public class ViajeClient {

    private final RestTemplate restTemplate;

    @Value("${viajes.service.url:http://localhost:8082}")
    private String viajesServiceUrl;

    public ViajeClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Obtiene los viajes de una cuenta en un período específico.
     *
     * @param idCuenta ID de la cuenta
     * @param fechaInicio Fecha de inicio del período (formato: yyyy-MM-dd)
     * @param fechaFin Fecha de fin del período (formato: yyyy-MM-dd)
     * @return Lista de viajes realizados en el período
     */
    public List<DetalleViajeDTO> obtenerViajesPorCuenta(Long idCuenta, String fechaInicio, String fechaFin) {
        String url = String.format("%s/api/viajes/cuenta/%d?fechaInicio=%s&fechaFin=%s",
                viajesServiceUrl, idCuenta, fechaInicio, fechaFin);

        ResponseEntity<List<DetalleViajeDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DetalleViajeDTO>>() {}
        );

        return response.getBody();
    }
}