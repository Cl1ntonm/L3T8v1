-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: poisepms5
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `architect`
--

DROP TABLE IF EXISTS `architect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `architect` (
  `arch_id` int NOT NULL,
  `project_number` int DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`arch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `architect`
--

LOCK TABLES `architect` WRITE;
/*!40000 ALTER TABLE `architect` DISABLE KEYS */;
INSERT INTO `architect` VALUES (1,1,'Leia','SkyWalker','827238752','leia@email.com','1 Alderaan rd'),(2,2,'Hermione','Granger','873476873','Hermione@email.com','8 Heathgate Hampstead'),(3,3,'Theodore','Seville','729787297','theodore@email.com','1544 North Saint Andrews small room'),(4,4,'Drew','Barrymore','722347299','drew@email.com','78 Malibu crescent'),(5,5,'Xander','Harris','162954879','xander@eail.com','231 Sunnydale Court'),(6,6,'Janet','Wood','459287103','janet@email.com','R2 4th Str Santa Monica'),(10,10,'Ash','Moodley','0724587458','ash@email.com','3 Northwald');
/*!40000 ALTER TABLE `architect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contractor`
--

DROP TABLE IF EXISTS `contractor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contractor` (
  `con_id` int NOT NULL,
  `project_number` int DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`con_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contractor`
--

LOCK TABLES `contractor` WRITE;
/*!40000 ALTER TABLE `contractor` DISABLE KEYS */;
INSERT INTO `contractor` VALUES (1,1,'Han','Solo','738928751','han@email.com','76 Correllia'),(2,2,'Ron','Weasley','738928751','ron@email.com','12 Burrow Place'),(3,3,'Simon','Seville','738977521','simon@email.com','1544 North Saint Andrews large room'),(4,4,'Lucy','Liu','738998121','lucy@email.com','90 San Fransisco place'),(5,5,'Willow','Rosenberg','348712543','willow@email.com','56 Tree Lane'),(6,6,'Chrissy','Snow','281673048','chrissy@email.com','R2 4th Str Santa Monica'),(10,10,'Kas','Thomas','0864581653','kas@email.com','10 Northwald');
/*!40000 ALTER TABLE `contractor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `cust_id` int NOT NULL,
  `project_number` int DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,1,'Luke','SkyWalker','727265101','Luke@email.com','15 Tatooine'),(2,2,'Harry','Potter','837877083','harry@email.com','89 Understair Land'),(3,3,'Alvin','Seville','871872650','alvin@email.com','1544 North SaintAndrews smallroom'),(4,4,'Cameron','Diaz','286091823','cameron@email.com','43 California close'),(5,5,'Buffy','Summers','871287562','buffy@email.com','1630 Revello Drive'),(6,6,'Jack','Tripper','672165031','jack@email.com','L2 Hacienda Palms'),(10,10,'Cliton','Moodley','0721268651','clinton@email.com','34 petriawood');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `project_number` int NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `erf` varchar(50) DEFAULT NULL,
  `amount_charged` float DEFAULT NULL,
  `amount_paid` float DEFAULT NULL,
  `deadline_date` date DEFAULT NULL,
  `completed_date` date DEFAULT NULL,
  `finalised` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`project_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Luke House','House','45 Ach to Island','456',500,400,'2020-01-01','2020-01-01','no'),(2,'Harry House','House','2 Grimmauld Place','456',400,300,'2020-01-01','2020-01-01','no'),(3,'Studio Apartment','Studio Apartment','1544 North Saint Andrews tree\n','457',200,200,'2019-01-01','2022-01-02','yes'),(4,'Cameron Garage','Garage','43 California close\n','459',600,200,'2018-01-01','2022-01-01','yes'),(5,'NightClub','NightClub','43 California close','459',200,200,'2018-01-01','2021-01-01','yes'),(6,'Jack Flat','Flat','2912 4th Str Santa Monica','461',200,200,'2025-01-01','2025-01-01','no'),(10,'Tikka Shop','Shop','45 Vie haven Drive','999',1000,1000,'2020-06-06','2022-10-10','yes');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-15  1:22:10
