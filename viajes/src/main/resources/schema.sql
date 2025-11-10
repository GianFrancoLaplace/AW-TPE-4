-- Tabla Usuario
CREATE TABLE usuario
(
    id     BIGINT PRIMARY KEY,
    nombre VARCHAR(255),
    email  VARCHAR(255)
);

-- Tabla Cuenta
CREATE TABLE cuenta
(
    id         BIGINT PRIMARY KEY,
    saldo      DECIMAL,
    id_usuario BIGINT,
    CONSTRAINT fk_cuenta_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id)
);

-- Tabla Monopatin
CREATE TABLE monopatin
(
    id     VARCHAR(50) PRIMARY KEY,
    codigo VARCHAR(50),
    estado VARCHAR(50)
);

-- Tabla Viaje
CREATE TABLE viaje
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario   BIGINT,
    id_cuenta    BIGINT,
    id_monopatin VARCHAR(50),
    estado       VARCHAR(50),
    CONSTRAINT fk_viaje_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id),
    CONSTRAINT fk_viaje_cuenta FOREIGN KEY (id_cuenta) REFERENCES cuenta (id),
    CONSTRAINT fk_viaje_monopatin FOREIGN KEY (id_monopatin) REFERENCES monopatin (id)
);

-- Tabla Pausa
CREATE TABLE pausa
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_viaje BIGINT,
    inicio   TIMESTAMP,
    fin      TIMESTAMP,
    CONSTRAINT fk_pausa_viaje FOREIGN KEY (id_viaje) REFERENCES viaje (id)
);
