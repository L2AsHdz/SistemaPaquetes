-- MariaDB dump 10.17  Distrib 10.4.7-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: SistemaPaquetes
-- ------------------------------------------------------
-- Server version	10.4.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Cliente`
--

DROP TABLE IF EXISTS `Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cliente` (
  `NIT` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Nombre` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Direccion` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Telefono` varchar(9) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`NIT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cliente`
--

LOCK TABLES `Cliente` WRITE;
/*!40000 ALTER TABLE `Cliente` DISABLE KEYS */;
INSERT INTO `Cliente` VALUES ('11111111','Cliente 1','direccion','6546-4651'),('22222222','Cliente 2',NULL,'    -    '),('33333333','Cliente 3',NULL,'8894-4664'),('44444444','Cliente 4',NULL,'    -    '),('55555555','Cliente 5','aqui :v','3658-7412');
/*!40000 ALTER TABLE `Cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ColaBodega`
--

DROP TABLE IF EXISTS `ColaBodega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ColaBodega` (
  `IdPaquete` int(11) NOT NULL,
  PRIMARY KEY (`IdPaquete`),
  CONSTRAINT `FK_COLAB_TO_PAQUETE_ID` FOREIGN KEY (`IdPaquete`) REFERENCES `Paquete` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ColaBodega`
--

LOCK TABLES `ColaBodega` WRITE;
/*!40000 ALTER TABLE `ColaBodega` DISABLE KEYS */;
/*!40000 ALTER TABLE `ColaBodega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ColaPuntoControl`
--

DROP TABLE IF EXISTS `ColaPuntoControl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ColaPuntoControl` (
  `IdPaquete` int(11) NOT NULL,
  `NoPuntoControl` int(11) NOT NULL,
  `IdRutaPC` int(11) NOT NULL,
  PRIMARY KEY (`IdRutaPC`,`NoPuntoControl`,`IdPaquete`),
  KEY `FK_COLAPC_TO_PAQUETE_ID` (`IdPaquete`),
  CONSTRAINT `FK_COLAPC_TO_PAQUETE_ID` FOREIGN KEY (`IdPaquete`) REFERENCES `Paquete` (`Id`),
  CONSTRAINT `FK_COLAPC_TO_PUNTOCONTROL` FOREIGN KEY (`IdRutaPC`, `NoPuntoControl`) REFERENCES `PuntoControl` (`IdRuta`, `Numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ColaPuntoControl`
--

LOCK TABLES `ColaPuntoControl` WRITE;
/*!40000 ALTER TABLE `ColaPuntoControl` DISABLE KEYS */;
INSERT INTO `ColaPuntoControl` VALUES (20,3,1),(24,3,1),(25,2,2),(19,3,2),(21,3,2),(7,4,2),(8,4,2),(12,4,2),(17,3,4),(14,4,4);
/*!40000 ALTER TABLE `ColaPuntoControl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Destino`
--

DROP TABLE IF EXISTS `Destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Destino` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CuotaDestino` float NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Destino`
--

LOCK TABLES `Destino` WRITE;
/*!40000 ALTER TABLE `Destino` DISABLE KEYS */;
INSERT INTO `Destino` VALUES (1,'Guatemala',40),(2,'El Salvador',50),(3,'Honduras',60),(4,'Costa Rica',70),(5,'Nicaragua',75),(6,'Panama',85);
/*!40000 ALTER TABLE `Destino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `IngresoPaquete`
--

DROP TABLE IF EXISTS `IngresoPaquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `IngresoPaquete` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CodigoFactura` int(11) NOT NULL,
  `IdPaquete` int(11) NOT NULL,
  `NitCliente` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Fecha` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PrecioPriorizacion` float NOT NULL,
  `PrecioLibra` float NOT NULL,
  `CuotaDestino` float NOT NULL,
  `CostoPeso` float NOT NULL,
  `Total` float NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_IP_TO_PAQUETE_ID` (`IdPaquete`),
  KEY `FK_IP_TO_CLIENTE_NIT` (`NitCliente`),
  CONSTRAINT `FK_IP_TO_CLIENTE_NIT` FOREIGN KEY (`NitCliente`) REFERENCES `Cliente` (`NIT`),
  CONSTRAINT `FK_IP_TO_PAQUETE_ID` FOREIGN KEY (`IdPaquete`) REFERENCES `Paquete` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `IngresoPaquete`
--

LOCK TABLES `IngresoPaquete` WRITE;
/*!40000 ALTER TABLE `IngresoPaquete` DISABLE KEYS */;
INSERT INTO `IngresoPaquete` VALUES (1,1,1,'11111111','2019-08-26',0,15,40,150,190),(2,1,2,'11111111','2019-08-26',0,15,60,120,180),(3,1,3,'11111111','2019-08-26',0,15,50,90,140),(4,1,4,'11111111','2019-08-26',0,15,40,180,220),(5,1,5,'11111111','2019-08-26',0,15,50,135,185),(6,2,6,'22222222','2019-08-26',0,15,40,150,190),(7,2,7,'22222222','2019-08-26',0,15,60,120,180),(8,2,8,'22222222','2019-08-26',0,15,60,180,240),(9,2,9,'22222222','2019-08-26',0,15,50,60,110),(10,2,10,'22222222','2019-08-26',0,15,75,90,165),(11,3,11,'33333333','2019-08-26',0,20,40,140,180),(12,3,12,'33333333','2019-08-26',0,20,60,100,160),(13,3,13,'33333333','2019-08-26',0,20,40,300,340),(14,3,14,'33333333','2019-08-26',0,20,75,60,135),(15,3,15,'33333333','2019-08-26',0,20,50,220,270),(16,4,16,'44444444','2019-08-26',0,20,50,80,130),(17,4,17,'44444444','2019-08-26',0,20,75,260,335),(18,4,18,'44444444','2019-08-26',0,20,40,220,260),(19,4,19,'44444444','2019-08-26',0,20,60,40,100),(20,4,20,'44444444','2019-08-26',0,20,40,360,400),(21,5,21,'55555555','2019-08-26',0,20,60,280,340),(22,5,22,'55555555','2019-08-26',0,20,50,420,470),(23,5,23,'55555555','2019-08-26',0,20,40,80,120),(24,5,24,'55555555','2019-08-26',0,20,40,140,180),(25,5,25,'55555555','2019-08-26',0,20,60,180,240);
/*!40000 ALTER TABLE `IngresoPaquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Paquete`
--

DROP TABLE IF EXISTS `Paquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Paquete` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Descripcion` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Peso` float NOT NULL,
  `EstadoRetiro` int(11) NOT NULL,
  `Priorizado` int(11) NOT NULL,
  `IdRuta` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PAQUETE_TO_RUTA_ID` (`IdRuta`),
  CONSTRAINT `FK_PAQUETE_TO_RUTA_ID` FOREIGN KEY (`IdRuta`) REFERENCES `Ruta` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Paquete`
--

LOCK TABLES `Paquete` WRITE;
/*!40000 ALTER TABLE `Paquete` DISABLE KEYS */;
INSERT INTO `Paquete` VALUES (1,'P1C1','	',10,2,0,1),(2,'P2C1','',8,2,0,2),(3,'P3C1','',6,2,0,3),(4,'P4C1','',12,2,0,5),(5,'P5C1','',9,2,0,3),(6,'P1C2','',10,2,0,5),(7,'P2C2','',8,1,0,2),(8,'P3C2','',12,1,0,2),(9,'P4C2','',4,2,0,3),(10,'P5C2','',6,2,0,4),(11,'P1C3','',7,2,0,5),(12,'P2C3','',5,1,0,2),(13,'P3C3','',15,2,0,1),(14,'P4C3','',3,1,0,4),(15,'P5C3','',11,2,0,3),(16,'P1C4','',4,2,0,3),(17,'P2C4','',13,1,0,4),(18,'P3C4','',11,2,0,1),(19,'P4C4','',2,1,0,2),(20,'P5C4','',18,1,0,1),(21,'P1C5','',14,1,0,2),(22,'P2C5','',21,2,0,3),(23,'P3C5','',4,2,0,5),(24,'P4C5','',7,1,0,1),(25,'P5C5','',9,1,0,2);
/*!40000 ALTER TABLE `Paquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PreciosGlobales`
--

DROP TABLE IF EXISTS `PreciosGlobales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PreciosGlobales` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Valor` float NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PreciosGlobales`
--

LOCK TABLES `PreciosGlobales` WRITE;
/*!40000 ALTER TABLE `PreciosGlobales` DISABLE KEYS */;
INSERT INTO `PreciosGlobales` VALUES (1,'Tarifa de operacion global',50),(2,'Precio por libra',20),(3,'Precio priorizacion',30);
/*!40000 ALTER TABLE `PreciosGlobales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProcesoPaquete`
--

DROP TABLE IF EXISTS `ProcesoPaquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProcesoPaquete` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `IdPaquete` int(11) NOT NULL,
  `NoPuntoControl` int(11) NOT NULL,
  `IdRutaPC` int(11) NOT NULL,
  `Horas` float NOT NULL,
  `TarifaOperacion` float NOT NULL,
  `Costo` float NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_PP_TO_PAQUETE_ID` (`IdPaquete`),
  KEY `FK_PP_TO_PUNTOCONTROL` (`IdRutaPC`,`NoPuntoControl`),
  CONSTRAINT `FK_PP_TO_PAQUETE_ID` FOREIGN KEY (`IdPaquete`) REFERENCES `Paquete` (`Id`),
  CONSTRAINT `FK_PP_TO_PUNTOCONTROL` FOREIGN KEY (`IdRutaPC`, `NoPuntoControl`) REFERENCES `PuntoControl` (`IdRuta`, `Numero`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProcesoPaquete`
--

LOCK TABLES `ProcesoPaquete` WRITE;
/*!40000 ALTER TABLE `ProcesoPaquete` DISABLE KEYS */;
INSERT INTO `ProcesoPaquete` VALUES (1,4,1,5,3,30,90),(2,6,1,5,5,30,150),(3,11,1,5,6,30,180),(4,1,1,1,3,25,75),(5,2,1,2,4,50,200),(6,7,1,2,5,50,250),(7,4,2,5,6,25,150),(8,13,1,1,2,25,50),(9,8,1,2,7,50,350),(10,18,1,1,4,25,100),(11,6,2,5,6,25,150),(12,3,1,3,3,25,75),(13,11,2,5,7,25,175),(14,5,1,3,5,25,125),(15,9,1,3,5,25,125),(16,15,1,3,8,25,200),(17,20,1,1,2,25,50),(18,10,1,4,3,50,150),(19,14,1,4,6,50,300),(20,17,1,4,4,50,200),(21,1,2,1,6,50,300),(22,23,1,5,2,30,60),(23,2,2,2,1.5,30,45),(24,13,2,1,3.5,50,175),(25,7,2,2,1.5,30,45),(26,4,3,5,2.5,15,37.5),(27,18,2,1,4.5,50,225),(28,20,2,1,3.75,50,187.5),(29,3,2,3,3.6,50,180),(30,6,3,5,4.75,15,71.25),(31,8,2,2,5.25,30,157.5),(32,5,2,3,3.5,50,175),(33,12,1,2,1.5,50,75),(34,16,1,3,3.2,25,80),(35,10,2,4,1.5,25,37.5),(36,10,3,4,1.75,50,87.5),(37,14,2,4,3.5,25,87.5),(38,14,3,4,4.3,50,215),(39,22,1,3,1.2,25,30),(40,23,2,5,2.75,25,68.75),(41,10,4,4,1.1,50,55),(42,11,3,5,2.1,15,31.5),(43,23,3,5,2.3,15,34.5),(44,1,3,1,1.2,50,60),(45,13,3,1,2.3,50,115),(46,18,3,1,2.4,50,120),(47,2,3,2,1.5,50,75),(48,7,3,2,1,50,50),(49,3,3,3,2.3,50,115),(50,5,3,3,1.5,50,75),(51,3,4,3,3,25,75),(52,5,4,3,1.75,25,43.75),(53,4,4,5,1.3,35,45.5),(54,6,4,5,2.8,35,98),(55,19,1,2,1.4,50,70),(56,21,1,2,2.2,50,110),(57,9,2,3,2.2,50,110),(58,15,2,3,3.5,50,175),(59,16,2,3,1.75,50,87.5),(60,12,2,2,4.1,30,123),(61,19,2,2,2,30,60),(62,2,4,2,1.25,50,62.5),(63,8,3,2,1.45,50,72.5),(64,12,3,2,2.5,50,125),(65,9,3,3,1.25,50,62.5),(66,15,3,3,2.4,50,120),(67,9,4,3,2.1,25,52.5),(68,15,4,3,2.25,25,56.25),(69,1,4,1,2.6,15,39),(70,13,4,1,3.4,15,51),(71,18,4,1,2.2,15,33),(72,24,1,1,3.5,25,87.5),(73,16,3,3,3.1,50,155),(74,16,4,3,1.4,25,35),(75,11,4,5,1.6,35,56),(76,24,2,1,1.7,50,85),(77,25,1,2,1.8,50,90),(78,17,2,4,1.5,25,37.5),(79,23,4,5,2.75,35,96.25),(80,22,2,3,2.6,50,130),(81,22,3,3,3.8,50,190),(82,22,4,3,1.35,25,33.75),(83,21,2,2,3,30,90);
/*!40000 ALTER TABLE `ProcesoPaquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PuntoControl`
--

DROP TABLE IF EXISTS `PuntoControl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PuntoControl` (
  `Numero` int(11) NOT NULL,
  `IdRuta` int(11) NOT NULL,
  `Nombre` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LimitePaquetes` int(11) NOT NULL,
  `DPIOperador` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TarifaOperacion` float DEFAULT NULL,
  `TarifaOperacionGlobal` float NOT NULL,
  PRIMARY KEY (`IdRuta`,`Numero`),
  KEY `FK_PC_TO_USUARIO_DPI` (`DPIOperador`),
  CONSTRAINT `FK_PC_TO_RUTA_ID` FOREIGN KEY (`IdRuta`) REFERENCES `Ruta` (`Id`),
  CONSTRAINT `FK_PC_TO_USUARIO_DPI` FOREIGN KEY (`DPIOperador`) REFERENCES `Usuario` (`DPI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PuntoControl`
--

LOCK TABLES `PuntoControl` WRITE;
/*!40000 ALTER TABLE `PuntoControl` DISABLE KEYS */;
INSERT INTO `PuntoControl` VALUES (1,1,'Punto 1-G',3,'1654846552131',25,35),(2,1,'Punto 2-G',4,'1654894121354',0,35),(3,1,'Punto 3-G',6,'7987132651323',0,35),(4,1,'Punto 4-G',5,'1654846552131',15,35),(1,2,'Punto 1-H',4,'1654846552131',0,35),(2,2,'Punto 2-H',3,'1654894121354',30,35),(3,2,'Punto 3-H',4,'7987132651323',0,35),(4,2,'Punto 4-H',3,'1654894121354',0,35),(1,3,'Punto 1-ES',5,'1654846552131',25,35),(2,3,'Punto 2-ES',4,'1654894121354',0,35),(3,3,'Punto 3-ES',3,'7987132651323',0,35),(4,3,'Punto 4-ES',4,'7987132651323',25,35),(1,4,'Punto 1-N',3,'7987132651323',0,35),(2,4,'Punto 2-N',3,'1654846552131',25,35),(3,4,'Punto 3-N',3,'1654846552131',0,35),(4,4,'Punto 4-N',2,'1654894121354',0,35),(1,5,'Punto 1-G2',4,'1654894121354',30,35),(2,5,'Punto 2-G2',3,'1654846552131',25,35),(3,5,'Punto 3-G2',4,'1654894121354',15,35),(4,5,'Punto 4-G2',5,'7987132651323',35,35);
/*!40000 ALTER TABLE `PuntoControl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RetiroPaquete`
--

DROP TABLE IF EXISTS `RetiroPaquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RetiroPaquete` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Costo` float NOT NULL,
  `Ingreso` float NOT NULL,
  `Ganancia` float NOT NULL,
  `IdPaquete` int(11) NOT NULL,
  `NitCliente` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FechaEntrega` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_RP_TO_PAQUETE_ID` (`IdPaquete`),
  KEY `FK_RP_TO_CLIENTE_NIT` (`NitCliente`),
  CONSTRAINT `FK_RP_TO_CLIENTE_NIT` FOREIGN KEY (`NitCliente`) REFERENCES `Cliente` (`NIT`),
  CONSTRAINT `FK_RP_TO_PAQUETE_ID` FOREIGN KEY (`IdPaquete`) REFERENCES `Paquete` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RetiroPaquete`
--

LOCK TABLES `RetiroPaquete` WRITE;
/*!40000 ALTER TABLE `RetiroPaquete` DISABLE KEYS */;
/*!40000 ALTER TABLE `RetiroPaquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ruta`
--

DROP TABLE IF EXISTS `Ruta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ruta` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Estado` int(11) NOT NULL,
  `Descripcion` varchar(250) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `IdDestino` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `FK_RUTA_TO_DESTINO_ID` (`IdDestino`),
  CONSTRAINT `FK_RUTA_TO_DESTINO_ID` FOREIGN KEY (`IdDestino`) REFERENCES `Destino` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ruta`
--

LOCK TABLES `Ruta` WRITE;
/*!40000 ALTER TABLE `Ruta` DISABLE KEYS */;
INSERT INTO `Ruta` VALUES (1,'RutaG',1,'',1),(2,'RutaH',1,'',3),(3,'RutaES',1,'',2),(4,'RutaN',1,'',5),(5,'RutaG2',1,'',1);
/*!40000 ALTER TABLE `Ruta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `DPI` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Nombre` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NombreUsuario` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Tipo` int(11) NOT NULL,
  `Estado` int(11) NOT NULL,
  `Password` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`DPI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES ('1654846552131','Alberto Lopez','operador1',2,1,'123'),('1654894121354','Javier Gonzalez','operador2',2,1,'123'),('3154155651212','Alexander Barrios','recep2',3,1,'123'),('3370312010920','Leonidas Lopez','admin3',1,1,'123'),('3370312070920','Abner Hernandez','admin2',1,1,'123'),('3370312150920','Asael Hernandez','admin1',1,1,'123'),('6654812315468','Alfredo del Cid','recep1',3,1,'123'),('7651114586664','Melanie Velasquez','recep3',3,1,'123'),('7987132651323','Luis Sales','operador3',2,1,'123');
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-26 13:52:02
