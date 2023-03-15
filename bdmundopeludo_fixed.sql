-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 04-06-2025 a las 04:27:09
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdmundopeludo`
--

CREATE TABLE `categoria` (
  `id_categoria` varchar(100) NOT NULL,
  `nombre_categoria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`) VALUES
('CAT00001', 'Alimento para Perros'),
('CAT00002', 'Alimento para Gatos'),
('CAT00003', 'Juguetes'),
('CAT00004', 'Accesorios'),
('CAT00005', 'Higiene y Cuidado');

CREATE TABLE `cliente` (
  `dni` varchar(100) NOT NULL,
  `nombre_apellido` varchar(100) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `celular` varchar(20) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `cliente` (`dni`, `nombre_apellido`, `correo`, `celular`, `direccion`) VALUES
('11111111', 'Juan Torres García', 'juan.torres@email.com', '999888777', 'Av. Primavera 123'),
('22222222', 'Marta Sánchez López', 'marta.sanchez@email.com', '988777666', 'Calle Los Pinos 45'),
('33333333', 'Roberto Jiménez Flores', 'roberto.jimenez@email.com', '977666555', 'Jr. Libertad 789'),
('44444444', 'Lucía Díaz Mendoza', 'lucia.diaz@email.com', '966555444', 'Av. Sol 567'),
('55555555', 'Pedro Castro Ríos', 'pedro.castro@email.com', '955444333', 'Calle Luna 234');

CREATE TABLE `detalle_orden` (
  `id_detalle` int(11) NOT NULL,
  `id_orden` varchar(100) DEFAULT NULL,
  `id_producto` varchar(100) DEFAULT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `detalle_orden` (`id_detalle`, `id_orden`, `id_producto`, `cantidad`, `precio_unitario`, `subtotal`) VALUES
(1, 'ORD00001', 'PRD00001', 1, 120.50, 120.50),
(2, 'ORD00001', 'PRD00003', 2, 15.90, 31.80),
(3, 'ORD00001', 'PRD00005', 1, 32.00, 32.00),
(4, 'ORD00002', 'PRD00002', 5, 8.75, 43.75),
(5, 'ORD00002', 'PRD00004', 1, 45.30, 45.30),
(6, 'ORD00003', 'PRD00001', 1, 120.50, 120.50),
(7, 'ORD00004', 'PRD00003', 2, 15.90, 31.80),
(8, 'ORD00004', 'PRD00005', 1, 32.00, 32.00),
(9, 'ORD00005', 'PRD00002', 3, 8.75, 26.25),
(10, 'ORD00005', 'PRD00003', 1, 15.90, 15.90),
(11, 'ORD00005', 'PRD00004', 1, 45.30, 45.30);

CREATE TABLE `orden` (
  `id_orden` varchar(100) NOT NULL,
  `dni_cliente` varchar(100) DEFAULT NULL,
  `dni_usuario` varchar(100) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `orden` (`id_orden`, `dni_cliente`, `dni_usuario`, `fecha`, `total`) VALUES
('ORD00001', '11111111', '12345678', '2025-06-03', 185.25),
('ORD00002', '22222222', '23456789', '2025-06-03', 64.65),
('ORD00003', '33333333', '34567890', '2025-06-03', 120.50),
('ORD00004', '44444444', '45678901', '2025-06-03', 53.20),
('ORD00005', '55555555', '56789012', '2025-06-03', 77.90);

CREATE TABLE `producto` (
  `id_producto` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `id_categoria` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `producto` (`id_producto`, `nombre`, `precio`, `stock`, `id_categoria`) VALUES
('PRD00001', 'Croquetas Premium para Perro 5kg', 120.50, 30, 'CAT00001'),
('PRD00002', 'Alimento Húmedo para Gato 85gr', 8.75, 50, 'CAT00002'),
('PRD00003', 'Pelota de Goma con Sonido', 15.90, 25, 'CAT00003'),
('PRD00004', 'Correa Retráctil para Mascotas', 45.30, 15, 'CAT00004'),
('PRD00005', 'Shampoo Antipulgas 500ml', 32.00, 20, 'CAT00005');

CREATE TABLE `usuario` (
  `dni` varchar(100) NOT NULL,
  `nombre_apellido` varchar(100) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `usuario` (`dni`, `nombre_apellido`, `correo`, `username`, `password`, `fecha`) VALUES
('12345678', 'Wienner Cataño López', 'wienner.cataño@email.com', 'win', '123456', '2025-06-03'),
('23456789', 'Carlos Gómez Ruiz', 'carlos.gomez@email.com', 'cgomez', 'petshop456', '2025-06-03'),
('34567890', 'María Rodríguez Sánchez', 'maria.rod@email.com', 'mrodriguez', 'petshop789', '2025-06-03'),
('45678901', 'Luis Fernández Díaz', 'luis.fernandez@email.com', 'lfernandez', 'petshop012', '2025-06-03'),
('56789012', 'Sofía Martínez Vargas', 'sofia.martinez@email.com', 'smartinez', 'petshop345', '2025-06-03');

ALTER TABLE `categoria` ADD PRIMARY KEY (`id_categoria`);
ALTER TABLE `cliente` ADD PRIMARY KEY (`dni`);
ALTER TABLE `detalle_orden` ADD PRIMARY KEY (`id_detalle`), ADD KEY `id_orden` (`id_orden`), ADD KEY `id_producto` (`id_producto`);
ALTER TABLE `orden` ADD PRIMARY KEY (`id_orden`), ADD KEY `dni_cliente` (`dni_cliente`), ADD KEY `dni_usuario` (`dni_usuario`);
ALTER TABLE `producto` ADD PRIMARY KEY (`id_producto`), ADD KEY `id_categoria` (`id_categoria`);
ALTER TABLE `usuario` ADD PRIMARY KEY (`dni`);

ALTER TABLE `detalle_orden` MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

ALTER TABLE `detalle_orden`
  ADD CONSTRAINT `detalle_orden_ibfk_1` FOREIGN KEY (`id_orden`) REFERENCES `orden` (`id_orden`),
  ADD CONSTRAINT `detalle_orden_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`);

ALTER TABLE `orden`
  ADD CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`dni_cliente`) REFERENCES `cliente` (`dni`),
  ADD CONSTRAINT `orden_ibfk_2` FOREIGN KEY (`dni_usuario`) REFERENCES `usuario` (`dni`);

ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`);

COMMIT;
