create database volaredb;
use volaredb;

create table aerolinea(
id_A integer primary key,
nombre_A varchar(50),
pais varchar(50),
codigoIATA varchar(50));

create table cliente(
id_C integer primary key,
nombre_C varchar(50),
email varchar(50),
telefono varchar(50),
ruta varchar(50));

-- Inserts para aerolinea
INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA)
VALUES (1, 'Iberia', 'España', 'IB');

INSERT INTO aerolinea (id_A, nombre_A, pais, codigoIATA)
VALUES (2, 'Air Europa', 'España', 'UX');

-- Inserts para cliente
INSERT INTO cliente (id_C, nombre_C, email, telefono, ruta)
VALUES (1, 'Asier Tartanga', 'asier@example.com', '600123456', 'Bilbao-Madrid');

INSERT INTO cliente (id_C, nombre_C, email, telefono, ruta)
VALUES (2, 'Maria Lopez', 'maria@example.com', '600654321', 'Madrid-Barcelona');

DELIMITER //

CREATE PROCEDURE REGISTRARAEROLINEA(P_ID_A INT , P_NOMBRE_A VARCHAR(50), P_PAIS VARCHAR(50), P_CODIGOIATA VARCHAR(50)) 
BEGIN 
INSERT INTO AEROLINEA (id_A, nombre_A, pais, codigoIATA) 
VALUE (P_ID_A , P_NOMBRE_A , P_PAIS , P_CODIGOIATA);
END //