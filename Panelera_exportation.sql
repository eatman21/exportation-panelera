-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 24, 2023 at 02:38 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

/*SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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

/*CREATE TABLE `Login_DTO` (
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

--- DAtos ingresados
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Averill Finkle', 'Butter Ripple - Phillips', 4, 'Czech Republic', '10/05/2021', 'Koruna', '$25.01', 'WFK');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Harrie Wapples', 'Chips - Doritos', 4, 'Sweden', '01/10/2022', 'Krona', '$10.44', 'RRT');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Myrtie Martignon', 'Tray - Foam, Square 4 - S', 3, 'Netherlands', '18/06/2021', 'Euro', '$30.20', 'VOG');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Farrel Haffard', 'Tortillas - Flour, 12', 5, 'Indonesia', '12/11/2022', 'Rupiah', '$48.83', 'IRC');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Myrlene Aveson', 'Paste - Black Olive', 3, 'Madagascar', '28/12/2021', 'Ariary', '$16.18', 'OPS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Kirbie Shaw', 'Swiss Chard - Red', 3, 'Philippines', '09/09/2022', 'Peso', '$33.78', 'OSD');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Port Symmons', 'Champagne - Brights, Dry', 4, 'Japan', '03/02/2023', 'Yen', '$41.99', 'MXC');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Milly Puddicombe', 'Chicken Breast Halal', 1, 'Uganda', '30/09/2022', 'Shilling', '$5.68', 'YAY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Sue Flatt', 'Lettuce - Belgian Endive', 1, 'Netherlands', '08/02/2022', 'Euro', '$11.85', 'UKA');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Lebbie Wardroper', 'Canada Dry', 7, 'China', '09/04/2022', 'Yuan Renminbi', '$28.52', 'NME');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Marvin Formoy', 'Soda Water - Club Soda, 355 Ml', 5, 'Canada', '09/09/2022', 'Dollar', '$6.87', 'UKG');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Trenna Collymore', 'Bread Crumbs - Panko', 4, 'Russia', '28/12/2022', 'Ruble', '$34.23', 'EXM');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Claire Bunn', 'Mushroom - Chanterelle Frozen', 5, 'China', '01/03/2023', 'Yuan Renminbi', '$37.82', 'DWA');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Milt Habbijam', 'Beef - Top Butt Aaa', 6, 'Poland', '08/11/2021', 'Zloty', '$48.54', 'LKK');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Berkie Gallant', 'Vinegar - Cider', 8, 'Costa Rica', '24/11/2022', 'Colon', '$6.36', 'LUB');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Daven Aindrais', 'Chicken - White Meat With Tender', 2, 'China', '06/03/2022', 'Yuan Renminbi', '$37.36', 'PDR');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Gina Budgeon', 'Ostrich - Prime Cut', 1, 'Argentina', '03/04/2022', 'Peso', '$36.25', 'ACO');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ingemar Studman', 'Seaweed Green Sheets', 2, 'Indonesia', '07/04/2023', 'Rupiah', '$49.37', 'TTD');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Adair Druitt', 'Flavouring - Orange', 9, 'Brazil', '25/09/2022', 'Real', '$33.58', 'HGN');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Katie Simao', 'Soup - Base Broth Beef', 2, 'Portugal', '25/07/2022', 'Euro', '$47.15', 'VEL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Carolus Allmen', 'Chocolate Bar - Smarties', 3, 'Indonesia', '20/08/2021', 'Rupiah', '$25.74', 'GWA');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ed Feldberg', 'Truffle Cups - Brown', 2, 'China', '16/08/2021', 'Yuan Renminbi', '$28.68', 'ILZ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Kristos Lebarree', 'Bread - Ciabatta Buns', 6, 'Indonesia', '23/03/2022', 'Rupiah', '$24.52', 'TGS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Abel Rolse', 'Food Colouring - Red', 5, 'China', '24/12/2021', 'Yuan Renminbi', '$7.32', 'CRW');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Artair Klimochkin', 'Steel Wool', 8, 'Togo', '24/09/2021', 'Franc', '$30.09', 'WTT');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Scarface Hawkeridge', 'Edible Flower - Mixed', 10, 'Brazil', '27/02/2023', 'Real', '$15.04', 'OVB');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Gerrie Gitsham', 'Skirt - 29 Foot', 5, 'Russia', '09/10/2021', 'Ruble', '$8.72', 'FKQ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Jase Christofe', 'Tea - Black Currant', 10, 'Qatar', '09/02/2022', 'Rial', '$23.69', 'LTT');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Auberon Bowra', 'Wakami Seaweed', 9, 'China', '19/03/2023', 'Yuan Renminbi', '$18.98', 'ROR');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Wernher Barbe', 'Cream Of Tartar', 9, 'Guatemala', '25/05/2021', 'Quetzal', '$14.67', 'BWI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ana Argent', 'Jameson - Irish Whiskey', 5, 'Russia', '04/01/2023', 'Ruble', '$27.78', 'JIK');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Finley Anthony', 'Soup - Campbells', 6, 'Greece', '21/09/2021', 'Euro', '$12.49', 'ANI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Arnold Leidecker', 'Beef - Rib Eye Aaa', 8, 'China', '17/08/2021', 'Yuan Renminbi', '$43.89', 'GXQ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Filberto MacDonell', 'Doilies - 10, Paper', 10, 'China', '06/11/2022', 'Yuan Renminbi', '$39.47', 'PNA');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Darrelle Titcom', 'Pork - Suckling Pig', 2, 'Chile', '16/09/2022', 'Peso', '$12.51', 'DTL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Wylma Walenta', 'Cookies - Assorted', 2, 'Russia', '19/06/2022', 'Ruble', '$20.98', 'CTI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ninnetta Sprasen', 'Onions - Cooking', 1, 'Poland', '28/08/2021', 'Zloty', '$36.67', 'RDG');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Kahaleel Winkle', 'Wine - Saint - Bris 2002, Sauv', 9, 'China', '13/02/2022', 'Yuan Renminbi', '$21.32', 'CNN');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Suzette O''Donohue', 'Flour - Whole Wheat', 9, 'Montenegro', '27/04/2022', 'Euro', '$10.07', 'LUC');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Fayth Lowers', 'Lid Tray - 12in Dome', 2, 'Sweden', '25/03/2022', 'Krona', '$8.03', 'NTN');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Kaiser Ruppele', 'Wine - Red, Wolf Blass, Yellow', 7, 'Kenya', '30/05/2022', 'Shilling', '$23.10', 'MAU');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ermin Ryhorovich', 'Sultanas', 2, 'Cambodia', '25/03/2023', 'Riels', '$13.44', 'IKI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Loren Kits', 'Wine - Barossa Valley Estate', 1, 'Czech Republic', '16/04/2022', 'Koruna', '$6.12', 'ENY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Melba Gusticke', 'Ocean Spray - Ruby Red', 3, 'Indonesia', '10/03/2022', 'Rupiah', '$39.20', 'PDC');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Devonna Runge', 'Yogurt - Banana, 175 Gr', 2, 'Indonesia', '09/04/2022', 'Rupiah', '$7.00', 'PFB');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Billy Spikings', 'Tomatoes Tear Drop', 2, 'Serbia', '17/05/2022', 'Dinar', '$32.40', 'YRM');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Arlette Gooddie', 'Pork - Liver', 7, 'China', '20/06/2022', 'Yuan Renminbi', '$18.01', 'ABJ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Toddy Jeeves', 'Lemonade - Pineapple Passion', 4, 'Armenia', '18/07/2022', 'Dram', '$35.10', 'KHH');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Rebekkah Yes', 'Sesame Seed Black', 10, 'South Africa', '19/05/2022', 'Rand', '$49.82', 'FOK');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Alverta Scoffins', 'Bar Mix - Pina Colada, 355 Ml', 9, 'Ukraine', '30/09/2021', 'Hryvnia', '$13.35', 'WTA');




insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Goldi Galvan', 'Jam - Strawberry, 20 Ml Jar', 9, 'China', '2021-08-22', 'Yuan Renminbi', '$9.94', 'TTD');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Alvin Grinham', 'Energy Drink - Franks Pineapple', 5, 'Indonesia', '2021-06-27', 'Rupiah', '$31.21', 'JHS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Georgy Lyptrit', 'Eggplant - Baby', 2, 'China', '2022-08-31', 'Yuan Renminbi', '$6.64', 'FSI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Anna Bennough', 'Shrimp, Dried, Small / Lb', 3, 'China', '2023-03-11', 'Yuan Renminbi', '$5.66', 'LWL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Raymund Limpricht', 'Wine - Chablis J Moreau Et Fils', 6, 'Finland', '2022-05-27', 'Euro', '$49.39', 'PLR');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Alick Connah', 'Container Clear 8 Oz', 7, 'Russia', '2021-11-15', 'Ruble', '$29.74', 'BER');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Abba Jankin', 'Chocolate - Dark', 6, 'Indonesia', '2021-05-19', 'Rupiah', '$40.39', 'GRY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ikey Crampin', 'Extract - Vanilla,artificial', 8, 'Indonesia', '2022-08-08', 'Rupiah', '$48.01', 'NRE');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Blaire Teodori', 'Scallops - In Shell', 2, 'Jamaica', '2023-02-14', 'Dollar', '$35.13', 'CNG');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Bone Pengilley', 'Energy Drink', 1, 'Brazil', '2022-10-01', 'Real', '$31.03', 'AIE');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Brinn Grigorey', 'Steamers White', 10, 'Ireland', '2021-09-19', 'Euro', '$10.97', 'HSS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Marjory Dyas', 'Poppy Seed', 8, 'Philippines', '2021-07-11', 'Peso', '$33.74', 'CAY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Bellina Deerr', 'Kippers - Smoked', 2, 'Canada', '2023-04-20', 'Dollar', '$29.56', 'AAV');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Bobby Erie', 'Bar Nature Valley', 9, 'Poland', '2022-11-23', 'Zloty', '$23.11', 'CNJ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Erroll Seamen', 'Carbonated Water - Wildberry', 1, 'France', '2023-03-31', 'Euro', '$27.47', 'MFL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Casie McLane', 'Nantucket - 518ml', 8, 'China', '2022-06-24', 'Yuan Renminbi', '$34.53', 'BIS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Thekla Jent', 'Wine La Vielle Ferme Cote Du', 2, 'Mongolia', '2022-07-30', 'Tugrik', '$34.70', 'MQX');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Celene Mattock', 'Muffin - Mix - Creme Brule 15l', 6, 'Philippines', '2022-06-28', 'Peso', '$40.40', 'RRM');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Alvis Blew', 'Chips - Miss Vickies', 1, 'Portugal', '2022-12-09', 'Euro', '$17.93', 'AKS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Frasquito Guinane', 'Passion Fruit', 1, 'Palestinian Territory', '2021-07-04', 'Shekel', '$9.63', 'KMC');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Peggy Gipp', 'Bacardi Breezer - Strawberry', 2, 'China', '2023-01-20', 'Yuan Renminbi', '$37.61', 'SZI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Taddeusz Dewane', 'Grand Marnier', 5, 'Indonesia', '2022-11-09', 'Rupiah', '$25.02', 'SXV');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Robbie Lethbrig', 'Wine - Jackson Triggs Okonagan', 1, 'Brazil', '2022-06-29', 'Real', '$39.26', 'DSM');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Price Clackers', 'Chocolate - Unsweetened', 4, 'Portugal', '2022-12-28', 'Euro', '$9.40', 'SKZ');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Ardeen Spence', 'Cheese - Victor Et Berthold', 2, 'Russia', '2022-03-28', 'Ruble', '$28.13', 'SVK');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Tonie Mattussevich', 'Mustard Prepared', 8, 'Indonesia', '2021-05-26', 'Rupiah', '$35.56', 'ECI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Addie Abbys', 'Pork - Chop, Frenched', 2, 'China', '2023-02-09', 'Yuan Renminbi', '$9.66', 'MKL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Pinchas McRobbie', 'Sour Cream', 7, 'Indonesia', '2023-01-05', 'Rupiah', '$16.38', 'IGH');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Marjy Harsnipe', 'Pasta - Rotini, Dry', 8, 'Brazil', '2022-02-23', 'Real', '$46.70', 'OGG');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Sidonnie Livesay', 'Wooden Mop Handle', 9, 'Indonesia', '2022-12-06', 'Rupiah', '$40.81', 'HYR');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Filip Driussi', 'Vinegar - Rice', 10, 'Iran', '2022-06-26', 'Rial', '$40.10', 'YUY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Howey Grelak', 'Garbage Bags - Clear', 1, 'Indonesia', '2021-05-03', 'Rupiah', '$17.38', 'ZPH');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Cos Vsanelli', 'Pasta - Detalini, White, Fresh', 4, 'Serbia', '2022-02-27', 'Dinar', '$27.97', 'TAT');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Anitra Whitter', 'Tart Shells - Savory, 3', 2, 'Brazil', '2022-08-27', 'Real', '$24.36', 'RSL');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Bondie Snoxill', 'Soup - Clam Chowder, Dry Mix', 6, 'South Africa', '2021-08-29', 'Rand', '$21.96', 'VNY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Rustin D''Ruel', 'Cookie - Dough Variety', 10, 'Colombia', '2022-11-15', 'Peso', '$12.33', 'HKS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Gerardo Puddifer', 'Shiro Miso', 7, 'Netherlands', '2022-06-18', 'Euro', '$34.02', 'GMS');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Kim Conachy', 'Wine - White, Pinot Grigio', 8, 'Sweden', '2022-06-27', 'Krona', '$15.18', 'RME');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Elset Shoreman', 'Ham - Black Forest', 5, 'United States', '2022-03-24', 'Dollar', '$20.37', 'LMP');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Thornie Bompas', 'Ice Cream - Turtles Stick Bar', 9, 'China', '2021-07-17', 'Yuan Renminbi', '$29.23', 'GDO');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Fredelia Bugler', 'Sea Bass - Fillets', 3, 'Moldova', '2021-11-19', 'Leu', '$36.45', 'ASF');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Patten Beharrell', 'Soup - Boston Clam Chowder', 9, 'Indonesia', '2022-11-26', 'Rupiah', '$8.95', 'SHE');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Loraine Neem', 'Bread - Bagels, Mini', 1, 'Indonesia', '2022-04-17', 'Rupiah', '$10.41', 'IAO');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Gisele Heatherington', 'Appetiser - Bought', 9, 'Greece', '2022-12-19', 'Euro', '$17.43', 'FCY');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Babs Durtnell', 'Oven Mitts 17 Inch', 6, 'United States', '2022-03-13', 'Dollar', '$33.12', 'SHH');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Bellina Isted', 'Honey - Comb', 9, 'Honduras', '2023-01-24', 'Lempira', '$26.97', 'FRI');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Gael Jacobsen', 'Sweet Pea Sprouts', 1, 'Argentina', '2021-08-21', 'Peso', '$12.38', 'TBV');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Freddy Phillpot', 'Rice - Aborio', 5, 'Japan', '2023-01-23', 'Yen', '$6.45', 'LXU');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Stacey Tschursch', 'Oil - Avocado', 9, 'Nigeria', '2022-02-18', 'Naira', '$31.17', 'BKA');
insert into create_orderdto (full_name, product_type, amount_order, destination, date, currency, Total, shipping_type) values ('Rania Faltin', 'Water - Perrier', 7, 'Macedonia', '2022-09-23', 'Denar', '$40.59', 'KVA');

*/


UPDATE `panelera_exportation`.`create_orderdto` SET `date`='2023-04-07';