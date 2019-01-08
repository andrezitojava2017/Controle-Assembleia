CREATE DATABASE  IF NOT EXISTS `assembleia` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `assembleia`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: assembleia
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_dizimos`
--

DROP TABLE IF EXISTS `tbl_dizimos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_dizimos` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `descricao` varchar(500) NOT NULL,
  `id_pessoas` int(11) NOT NULL,
  `id_entrada` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_dizimos_tbl_pessoas1_idx` (`id_pessoas`),
  KEY `fk_tbl_dizimos_tbl_entrada1_idx` (`id_entrada`),
  CONSTRAINT `fk_tbl_dizimos_tbl_entrada1` FOREIGN KEY (`id_entrada`) REFERENCES `tbl_entrada` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_dizimos_tbl_pessoas1` FOREIGN KEY (`id_pessoas`) REFERENCES `tbl_pessoas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_dizimos`
--

LOCK TABLES `tbl_dizimos` WRITE;
/*!40000 ALTER TABLE `tbl_dizimos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_dizimos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_entrada`
--

DROP TABLE IF EXISTS `tbl_entrada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_entrada` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `competencia` varchar(10) NOT NULL,
  `descricao` varchar(1000) NOT NULL,
  `valor` decimal(10,0) NOT NULL,
  `id_tipo_entrada` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_entrada_tbl_tipo_idx` (`id_tipo_entrada`),
  CONSTRAINT `fk_tbl_entrada_tbl_tipo` FOREIGN KEY (`id_tipo_entrada`) REFERENCES `tbl_tipo_entradas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_entrada`
--

LOCK TABLES `tbl_entrada` WRITE;
/*!40000 ALTER TABLE `tbl_entrada` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_entrada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_pessoas`
--

DROP TABLE IF EXISTS `tbl_pessoas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_pessoas` (
  `id` int(11) NOT NULL,
  `nome` varchar(250) NOT NULL,
  `identidade` varchar(50) DEFAULT NULL,
  `cpf` varchar(20) DEFAULT NULL,
  `cnpj` varchar(20) DEFAULT NULL,
  `data_cadastro` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_pessoas`
--

LOCK TABLES `tbl_pessoas` WRITE;
/*!40000 ALTER TABLE `tbl_pessoas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_pessoas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_saidas`
--

DROP TABLE IF EXISTS `tbl_saidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_saidas` (
  `id` int(11) NOT NULL,
  `data` date NOT NULL,
  `competencia` varchar(10) NOT NULL,
  `descricao_saida` varchar(1000) NOT NULL,
  `valor` decimal(10,0) NOT NULL,
  `numero_recibo` int(11) DEFAULT NULL,
  `id_credor` int(11) NOT NULL,
  `id_tipo_saida` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tbl_saidas_tbl_pessoas1_idx` (`id_credor`),
  KEY `fk_tbl_saidas_tbl_tipo_saidas1_idx` (`id_tipo_saida`),
  CONSTRAINT `fk_tbl_saidas_tbl_pessoas1` FOREIGN KEY (`id_credor`) REFERENCES `tbl_pessoas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_saidas_tbl_tipo_saidas1` FOREIGN KEY (`id_tipo_saida`) REFERENCES `tbl_tipo_saidas` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_saidas`
--

LOCK TABLES `tbl_saidas` WRITE;
/*!40000 ALTER TABLE `tbl_saidas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_saidas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tipo_entradas`
--

DROP TABLE IF EXISTS `tbl_tipo_entradas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_tipo_entradas` (
  `id` int(22) NOT NULL,
  `descricao_tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tipo_entradas`
--

LOCK TABLES `tbl_tipo_entradas` WRITE;
/*!40000 ALTER TABLE `tbl_tipo_entradas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tipo_entradas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tipo_saidas`
--

DROP TABLE IF EXISTS `tbl_tipo_saidas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_tipo_saidas` (
  `id` int(11) NOT NULL,
  `descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tipo_saidas`
--

LOCK TABLES `tbl_tipo_saidas` WRITE;
/*!40000 ALTER TABLE `tbl_tipo_saidas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_tipo_saidas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-05 21:50:08
