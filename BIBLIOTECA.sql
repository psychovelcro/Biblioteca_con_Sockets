-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 30-11-2021 a las 14:24:32
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `Biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `BIBLIOTECA`
--

CREATE TABLE `BIBLIOTECA` (
  `id` int(10) UNSIGNED NOT NULL,
  `ISBN` varchar(30) DEFAULT NULL,
  `TITULO` varchar(40) DEFAULT NULL,
  `AUTOR` varchar(40) DEFAULT NULL,
  `PRECIO` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `BIBLIOTECA`
--

INSERT INTO `BIBLIOTECA` (`id`, `ISBN`, `TITULO`, `AUTOR`, `PRECIO`) VALUES
(1, '978-84-8314', 'Codigo Limpio', 'Robert C. Martin', 47.45),
(2, '978-84-8315', 'Clean Architecture', 'Robert C. Martin', 31.37),
(3, '978-84-8316', 'Patrones de Diseño', 'Erich Gamma', 39.76),
(4, '978-84-8317', 'Refactoring', 'Fowler Martin', 49.39),
(5, '978-84-8318', 'The Pragmatic Programmer', 'David Thomas', 42.58),
(6, '978-84-8319', 'Estructura de datos', ' Mark Weiss', 54.95),
(14, '63749-342-223', 'La cabra 2', 'Pable henandez', 56.8),
(15, '63749-342-223', 'La cabra 2', 'Pable henandez', 56.8),
(16, '63749-342-223', 'La cabra 2', 'Pable henandez', 56.8),
(17, '63749-342-223', 'El pastor', 'Pedro Sanchez', 47.89),
(18, '63749-342-223', 'El pastor', 'Pedro Sanchez', 47.89);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `BIBLIOTECA`
--
ALTER TABLE `BIBLIOTECA`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `BIBLIOTECA`
--
ALTER TABLE `BIBLIOTECA`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
