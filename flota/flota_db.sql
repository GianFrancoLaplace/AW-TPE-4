CREATE DATABASE IF NOT EXISTS flota_db;

CREATE TABLE monopatin (
                           id_monopatin BIGINT AUTO_INCREMENT PRIMARY KEY,
                           estado VARCHAR(50) NOT NULL,
                           latitud_actual DOUBLE,
                           longitud_actual DOUBLE,
                           km_totales_acumulados DOUBLE DEFAULT 0.0,
                           tiempo_uso_total_minutos INT DEFAULT 0,
                           INDEX idx_estado (estado),
                           INDEX idx_ubicacion (latitud_actual, longitud_actual)
);

CREATE TABLE parada (
                        id_parada BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        latitud_centro DOUBLE NOT NULL,
                        longitud_centro DOUBLE NOT NULL,
                        radio_metros DOUBLE NOT NULL,
                        activa BOOLEAN DEFAULT TRUE,
                        INDEX idx_ubicacion (latitud_centro, longitud_centro)
);
