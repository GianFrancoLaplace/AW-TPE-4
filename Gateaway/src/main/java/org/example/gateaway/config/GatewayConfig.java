package org.example.gateaway.config;

import org.example.gateaway.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Auth sin filtro
                .route("auth-service-public", r -> r
                        .path("/api/usuario/login", "/api/usuario/register")
                        .uri("http://localhost:8081"))

                // Auth protegido (solo endpoints que deben requerir JWT)
                .route("auth-service-protected", r -> r
                        .path("/api/usuario/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8081"))

                // Viajes
                .route("viajes-service", r -> r
                        .path("/api/viajes/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8082"))

                // Flota
                .route("flota-service", r -> r
                        .path("/api/flota/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8083"))

                // Mantenimiento
                .route("mantenimiento-service", r -> r
                        .path("/api/mantenimiento/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8084"))

                // Cuenta
                .route("cuenta-service", r -> r
                        .path("/api/cuenta/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8085"))

                // Tarifa
                .route("tarifa-service", r -> r
                        .path("/api/tarifa/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("http://localhost:8086"))

                .build();
    }

}