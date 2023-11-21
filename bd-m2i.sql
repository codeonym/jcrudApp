-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 21, 2023 at 12:18 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bd_m2i`
--
CREATE DATABASE IF NOT EXISTS `bd_m2i` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bd_m2i`;


-- Create User
CREATE USER 'admin'@'%' IDENTIFIED BY 'master2022';

-- Grant All Privileges on Database
GRANT ALL PRIVILEGES ON bd_m2i.* TO 'admin'@'%';

-- Grant the Right to Connect
GRANT USAGE ON *.* TO 'admin'@'%';

-- Flush Privileges to Apply Changes
FLUSH PRIVILEGES;


-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE `absence` (
  `absence_id` int(10) NOT NULL,
  `absence_date` datetime NOT NULL,
  `etudiant_id` int(10) NOT NULL,
  `module_id` int(6) NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`absence_id`, `absence_date`, `etudiant_id`, `module_id`) VALUES
(1, '2023-11-05 16:13:21', 2, 1),
(2, '2023-11-05 16:13:21', 2, 3),
(3, '2023-11-14 16:13:21', 2, 6),
(4, '2023-10-19 16:13:21', 3, 1),
(5, '2023-11-12 16:13:21', 4, 5),
(6, '2023-11-08 16:13:21', 5, 1),
(7, '2023-10-25 16:13:21', 5, 3),
(8, '2023-11-10 16:13:21', 7, 3),
(9, '2023-10-23 16:13:21', 7, 4),
(10, '2023-11-05 16:13:21', 7, 5),
(11, '2023-11-17 16:13:21', 7, 6),
(12, '2023-10-28 16:13:21', 8, 4),
(13, '2023-11-07 16:13:21', 8, 5),
(14, '2023-10-22 16:13:21', 10, 6),
(15, '2023-10-21 16:13:21', 11, 1),
(16, '2023-10-27 16:13:21', 14, 4),
(17, '2023-11-11 16:13:21', 14, 5),
(18, '2023-10-25 16:13:21', 15, 3),
(19, '2023-11-02 16:13:21', 17, 1),
(20, '2023-11-15 16:13:21', 18, 4),
(21, '2023-11-11 16:13:21', 20, 1),
(22, '2023-11-20 09:00:00', 1, 2),
(23, '2023-12-12 10:00:00', 26, 1),
(24, '2023-12-12 09:00:00', 26, 1),
(25, '2023-10-10 09:00:00', 27, 2);

-- --------------------------------------------------------

--
-- Table structure for table `etudiants`
--

CREATE TABLE `etudiants` (
  `etudiant_id` int(10) NOT NULL,
  `nom` varchar(32) NOT NULL,
  `prenom` varchar(32) NOT NULL,
  `date_naissance` date NOT NULL,
  `cne` varchar(10) NOT NULL,
  `adresse` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `etudiants`
--

INSERT INTO `etudiants` (`etudiant_id`, `nom`, `prenom`, `date_naissance`, `cne`, `adresse`) VALUES
(1, 'ABOUZIA', 'NADIA', '2001-05-08', 'A200583100', '1200 Rue Example, City'),
(2, 'ARABAH', 'SOUHAILA', '2000-10-30', 'B200103000', '456 Street Example, City'),
(3, 'BADRA', 'AMAL', '2001-05-08', 'C200583300', '789 Boulevard Example, City'),
(4, 'BARIGOU', 'FATIMA EZZAHRA', '2001-04-02', 'D200579200', '101 Avenue Example, City'),
(5, 'BASBOUS', 'CHARAF', '2001-02-17', 'E200321700', '202 Lane Example, City'),
(6, 'BELFEDIL', 'ASMA', '2001-03-30', 'F180033000', '303 Court Example, City'),
(7, 'BOUHMALA', 'FATIMA-EZZAHRA', '2001-05-17', 'G180056700', '404 Drive Example, City'),
(8, 'BOUNAGA', 'YOUSSFE', '2000-06-07', 'H200067700', '505 Street Example, City'),
(9, 'BOYA', 'RACHIDA', '2001-08-17', 'I200189700', '606 Boulevard Example, City'),
(10, 'CHEIKH', 'ABDERRAHYM', '2000-12-15', 'J180388600', '707 Avenue Example, City'),
(11, 'CHTIOUI', 'IHSSANE', '2001-08-30', 'K170083000', '808 Lane Example, City'),
(12, 'DENSALI', 'ABDELHAMID', '2002-07-07', 'L190370700', '909 Street Example, City'),
(13, 'EL ASSAL', 'ASMAE', '2000-10-10', 'M180093300', '1010 Boulevard Example, City'),
(14, 'EL HASSANI', 'FATIHA', '2002-08-15', 'N200583500', '1111 Avenue Example, City'),
(15, 'EL MOUATE', 'SALMA', '1999-11-03', 'O190319200', '1212 Lane Example, City'),
(16, 'EL MOUSTAID', 'NADA', '2000-08-09', 'P200580900', '1313 Street Example, City'),
(17, 'ERREDNAOUI', 'WIDAD', '2000-09-25', 'Q170175700', '1414 Boulevard Example, City'),
(18, 'ES-SAMY', 'SIHAM', '2002-10-10', 'R190119200', '1515 Avenue Example, City'),
(19, 'EZZOUYN', 'HANANE', '2001-11-10', 'S200311000', '1616 Lane Example, City'),
(20, 'HANABI', 'GHITA', '2002-12-15', 'T200121500', '1717 Street Example, City'),
(21, 'BERKANI', 'AYOUB', '2001-01-25', 'H120018400', '785 Street Example, City'),
(22, 'RAMDANI', 'SANAE', '1999-01-31', 'H145236781', '123 Rue Example, City'),
(26, 'ILYAS', 'OUJDI', '2001-12-01', 'H120018444', '1222 Rue Example, City'),
(27, 'ALLALI', 'SAMIR', '2001-12-12', 'H120012000', '123 Avenu Example, City');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(6) NOT NULL,
  `module_nom` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `module_nom`) VALUES
(1, 'Machine Learning'),
(2, 'Jakarta EE'),
(3, 'Technologies IP et IoT'),
(4, 'Cryptographie appliquée et Sécurité des réseaux'),
(5, 'Anglais Technique'),
(6, 'BD Avancées et BD NoSQL');

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `note_id` int(6) NOT NULL,
  `note` int(2) NOT NULL,
  `etudiant_id` int(10) NOT NULL,
  `module_id` int(6) NOT NULL
) ;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`note_id`, `note`, `etudiant_id`, `module_id`) VALUES
(1, 7, 1, 1),
(2, 17, 1, 2),
(3, 12, 1, 3),
(4, 7, 1, 4),
(5, 17, 1, 5),
(6, 2, 1, 6),
(7, 2, 2, 1),
(8, 5, 2, 2),
(9, 17, 2, 3),
(10, 11, 2, 4),
(11, 3, 2, 5),
(12, 4, 2, 6),
(13, 10, 3, 1),
(14, 17, 3, 2),
(15, 17, 3, 3),
(16, 13, 3, 4),
(17, 12, 3, 5),
(18, 2, 3, 6),
(19, 14, 4, 1),
(20, 5, 4, 2),
(21, 4, 4, 3),
(22, 4, 4, 4),
(23, 8, 4, 5),
(24, 9, 4, 6),
(25, 20, 5, 1),
(26, 11, 5, 2),
(27, 17, 5, 3),
(28, 10, 5, 4),
(29, 2, 5, 5),
(30, 19, 5, 6),
(31, 7, 6, 1),
(32, 19, 6, 2),
(33, 15, 6, 3),
(34, 18, 6, 4),
(35, 3, 6, 5),
(36, 3, 6, 6),
(37, 4, 7, 1),
(38, 11, 7, 2),
(39, 4, 7, 3),
(40, 9, 7, 4),
(41, 11, 7, 5),
(42, 9, 7, 6),
(43, 10, 8, 1),
(44, 4, 8, 2),
(45, 12, 8, 3),
(46, 5, 8, 4),
(47, 10, 8, 5),
(48, 15, 8, 6),
(49, 6, 9, 1),
(50, 3, 9, 2),
(51, 19, 9, 3),
(52, 5, 9, 4),
(53, 10, 9, 5),
(54, 14, 9, 6),
(55, 0, 10, 1),
(56, 19, 10, 2),
(57, 13, 10, 3),
(58, 9, 10, 4),
(59, 4, 10, 5),
(60, 16, 10, 6),
(61, 6, 11, 1),
(62, 4, 11, 2),
(63, 0, 11, 3),
(64, 10, 11, 4),
(65, 9, 11, 5),
(66, 15, 11, 6),
(67, 7, 12, 1),
(68, 9, 12, 2),
(69, 5, 12, 3),
(70, 16, 12, 4),
(71, 8, 12, 5),
(72, 10, 12, 6),
(73, 5, 13, 1),
(74, 17, 13, 2),
(75, 8, 13, 3),
(76, 10, 13, 4),
(77, 7, 13, 5),
(78, 4, 13, 6),
(79, 0, 14, 1),
(80, 9, 14, 2),
(81, 5, 14, 3),
(82, 19, 14, 4),
(83, 17, 14, 5),
(84, 11, 14, 6),
(85, 2, 15, 1),
(86, 19, 15, 2),
(87, 7, 15, 3),
(88, 17, 15, 4),
(89, 3, 15, 5),
(90, 7, 15, 6),
(91, 7, 16, 1),
(92, 13, 16, 2),
(93, 3, 16, 3),
(94, 17, 16, 4),
(95, 15, 16, 5),
(96, 7, 16, 6),
(97, 6, 17, 1),
(98, 13, 17, 2),
(99, 4, 17, 3),
(100, 1, 17, 4),
(101, 16, 17, 5),
(102, 13, 17, 6),
(103, 20, 18, 1),
(104, 19, 18, 2),
(105, 14, 18, 3),
(106, 13, 18, 4),
(107, 4, 18, 5),
(108, 3, 18, 6),
(109, 2, 19, 1),
(110, 19, 19, 2),
(111, 12, 19, 3),
(112, 1, 19, 4),
(113, 8, 19, 5),
(114, 18, 19, 6),
(115, 6, 20, 1),
(116, 17, 20, 2),
(117, 4, 20, 3),
(118, 12, 20, 4),
(119, 5, 20, 5),
(120, 11, 20, 6),
(128, 12, 21, 1),
(129, 13, 21, 2),
(130, 14, 21, 3),
(131, 12, 21, 4),
(132, 16, 21, 5),
(133, 11, 21, 6),
(134, 12, 22, 1),
(135, 16, 22, 2),
(136, 18, 22, 3),
(137, 17, 22, 4),
(138, 20, 22, 5),
(139, 10, 22, 6),
(140, 9, 26, 1),
(141, 10, 26, 2),
(142, 12, 26, 3),
(143, 12, 26, 4),
(144, 10, 26, 5),
(145, 10, 26, 6),
(146, 15, 27, 1),
(147, 15, 27, 2),
(148, 15, 27, 3),
(149, 16, 27, 4),
(150, 18, 27, 5),
(151, 16, 27, 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`) VALUES
(1, 'E.M.Jaara', 'e98de538b5136ecd3c3d1fcff9a0f637d3977a6b66e01f613f45f50b158334d8'),
(2, 'A.Lakhouaja', 'e98de538b5136ecd3c3d1fcff9a0f637d3977a6b66e01f613f45f50b158334d8'),
(3, 'O.Mousaoui', 'e98de538b5136ecd3c3d1fcff9a0f637d3977a6b66e01f613f45f50b158334d8'),
(4, 'K.Laaroussi', 'e98de538b5136ecd3c3d1fcff9a0f637d3977a6b66e01f613f45f50b158334d8'),
(5, 'S.Mazouz', 'e98de538b5136ecd3c3d1fcff9a0f637d3977a6b66e01f613f45f50b158334d8');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absence`
--
ALTER TABLE `absence`
  ADD PRIMARY KEY (`absence_id`),
  ADD KEY `etudiant_id_idx` (`etudiant_id`),
  ADD KEY `module_id_idx` (`module_id`);

--
-- Indexes for table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`etudiant_id`),
  ADD UNIQUE KEY `cne_UNIQUE` (`cne`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_id`),
  ADD KEY `etudiant_id_idx` (`etudiant_id`),
  ADD KEY `module_id_idx` (`module_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absence`
--
ALTER TABLE `absence`
  MODIFY `absence_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `etudiants`
--
ALTER TABLE `etudiants`
  MODIFY `etudiant_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `note_id` int(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `absence_etudiant_fk` FOREIGN KEY (`etudiant_id`) REFERENCES `etudiants` (`etudiant_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `absence_module_fk` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_etudiant_fk` FOREIGN KEY (`etudiant_id`) REFERENCES `etudiants` (`etudiant_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `notes_module_fk` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
