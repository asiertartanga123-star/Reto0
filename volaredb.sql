create database if not exists volaredb;
use volaredb;

create table if not exists aerolinea(
id_A integer primary key,
nombre_A varchar(50),
pais varchar(50),
codigoIATA varchar(50));

create table if not exists cliente(
id_C integer primary key,
nombre_C varchar(50),
email varchar(50),
telefono varchar(50),
ruta varchar(255));

-- Inserts para aerolinea
INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA)
VALUES (1, 'Iberia', 'España', 'IB')
ON DUPLICATE KEY UPDATE id_A = VALUES(id_A);

INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA)
VALUES (2, 'Air Europa', 'España', 'UX')
ON DUPLICATE KEY UPDATE id_A = VALUES(id_A);

-- Inserts para cliente
INSERT INTO cliente (id_C, nombre_C, email, telefono, ruta)
VALUES (1, 'Asier Tartanga', 'asier@example.com', '600123456', 'src/Imagenes/descarga.jpg')
ON DUPLICATE KEY UPDATE ruta = VALUES(ruta);

INSERT INTO cliente (id_C, nombre_C, email, telefono, ruta)
VALUES (2, 'Maria Lopez', 'maria@example.com', '600654321', 'src/Imagenes/descarga (1).jpg')
ON DUPLICATE KEY UPDATE ruta = VALUES(ruta);

INSERT INTO cliente (id_C, nombre_C, email, telefono, ruta)
VALUES (3, 'Cliente 3', '', '', 'src/Imagenes/descarga (2).jpg')
ON DUPLICATE KEY UPDATE ruta = VALUES(ruta);
