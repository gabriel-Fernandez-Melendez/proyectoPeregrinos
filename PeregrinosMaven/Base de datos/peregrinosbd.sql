-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-11-2022 a las 17:38:42
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `peregrinosbd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carnet`
--

CREATE TABLE `carnet` (
  `id_peregrino` int(50) NOT NULL,
  `fecha_exp` date NOT NULL,
  `distancia` double NOT NULL,
  `n_vips` int(10) NOT NULL,
  `nombre_parada` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `carnet`
--

INSERT INTO `carnet` (`id_peregrino`, `fecha_exp`, `distancia`, `n_vips`, `nombre_parada`) VALUES
(1, '2022-11-03', 0, 0, 'La roca'),
(2, '2022-11-03', 0, 0, 'La cabaña'),
(3, '2022-11-03', 0, 2, 'La cabaña'),
(4, '2022-11-03', 0, 0, 'La cabaña'),
(5, '2022-11-03', 0, 0, 'La cabaña');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id` int(11) NOT NULL,
  `id_peregrino` int(50) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `usur` varchar(50) NOT NULL,
  `pass` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `credenciales`
--

INSERT INTO `credenciales` (`id`, `id_peregrino`, `tipo`, `usur`, `pass`) VALUES
(1, 1, 'Peregrino', 'esmabulock', 'esmabulock'),
(2, 2, 'Peregrino', 'john', 'clave'),
(3, 3, 'Peregrino', 'antonio', 'sepuede'),
(4, 4, 'Peregrino', 'ana', 'caminando'),
(5, 5, 'Peregrino', 'andrea', 'llegamos'),
(6, 6, 'Peregrino', 'john', 'cuidao');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estancias`
--

CREATE TABLE `estancias` (
  `id` int(50) NOT NULL,
  `id_peregrino` int(50) NOT NULL,
  `fecha` date NOT NULL,
  `vip` tinyint(1) NOT NULL,
  `nombre_parada` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `estancias`
--

INSERT INTO `estancias` (`id`, `id_peregrino`, `fecha`, `vip`, `nombre_parada`) VALUES
(1, 1, '2022-11-03', 0, 'La roca'),
(2, 1, '2022-11-03', 1, 'El pico'),
(3, 1, '2022-11-03', 1, 'Pastoral'),
(4, 1, '2022-11-03', 0, 'La cabaña'),
(5, 1, '2022-11-03', 0, 'Bar el caminante'),
(6, 2, '2022-11-03', 0, 'La roca'),
(7, 2, '2022-11-03', 0, 'Pastoral');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parada`
--

CREATE TABLE `parada` (
  `id` int(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `region` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `parada`
--

INSERT INTO `parada` (`id`, `nombre`, `region`) VALUES
(1, 'La roca', 'A'),
(2, 'El pico', 'G'),
(3, 'Pastoral', 'G'),
(4, 'La cabaña', 'G'),
(5, 'Bar el caminante', 'A');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peregrino`
--

CREATE TABLE `peregrino` (
  `nombre` varchar(50) NOT NULL,
  `nacionalidad` varchar(50) NOT NULL,
  `id` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `peregrino`
--

INSERT INTO `peregrino` (`nombre`, `nacionalidad`, `id`) VALUES
('esmabulock', 'Venezuela', 1),
('john', 'francia', 2),
('antonio', 'españa', 3),
('ana', 'españa', 4),
('andrea', 'alemania', 5),
('john', 'espaÃ±a', 6),


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visitas`
--

CREATE TABLE `visitas` (
  `id` int(50) NOT NULL,
  `id_peregrino` int(50) NOT NULL,
  `id_parada` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `visitas`
--

INSERT INTO `visitas` (`id`, `id_peregrino`, `id_parada`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 2, 1),
(7, 2, 2),
(8, 2, 3),
(9, 2, 4),
(10, 2, 5),
(11, 3, 1),
(12, 3, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carnet`
--
ALTER TABLE `carnet`
  ADD PRIMARY KEY (`id_peregrino`),
  ADD KEY `id_peregrino` (`id_peregrino`),
  ADD KEY `nombre_parada` (`nombre_parada`);

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_peregrino` (`id_peregrino`);

--
-- Indices de la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_peregrino` (`id_peregrino`);

--
-- Indices de la tabla `parada`
--
ALTER TABLE `parada`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `peregrino`
--
ALTER TABLE `peregrino`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `visitas`
--
ALTER TABLE `visitas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_peregrino` (`id_peregrino`),
  ADD KEY `id_parada` (`id_parada`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `estancias`
--
ALTER TABLE `estancias`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `parada`
--
ALTER TABLE `parada`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `peregrino`
--
ALTER TABLE `peregrino`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `visitas`
--
ALTER TABLE `visitas`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carnet`
--
ALTER TABLE `carnet`
  ADD CONSTRAINT `carnet_ibfk_1` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrino` (`id`);

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `credenciales_ibfk_1` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrino` (`id`);

--
-- Filtros para la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD CONSTRAINT `estancias_ibfk_1` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrino` (`id`);

--
-- Filtros para la tabla `visitas`
--
ALTER TABLE `visitas`
  ADD CONSTRAINT `visitas_ibfk_2` FOREIGN KEY (`id_parada`) REFERENCES `parada` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `visitas_ibfk_3` FOREIGN KEY (`id_peregrino`) REFERENCES `peregrino` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
