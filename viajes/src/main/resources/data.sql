-- Insertar Usuario de prueba
INSERT INTO usuario (id, nombre, email) VALUES (999, 'Usuario Test', 'test@correo.com');

-- Insertar Cuenta de prueba
INSERT INTO cuenta (id, saldo, id_usuario) VALUES (888, 1000.00, 999);

-- Insertar Monopatín de prueba
INSERT INTO monopatin (id, codigo, estado) VALUES ('MP-123', 'MONO-123', 'disponible');
