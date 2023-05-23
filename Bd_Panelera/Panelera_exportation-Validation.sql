-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 23, 2023 at 08:00 AM
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
-- Table structure for table `Crear_UsuarioDTO`
--

CREATE TABLE `Crear_UsuarioDTO` (
  `ID` int(11) NOT NULL,
  `Full_Name` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Phone_Number` int(11) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `User_Name` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Fecha_nacimiento` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Crear_UsuarioDTO`
--

INSERT INTO `Crear_UsuarioDTO` (`ID`, `Full_Name`, `Email`, `Phone_Number`, `Address`, `User_Name`, `Password`, `Fecha_nacimiento`) VALUES
(1, 'Michael J Thomas', 'Thomas23@Homecenter.com', 2134567890, '3378 Agata St', 'ThomasJK.90', '123456', '2013-05-09'),
(2, 'Fabian Perez', 'Fabain@gmail.com', 0, '2345Bonde St', 'Fabian23', '1234567', '1982-06-08'),
(3, 'carqwo', 'wqweoqlwe', 0, 'weqeqeqe', 'adadad', 'adada', '2023-05-05'),
(4, 'Carl Rose', 'Aeeaead@gmail.com', 0, '234 sasdf St', 'Carl7890', '12345', '2023-05-12'),
(5, 'Delton Baker', 'Bakerdelton24@gmail.com', 0, '2345 Cove St', 'Bakerdelton23', '123456789', '2023-05-12'),
(6, 'Sue Vladimir', 'Vladimirs45@gmail.com', 0, '2348 Dilk Tr', 'Vladimirs45', '2345678', '2023-05-19'),
(7, 'Vanessa Pomare', 'pormarev37@gmail.com', 0, '2345 sea side St', 'Pomarev36', '12345678', '1979-04-12'),
(8, 'Bryan Colon', 'eatman21.c@gmail.com', 0, '2345 asdf ST', 'Bryanc23', '1234567', '2023-05-12'),
(9, 'Jessica Pomare', 'Jessica.Pomare@gmail.com', 0, '2344 Cove St', 'Jessicap23', '12345678', '2023-05-12'),
(10, 'Jessica Pomare', 'Jessica.Pomare@gmail.com', 0, '2344 Cove St', 'Jessicap23', '12345678', '2023-05-12'),
(11, 'Jessica Pomare', 'Jessica.Pomaregmail.com', 0, '2344 Cove St', 'Jessicap23', '12345678', '2023-05-12'),
(12, 'Carl Brown', 'eatman@gmail.com', 0, '2345 Side St', 'Brown.c23', 'J++59kdujhpvAoO+v45pSw==', '1987-05-08'),
(13, 'Chris Hoofman', 'Hoofman21@gmail.com', 0, '2345 Sid St', 'Hoofman23', '9QyqksgfBxO4kJPMEKJXhQ==', '1976-02-10'),
(14, 'Carol Bao', 'Bao@gmail.com', 0, '2345 Codjhjj st', 'Bao23', '4P5vVId7ywzCwC6K6HIZzA==', '1987-02-12'),
(15, 'Frank Pomare', 'Pomare.23@gmail.com', 0, '2345 Cove Sd', 'Frank', '7QhCNdBdiFNu1aDg1lGpTA==', '1985-05-06');

-- --------------------------------------------------------

--
-- Table structure for table `Create_OrderDTO`
--

CREATE TABLE `Create_OrderDTO` (
  `employe_ID` int(11) NOT NULL,
  `full_name` varchar(45) NOT NULL,
  `product_type` varchar(45) NOT NULL,
  `amount_order` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `currency` varchar(45) NOT NULL,
  `Total` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shipping_type` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Create_OrderDTO`
--

INSERT INTO `Create_OrderDTO` (`employe_ID`, `full_name`, `product_type`, `amount_order`, `destination`, `date`, `currency`, `Total`, `shipping_type`) VALUES
(12351, 'Lodovico Wyles', 'Panela', '4', '83 Sullivan Plaza', '2021-08-13', 'Dollar', '$10.00', 'Plane'),
(12352, 'Raquela Kilmurray', 'Panela', '5', '01295 Ilene Avenue', '2022-10-22', 'Dollar', '$8.28', 'Plane'),
(12353, 'Cora Rivard', 'Panela', '5', '63036 Forest Run Plaza', '2020-01-12', 'Dollar', '$20.77', 'Plane'),
(12354, 'Rockwell Stirgess', 'Panela', '3', '1601 Merry Hill', '2019-06-15', 'Dollar', '$19.49', 'Plane'),
(12355, 'Klemens Jopp', 'Panela', '3', '6 Ridgeway Park', '2019-10-07', 'Dollar', '$18.04', 'Plane'),
(12356, 'Rose Baker', 'Syrup', '300', '2390 W Vanty Dr', '2019-08-31', 'Euro', '1650000.0', 'Boat'),
(12357, 'Jack Collip', 'Panela', '5', '37 Sage Point', '2018-05-29', 'Dollar', '$23.73', 'Plane'),
(12358, 'Madlen Saphir', 'Panela', '3', '537 Susan Crossing', '2023-02-11', 'Dollar', '$21.06', 'Plane'),
(12359, 'Freeland Orhtmann', 'Panela', '5', '4775 Everett Center', '2018-12-23', 'Dollar', '$18.04', 'Plane'),
(12360, 'Inigo Dougal', 'Panela', '1', '02 Rutledge Place', '2022-01-05', 'Euro', '5500.0', 'Plane'),
(12361, 'Rebe Tythe', 'Panela', '3', '6958 Eagan Park', '2022-12-17', 'Dollar', '$16.62', 'Plane'),
(12362, 'Leanor Jacquest', 'Panela', '2', '15 Johnson Plaza', '2018-06-17', 'Dollar', '$15.62', 'Plane'),
(12363, 'Ebba Alastair', 'Panela', '1', '5 Pearson Court', '2019-10-10', 'Dollar', '$15.89', 'Plane'),
(12364, 'Thorny Teggart', 'Panela', '4', '27934 Londonderry Terrace', '2019-09-24', 'Dollar', '$11.03', 'Plane'),
(12365, 'Fairlie Geikie', 'Panela', '3', '99 Mcbride Alley', '2021-02-23', 'Dollar', '$6.16', 'Plane'),
(12367, 'Drew Brown', 'Panela', '1', '0503 Linden Lane', '2022-07-31', 'Dollar', '4500.0', 'Boat'),
(12368, 'Jandy Bolgar', 'Panela', '1', '6312 Warrior Lane', '2020-06-09', 'Dollar', '$8.70', 'Plane'),
(12369, 'Normy Hacquard', 'Panela', '1', '836 Meadow Valley Trail', '2019-05-14', 'Dollar', '$8.73', 'Plane'),
(12370, 'Gav Graham', 'Ethanol', '3', '132 Dwight Pass', '2021-07-20', 'Dollar', '$25.04', 'Plane'),
(12371, 'Romonda Meijer', 'Ethanol', '5', '0013 Jana Crossing', '2022-05-15', 'Dollar', '$25.01', 'Plane'),
(12372, 'Jeremiah Elliss', 'Ethanol', '3', '76428 Karstens Center', '2020-09-16', 'Dollar', '$15.03', 'Plane'),
(12373, 'Hana Kleinzweig', 'Ethanol', '3', '0 American Ash Court', '2019-05-16', 'Dollar', '$22.37', 'Plane'),
(12374, 'Koenraad Duquesnay', 'Ethanol', '5', '87 Emmet Circle', '2018-06-08', 'Dollar', '$5.34', 'Plane'),
(12375, 'Ingunna Feasey', 'Ethanol', '4', '57 Kedzie Crossing', '2020-07-03', 'Dollar', '$18.25', 'Plane'),
(12376, 'Gilburt Lorincz', 'Ethanol', '5', '354 Knutson Point', '2021-03-27', 'Dollar', '$18.49', 'Plane'),
(12377, 'Bryce Bore', 'Ethanol', '1', '0 Basil Pass', '2021-06-25', 'Dollar', '$19.75', 'Plane'),
(12378, 'Paddy Reddihough', 'Panela', '4', '95 Dakota Hill', '2019-07-07', 'Dollar', '18000.0', 'Plane'),
(12379, 'Krystal Mussotti', 'Ethanol', '2', '0 Northport Parkway', '2019-04-02', 'Dollar', '$15.24', 'Plane'),
(12380, 'Arel Padson', 'Ethanol', '2', '41 Armistice Alley', '2018-11-07', 'Dollar', '$28.90', 'Plane'),
(12381, 'Rip Castella', 'Ethanol', '2', '0 Lillian Road', '2018-08-26', 'Euro', '$17.57', 'Plane'),
(12382, 'Phaidra Bewshea', 'Ethanol', '1', '70 Buhler Drive', '2021-10-13', 'Euro', '$27.63', 'Plane'),
(12383, 'Barry Royston', 'Ethanol', '4', '275 Declaration Plaza', '2019-08-02', 'Euro', '$6.71', 'Plane'),
(12384, 'Tamra Velte', 'Ethanol', '5', '6441 Armistice Road', '2020-01-21', 'Euro', '$15.28', 'Plane'),
(12385, 'Merrel Saffe', 'Ethanol', '3', '28627 Sauthoff Center', '2019-03-26', 'Euro', '$12.51', 'Plane'),
(12386, 'Harriett Denisyev', 'Ethanol', '1', '3472 Rieder Circle', '2022-07-03', 'Euro', '$6.94', 'Plane'),
(12387, 'Pauline Tibald', 'Ethanol', '4', '931 Dixon Plaza', '2020-11-28', 'Euro', '$6.43', 'Plane'),
(12388, 'Gilligan Blew', 'Wine', '4', '0864 Raven Drive', '2022-03-17', 'Euro', '$9.47', 'Plane'),
(12389, 'Darby Martt', 'Wine', '1', '9 4th Way', '2021-04-05', 'Euro', '$6.80', 'Plane'),
(12390, 'Alys Mahaffey', 'Wine', '4', '4 Oak Valley Point', '2018-07-19', 'Euro', '$10.69', 'Plane'),
(12391, 'Hamel Clouter', 'Wine', '5', '253 Nova Street', '2021-10-15', 'Euro', '$5.24', 'Plane'),
(12392, 'Mar Findlater', 'Wine', '3', '2026 Bluejay Alley', '2019-09-06', 'Euro', '$24.89', 'Plane'),
(12393, 'Dinah Collecott', 'Wine', '4', '646 Northport Point', '2019-04-30', 'Euro', '$23.00', 'Plane'),
(12394, 'Vernen Rumble', 'Wine', '1', '87 Northland Terrace', '2020-09-28', 'Euro', '$17.17', 'Plane'),
(12395, 'Barnard Dolder', 'Wine', '1', '40113 Calypso Hill', '2019-03-22', 'Euro', '$25.60', 'Plane'),
(12396, 'Cayla Iley', 'Wine', '1', '512 Nobel Circle', '2023-02-11', 'Euro', '$16.64', 'Plane'),
(12397, 'Cher Senton', 'Wine', '3', '17 Ridgeview Way', '2018-05-09', 'Euro', '$13.59', 'Boat'),
(12398, 'Bartholomeo Syseland', 'Wine', '5', '279 Fuller Avenue', '2018-10-14', 'Euro', '$9.10', 'Boat'),
(12399, 'Rois McFater', 'Wine', '1', '275 Del Sol Terrace', '2021-09-11', 'Euro', '$25.82', 'Boat'),
(12400, 'Holli Penna', 'Wine', '1', '2462 Dunning Street', '2023-04-22', 'Euro', '$18.24', 'Boat'),
(12401, 'Tandy Haws', 'Wine', '5', '5 Holmberg Circle', '2018-10-15', 'Euro', '$22.09', 'Boat'),
(12402, 'Rickard Krinks', 'Wine', '5', '05 Center Street', '2020-12-16', 'Euro', '$24.38', 'Boat'),
(12403, 'Dyane Devonald', 'Wine', '3', '8 Prairie Rose Alley', '2023-04-20', 'Euro', '$5.10', 'Boat'),
(12404, 'Andres Gillease', 'Syrup', '5', '33 Bellgrove Road', '2018-07-06', 'Euro', '$23.45', 'Boat'),
(12405, 'Diane-marie Gosz', 'Syrup', '4', '376 Pond Point', '2018-08-15', 'Euro', '$18.49', 'Boat'),
(12406, 'Skippy Jouandet', 'Syrup', '2', '22 Kings Pass', '2023-01-29', 'Euro', '$8.55', 'Boat'),
(12407, 'Iseabal Buesden', 'Syrup', '1', '48 Atwood Junction', '2018-11-01', 'Euro', '$29.21', 'Boat'),
(12408, 'Graig Sagg', 'Syrup', '5', '327 Roth Parkway', '2020-04-19', 'Euro', '$17.89', 'Boat'),
(12409, 'Leah Duddy', 'Syrup', '2', '32 Victoria Center', '2022-04-03', 'Euro', '$5.11', 'Boat'),
(12410, 'Wendell Blackader', 'Syrup', '5', '06811 Eagan Lane', '2022-09-03', 'Euro', '$10.94', 'Boat'),
(12411, 'Myrle Martinson', 'Syrup', '2', '787 Manufacturers Street', '2019-12-12', 'Euro', '$20.70', 'Boat'),
(12412, 'Britt O\'Hdirscoll', 'Syrup', '2', '17 Burning Wood Road', '2018-10-19', 'Euro', '$14.04', 'Boat'),
(12413, 'Atlanta Prine', 'Syrup', '2', '70 Ludington Plaza', '2020-09-23', 'Euro', '$18.57', 'Boat'),
(12414, 'Bradney McGill', 'Syrup', '3', '97382 4th Place', '2021-10-06', 'Euro', '$18.73', 'Boat'),
(12415, 'Oralia Gynni', 'Syrup', '3', '263 Steensland Trail', '2023-03-13', 'Euro', '$16.60', 'Boat'),
(12416, 'Goldia Oliver-Paull', 'Syrup', '4', '85 Tony Point', '2021-11-21', 'Euro', '$12.56', 'Boat'),
(12417, 'Luella Bovis', 'Syrup', '1', '44 Norway Maple Center', '2020-09-10', 'Euro', '$9.83', 'Boat'),
(12418, 'Mikey Batiste', 'Syrup', '2', '55878 Namekagon Court', '2021-07-11', 'Euro', '$17.64', 'Boat'),
(12419, 'Clare Marconi', 'Syrup', '1', '5519 Meadow Vale Way', '2020-11-16', 'Euro', '$13.27', 'Boat'),
(12420, 'Dallas Aleksandrov', 'Syrup', '2', '84533 Beilfuss Lane', '2019-06-26', 'Euro', '$18.44', 'Boat'),
(12421, 'Willamina Massenhove', 'Syrup', '3', '2986 Lien Drive', '2019-04-18', 'Euro', '$14.32', 'Boat'),
(12422, 'Orran Deners', 'Syrup', '1', '78 Toban Avenue', '2019-10-26', 'Euro', '$28.07', 'Boat'),
(12423, 'Delainey Louys', 'Syrup', '2', '19 Becker Hill', '2020-11-21', 'Euro', '$16.51', 'Boat'),
(12424, 'Zerk Spatari', 'Syrup', '5', '34179 Dorton Pass', '2022-12-05', 'Dollar', '$18.84', 'Boat'),
(12425, 'Aggi Ghiriardelli', 'Syrup', '2', '740 Eggendart Drive', '2018-11-21', 'Dollar', '$8.25', 'Boat'),
(12426, 'Charity Pawsey', 'Syrup', '2', '288 Marcy Hill', '2020-02-20', 'Dollar', '$9.26', 'Boat'),
(12427, 'Dirk Simpkiss', 'Syrup', '4', '8020 Wayridge Lane', '2022-02-12', 'Dollar', '$28.71', 'Boat'),
(12428, 'Charlton Ledford', 'Syrup', '1', '39897 Sachs Parkway', '2020-08-05', 'Dollar', '$16.47', 'Boat'),
(12429, 'Nicoline Andriulis', 'Syrup', '2', '13 Sycamore Trail', '2023-03-31', 'Dollar', '$29.08', 'Boat'),
(12430, 'Alexina Whopples', 'Sugar', '1', '81 Dahle Alley', '2020-07-23', 'Dollar', '$8.39', 'Boat'),
(12431, 'Garwood Bartolommeo', 'Sugar', '2', '07897 Commercial Hill', '2019-10-01', 'Dollar', '$27.16', 'Boat'),
(12432, 'Any Senogles', 'Sugar', '2', '45527 Ludington Circle', '2019-09-25', 'Dollar', '$22.19', 'Boat'),
(12433, 'Brittany Bridat', 'Sugar', '1', '42 Welch Junction', '2019-05-07', 'Dollar', '$9.88', 'Boat'),
(12434, 'Alexi Jimmison', 'Sugar', '3', '13727 Larry Park', '2020-01-20', 'Dollar', '$21.68', 'Boat'),
(12435, 'Belita Charlin', 'Sugar', '5', '60 Reindahl Point', '2021-09-16', 'Dollar', '$26.03', 'Boat'),
(12436, 'Charlot Gaitskill', 'Sugar', '4', '108 Artisan Point', '2022-06-03', 'Dollar', '$9.42', 'Boat'),
(12437, 'Marcela Giraldo', 'Sugar', '5', '76717 Mosinee Avenue', '2021-04-01', 'Dollar', '$6.55', 'Boat'),
(12438, 'Maddy Nicklin', 'Sugar', '2', '3 New Castle Terrace', '2020-08-06', 'Dollar', '$10.60', 'Boat'),
(12439, 'Kally Holby', 'Sugar', '3', '5391 Dakota Lane', '2022-09-25', 'Dollar', '$10.03', 'Boat'),
(12440, 'Selia Allchorne', 'Sugar', '2', '172 Corry Street', '2022-06-29', 'Dollar', '$15.22', 'Boat'),
(12441, 'Blaire Estcourt', 'Sugar', '5', '7 Sugar Circle', '2022-12-28', 'Dollar', '$24.12', 'Boat'),
(12442, 'Delila Bellocht', 'Sugar', '2', '32293 Shasta Pass', '2018-07-02', 'Dollar', '$24.25', 'Boat'),
(12443, 'Derron Karslake', 'Sugar', '2', '4669 Northridge Center', '2022-08-16', 'Dollar', '$12.50', 'Boat'),
(12444, 'Carr Kinnane', 'Sugar', '4', '9 Summit Lane', '2022-06-13', 'Dollar', '$26.35', 'Boat'),
(12445, 'Kim Ferenczi', 'Sugar', '5', '53288 Di Loreto Parkway', '2018-11-04', 'Dollar', '$25.33', 'Boat'),
(12446, 'Evania Leon', 'Sugar', '1', '83028 Dottie Point', '2020-09-07', 'Dollar', '$22.13', 'Boat'),
(12447, 'Lorine Fulton', 'Sugar', '2', '7 Jana Point', '2021-08-07', 'Dollar', '$26.49', 'Boat'),
(12448, 'Shanan Lanphier', 'Sugar', '3', '421 Loomis Point', '2023-03-01', 'Dollar', '$29.88', 'Boat'),
(12449, 'Florenza Jaskowicz', 'Sugar', '2', '91449 Sugar Lane', '2020-03-22', 'Dollar', '$5.08', 'Boat'),
(12450, 'Culver Dorricott', 'Sugar', '4', '90583 Carberry Terrace', '2022-01-14', 'Dollar', '$11.79', 'Boat'),
(12451, 'Farony Rose', 'Panela', '24', '2345 1234', NULL, 'Euro', '132000.0', 'Plane'),
(12452, 'Rox ann Webber', 'Panela', '34', '123 Denvert St', NULL, 'Euro', '187000.0', 'Plane'),
(12453, 'Mark Scam', 'Ethanol', '34', '458 W Brook St', NULL, 'Euro', '187000.0', 'Plane'),
(12454, 'Victor Sanders', 'Wine', '23', '5667 W Blackrose', NULL, 'Dollar', '103500.0', 'Boat'),
(12455, 'James Belton', 'Sugar', '54', ' 7689 fresh water', NULL, 'Dollar', '243000.0', 'Boat'),
(12456, 'Rose Baker', 'Syrup', '100', '2390 W Vanty Dr', NULL, 'Euro', '550000.0', 'Plane'),
(12458, 'Ferico Bardo', 'Ethanol', '300', '8907 Side by side', NULL, 'Dollar', '1350000.0', 'Plane'),
(12459, 'Demark Funzer', 'Panela', '100', '1234 Feaa', NULL, 'Dollar', '450000.0', 'Boat');

-- --------------------------------------------------------

--
-- Table structure for table `Login_DTO`
--

CREATE TABLE `Login_DTO` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Login_DTO`
--

INSERT INTO `Login_DTO` (`username`, `password`) VALUES
('cpuseyr.5', '123'),
('cpuseyr.5', '7651'),
('cpuseyr.5', '7896'),
('cpuseyr.5', '2341'),
('cpuseyr.5', '7865'),
('Bao23', '4P5vVId7ywzCwC6K6HIZzA=='),
('Frank', '7QhCNdBdiFNu1aDg1lGpTA==');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Crear_UsuarioDTO`
--
ALTER TABLE `Crear_UsuarioDTO`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `Create_OrderDTO`
--
ALTER TABLE `Create_OrderDTO`
  ADD PRIMARY KEY (`employe_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Crear_UsuarioDTO`
--
ALTER TABLE `Crear_UsuarioDTO`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `Create_OrderDTO`
--
ALTER TABLE `Create_OrderDTO`
  MODIFY `employe_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12462;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
