-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 24, 2023 at 02:38 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Panelera_exportation`
--

-- --------------------------------------------------------

--
-- Table structure for table `Create_OrderDTO`
--
drop table Create_OrderDTO;

CREATE TABLE `Create_OrderDTO` (
  `employe_ID` int(11) NOT NULL auto_increment,
  `full_name` varchar(45) NOT NULL,
  `product_type` varchar(45) NOT NULL,
  `amount_order` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `currency` varchar(45) NOT NULL,
  `Total` varchar(11) NOT NULL,
  `shipping_type` varchar(45) NOT NULL,
  primary key(employe_ID)
) 

--
-- Dumping data for table `Create_OrderDTO`
--

INSERT INTO `Create_OrderDTO` (`full_name`, `product_type`, `amount_order`, `destination`, `date`, `currency`, `Total`, `shipping_type`) VALUES
('Windy Rose', 'Sugar', '22', '1234 Den vert Co 54366', '2023-04-05', '', '', ''),
('Farony Rose', 'Suryp', '22', '1237 Jasper St', '2023-04-07', '', '', ''),
('Mary Bright', 'Sugar', '22', '2343 Port everglass', '2023-04-13', 'dollar', '', 'Boat'),
('Jason Pomare', 'Ethanol', '34', '2378 27th St', '2023-04-07', '', '', ''),
('Cristina Bent', 'panela', '22', '2345 Evert St Tx 34567', '2023-04-05', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `Login_DTO`
--

CREATE TABLE `Login_DTO` (
id int not null auto_increment,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  primary key (id)
)

--
-- Dumping data for table `Login_DTO`
--

INSERT INTO `Login_DTO` (`username`, `password`) VALUES
('Carol', '123'),
('1234', 'Rose'),
('3456', 'Brown'),
('Farony', '7651'),
('Juan', '7896'),
('Rose', '2341'),
('Sally', '7865');


