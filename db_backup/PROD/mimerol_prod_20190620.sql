-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ofo_prod
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `address` (
  `address_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `district_id` bigint(20) DEFAULT NULL,
  `ward` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email_add` varchar(255) DEFAULT NULL,
  `phone_number` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_status` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `address_desc` longtext,
  PRIMARY KEY (`address_id`),
  KEY `fk_dist_add` (`district_id`),
  KEY `fk_u_add` (`user_id`),
  CONSTRAINT `fk_dist_add` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`),
  CONSTRAINT `fk_u_add` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (32,66,1,NULL,'Marginal, Managua, Nicaragua',NULL,NULL,1,'2019-03-26 09:45:22',NULL,NULL,NULL,'Turn left');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute`
--

DROP TABLE IF EXISTS `attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `attribute` (
  `atribute_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribute_group_id` bigint(20) NOT NULL,
  `content_dep_id` bigint(20) NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_status` int(1) DEFAULT NULL,
  PRIMARY KEY (`atribute_id`),
  KEY `fk_a_gr` (`attribute_group_id`),
  KEY `fk_con_def_att` (`content_dep_id`),
  CONSTRAINT `fk_a_gr` FOREIGN KEY (`attribute_group_id`) REFERENCES `attribute_group` (`attibute_group_id`),
  CONSTRAINT `fk_con_def_att` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute`
--

LOCK TABLES `attribute` WRITE;
/*!40000 ALTER TABLE `attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attribute_group`
--

DROP TABLE IF EXISTS `attribute_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `attribute_group` (
  `attibute_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_dep_id` bigint(20) NOT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` int(1) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`attibute_group_id`),
  KEY `fk_con_def_attg` (`content_dep_id`),
  CONSTRAINT `fk_con_def_attg` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attribute_group`
--

LOCK TABLES `attribute_group` WRITE;
/*!40000 ALTER TABLE `attribute_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `attribute_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `sort_order` int(11) DEFAULT '1',
  `url_slug` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `fk_con_dep` (`content_dep_id`),
  CONSTRAINT `fk_con_dep` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (61,423,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(62,424,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL),(63,425,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(64,426,NULL,NULL,1,3,NULL,NULL,NULL,NULL,NULL),(65,427,NULL,NULL,2,3,NULL,NULL,NULL,NULL,'2018-07-07 16:58:18'),(66,428,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(67,429,NULL,NULL,1,4,NULL,NULL,NULL,NULL,NULL),(68,430,NULL,NULL,1,4,NULL,NULL,NULL,NULL,NULL),(69,431,NULL,NULL,1,5,NULL,NULL,NULL,NULL,NULL),(70,432,NULL,NULL,1,5,NULL,NULL,NULL,NULL,NULL),(71,433,NULL,NULL,1,4,NULL,NULL,NULL,NULL,NULL),(72,434,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(73,435,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(74,436,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(75,437,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(76,438,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(77,439,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(78,440,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(79,441,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(80,442,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(81,443,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(82,444,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(83,445,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(84,614,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `city` (
  `city_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_id` bigint(20) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `city_code` varchar(50) NOT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`city_id`),
  KEY `fk_country_idx` (`country_id`),
  CONSTRAINT `fk_country` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (3,4,'Ha Noi','ha-noi',1,NULL,NULL,NULL,NULL),(6,5,'Madrid','madrid',1,NULL,NULL,NULL,NULL),(68,5,'Bilwi','bilwi',1,NULL,NULL,NULL,NULL),(69,5,'Bonanza','bonanza',1,NULL,NULL,NULL,NULL),(70,5,'Mulukukú','mulukukú',1,NULL,NULL,NULL,NULL),(71,5,'Prinzapolka','prinzapolka',1,NULL,NULL,NULL,NULL),(72,5,'Rosita','rosita',1,NULL,NULL,NULL,NULL),(73,5,'Siuna','siuna',1,NULL,NULL,NULL,NULL),(74,5,'Waslala','waslala',1,NULL,NULL,NULL,NULL),(75,5,'Waspán','waspán',1,NULL,NULL,NULL,NULL),(76,5,'Bluefields','bluefields',1,NULL,NULL,NULL,NULL),(77,5,'Corn Island','corn-island',1,NULL,NULL,NULL,NULL),(78,5,'Desembocadura de la Cruz de Río Grande','desembocadura-de-la-cruz-de-río-grande',1,NULL,NULL,NULL,NULL),(79,5,'El Ayote','el-ayote',1,NULL,NULL,NULL,NULL),(80,5,'El Tortugero','el-tortugero',1,NULL,NULL,NULL,NULL),(81,5,'Kukra Hill','kukra-hill',1,NULL,NULL,NULL,NULL),(82,5,'La Cruz de Río Grande','la-cruz-de-río-grande',1,NULL,NULL,NULL,NULL),(83,5,'Laguna de Perlas','laguna-de-perlas',1,NULL,NULL,NULL,NULL),(84,5,'Muelle de los Bueyes','muelle-de-los-bueyes',1,NULL,NULL,NULL,NULL),(85,5,'Nueva Guinea','nueva-guinea',1,NULL,NULL,NULL,NULL),(86,5,'Bocana de Paiwas','bocana-de-paiwas',1,NULL,NULL,NULL,NULL),(87,5,'El Rama','el-rama',1,NULL,NULL,NULL,NULL),(88,5,'Boaco','boaco',1,NULL,NULL,NULL,NULL),(89,5,'Camoapa','camoapa',1,NULL,NULL,NULL,NULL),(90,5,'San Lorenzo','san-lorenzo',1,NULL,NULL,NULL,NULL),(91,5,'San José de los Remates','san-josé-de-los-remates',1,NULL,NULL,NULL,NULL),(92,5,'Santa Lucía','santa-lucía',1,NULL,NULL,NULL,NULL),(93,5,'Teustepe','teustepe',1,NULL,NULL,NULL,NULL),(94,5,'Diriamba','diriamba',1,NULL,NULL,NULL,NULL),(95,5,'Dolores','dolores',1,NULL,NULL,NULL,NULL),(96,5,'El Rosario','el-rosario',1,NULL,NULL,NULL,NULL),(97,5,'Jinotepe','jinotepe',1,NULL,NULL,NULL,NULL),(98,5,'La Conquista','la-conquista',1,NULL,NULL,NULL,NULL),(99,5,'La Paz de Carazo','la-paz-de-carazo',1,NULL,NULL,NULL,NULL),(100,5,'San Marcos','san-marcos',1,NULL,NULL,NULL,NULL),(101,5,'Santa Teresa','santa-teresa',1,NULL,NULL,NULL,NULL),(102,5,'Chichigalpa','chichigalpa',1,NULL,NULL,NULL,NULL),(103,5,'Chinandega','chinandega',1,NULL,NULL,NULL,NULL),(104,5,'San Juan de Cinco Pinos','san-juan-de-cinco-pinos',1,NULL,NULL,NULL,NULL),(105,5,'Corinto','corinto',1,NULL,NULL,NULL,NULL),(106,5,'El Realejo','el-realejo',1,NULL,NULL,NULL,NULL),(107,5,'El Viejo','el-viejo',1,NULL,NULL,NULL,NULL),(108,5,'Posoltega','posoltega',1,NULL,NULL,NULL,NULL),(109,5,'San Francisco del Norte','san-francisco-del-norte',1,NULL,NULL,NULL,NULL),(110,5,'San Pedro del Norte','san-pedro-del-norte',1,NULL,NULL,NULL,NULL),(111,5,'Santo Tomás del Norte','santo-tomás-del-norte',1,NULL,NULL,NULL,NULL),(112,5,'Somotillo','somotillo',1,NULL,NULL,NULL,NULL),(113,5,'Puerto Morazán','puerto-morazán',1,NULL,NULL,NULL,NULL),(114,5,'Villanueva','villanueva',1,NULL,NULL,NULL,NULL),(115,5,'Acoyapa','acoyapa',1,NULL,NULL,NULL,NULL),(116,5,'Comalapa','comalapa',1,NULL,NULL,NULL,NULL),(117,5,'San Francisco de Cuapa','san-francisco-de-cuapa',1,NULL,NULL,NULL,NULL),(118,5,'El Coral','el-coral',1,NULL,NULL,NULL,NULL),(119,5,'Juigalpa','juigalpa',1,NULL,NULL,NULL,NULL),(120,5,'La Libertad','la-libertad',1,NULL,NULL,NULL,NULL),(121,5,'San Pedro de Lóvago','san-pedro-de-lóvago',1,NULL,NULL,NULL,NULL),(122,5,'Santo Domingo','santo-domingo',1,NULL,NULL,NULL,NULL),(123,5,'Santo Tomás','santo-tomás',1,NULL,NULL,NULL,NULL),(124,5,'Villa Sandino','villa-sandino',1,NULL,NULL,NULL,NULL),(125,5,'Condega','condega',1,NULL,NULL,NULL,NULL),(126,5,'Estelí','estelí',1,NULL,NULL,NULL,NULL),(127,5,'La Trinidad','la-trinidad',1,NULL,NULL,NULL,NULL),(128,5,'Pueblo Nuevo','pueblo-nuevo',1,NULL,NULL,NULL,NULL),(129,5,'San Juan de Limay','san-juan-de-limay',1,NULL,NULL,NULL,NULL),(130,5,'San Nicolás','san-nicolás',1,NULL,NULL,NULL,NULL),(131,5,'Diriá','diriá',1,NULL,NULL,NULL,NULL),(132,5,'Diriomo','diriomo',1,NULL,NULL,NULL,NULL),(133,5,'Granada','granada',1,NULL,NULL,NULL,NULL),(134,5,'Nandaime','nandaime',1,NULL,NULL,NULL,NULL),(135,5,'El Cuá','el-cuá',1,NULL,NULL,NULL,NULL),(136,5,'Jinotega','jinotega',1,NULL,NULL,NULL,NULL),(137,5,'La Concordia','la-concordia',1,NULL,NULL,NULL,NULL),(138,5,'San José de Bocay','san-josé-de-bocay',1,NULL,NULL,NULL,NULL),(139,5,'San Rafael del Norte','san-rafael-del-norte',1,NULL,NULL,NULL,NULL),(140,5,'San Sebastián de Yalí','san-sebastián-de-yalí',1,NULL,NULL,NULL,NULL),(141,5,'Santa María de Pantasma','santa-maría-de-pantasma',1,NULL,NULL,NULL,NULL),(142,5,'Wiwilí de Jinotega','wiwilí-de-jinotega',1,NULL,NULL,NULL,NULL),(143,5,'San José de Achuapa','san-josé-de-achuapa',1,NULL,NULL,NULL,NULL),(144,5,'El Jicaral','el-jicaral',1,NULL,NULL,NULL,NULL),(145,5,'El Sauce','el-sauce',1,NULL,NULL,NULL,NULL),(146,5,'La Paz Centro','la-paz-centro',1,NULL,NULL,NULL,NULL),(147,5,'Larreynaga','larreynaga',1,NULL,NULL,NULL,NULL),(148,5,'León','león',1,NULL,NULL,NULL,NULL),(149,5,'Nagarote','nagarote',1,NULL,NULL,NULL,NULL),(150,5,'Quezalguaque','quezalguaque',1,NULL,NULL,NULL,NULL),(151,5,'Santa Rosa del Peñón','santa-rosa-del-peñón',1,NULL,NULL,NULL,NULL),(152,5,'Telica','telica',1,NULL,NULL,NULL,NULL),(153,5,'Las Sabanas','las-sabanas',1,NULL,NULL,NULL,NULL),(154,5,'Palacagüina','palacagüina',1,NULL,NULL,NULL,NULL),(155,5,'San José de Cusmapa','san-josé-de-cusmapa',1,NULL,NULL,NULL,NULL),(156,5,'San Juan del Río Coco','san-juan-del-río-coco',1,NULL,NULL,NULL,NULL),(157,5,'San Lucas','san-lucas',1,NULL,NULL,NULL,NULL),(158,5,'Somoto','somoto',1,NULL,NULL,NULL,NULL),(159,5,'Telpaneca','telpaneca',1,NULL,NULL,NULL,NULL),(160,5,'Totogalpa','totogalpa',1,NULL,NULL,NULL,NULL),(161,5,'Yalagüina','yalagüina',1,NULL,NULL,NULL,NULL),(162,5,'Ciudad Sandino','ciudad-sandino',1,NULL,NULL,NULL,NULL),(163,5,'El Crucero','el-crucero',1,NULL,NULL,NULL,NULL),(164,5,'Managua','managua',1,NULL,NULL,NULL,NULL),(165,5,'Mateare','mateare',1,NULL,NULL,NULL,NULL),(166,5,'San Francisco Libre','san-francisco-libre',1,NULL,NULL,NULL,NULL),(167,5,'San Rafael del Sur','san-rafael-del-sur',1,NULL,NULL,NULL,NULL),(168,5,'Ticuantepe','ticuantepe',1,NULL,NULL,NULL,NULL),(169,5,'Tipitapa','tipitapa',1,NULL,NULL,NULL,NULL),(170,5,'Villa El Carmen','villa-el-carmen',1,NULL,NULL,NULL,NULL),(171,5,'Catarina','catarina',1,NULL,NULL,NULL,NULL),(172,5,'La Concepción','la-concepción',1,NULL,NULL,NULL,NULL),(173,5,'Masatepe','masatepe',1,NULL,NULL,NULL,NULL),(174,5,'Masaya','masaya',1,NULL,NULL,NULL,NULL),(175,5,'Nandasmo','nandasmo',1,NULL,NULL,NULL,NULL),(176,5,'Nindirí','nindirí',1,NULL,NULL,NULL,NULL),(177,5,'Niquinohomo','niquinohomo',1,NULL,NULL,NULL,NULL),(178,5,'San Juan de Oriente','san-juan-de-oriente',1,NULL,NULL,NULL,NULL),(179,5,'Tisma','tisma',1,NULL,NULL,NULL,NULL),(180,5,'Ciudad Darío','ciudad-darío',1,NULL,NULL,NULL,NULL),(181,5,'El Tuma-La Dalia','el-tuma-la-dalia',1,NULL,NULL,NULL,NULL),(182,5,'Esquipulas','esquipulas',1,NULL,NULL,NULL,NULL),(183,5,'Matagalpa','matagalpa',1,NULL,NULL,NULL,NULL),(184,5,'Matiguás','matiguás',1,NULL,NULL,NULL,NULL),(185,5,'Muy Muy','muy-muy',1,NULL,NULL,NULL,NULL),(186,5,'Rancho Grande','rancho-grande',1,NULL,NULL,NULL,NULL),(187,5,'Río Blanco','río-blanco',1,NULL,NULL,NULL,NULL),(188,5,'San Dionisio','san-dionisio',1,NULL,NULL,NULL,NULL),(189,5,'San Isidro','san-isidro',1,NULL,NULL,NULL,NULL),(190,5,'San Ramón','san-ramón',1,NULL,NULL,NULL,NULL),(191,5,'Sébaco','sébaco',1,NULL,NULL,NULL,NULL),(192,5,'Terrabona','terrabona',1,NULL,NULL,NULL,NULL),(193,5,'Ciudad Antigua','ciudad-antigua',1,NULL,NULL,NULL,NULL),(194,5,'Dipilto','dipilto',1,NULL,NULL,NULL,NULL),(195,5,'El Jícaro','el-jícaro',1,NULL,NULL,NULL,NULL),(196,5,'Wiwilí de Nueva Segovia','wiwilí-de-nueva-segovia',1,NULL,NULL,NULL,NULL),(197,5,'Jalapa','jalapa',1,NULL,NULL,NULL,NULL),(198,5,'Macuelizo','macuelizo',1,NULL,NULL,NULL,NULL),(199,5,'Mozonte','mozonte',1,NULL,NULL,NULL,NULL),(200,5,'Murra','murra',1,NULL,NULL,NULL,NULL),(201,5,'Ocotal','ocotal',1,NULL,NULL,NULL,NULL),(202,5,'Quilalí','quilalí',1,NULL,NULL,NULL,NULL),(203,5,'San Fernando','san-fernando',1,NULL,NULL,NULL,NULL),(204,5,'Santa María','santa-maría',1,NULL,NULL,NULL,NULL),(205,5,'El Almendro','el-almendro',1,NULL,NULL,NULL,NULL),(206,5,'El Castillo','el-castillo',1,NULL,NULL,NULL,NULL),(207,5,'Morrito','morrito',1,NULL,NULL,NULL,NULL),(208,5,'San Carlos','san-carlos',1,NULL,NULL,NULL,NULL),(209,5,'San Juan de Nicaragua','san-juan-de-nicaragua',1,NULL,NULL,NULL,NULL),(210,5,'San Miguelito','san-miguelito',1,NULL,NULL,NULL,NULL),(211,5,'Altagracia','altagracia',1,NULL,NULL,NULL,NULL),(212,5,'Belén','belén',1,NULL,NULL,NULL,NULL),(213,5,'Buenos Aires','buenos-aires',1,NULL,NULL,NULL,NULL),(214,5,'Cárdenas','cárdenas',1,NULL,NULL,NULL,NULL),(215,5,'Moyogalpa','moyogalpa',1,NULL,NULL,NULL,NULL),(216,5,'Potosí','potosí',1,NULL,NULL,NULL,NULL),(217,5,'Rivas','rivas',1,NULL,NULL,NULL,NULL),(218,5,'San Jorge','san-jorge',1,NULL,NULL,NULL,NULL),(219,5,'San Juan del Sur','san-juan-del-sur',1,NULL,NULL,NULL,NULL),(220,5,'Tola','tola',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_us`
--

DROP TABLE IF EXISTS `contact_us`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `contact_us` (
  `contact_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(255) DEFAULT NULL,
  `contact_type` int(11) DEFAULT NULL,
  `c_name` varchar(255) DEFAULT NULL,
  `c_phone` varchar(100) DEFAULT NULL,
  `c_email` varchar(255) DEFAULT NULL,
  `c_message` text,
  `created_date` datetime DEFAULT NULL,
  `is_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_us`
--

LOCK TABLES `contact_us` WRITE;
/*!40000 ALTER TABLE `contact_us` DISABLE KEYS */;
INSERT INTO `contact_us` VALUES (25,'pregunta',3,'JC','83298869','jcnamendi@gmail.com','TESTO','2018-07-30 10:03:45',1);
/*!40000 ALTER TABLE `contact_us` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content_definition`
--

DROP TABLE IF EXISTS `content_definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `content_definition` (
  `content_dep_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=649 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_definition`
--

LOCK TABLES `content_definition` WRITE;
/*!40000 ALTER TABLE `content_definition` DISABLE KEYS */;
INSERT INTO `content_definition` VALUES (423,'Category_Name'),(424,'Category_Name'),(425,'Category_Name'),(426,'Category_Name'),(427,'Category_Name'),(428,'Category_Name'),(429,'Category_Name'),(430,'Category_Name'),(431,'Category_Name'),(432,'Category_Name'),(433,'Category_Name'),(434,'Category_Name'),(435,'Category_Name'),(436,'Category_Name'),(437,'Category_Name'),(438,'Category_Name'),(439,'Category_Name'),(440,'Category_Name'),(441,'Category_Name'),(442,'Category_Name'),(443,'Category_Name'),(444,'Category_Name'),(445,'Category_Name'),(446,'Burger King'),(447,'Burger King'),(448,'Burger King'),(449,'Burger King'),(450,'Burger King'),(451,'Burger King'),(452,'Burger King'),(453,'Burger King'),(454,'Burger King'),(455,'Burger King'),(456,'Burger King'),(457,'Burger King'),(458,'Home Cook Deli'),(459,'Home Cook Deli'),(460,'Jollibee'),(461,'Al Fresco\'s'),(462,''),(463,''),(464,''),(465,''),(466,''),(467,''),(468,''),(469,''),(470,''),(471,''),(472,''),(473,''),(474,''),(475,''),(476,''),(477,''),(478,''),(479,''),(480,''),(481,''),(482,''),(483,''),(484,''),(485,''),(486,''),(487,''),(488,''),(489,''),(490,''),(491,''),(492,''),(493,''),(494,''),(495,''),(496,''),(497,''),(498,''),(499,''),(500,''),(501,''),(502,''),(503,''),(504,''),(505,''),(506,''),(507,''),(508,''),(509,'Menu_item'),(510,'Menu_item'),(511,'Menu_item'),(512,'Menu_item'),(513,'Menu_item'),(514,'Menu_item'),(515,'Menu_item'),(516,'Menu_item'),(517,'Menu_item'),(518,'Menu_item'),(519,'Menu_item'),(520,'Menu_item'),(521,'Menu_item'),(522,'Menu_extral_item'),(523,'Extra_item'),(524,'Menu_item'),(525,'Menu_extral_item'),(526,'Extra_item'),(527,'Menu_item'),(528,'Menu_item'),(529,'Menu_item'),(530,'Menu_item'),(531,'Menu_item'),(532,'Menu_item'),(536,'Menu_item'),(537,'Menu_item'),(538,'Menu_item'),(539,'Menu_item'),(540,'Menu_item'),(541,'Menu_item'),(542,'Menu_item'),(543,'Menu_item'),(544,'Menu_item'),(545,'Menu_item'),(546,'Menu_item'),(547,'Menu_extral_item'),(548,'Extra_item'),(549,'Extra_item'),(550,'Menu_item'),(551,'Menu_extral_item'),(552,'Extra_item'),(553,'Menu_item'),(554,'Menu_item'),(555,'Menu_item'),(556,'Menu_item'),(557,'Menu_item'),(558,'Menu_item'),(559,'Menu_item'),(560,'Menu_item'),(561,'Menu_extral_item'),(562,'Extra_item'),(563,'Extra_item'),(564,'Extra_item'),(565,'Extra_item'),(566,'Extra_item'),(567,'Menu_item'),(568,'Menu_extral_item'),(569,'Extra_item'),(570,'Extra_item'),(571,'Extra_item'),(572,'Extra_item'),(573,'Extra_item'),(574,'Menu_item'),(575,'Menu_extral_item'),(576,'Extra_item'),(577,'Extra_item'),(578,'Extra_item'),(579,'Extra_item'),(580,'Extra_item'),(581,'Menu_item'),(582,'Menu_item'),(583,'Menu_item'),(584,'Menu_item'),(585,'Menu_item'),(586,'Menu_extral_item'),(587,'Extra_item'),(588,'Extra_item'),(589,'Extra_item'),(590,'Menu_item'),(591,'Menu_item'),(592,'Menu_item'),(593,'Menu_item'),(594,'Menu_item'),(595,'Menu_item'),(601,'Menu_extral_item'),(602,'Extra_item'),(603,'Extra_item'),(604,'Extra_item'),(605,'Extra_item'),(606,'Menu_extral_item'),(607,'Extra_item'),(608,'Extra_item'),(609,'Promotion_Name'),(610,'ffff'),(611,'Promotion_Name'),(612,'Promotion_Name'),(613,'Promotion_Name'),(614,'Category_Name'),(615,'Carne Asada Donatello'),(616,''),(617,'Menu_item'),(618,'Menu_extral_item'),(619,'Extra_item'),(620,'Menu_item'),(623,'Menu_extral_item'),(624,'Extra_item'),(625,'Menu_extral_item'),(626,'Extra_item'),(627,'Extra_item'),(628,''),(629,'Promotion_Name'),(630,'Menu_item'),(640,'Menu_extral_item'),(641,'Extra_item'),(642,'Extra_item'),(643,'Extra_item'),(644,'Extra_item'),(645,'Menu_extral_item'),(646,'Extra_item'),(647,'Extra_item'),(648,'Menu_item');
/*!40000 ALTER TABLE `content_definition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content_email`
--

DROP TABLE IF EXISTS `content_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `content_email` (
  `content_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language_id` bigint(20) NOT NULL,
  `Subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `content_body` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_status` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `to` text,
  `cc` text,
  PRIMARY KEY (`content_id`),
  KEY `fk_lang_con` (`language_id`),
  CONSTRAINT `fk_lang_con` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_email`
--

LOCK TABLES `content_email` WRITE;
/*!40000 ALTER TABLE `content_email` DISABLE KEYS */;
INSERT INTO `content_email` VALUES (1,10,'Almost done! Please confirm your account.','Almost done! Please confirm your account.',3,'<div class=\"email-confirm\" style=\"float: left; width: 50%; margin: 0 auto\">\r\n        <div class=\"email-header\" style=\"float: left; width: 100%;\">\r\n            <img style=\"width: 65px; float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n            <h2 style=\"float: right; color: #777474; margin-left: 20px;\">Almost done! Please confirm your account.</h2>\r\n        </div>\r\n        <div class=\"email-body\" style=\"float: left; border:1px solid #cccccc; width: 100%; margin-top: 15px; padding: 10px\">\r\n            <h3 style=\"color: blue; margin: 0; border-bottom: 1px solid #ccc; padding-bottom: 10px;\">Just one more step to complete!</h3>\r\n            <p>Your account is almost done! Please click on the confirmation link below to complete and use your account.</p>\r\n            <a style=\"text-transform: uppercase\" href=\"${urlVerify}\">VERIFY ACCOUNT</a>\r\n        </div>\r\n        <div class=\"email-footer\" style=\"float: left; text-align: center; width: 100%; padding-top: 15px;\">\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn/\">Homepage</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn//contact-us\">Contact us</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26\" href=\"https://bmbsoft.com.vn//term\">Term</a>\r\n            <p>&copy; ${year} ${siteName}</p>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(2,10,'New password for your account','New password for your account',4,'<div class=\"email-confirm\" style=\"float: left; width: 50%; margin: 0 auto\">\r\n        <div class=\"email-header\" style=\"float: left; width: 100%;\">\r\n            <img style=\"width: 65px; float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n            <h2 style=\"float: right; color: #777474; margin-left: 20px;\">New password for your account</h2>\r\n        </div>\r\n        <div class=\"email-body\" style=\"float: left; border:1px solid #cccccc; width: 100%; margin-top: 15px; padding: 10px\">\r\n            <h3 style=\"color: blue; margin: 0; border-bottom: 1px solid #ccc; padding-bottom: 10px;\">A new password has been created for your account with ${siteName}!</h3>\r\n            <p>Hello ${fullName},</p>\r\n            <p>You have just requested a new password for your customer account with ${siteName}.</p>\r\n            <p>Your new password and other account data are listed below.</p>\r\n\r\n            <p>Username: ${userName}</p>\r\n            <p>New password: ${newpass}</p>\r\n\r\n            <p>Yours sincerely, </p>\r\n            <p>${siteName}</p>\r\n        </div>\r\n        <div class=\"email-footer\" style=\"float: left; text-align: center; width: 100%; padding-top: 15px;\">\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn/\">Homepage</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn//contact-us\">Contact us</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26\" href=\"https://bmbsoft.com.vn//term\">Term</a>\r\n            <p>&copy; ${year} ${siteName}</p>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(4,8,'Nova senha para sua conta','Nova senha para sua conta',4,'<div class = \"email-confirm\" style = \"flutuante: esquerdo; largura: 50%; margem: 0 auto\">\r\n        <div class = \"email-header\" style = \"flutuante: esquerdo; largura: 100%;\">\r\n            <img style = \"width: 65px; float: esquerda;\" src = \"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n            <h2 style = \"float: right; color: # 777474; margin-left: 20px;\"> Nova senha para sua conta </ ​​h2>\r\n        </ div>\r\n        <div class = \"email-body\" style = \"flutuante: esquerdo; limite: 1px sólido #cccccc; largura: 100%; margem superior: 15px; preenchimento: 10px\">\r\n            <h3 style = \"color: blue; margin: 0; borda inferior: 1px solid #ccc; padding-bottom: 10px;\"> Uma nova senha foi criada para sua conta com ${siteName}! </ h3>\r\n            <p> Olá, ${fullName}, </ p>\r\n            <p> Você acabou de solicitar uma nova senha para sua conta de cliente no ${siteName}. </ p>\r\n            <p> Sua nova senha e outros dados da conta estão listados abaixo. </ p>\r\n\r\n            <p> Nome de usuário: ${userName} </ p>\r\n            <p> Nova senha: ${newpass} </ p>\r\n\r\n            <p> Atenciosamente, </ p>\r\n            <p> ${siteName} </ p>\r\n        </ div>\r\n        <div class = \"email-rodapé\" style = \"float: left; alinhamento de texto: centro; largura: 100%; padding-top: 15px;\">\r\n            <a style = \"color: # ff7d26; margin: 0 5px;\" href = \"https://bmbsoft.com.vn/\"> Página inicial </a> <span style = \"color: #cccccc; fonte-peso: negrito\"> | </ span>\r\n            <a style = \"color: # ff7d26; margin: 0 5px;\" href = \"https://bmbsoft.com.vn//entreemcontato\"> Entre em contato </a> <span style = \"color: #cccccc; font-weight: bold\"> | </ span>\r\n            <a style=\"color: #ff7d26\" href=\"https://bmbsoft.com.vn//term\"> Termo </a>\r\n            <p> e cópia; ${year} ${siteName} </ p>\r\n        </ div>\r\n    </ div>',1,NULL,NULL,NULL,NULL),(5,8,'Quase pronto! Por favor, confirme sua conta.','Quase pronto! Por favor, confirme sua conta.',3,'<div class = \"email-confirm\" style = \"flutuante: esquerdo; largura: 50%; margem: 0 auto\">\r\n        <div class = \"email-header\" style = \"flutuante: esquerdo; largura: 100%;\">\r\n            <img style = \"width: 65px; float: esquerda;\" src = \"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n            <h2 style = \"float: right; color: # 777474; margem-esquerda: 20px;\"> Quase pronto! Por favor, confirme sua conta. </ H2>\r\n        </ div>\r\n        <div class = \"email-body\" style = \"flutuante: esquerdo; limite: 1px sólido #cccccc; largura: 100%; margem superior: 15px; preenchimento: 10px\">\r\n            <h3 style = \"color: blue; margin: 0; border-bottom: 1px sólido #ccc; padding-bottom: 10px;\"> Apenas mais um passo para concluir! </ h3>\r\n            <p> Sua conta está quase pronta! Por favor, clique no link de confirmação abaixo para completar e usar sua conta. </ P>\r\n            <a style=\"text-transform: uppercase\" href=\"${urlVerify}\"> VERIFIQUE CONTA </a>\r\n        </ div>\r\n        <div class = \"email-rodapé\" style = \"float: left; alinhamento de texto: centro; largura: 100%; padding-top: 15px;\">\r\n            <a style = \"color: # ff7d26; margin: 0 5px;\" href = \"https://bmbsoft.com.vn/\"> Página inicial </a> <span style = \"color: #cccccc; fonte-peso: negrito\"> | </ span>\r\n            <a style = \"color: # ff7d26; margin: 0 5px;\" href = \"https://bmbsoft.com.vn//entreemcontato\"> Entre em contato </a> <span style = \"color: #cccccc; font-weight: bold\"> | </ span>\r\n            <a style=\"color: #ff7d26\" href=\"https://bmbsoft.com.vn//term\"> Termo </a>\r\n            <p> e cópia; ${year} ${siteName} </ p>\r\n        </ div>\r\n    </ div>',1,NULL,NULL,NULL,NULL),(6,10,'Your order (${orderCode}) at ${restaurantName}','Payment',5,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\">\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Order: (${orderCode}) at ${restaurantName}</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Order code: ${orderCode}\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:9pt;font-weight:normal\">\r\n                    <b>Your order has been processed and transferred to ${restaurantName}</b>\r\n                    <br>\r\n                    <br> If you have an urgent request or other note about your order, please call ${restaurantAddress} on ${restaurantPhone}. With other issues click on Mimerol.com.\r\n                    <a href=\"https://mimerol.bmbsoft.com.vn/contact-us\" style=\"color:#ff3102;font-weight:bold;text-decoration:none\" target=\"_blank\">click here</a> or call us on 1800 6123\r\n                    <br>\r\n                    <br> Be careful: please do not reply to this email. ${restaurantName} may wish to contact you. Please check\r\n                    the phone number you provided and your email.\r\n                    <br>\r\n                    <br> On Sundays and holidays, the delivery time may be longer than usual. Bad weather and traffic can also\r\n                    affect delivery times. If there is a delay in delivery, ${restaurantName} will contact you for notification.\r\n                    Unfortunately Mimerol.com not related to the preparation time of food as well as the delivery time\r\n                    of ${restaurantName}.\r\n                    <br>\r\n                    <br> On behalf of ${restaurantName},\r\n                    <b style=\"color:#ff3102\">Mimerol.com</b> thank you for your order!\r\n                </div>\r\n                <br>\r\n                <div style=\"height:40px;border:1px solid #eaeaea;background:#f7f7f7;font-family:Arial;text-align:left;font-size:9pt;font-weight:bold;color:#000000;padding-bottom:10px;padding-top:5px\">\r\n                    <table>\r\n                        <tbody>\r\n                            <tr>\r\n                                <td style=\"vertical-align:middle;width:45px;padding-left:10px\">\r\n                                    <img style=\"vertical-align:middle\" src=\"https://ci5.googleusercontent.com/proxy/0GWS4e33LgEMcMqXJ9JhVdlIckIWPPsQw5HiYqngQcq0EEgsmHyLT8fl2dxIAEd1rGmOQPpESqKJU3sLOkvg1G_12tOFiuBFwPdSpbE=s0-d-e1-ft#https://static.vietnammm.com/images/paymethods/cash.gif\">\r\n                                </td>\r\n                                <td style=\"vertical-align:middle;font-weight:bold;padding-left:10px\">\r\n                                    <span style=\"font-family:Arial;font-size:10pt\">Transaction Fee: ${paymentType}\r\n                                        <br>\r\n                                        <i>The exact amount</i>\r\n                                    </span>\r\n                                </td>\r\n                            </tr>\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n            </div>\r\n            <br>\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Your order\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    <table style=\"width:100%;font-family:Arial;font-size:13px\">\r\n                        <tbody>\r\n                                ${orderLineItems}\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n                <div style=\"font-family:Arial;text-align:right;font-size:9pt;font-weight:normal\">\r\n                    <table style=\"width:100%;font-family:Arial;text-align:right;font-size:9pt;font-weight:normal\">\r\n                        <tbody>\r\n                            <tr>\r\n                                <td>Delivery charges:</td>\r\n                                <td style=\"padding-left:10px;width:70px\">${deliveryCost} ${symbolLeft}</td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td>\r\n                                    <b>Total:</b>\r\n                                </td>\r\n                                <td style=\"padding-left:10px;width:70px\">\r\n                                    <b>${totalPrice} ${symbolLeft}</b>\r\n                                </td>\r\n                            </tr>\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n            </div>\r\n            <br>\r\n            <div style=\"overflow:auto;width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Your address\r\n                </div>\r\n                <br>\r\n                <div style=\"float:left;font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    ${userName}\r\n                    <br>${userAddress}\r\n                    <br>${userDistrict}\r\n                    <br>${userCity} \r\n                    <br>Phone number: ${userNumber}\r\n                    <br>\r\n                </div>\r\n\r\n            </div>\r\n            <br>\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Other information\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    Delivery time:\r\n                    <b>${deliveryTime}</b>\r\n                    <br>Note:\r\n                    <br>${remarks}\r\n                    <br>\r\n                </div>\r\n\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Homepage</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Customer service</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Terms of use</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2018 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,'dinhmao.it@gmail.com;qviet92@gmail.com'),(7,10,'${orderCode} Delivered','Delivered',6,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">[${orderCode}] Delivered</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Dear customer,\r\n                </div>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:12px;font-weight:normal\">\r\n                    <br> Your order with order number\r\n                    <b>${orderCode}</b> has been\r\n                    <u>successfully sent</u> to\r\n                    <b>${restaurantName} </b>. We wish you a very enjoyable dinner!\r\n                    <br>\r\n                    <br> Regards,\r\n                    <br>\r\n                    <a href=\"#\" target=\"_blank\">mimerol.com</a>\r\n                    <br>\r\n                    <br> N.B. You are receiving this email because you have ordered after ${openTime}h and our callcenter is closed\r\n                    after ${closeTime}h. When ordering throughout the day you will not receive this extra email.\r\n                </div>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Homepage</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Customer service</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Terms of use</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,'dinhmao.it@gmail.com'),(8,10,'Mimerol.com news, promotions and incentives','Promotion',7,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Mimerol.com news, promotions and incentives</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Please confirm your change\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:12px;font-weight:normal\">\r\n                    You have just changed one or more options in your account Vietnammm.com. To agree to the changes above, please click the\r\n                    button below.\r\n                    <br>\r\n                    <br>\r\n                    <a href=\"#\" target=\"_blank\">Confirm your change</a>\r\n                </div>\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Homepage</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Customer service</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Terms of use</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(9,10,'Comment on your order from \"${restaurantName}\" (${orderCode})','Review',8,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Comment on your order from \"${restaurantName}\" (${orderCode})</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:12px;padding-bottom:10px\">\r\n                    Welcome ${fullName},\r\n                    <br>\r\n                    <br> Thank you for ordering at \"${restaurantName}\". Hope the dishes you put are wonderful.\r\n                    We would like to invite you to review for your order. Your comment will be very important for other guests\r\n                    to choose the restaurant to order.\r\n                    <br>\r\n                    <br>\r\n                    <a href=\"#\" style=\"color:#3559b5;font-weight:bold;text-decoration:underline;font-size:14px\" target=\"_blank\">Comment on your order here</a>\r\n                    <br>\r\n                    <br> Do you order delicious food? Please spread this information! Do you have complaints or comments about\r\n                    your order? You can contact the restaurant directly so they can assist you the fastest. And of course,\r\n                    you can contact our Customer Service at any time.\r\n                    <br>\r\n                    <br> Kind regards,\r\n                    <br>\r\n                    <b style=\"color:#ff3102\">\r\n                        <a href=\"https://bmbsoft.com.vn/\" target=\"_blank\">mimerol.com</a>\r\n                    </b>\r\n                </div>\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Homepage</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Customer service</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Terms of use</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(10,8,'Su pedido ($ {orderCode}) en $ {restaurantName}','Payment',5,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\">\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Orden: (3ZFY1F) en ${restaurantName}</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Código de pedido: ${orderCode}\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:9pt;font-weight:normal\">\r\n                    <b>Su orden ha sido procesada y transferida a ${restaurantName}</b>\r\n                    <br>\r\n                    <br> Si tiene una solicitud urgente u otra nota sobre su pedido, llame a ${restaurantName} al ${restaurantPhone}.\r\n                    Si tiene otros problemas, haga clic en Mimerol.com.\r\n                    <a href=\"https://mimerol.bmbsoft.com.vn/contact-us\" style=\"color:#ff3102;font-weight:bold;text-decoration:none\" target=\"_blank\">haga clic aquí</a> o llámenos al 1800 6123\r\n                    <br>\r\n                    <br> Tenga cuidado: no responda a este correo electrónico. ${restaurantName} puede desear contactarte. Por\r\n                    favor, verifique el número de teléfono que proporcionó y su correo electrónico.\r\n                    <br>\r\n                    <br> Los domingos y festivos, el tiempo de entrega puede ser más prolongado de lo habitual. El mal tiempo\r\n                    y el tráfico también pueden afectar los tiempos de entrega. Si hay un retraso en la entrega, ${restaurantName}\r\n                    se comunicará con usted para recibir una notificación. Desafortunadamente, Mimerol.com no está relacionado\r\n                    con el tiempo de preparación de la comida ni con el tiempo de entrega de Danang Baked Meats.\r\n                    <br>\r\n                    <br> En nombre de ${restaurantName},\r\n                    <b style=\"color:#ff3102\">Mimerol.com</b> ¡gracias por su orden!\r\n                </div>\r\n                <br>\r\n                <div style=\"height:40px;border:1px solid #eaeaea;background:#f7f7f7;font-family:Arial;text-align:left;font-size:9pt;font-weight:bold;color:#000000;padding-bottom:10px;padding-top:5px\">\r\n                    <table>\r\n                        <tbody>\r\n                            <tr>\r\n                                <td style=\"vertical-align:middle;width:45px;padding-left:10px\">\r\n                                    <img style=\"vertical-align:middle\" src=\"https://ci5.googleusercontent.com/proxy/0GWS4e33LgEMcMqXJ9JhVdlIckIWPPsQw5HiYqngQcq0EEgsmHyLT8fl2dxIAEd1rGmOQPpESqKJU3sLOkvg1G_12tOFiuBFwPdSpbE=s0-d-e1-ft#https://static.vietnammm.com/images/paymethods/cash.gif\">\r\n                                </td>\r\n                                <td style=\"vertical-align:middle;font-weight:bold;padding-left:10px\">\r\n                                    <span style=\"font-family:Arial;font-size:10pt\">Tarifa de transacción: ${paymentType}\r\n                                        <br>\r\n                                        <i>El monto exacto</i>\r\n                                    </span>\r\n                                </td>\r\n                            </tr>\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n            </div>\r\n            <br>\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Su pedido\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    <table style=\"width:100%;font-family:Arial;font-size:13px\">\r\n                        <tbody>\r\n                            ${orderLineItems}\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n                <div style=\"font-family:Arial;text-align:right;font-size:9pt;font-weight:normal\">\r\n                    <table style=\"width:100%;font-family:Arial;text-align:right;font-size:9pt;font-weight:normal\">\r\n                        <tbody>\r\n                            <tr>\r\n                                <td>Los gastos de envío:</td>\r\n                                <td style=\"padding-left:10px;width:70px\">${deliveryCost} ${symbolLeft}</td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td>\r\n                                    <b>Total:</b>\r\n                                </td>\r\n                                <td style=\"padding-left:10px;width:70px\">\r\n                                    <b>${totalPrice} ${symbolLeft}</b>\r\n                                </td>\r\n                            </tr>\r\n                        </tbody>\r\n                    </table>\r\n                </div>\r\n            </div>\r\n            <br>\r\n            <div style=\"overflow:auto;width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Your address\r\n                </div>\r\n                <br>\r\n                <div style=\"float:left;font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    ${userName}\r\n                    <br>${userAddress}\r\n                    <br>${userDistrict}\r\n                    <br>${userCity} \r\n                    <br>\r\n                    <br>Número de teléfono: ${userNumber}\r\n                    <br>\r\n                </div>\r\n\r\n            </div>\r\n            <br>\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px;font-family:Arial;font-size:9pt\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Otra información\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:left;font-size:9pt;font-weight:normal\">\r\n                    El tiempo de entrega:\r\n                    <b>${deliveryTime}</b>\r\n                    <br>Note:\r\n                    <br>${remarks}\r\n                    <br>\r\n                </div>\r\n\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Página principal</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Servicio al cliente</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Términos de Uso</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,'dinhmao.it@gmail.com;qviet92@gmail.com'),(11,8,'$ {orderCode} Entregado','Delivered',6,' <div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">[${orderCode}] Entregado</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Estimado cliente,\r\n                </div>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:12px;font-weight:normal\">\r\n                    <br> Su pedido con número de pedido\r\n                    <b>${orderCode}</b> ha sido\r\n                    <u>enviado exitosamente</u> a\r\n                    <b>${restaurantName} </b>. ¡Te deseamos una cena muy agradable!\r\n                    <br>\r\n                    <br> Saludos,\r\n                    <br>\r\n                    <a href=\"https://bmbsoft.com.vn/\" target=\"_blank\">mimerol.com</a>\r\n                    <br>\r\n                    <br> Recibirá este correo electrónico porque lo ordenó después de las 23:00 hy nuestro centro de llamadas\r\n                    se cerró después de las 23:00 h. Al hacer el pedido durante todo el día, no recibirá este correo electrónico\r\n                    adicional\r\n                </div>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Página principal</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Servicio al cliente</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Términos de Uso</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(12,8,'Mimerol.com noticias, promociones e incentivos','Promotion',7,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Mimerol.com noticias, promociones e incentivos</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Por favor confirma tu cambio\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:12px;font-weight:normal\">\r\n                    Acaba de cambiar una o más opciones en su cuenta Vietnammm.com. Para aceptar los cambios anteriores, haga clic en el botón\r\n                    a continuación.\r\n                    <br>\r\n                    <br>\r\n                    <a href=\"#\" target=\"_blank\">CERTIFICAR CAMBIOS</a>\r\n                </div>\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Página principal</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Servicio al cliente</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Términos de Uso</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div><div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn:8443/images/mimerol_logo.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Mimerol.com noticias, promociones e incentivos</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:16px;font-weight:bold;color:#3256b4;padding-bottom:10px\">\r\n                    Por favor confirma tu cambio\r\n                </div>\r\n                <br>\r\n                <div style=\"font-family:Arial;text-align:justify;font-size:12px;font-weight:normal\">\r\n                    Acaba de cambiar una o más opciones en su cuenta Vietnammm.com. Para aceptar los cambios anteriores, haga clic en el botón\r\n                    a continuación.\r\n                    <br>\r\n                    <br>\r\n                    <a href=\"#\" target=\"_blank\">CERTIFICAR CAMBIOS</a>\r\n                </div>\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Página principal</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Servicio al cliente</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Términos de Uso</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(13,8,'Comenta tu pedido de \"${restaurantName}\" (${orderCode})\r\n','Review',8,'<div style=\"text-align:center\">\r\n        <div style=\"width:600px\">\r\n            <table style=\"width:600px;height:118px\">\r\n                <tbody>\r\n                    <tr>\r\n                        <td style=\"vertical-align:bottom;width:112px\">\r\n                            <img style=\"width: 65px;float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n                        </td>\r\n                        <td style=\"vertical-align:bottom;width:480px;text-align:right;font-family:Arial;font-size:20px;color:#999999;font-weight:bold\">Comenta tu pedido de \"${restaurantName}\" (${orderCode})</td>\r\n                    </tr>\r\n                </tbody>\r\n            </table>\r\n\r\n            <div style=\"width:578px;text-align:center;border:1px solid #eaeaea;padding:10px\">\r\n                <div style=\"border-bottom:1px solid #eaeaea;font-family:Arial;text-align:left;font-size:12px;padding-bottom:10px\">\r\n                    Bienvenido ${fullName},\r\n                    <br>\r\n                    <br> Gracias por pedir en \"${restaurantName}\". Espero que los platos que pones sean maravillosos.\r\n                    Nos gustaría invitarlo a revisar su pedido. Su comentario será muy importante para que otros huéspedes\r\n                    elijan el restaurante que ordenan.\r\n                    <br>\r\n                    <br>\r\n                    <a href=\"#\" style=\"color:#3559b5;font-weight:bold;text-decoration:underline;font-size:14px\" target=\"_blank\">Comenta tu pedido aquí</a>\r\n                    <br>\r\n                    <br> ¿Pides comida deliciosa? ¡Divulgue esta información! ¿Tiene quejas o comentarios sobre su pedido? Puedes\r\n                    contactar al restaurante directamente para que puedan ayudarte más rápido. Y, por supuesto, puede comunicarse\r\n                    con nuestro Servicio al Cliente en cualquier momento.\r\n                    <br>\r\n                    <br> Saludos cordiales,\r\n                    <br>\r\n                    <b style=\"color:#ff3102\">\r\n                        <a href=\"https://bmbsoft.com.vn/\" target=\"_blank\">mimerol.com</a>\r\n                    </b>\r\n                </div>\r\n            </div>\r\n\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:10px\">\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Página principal</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\">Servicio al cliente</a> |\r\n                <a style=\"color:#ff3102;font-weight:bold;text-decoration:none\" href=\"https://bmbsoft.com.vn/\" target=\"_blank\">Términos de Uso</a>\r\n            </div>\r\n            <div style=\"width:600px;text-align:center;font-family:Arial;font-size:12px;padding-top:4px;color:#656565\">\r\n                © 2008 - 2018 Mimerol.com\r\n            </div>\r\n        </div>\r\n    </div>',2,NULL,NULL,NULL,NULL),(14,10,'Create new user','CreateNewUser',9,'<div class=\"email-confirm\" style=\"float: left; width: 50%; margin: 0 auto\">\r\n        <div class=\"email-header\" style=\"float: left; width: 100%;\">\r\n            <img style=\"width: 65px; float: left;\" src=\"https://bmbsoft.com.vn/72a3037e132ea5d7ff5e879d96599072.png\" />\r\n            <h2 style=\"float: right; color: #777474; margin-left: 20px;\">Register new user</h2>\r\n        </div>\r\n        <div class=\"email-body\" style=\"float: left; border:1px solid #cccccc; width: 100%; margin-top: 15px; padding: 10px\">\r\n            <h3 style=\"color: blue; margin: 0; border-bottom: 1px solid #ccc; padding-bottom: 10px;\">Create new user successfully!</h3>\r\n            <p>Hello ${fullName},</p>\r\n            <p>You already become a member of system ${siteName}.</p>\r\n            <p>Please access into system with information below:</p>\r\n\r\n            <p>Username: ${userName}</p>\r\n            <p>Password: ${password}</p>\r\n            <p>Url     :  <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn/\">${siteName}</a></p>\r\n \r\n            <p>Yours sincerely, </p>\r\n            <p>${siteName}</p>\r\n        </div>\r\n        <div class=\"email-footer\" style=\"float: left; text-align: center; width: 100%; padding-top: 15px;\">\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn/\">Homepage</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26; margin: 0 5px;\" href=\"https://bmbsoft.com.vn//contact-us\">Contact us</a><span style=\"color: #cccccc; font-weight: bold\">|</span>\r\n            <a style=\"color: #ff7d26\" href=\"https://bmbsoft.com.vn//term\">Term</a>\r\n            <p>&copy; ${year} ${siteName}</p>\r\n        </div>\r\n    </div>',1,NULL,NULL,NULL,NULL),(15,8,'Create new user','CreateNewUser',9,'https://bmbsoft.com.vn:8443/images/mimerol_logo.png',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `content_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content_entry`
--

DROP TABLE IF EXISTS `content_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `content_entry` (
  `content_entry_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language_id` bigint(20) NOT NULL,
  `content_dep_id` bigint(20) NOT NULL,
  `code` text NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`content_entry_id`),
  KEY `fk_language` (`language_id`),
  KEY `fk_content_dep` (`content_dep_id`),
  CONSTRAINT `fk_content_dep` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1946 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_entry`
--

LOCK TABLES `content_entry` WRITE;
/*!40000 ALTER TABLE `content_entry` DISABLE KEYS */;
INSERT INTO `content_entry` VALUES (1224,8,423,'category_name','Arroz'),(1225,10,423,'category_name','Rice'),(1228,8,424,'category_name','Arroz'),(1229,10,424,'category_name','Rice_update'),(1230,8,425,'category_name','Hamburguesas'),(1231,10,425,'category_name','Burgers'),(1232,8,426,'category_name','Americano'),(1233,10,426,'category_name','American'),(1234,8,427,'category_name','Categoría eliminada'),(1235,10,427,'category_name','Deleted category'),(1236,8,428,'category_name','Sándwich'),(1237,10,428,'category_name','Sandwiches'),(1238,8,429,'category_name','Sopas de fideos'),(1239,10,429,'category_name','Noodle soups'),(1240,8,430,'category_name','Italiano y Pizza'),(1241,10,430,'category_name','Italian & Pizza'),(1242,8,431,'category_name','Japonesa / Sushi'),(1243,10,431,'category_name','Japanese/Sushi'),(1244,8,432,'category_name','Almuerzo de oficina'),(1245,10,432,'category_name','Office lunch'),(1246,8,433,'category_name','Coreano'),(1247,10,433,'category_name','Korean'),(1248,8,434,'category_name','Mexicano'),(1249,10,434,'category_name','Mexican'),(1250,8,435,'category_name','Ensaladas'),(1251,10,435,'category_name','Salads'),(1252,8,436,'category_name','Vegetariano'),(1253,10,436,'category_name','Vegetarian'),(1254,8,437,'category_name','Sopas'),(1255,10,437,'category_name','Soups'),(1256,8,438,'category_name','Té de café y leche'),(1257,10,438,'category_name','Coffee & Milk Tea'),(1258,8,439,'category_name','Caracoles / Mariscos'),(1259,10,439,'category_name','Snails/Seafood'),(1260,8,440,'category_name','BBQ'),(1261,10,440,'category_name','BBQ'),(1262,8,441,'category_name','Otro'),(1263,10,441,'category_name','Other'),(1264,8,442,'category_name','Otras bebidas'),(1265,10,442,'category_name','Other drinks'),(1266,8,443,'category_name','Smoothies y jugo de fruta'),(1267,10,443,'category_name','Smoothies & Fruit Juice'),(1268,8,444,'category_name','Medio este'),(1269,10,444,'category_name','Middle Eastern'),(1270,8,445,'category_name','Postres'),(1271,10,445,'category_name','Desserts'),(1274,8,447,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1275,10,447,'restaurant_description','Sell Rice, American and Burgers is main.'),(1276,8,448,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1277,10,448,'restaurant_description','Sell Rice, American and Burgers is main.'),(1278,8,449,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1279,10,449,'restaurant_description','Sell Rice, American and Burgers is main.'),(1280,8,450,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1281,10,450,'restaurant_description','Sell Rice, American and Burgers is main.'),(1282,8,451,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1283,10,451,'restaurant_description','Sell Rice, American and Burgers is main.'),(1284,8,452,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1285,10,452,'restaurant_description','Sell Rice, American and Burgers is main.'),(1286,8,453,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1287,10,453,'restaurant_description','Sell Rice, American and Burgers is main.'),(1288,8,454,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1289,10,454,'restaurant_description','Sell Rice, American and Burgers is main.'),(1290,8,455,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1291,10,455,'restaurant_description','Sell Rice, American and Burgers is main.'),(1292,8,456,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1293,10,456,'restaurant_description','Sell Rice, American and Burgers is main.'),(1294,8,457,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1295,10,457,'restaurant_description','Sell Rice, American and Burgers is main.'),(1302,8,458,'restaurant_description','Vende principalmente sandwiches, hamburguesas, italiano y pizza'),(1303,10,458,'restaurant_description','Sell mainly Sandwiches, Burgers, Italian & Pizza'),(1310,8,462,'menu_name','Double Whopper (comida)'),(1311,10,462,'menu_name','Double Whopper (Meal)'),(1312,8,463,'menu_name','Double Whopper (A la carta)'),(1313,10,463,'menu_name','Double Whopper (A-la-carte)'),(1314,8,464,'menu_name','Whopper (comida)'),(1315,10,464,'menu_name','Whopper (Meal)'),(1316,8,465,'menu_name','Whopper (A la carta)'),(1317,10,465,'menu_name','Whopper (A-la-carte)'),(1318,8,466,'menu_name','Hamburguesa de carne (comida)'),(1319,10,466,'menu_name','Beef Burger (Meal)'),(1320,8,467,'menu_name','Hamburguesa de carne (a la carta)'),(1321,10,467,'menu_name','Beef Burger (A-la-carte)'),(1322,8,468,'menu_name','Hamburguesa de pollo (comida)'),(1323,10,468,'menu_name','Chicken Burger (Meal)'),(1324,8,469,'menu_name','Hamburguesa de pollo (A la carta)'),(1325,10,469,'menu_name','Chicken Burger (A-la-carte)'),(1326,8,470,'menu_name','Pollo frito'),(1327,10,470,'menu_name','Chicken Fried'),(1328,8,471,'menu_name','Rice King'),(1329,10,471,'menu_name','Rice King'),(1330,8,472,'menu_name','Lados y postres'),(1331,10,472,'menu_name','Sides And Desserts'),(1332,8,473,'menu_name','Bebidas'),(1333,10,473,'menu_name','Drinks'),(1334,8,474,'menu_name','café'),(1335,10,474,'menu_name','Coffee'),(1336,8,475,'menu_name','No publicar'),(1337,10,475,'menu_name','Unpublish'),(1338,8,476,'menu_name','Pizza'),(1339,10,476,'menu_name','Pizza'),(1340,8,477,'menu_name','Hamburguesa / sándwich'),(1341,10,477,'menu_name','Burger/Sandwich'),(1342,8,478,'menu_name','Pasta (espagueti - penne)'),(1343,10,478,'menu_name','Pasta (spaghetti - penne)'),(1344,8,479,'menu_name','Comida mexicana'),(1345,10,479,'menu_name','Mexican Food'),(1346,8,480,'menu_name','Selección de ensalada'),(1347,10,480,'menu_name','Salad Selection'),(1348,8,481,'menu_name','Botella de alcohol'),(1349,10,481,'menu_name','Spirit bottle'),(1350,8,482,'menu_name','Cerveza'),(1351,10,482,'menu_name','Beer'),(1352,8,483,'menu_name','Cerveza (6 latas)'),(1353,10,483,'menu_name','Beer (6 cans)'),(1354,8,484,'menu_name','Beer (24 cans)'),(1355,10,484,'menu_name','Beer (24 cans)'),(1356,8,485,'menu_name','Refresco'),(1357,10,485,'menu_name','Soft drink'),(1358,8,486,'menu_name','Cigarrillo'),(1359,10,486,'menu_name','Cigarette'),(1360,8,487,'menu_name','Nuevos platos'),(1361,10,487,'menu_name','New Dishes'),(1362,8,488,'menu_name','Alegría de pollo'),(1363,10,488,'menu_name','Chicken joy'),(1364,8,489,'menu_name','Pollo chili'),(1365,10,489,'menu_name','Chilli chicken'),(1366,8,490,'menu_name','Espaguetis'),(1367,10,490,'menu_name','Spaghetti'),(1368,8,491,'menu_name','Comidas de arroz'),(1369,10,491,'menu_name','Rice meals'),(1370,8,492,'menu_name','Emparedado'),(1371,10,492,'menu_name','Sandwich'),(1372,8,493,'menu_name','Bocadillo'),(1373,10,493,'menu_name','Snack'),(1374,8,494,'menu_name','papas fritas'),(1375,10,494,'menu_name','French fries'),(1376,8,495,'menu_name','Postre'),(1377,10,495,'menu_name','Dessert'),(1378,8,496,'menu_name','Beber'),(1379,10,496,'menu_name','Drink'),(1380,8,497,'menu_name','Platos populares'),(1381,10,497,'menu_name','Popular Dishes'),(1382,8,498,'menu_name','Entrantes'),(1383,10,498,'menu_name','Starters'),(1384,8,499,'menu_name','Ensaladas'),(1385,10,499,'menu_name','Salads'),(1386,8,500,'menu_name','Pastas'),(1387,10,500,'menu_name','Pasta'),(1388,8,501,'menu_name','Hamburguesas'),(1389,10,501,'menu_name','Burgers'),(1390,8,502,'menu_name','Costillas'),(1391,10,502,'menu_name','Ribs'),(1392,8,503,'menu_name','Selección de Chef y Principal'),(1393,10,503,'menu_name','Chef\'s Selection & Main'),(1394,8,504,'menu_name','Pizza (grande)'),(1395,10,504,'menu_name','Pizza (Large)'),(1396,8,505,'menu_name','Pizzas (Xlarge)'),(1397,10,505,'menu_name','Pizzas (Xlarge)'),(1398,8,506,'menu_name','Té helado Lipton'),(1399,10,506,'menu_name','Lipton ice tea'),(1400,8,507,'menu_name','Bebidas sin alcohol'),(1401,10,507,'menu_name','Soft drinks'),(1402,8,508,'menu_name','Zumo de frutas'),(1403,10,508,'menu_name','Fruit juice'),(1412,10,510,'menu_item_description','Grilled BBQ chicken fillet topped with cheese, lettuce, onion & tomato.'),(1413,10,510,'menu_item_name','BBQ Al\'s Chicken Burger'),(1414,8,510,'menu_item_name','Hamburguesa de pollo BBQ Al\'s'),(1415,8,510,'menu_item_description','Filete de pollo asado a la parrilla con queso, lechuga, cebolla y tomate.'),(1416,8,509,'menu_item_name','Hamburguesa de queso y carne'),(1417,8,509,'menu_item_description','Empanada de carne importada, cubierta con queso, lechuga, cebolla y tomate.'),(1418,10,509,'menu_item_description','Beef Cheese Burger'),(1419,10,509,'menu_item_name','Imported beef patty, topped with cheese, lettuce, onion & tomato.'),(1420,8,511,'menu_item_name','Steak Burger'),(1421,8,511,'menu_item_description','Bistec importado cubierto con tocino, cebolla frita y queso derretido. Elección de: Raras, medianas o bien hechas.'),(1422,10,511,'menu_item_name','Steak Burger'),(1423,10,511,'menu_item_description','Imported steak topped with bacon, fried onion & melted cheese. Choice of: Rare, Medium or Well done.'),(1424,8,512,'menu_item_name','Aussie Burger'),(1425,8,512,'menu_item_description','Aussie empanada de carne, cubierto con queso, tocino, huevo, tomate, lechuga, remolacha y cebolla frita. Elección de: Raras, medianas o bien hechas.'),(1426,10,512,'menu_item_name','Aussie Burger'),(1427,10,512,'menu_item_description','Aussie beef patty, topped with cheese, bacon, egg, tomato, lettuce, beetroot & fried onion. Choice of: Rare, Medium or Well done.'),(1428,8,513,'menu_item_name','Grupo Pigmeo (6)'),(1429,8,513,'menu_item_description','6 RIBS de atajo a la parrilla servidos con cuñas.'),(1430,10,513,'menu_item_name','Pygmy Group (6)'),(1431,10,513,'menu_item_description','6 grilled shortcut RIBS served with wedges.'),(1432,8,514,'menu_item_name','Mini costillas (no tan mini)'),(1433,8,514,'menu_item_description','5-6 BBQ RIBS de tamaño completo con cuñas.'),(1434,10,514,'menu_item_name','Mini Ribs (Not So Mini)'),(1435,10,514,'menu_item_description','5-6 full size BBQ RIBS served with wedges.'),(1436,8,515,'menu_item_name','Plato pigmeo (10 piezas)'),(1437,8,515,'menu_item_description','10 costillas de atajo a la parrilla, servidas con cuñas.'),(1438,10,515,'menu_item_name','Pygmy Platter (10Pcs)'),(1439,10,515,'menu_item_description','10 grilled shortcut RIBS, served with wedges.'),(1440,8,516,'menu_item_name','Caja de pigmeos (20 piezas)'),(1441,8,516,'menu_item_description','20 RIBS de atajo a la parrilla, servidos con cuñas.'),(1442,10,516,'menu_item_name','Pygmy Box (20Pcs)'),(1443,10,516,'menu_item_description','20 grilled shortcut RIBS, served with wedges.'),(1444,8,517,'menu_item_name','Soda de arándanos (grande)'),(1445,8,517,'menu_item_description','Vender Blueberry Soda'),(1446,10,517,'menu_item_name','Blueberry Soda (Large)'),(1447,10,517,'menu_item_description','sell Blueberry Soda'),(1448,8,518,'menu_item_name','Blueberry Soda (regular)'),(1449,8,518,'menu_item_description','Blueberry Soda (regular)'),(1450,10,518,'menu_item_name','Blueberry Soda (regular)'),(1451,10,518,'menu_item_description','Sell Blueberry Soda (regular)'),(1452,8,519,'menu_item_name','Té de melocotón (medio)'),(1453,8,519,'menu_item_description','Té de melocotón (medio)'),(1454,10,519,'menu_item_name','Peach tea (medium)'),(1455,10,519,'menu_item_description','sell Peach tea (medium)'),(1456,8,520,'menu_item_name','Nugget de pescado con arroz'),(1457,8,520,'menu_item_description','Nugget de pescado con arroz'),(1458,10,520,'menu_item_name','Fish Nugget with rice'),(1459,10,520,'menu_item_description','Sell Fish Nugget with rice'),(1460,8,521,'menu_item_name','Nugget de pescado con arroz + sopa de pollo + refresco'),(1461,8,521,'menu_item_description','Elección de: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea with Jelly y más.'),(1462,10,521,'menu_item_name','Fish Nugget with rice + chicken soup + softdrink'),(1463,10,521,'menu_item_description','Choice of: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea With Jelly and more.'),(1464,8,523,'menu_extra_item_title','Pepsi Regular'),(1465,10,523,'menu_extra_item_title','Pepsi Regular'),(1466,8,524,'menu_item_name','Sándwich de barbacoa + refresco regular'),(1467,8,524,'menu_item_description','Elección de: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea with Jelly y más.'),(1468,10,524,'menu_item_name','BBQ sandwich + regular soft drink'),(1469,10,524,'menu_item_description','Choice of: Peps i Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea With Jelly and more.'),(1470,8,526,'menu_extra_item_title','Té de leche verde con gelatina (+9,000 ₫)'),(1471,10,526,'menu_extra_item_title','Green Milk Tea With Jelly (+9.000 ₫)'),(1472,8,527,'menu_item_name','Chickenjoy - 1 pc'),(1473,8,527,'menu_item_description','Chickenjoy - 1 pc'),(1474,10,527,'menu_item_name','Chickenjoy - 1 pc'),(1475,10,527,'menu_item_description','Chickenjoy - 1 pc'),(1476,8,528,'menu_item_name','Arroz extra'),(1477,8,528,'menu_item_description','Arroz extra'),(1478,10,528,'menu_item_name','Extra rice'),(1479,10,528,'menu_item_description','Extra rice'),(1480,8,529,'menu_item_name','Cheezy Tone Fries (regular)'),(1481,8,529,'menu_item_description','Cheezy Tone Fries (regular)'),(1482,10,529,'menu_item_name','Cheezy Tone Fries (regular)'),(1483,10,529,'menu_item_description','Cheezy Tone Fries (regular)'),(1484,8,530,'menu_item_name','Giro de la taza de vainilla'),(1485,8,530,'menu_item_description','servido en taza'),(1486,10,530,'menu_item_name','Vanilla Cup Twirl'),(1487,10,530,'menu_item_description','served in cup'),(1488,8,531,'menu_item_name','Ovaltine (regular)'),(1489,8,531,'menu_item_description','Ovaltine (regular)'),(1490,10,531,'menu_item_name','Ovaltine (regular)'),(1491,10,531,'menu_item_description','Ovaltine (regular)'),(1500,8,536,'menu_item_name','N3 (Spaghetti + 1pc Chickenjoy + papas fritas + refresco)'),(1501,8,536,'menu_item_description','+ Espagueti + 1pc Chickenjoy + Papas fritas regulares + Refresco Elección de: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea with Jelly y más.'),(1502,10,536,'menu_item_name','N3 (Spaghetti + 1pc Chickenjoy + Regular Fries + Soft drink)'),(1503,10,536,'menu_item_description','+ Spaghetti  + 1pc Chickenjoy  + Regular Fries  + Soft drink Choice of: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea With Jelly and more.'),(1504,8,537,'menu_item_name','ensalada César'),(1505,8,537,'menu_item_description','Romen, pollo, huevo, tomate bebé, salsa de caesar'),(1506,10,537,'menu_item_name','Caesar salad'),(1507,10,537,'menu_item_description','Romen, chicken, egg, baby tomato, caesar sauce'),(1508,8,538,'menu_item_name','Ensalada de verduras'),(1509,8,538,'menu_item_description','Lechuga, tomate de bebé, pepino, salsa de vinagreta'),(1510,10,538,'menu_item_name','Garden salad'),(1511,10,538,'menu_item_description','Lettuce, baby tomato, cucumber, vinaigrette sauce'),(1512,8,539,'menu_item_name','Ensalada de sésamo y pollo'),(1513,8,539,'menu_item_description','Lechuga, aguacate, tomate bebé, pollo, salsa de sésamo'),(1514,10,539,'menu_item_name','Chicken sesame salad'),(1515,10,539,'menu_item_description','Lettuce, avocado, baby tomato, chicken, seasame sauce'),(1516,8,540,'menu_item_name','Ginebra de Gordon 700ml'),(1517,8,540,'menu_item_description','Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.'),(1518,10,540,'menu_item_name','Gordon\'s gin 700ml'),(1519,10,540,'menu_item_description','Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.'),(1520,8,541,'menu_item_name','Vodka Smirnoff 700 ml'),(1521,8,541,'menu_item_description','Elección de: Coque [gratis], coque dietético [gratis], refresco [gratis], Lavie 500ml [gratis], tónico [gratis] y más.'),(1522,10,541,'menu_item_name','Smirnoff vodka 700 ml'),(1523,10,541,'menu_item_description','Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.'),(1524,8,542,'menu_item_name','Red label whisky 750ml'),(1525,8,542,'menu_item_description','Elección de: Coque [gratis], coque dietético [gratis], refresco [gratis], Lavie 500ml [gratis], tónico [gratis] y más.'),(1526,10,542,'menu_item_name','Red label whisky 750ml'),(1527,10,542,'menu_item_description','Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.'),(1528,8,543,'menu_item_name','Cerveza Heineken (1 lata)'),(1529,8,543,'menu_item_description','Cerveza Heineken (1 lata)'),(1530,10,543,'menu_item_name','Heineken beer (1 can)'),(1531,10,543,'menu_item_description','Heineken beer (1 can)'),(1532,8,544,'menu_item_name','Cerveza Halida (1 lata)'),(1533,8,544,'menu_item_description','Cerveza Halida (1 lata)'),(1534,10,544,'menu_item_name','Halida beer (1 can)'),(1535,10,544,'menu_item_description','Halida beer (1 can)'),(1536,8,545,'menu_item_name','Cerveza Heineken (6 latas)'),(1537,8,545,'menu_item_description','Cerveza Heineken (6 latas)'),(1538,10,545,'menu_item_name','Heineken beer (6 cans)'),(1539,10,545,'menu_item_description','Heineken beer (6 cans)'),(1540,8,546,'menu_item_name','New Me Burger [Comida]'),(1541,8,546,'menu_item_description','Servido con papas fritas + bebida Elección de: Coca Cola, Fanta, Sprite, Mejora a papas fritas grandes, Agrega empanada, Agrega empanada de pescado, Agrega empanada crujiente Chic\'n y más.'),(1542,10,546,'menu_item_name','New Me Burger [Meal]'),(1543,10,546,'menu_item_description','Served with french fries + drink Choice of: Coke, Fanta, Sprite, Upgrade to large fries, Add patty, Add Fish patty, Add Chic\'n crisp patty and more.'),(1544,8,548,'menu_extra_item_title','Coca'),(1545,10,548,'menu_extra_item_title','Coke'),(1546,8,549,'menu_extra_item_title','Fanta'),(1547,10,549,'menu_extra_item_title','Fanta'),(1548,8,550,'menu_item_name','BK Cheese Burger [Comida]'),(1549,8,550,'menu_item_description','Servido con papas fritas + bebida Elección de: Coca Cola, Fanta, Sprite, Mejora a papas fritas grandes, Agrega empanada, Agrega empanada de pescado, Agrega empanada crujiente Chic\'n y más.'),(1550,10,550,'menu_item_name','BK Cheese Burger [Meal]'),(1551,10,550,'menu_item_description','Served with french fries + drink Choice of: Coke, Fanta, Sprite, Upgrade to large fries, Add patty, Add Fish patty, Add Chic\'n crisp patty and more.'),(1552,8,552,'menu_extra_item_title','Duende'),(1553,10,552,'menu_extra_item_title','Sprite'),(1554,8,553,'menu_item_name','BK BBQ Single Bacon Cheese [Comida]'),(1555,8,553,'menu_item_description','Servido con papas fritas + bebida Elección de: Coca Cola, Fanta, Sprite, Mejora a papas fritas grandes, Agrega empanada, Agrega empanada de pescado, Agrega empanada crujiente Chic\'n y más.'),(1556,10,553,'menu_item_name','BK BBQ Single Bacon Cheese [Meal]'),(1557,10,553,'menu_item_description','Served with french fries + drink Choice of: Coke, Fanta, Sprite, Upgrade to large fries, Add patty, Add Fish patty, Add Chic\'n crisp patty and more.'),(1562,8,555,'menu_item_name','Café helado y leche'),(1563,8,555,'menu_item_description','Café helado y leche'),(1564,10,555,'menu_item_name','Iced Coffee & Milk'),(1565,10,555,'menu_item_description','Iced Coffee & Milk'),(1566,8,556,'menu_item_name','Capuchino helado'),(1567,8,556,'menu_item_description','Capuchino helado'),(1568,10,556,'menu_item_name','Iced Capuccino'),(1569,10,556,'menu_item_description','Iced Capuccino'),(1570,8,557,'menu_item_name','Iced Mocha'),(1571,8,557,'menu_item_description','Iced Mocha'),(1572,10,557,'menu_item_name','Iced Mocha'),(1573,10,557,'menu_item_description','Iced Mocha'),(1574,8,558,'menu_item_name','Capuchino caliente'),(1575,8,558,'menu_item_description','Capuchino caliente'),(1576,10,558,'menu_item_name','Hot Capuccino'),(1577,10,558,'menu_item_description','Hot Capuccino'),(1578,8,559,'menu_item_name','Chocolate caliente'),(1579,8,559,'menu_item_description','Chocolate caliente'),(1580,10,559,'menu_item_name','Hot Chocolate'),(1581,10,559,'menu_item_description','Hot Chocolate'),(1582,8,560,'menu_item_name','BK Cheese Burger'),(1583,8,560,'menu_item_description','La buena hamburguesa con queso BURGER KING ®. Encurtidos crujientes, salsa de tomate roja, mostaza amarilla y, apropiadamente, queso, todo sobre un panecillo de semillas de sésamo. Elección de: agregue la empanada, agregue la empanada de pescado, agregue la empanada fresca de Chic\'n, agregue el tocino, agregue el queso americano (x2) y más.'),(1584,10,560,'menu_item_name','BK Cheese Burger'),(1585,10,560,'menu_item_description','The good ol\' BURGER KING ® Cheeseburger. Crunchy pickles, red ketchup, yellow mustard, and fittingly, cheese - all on a sesame seed bun. Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1586,8,562,'menu_extra_item_title','Agregue la empanada (+20,000)'),(1587,10,562,'menu_extra_item_title','Add patty (+20.000)'),(1588,8,563,'menu_extra_item_title','Agregue la hamburguesa crocante de Chic\'n (+20,000)'),(1589,10,563,'menu_extra_item_title','Add Chic\'n crisp patty (+20.000)'),(1590,8,564,'menu_extra_item_title','Agregue queso americano (x2) (+10,000)'),(1591,10,564,'menu_extra_item_title','Add American cheese (x2) (+10.000)'),(1592,8,565,'menu_extra_item_title','Agregue la hamburguesa de pescado (+20,000)'),(1593,10,565,'menu_extra_item_title','Add Fish patty (+20.000)'),(1594,8,566,'menu_extra_item_title','Agregar tocino (+10,000)'),(1595,10,566,'menu_extra_item_title','Add Bacon (+10.000)'),(1596,8,567,'menu_item_name','BK BBQ Single Bacon Cheese'),(1597,8,567,'menu_item_description','Bacon Burger ofrece una deliciosa empanada de carne a la parrilla al fuego, coronada con tocino de corte grueso y ahumado natural, deliciosa salsa BBQ, salsa de tomate sobre un panecillo de semillas de sésamo tostado. Elección de: agregue la empanada, agregue la empanada de pescado, agregue la empanada fresca de Chic\'n, agregue el tocino, agregue el queso americano (x2) y más.'),(1598,10,567,'menu_item_name','BK BBQ Single Bacon Cheese'),(1599,10,567,'menu_item_description','Bacon Burger features a savory fire-grilled beef patty, topped with naturally smoked thick-cut bacon, delicious BBQ sauce , ketchup on a toasted sesame seed bun. Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1600,8,569,'menu_extra_item_title','Agregue la empanada (+20,000)'),(1601,10,569,'menu_extra_item_title','Add patty (+20.000)'),(1602,8,570,'menu_extra_item_title','Agregue la hamburguesa crocante de Chic\'n (+20,000)'),(1603,10,570,'menu_extra_item_title','Add Chic\'n crisp patty (+20.000)'),(1604,8,571,'menu_extra_item_title','Agregue queso americano (x2) (+10,000)'),(1605,10,571,'menu_extra_item_title','Add American cheese (x2) (+10.000)'),(1606,8,572,'menu_extra_item_title','Agregue la hamburguesa de pescado (+20,000)'),(1607,10,572,'menu_extra_item_title','Add Fish patty (+20.000)'),(1608,8,573,'menu_extra_item_title','Agregar tocino (+10,000)'),(1609,10,573,'menu_extra_item_title','Add Bacon (+10.000)'),(1610,8,574,'menu_item_name','New Me Burger'),(1611,8,574,'menu_item_description','Elección de: agregue la empanada, agregue la empanada de pescado, agregue la empanada fresca de Chic\'n, agregue el tocino, agregue el queso americano (x2) y más.'),(1612,10,574,'menu_item_name','New Me Burger'),(1613,10,574,'menu_item_description','Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1614,8,576,'menu_extra_item_title','Agregue la empanada (+20,000)'),(1615,10,576,'menu_extra_item_title','Add patty (+20.000)'),(1616,8,577,'menu_extra_item_title','Agregue la hamburguesa crocante de Chic\'n (+20,000)'),(1617,10,577,'menu_extra_item_title','Add Chic\'n crisp patty (+20.000)'),(1618,8,578,'menu_extra_item_title','Agregue queso americano (x2) (+10,000)'),(1619,10,578,'menu_extra_item_title','Add American cheese (x2) (+10.000)'),(1620,8,579,'menu_extra_item_title','Agregue la hamburguesa de pescado (+20,000)'),(1621,10,579,'menu_extra_item_title','Add Fish patty (+20.000)'),(1622,8,580,'menu_extra_item_title','Agregar tocino (+10,000)'),(1623,10,580,'menu_extra_item_title','Add Bacon (+10.000)'),(1624,8,581,'menu_item_name','Hamburguesa Tendergrill [Comida]'),(1625,8,581,'menu_item_description','Servido con papas fritas y bebida Elección de: Coca Cola, Fanta, Sprite, Mejora a papas fritas grandes, Agrega empanada, Agrega empanada de pescado, Agrega empanada crujiente Chic\'n y más.'),(1626,10,581,'menu_item_name','Tendergrill Burger [Meal]'),(1627,10,581,'menu_item_description','Served with french fries & drink Choice of: Coke, Fanta, Sprite, Upgrade to large fries, Add patty, Add Fish patty, Add Chic\'n crisp patty and more.'),(1628,8,582,'menu_item_name','Chic\'n Crisp Cheese Burger [Comida]'),(1629,8,582,'menu_item_description','Elección de: Coca Cola, Fanta, Sprite, Mejora a papas fritas grandes, Agrega empanada, Agrega empanada de pescado, Agrega empanada crujiente Chic\'n y más.'),(1630,10,582,'menu_item_name','Chic\'n Crisp Cheese Burger [Meal]'),(1631,10,582,'menu_item_description','Choice of: Coke, Fanta, Sprite, Upgrade to large fries, Add patty, Add Fish patty, Add Chic\'n crisp patty and more.'),(1632,8,583,'menu_item_name','HAMBURGUESA picante tierna.'),(1633,8,583,'menu_item_description','Nuestro TENDERCRISP® Chicken Sandwich es un filete de pollo de primera calidad, generosamente empanizado con condimentos caseros y cuidadosamente cubierto con lechuga fresca, tomates maduros y mayonesa cremosa entre un panecillo de semillas de sésamo. Elección de: agregue la empanada, agregue la empanada de pescado, agregue la empanada fresca de Chic\'n, agregue el tocino, agregue el queso americano (x2) y más.'),(1634,10,583,'menu_item_name','Spicy Tendercrisp BURGER'),(1635,10,583,'menu_item_description','Our TENDERCRISP® Chicken Sandwich is a premium white meat chicken fillet generously breaded with homemade seasoning and carefully layered with fresh lettuce, ripe tomatoes, and creamy mayonnaise between a sesame seed bun. Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1636,8,584,'menu_item_name','Chic\'n Crisp Cheese Burger'),(1637,8,584,'menu_item_description','Elección de: agregue la empanada, agregue la empanada de pescado, agregue la empanada fresca de Chic\'n, agregue el tocino, agregue el queso americano (x2) y más.'),(1638,10,584,'menu_item_name','Chic\'n Crisp Cheese Burger'),(1639,10,584,'menu_item_description','Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1640,8,585,'menu_item_name','Doble Whopper BBQ Bacon Cheese (Comida)'),(1641,8,585,'menu_item_description','Servido con papas fritas + bebida'),(1642,10,585,'menu_item_name','Double Whopper BBQ Bacon Cheese (Meal)'),(1643,10,585,'menu_item_description','Served with french fries + drink'),(1644,8,587,'menu_extra_item_title','Agregue la empanada de whopper (+35,000)'),(1645,10,587,'menu_extra_item_title','Add whopper patty (+35.000)'),(1646,8,588,'menu_extra_item_title','Agregue la hamburguesa tierna a la parrilla (+35,000)'),(1647,10,588,'menu_extra_item_title','Add Tender grill patty (+35.000)'),(1648,8,589,'menu_extra_item_title','Agregue la hamburguesa crocante de Chic\'n (+20,000)'),(1649,10,589,'menu_extra_item_title','Add Chic\'n crisp patty (+20.000)'),(1654,8,591,'menu_item_name','BBQ de pollo (1pc)'),(1655,8,591,'menu_item_description','BBQ de pollo (1pc)'),(1656,10,591,'menu_item_name','Chicken BBQ (1pc)'),(1657,10,591,'menu_item_description','Chicken BBQ (1pc)'),(1662,8,593,'menu_item_name','Crujiente (1pc)'),(1663,8,593,'menu_item_description','Crujiente (1pc)'),(1664,10,593,'menu_item_name','Crispy (1pc)'),(1665,10,593,'menu_item_description','Crispy (1pc)'),(1698,8,595,'menu_item_name','Carne de res (2 piezas) y arroz'),(1699,8,595,'menu_item_description','Elección de: cambie el arroz por papas fritas, agregue el coque, agregue pescado o agregue la empanada de carne.'),(1700,10,595,'menu_item_name','Beef (2 pcs) & Rice'),(1701,10,595,'menu_item_description','Choice of: Change rice to fries, Add coke, Add fish or Add beef patty.'),(1702,8,602,'menu_extra_item_title','Agregar pescado (+15,000)'),(1703,10,602,'menu_extra_item_title','Add fish (+15.000)'),(1704,8,603,'menu_extra_item_title','Añadir coque (+15.000)'),(1705,10,603,'menu_extra_item_title','Add coke (+15.000)'),(1706,8,604,'menu_extra_item_title','Agregue la empanada de carne (+15,000)'),(1707,10,604,'menu_extra_item_title','Add beef patty (+15.000)'),(1708,8,605,'menu_extra_item_title','Cambiar el arroz a papas fritas (+5,000)'),(1709,10,605,'menu_extra_item_title','Change rice to fries (+5.000)'),(1710,8,594,'menu_item_description','2 piezas BBQ Chicken + French Fries medium size + coke medium'),(1711,8,594,'menu_item_name','Combo de pollo a la barbacoa'),(1712,10,594,'menu_item_description','2 pieces BBQ Chicken + French Fries medium size + coke medium size'),(1713,10,594,'menu_item_name','BBQ Chicken Combo'),(1714,8,592,'menu_item_description','Flamin \'(1pc)'),(1715,8,592,'menu_item_name','Flamin \'(1pc)'),(1716,10,592,'menu_item_name','Flamin\' (1pc)'),(1717,10,592,'menu_item_description','Flamin\' (1pc)'),(1718,10,590,'menu_item_name','Double Whopper JR. (Meal)'),(1719,10,590,'menu_item_description','Served with french fries + drink'),(1720,8,590,'menu_item_description','Servido con papas fritas + bebida'),(1721,8,590,'menu_item_name','Double Whopper JR. (Comida)'),(1722,8,554,'menu_item_name','Café helado'),(1723,8,554,'menu_item_description','Café helado'),(1724,10,554,'menu_item_description','Iced Coffee'),(1725,10,554,'menu_item_name','Iced Coffee'),(1728,10,459,'restaurant_description','Sell mainly Sandwiches, Burgers, Italian & Pizza'),(1729,8,459,'restaurant_description','Vende principalmente sandwiches, hamburguesas, italiano y pizza'),(1734,10,601,'menu_extra_item_name','1'),(1735,8,601,'menu_extra_item_name','1 ES'),(1736,10,522,'menu_extra_item_name','1'),(1737,8,522,'menu_extra_item_name','1 ES'),(1738,10,525,'menu_extra_item_name','1'),(1739,8,525,'menu_extra_item_name','1 ES'),(1742,10,547,'menu_extra_item_name','1'),(1743,8,547,'menu_extra_item_name','1 ES'),(1744,10,551,'menu_extra_item_name','1'),(1745,8,551,'menu_extra_item_name','1 ES'),(1746,10,561,'menu_extra_item_name','1'),(1747,8,561,'menu_extra_item_name','1 ES'),(1748,10,568,'menu_extra_item_name','1'),(1749,8,568,'menu_extra_item_name','1 ES'),(1750,10,575,'menu_extra_item_name','1'),(1751,8,575,'menu_extra_item_name','1 ES'),(1752,10,586,'menu_extra_item_name','1'),(1753,8,586,'menu_extra_item_name','1 ES'),(1754,10,601,'menu_extra_item_name','1'),(1755,8,601,'menu_extra_item_name','1 ES'),(1756,10,532,'menu_item_name','Chilli Chicken (2pcs) + Regular Fries + Soft drink'),(1757,10,532,'menu_item_description','Choice of: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea With Jelly and more.'),(1758,8,532,'menu_item_name','Chilli Chicken (2 piezas) + papas fritas regulares + refresco'),(1759,8,532,'menu_item_description','Elección de: Pepsi Regular, 7up Regular, Mirinda Regular, Sarsi Regular, Red Milk Tea, Green Milk Tea with Jelly y más.'),(1760,10,606,'menu_extra_item_name','1'),(1761,8,606,'menu_extra_item_name','1 ES'),(1762,10,607,'menu_extra_item_title','boil chicken'),(1763,8,607,'menu_extra_item_title','hervir el pollo'),(1764,10,608,'menu_extra_item_title','black chicken'),(1765,8,608,'menu_extra_item_title','gallina negra'),(1766,8,609,'promotion_name','Bienvenida Copa del mundo 2018'),(1767,8,609,'promotion_desc','Reduce un 10% por todos los pedidos desde el 1 de julio de 2018 hasta el 30 de julio de 2018.'),(1768,10,609,'promotion_name','Welcome world cup 2018'),(1769,10,609,'promotion_desc','Reduce 10% per all orders from 1-July-2018 to 30-July-2018.'),(1796,10,611,'promotion_desc','Discount 10% for all order from 1-Jul-2018 to 31-Jul-2018'),(1797,10,611,'promotion_name','Welcome world cup 2018'),(1798,8,611,'promotion_desc','Descuento del 10% para todas las órdenes desde 1-Jul-2018 hasta 31-Jul-2018'),(1799,8,611,'promotion_name','Bienvenida Copa del mundo 2018'),(1808,8,613,'promotion_desc','No aplique esta promoción'),(1809,8,613,'promotion_name','Esta promoción fue expirada.'),(1810,10,613,'promotion_desc','Don\'t apply this promotion'),(1811,10,613,'promotion_name','This Promotion was been expired.'),(1812,8,446,'restaurant_description','Vender arroz, estadounidense y hamburguesas es lo principal.'),(1813,10,446,'restaurant_description','Sell Rice, American and Burgers is main.'),(1814,8,612,'promotion_name','Congrats U23 Viet Nam ha ido a la ronda final'),(1815,8,612,'promotion_desc','Descuento del 5% para todos los pedidos a partir del 1 de mayo de 2018 hasta el 15 de mayo de 2018'),(1816,10,612,'promotion_desc','Discount 5% for all order from 1-May-2018 to 15-May-2018'),(1817,10,612,'promotion_name','Congrats U23 Viet Nam gone to final round'),(1818,8,614,'category_name','Asados'),(1819,10,614,'category_name','Grill'),(1824,8,617,'menu_item_name','Carne asada'),(1825,8,617,'menu_item_description','Carne asada a la parrilla, acompañada de platano, gallo pinto y ensalada de repollo con tomate.'),(1826,10,617,'menu_item_name','Grilled beef'),(1827,10,617,'menu_item_description',''),(1828,8,618,'menu_extra_item_name','Queso frito'),(1829,10,618,'menu_extra_item_name','Fried cheese'),(1830,8,619,'menu_extra_item_title','Extra'),(1831,10,619,'menu_extra_item_title','Extra'),(1832,8,616,'menu_name','Asados'),(1833,10,616,'menu_name','Grilled Food'),(1842,10,620,'menu_item_name','Grilled pork'),(1843,10,620,'menu_item_description','grilled pork with fried rice and beans, plantain slices and cabbage with tomato salad'),(1844,8,620,'menu_item_description','Carne de cerdo asada acompañada de tajadas de platano, gallopinto y ensalada de repollo con tomate'),(1845,8,620,'menu_item_name','Cerdo Asado'),(1846,10,623,'menu_extra_item_name','Fried cheese'),(1847,8,623,'menu_extra_item_name','Queso frito'),(1848,10,624,'menu_extra_item_title','Extras'),(1849,8,624,'menu_extra_item_title','Extras'),(1850,8,625,'menu_extra_item_name','Tipo de Platano'),(1851,10,625,'menu_extra_item_name','Plantain type'),(1852,8,626,'menu_extra_item_title','Platano Verde'),(1853,10,626,'menu_extra_item_title','Green Plantain'),(1854,8,627,'menu_extra_item_title','Platano Maduro'),(1855,10,627,'menu_extra_item_title','Yellow Plantain'),(1856,8,628,'menu_name','Fritanga'),(1857,10,628,'menu_name','Deep Fried food'),(1868,10,461,'restaurant_description','Sell Mainly American, Burgers, Italian & Pizza'),(1869,8,461,'restaurant_description','Vende principalmente estadounidense, hamburguesas, italiano y pizza'),(1872,8,610,'restaurant_description','12'),(1873,10,610,'restaurant_description','12'),(1874,10,460,'restaurant_description','Sell Mainly American, Burgers, Italian & Pizza'),(1875,8,460,'restaurant_description','Vende principalmente estadounidense, hamburguesas, italiano y pizza'),(1882,8,629,'promotion_name','Descuento agostino'),(1883,8,629,'promotion_desc','10% de descuento el primero de agosto'),(1884,10,629,'promotion_name',''),(1885,10,629,'promotion_desc',''),(1916,10,630,'menu_item_name','grilled chicken'),(1917,10,630,'menu_item_description','grilled chicken'),(1918,8,630,'menu_item_description','Pollo asado, con platano verde o maduro y ensalda de repollo.'),(1919,8,630,'menu_item_name','Pollo Asado'),(1920,10,640,'menu_extra_item_name','Extras'),(1921,8,640,'menu_extra_item_name','Extras'),(1922,10,641,'menu_extra_item_title','Fried Cheese'),(1923,8,641,'menu_extra_item_title','Queso Frito'),(1924,10,642,'menu_extra_item_title','Boiled egg'),(1925,8,642,'menu_extra_item_title','Huevo cocido'),(1926,10,643,'menu_extra_item_title','Rice and beans'),(1927,8,643,'menu_extra_item_title','Gallo Pinto'),(1928,10,644,'menu_extra_item_title','Pescoxon'),(1929,8,644,'menu_extra_item_title','Pescozon'),(1930,8,645,'menu_extra_item_name','Platano'),(1931,10,645,'menu_extra_item_name','Plantain'),(1932,8,646,'menu_extra_item_title','Maduro'),(1933,10,646,'menu_extra_item_title','Maduro'),(1934,8,647,'menu_extra_item_title','Verde'),(1935,10,647,'menu_extra_item_title','Green'),(1940,8,648,'menu_item_name','Enchilada'),(1941,8,648,'menu_item_description','Tortilla frita, rellena de arroz y carne'),(1942,10,648,'menu_item_description','Deep fried tortilla filled with beef and rice'),(1943,10,648,'menu_item_name','Enchilada'),(1944,10,615,'restaurant_description','Grilled and fried food'),(1945,8,615,'restaurant_description','Asados y fritanga');
/*!40000 ALTER TABLE `content_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `country` (
  `country_id` bigint(45) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(45) NOT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`country_id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (4,'Viet Nam','vi',1,NULL,NULL,NULL,NULL),(5,'Spanish','es',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `currency` (
  `currency_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(3) DEFAULT NULL,
  `is_default` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `symbol_left` varchar(255) DEFAULT NULL,
  `symbol_right` varchar(255) DEFAULT NULL,
  `is_status` int(11) DEFAULT NULL,
  `round_decimal` decimal(10,0) DEFAULT NULL,
  `is_hide` bit(1) DEFAULT NULL,
  `sort_ordinal` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`currency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'USD','\0','USD','$',NULL,1,NULL,NULL,1,NULL),(2,'EUR','\0','EUR','€',NULL,1,NULL,NULL,2,NULL),(3,'NIO','','NIO','C$',NULL,1,NULL,NULL,3,NULL);
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency_rate`
--

DROP TABLE IF EXISTS `currency_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `currency_rate` (
  `currency_rate_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency_id` bigint(20) NOT NULL,
  `checksum` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `rate` float DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`currency_rate_id`),
  KEY `fk_c_r_id` (`currency_id`),
  CONSTRAINT `fk_c_r_id` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`currency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency_rate`
--

LOCK TABLES `currency_rate` WRITE;
/*!40000 ALTER TABLE `currency_rate` DISABLE KEYS */;
INSERT INTO `currency_rate` VALUES (3,1,'1',1,'2018-06-07 00:46:53'),(4,2,'1',1,'2018-06-07 00:47:16'),(5,3,'1',1,'2018-06-09 23:03:03');
/*!40000 ALTER TABLE `currency_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `district` (
  `district_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`district_id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_city` (`city_id`),
  CONSTRAINT `fk_city` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,3,'Cau giay','cau-giay',1,NULL,NULL,NULL,NULL),(2,6,'Madrid','madrid',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extra_item`
--

DROP TABLE IF EXISTS `extra_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `extra_item` (
  `extra_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_extra_item_id` bigint(20) NOT NULL,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`extra_item_id`),
  KEY `fk_de_ex_id` (`content_dep_id`),
  KEY `fk_menu_extra_item_id` (`menu_extra_item_id`),
  CONSTRAINT `fk_de_ex_id` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_menu_extra_item_id` FOREIGN KEY (`menu_extra_item_id`) REFERENCES `menu_extra_item` (`menu_extra_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_item`
--

LOCK TABLES `extra_item` WRITE;
/*!40000 ALTER TABLE `extra_item` DISABLE KEYS */;
INSERT INTO `extra_item` VALUES (93,68,523,2000),(94,69,526,9000),(97,71,548,10000),(98,71,549,5000),(99,72,552,7000),(100,73,562,20000),(101,73,563,20000),(102,73,564,10000),(103,73,565,20000),(104,73,566,10000),(105,74,569,20000),(106,74,570,20000),(107,74,571,10000),(108,74,572,20000),(109,74,573,10000),(110,75,576,20000),(111,75,577,20000),(112,75,578,10000),(113,75,579,20000),(114,75,580,10000),(115,76,587,35000),(116,76,588,35000),(117,76,589,20000),(122,78,602,15000),(123,78,603,15000),(124,78,604,15000),(125,78,605,5000),(126,79,607,2000),(127,79,608,1000),(128,80,619,30),(130,82,624,30),(131,83,626,0),(132,83,627,0),(139,87,641,30),(140,87,642,5),(141,87,643,10),(142,87,644,20),(143,88,646,0),(144,88,647,0);
/*!40000 ALTER TABLE `extra_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favouries`
--

DROP TABLE IF EXISTS `favouries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `favouries` (
  `favories_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `is_status` int(11) DEFAULT '1',
  PRIMARY KEY (`favories_id`),
  KEY `fk_f_us` (`user_id`),
  KEY `fk_f_res` (`restaurant_id`),
  CONSTRAINT `fk_f_res` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_f_us` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favouries`
--

LOCK TABLES `favouries` WRITE;
/*!40000 ALTER TABLE `favouries` DISABLE KEYS */;
/*!40000 ALTER TABLE `favouries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_type`
--

DROP TABLE IF EXISTS `food_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `food_type` (
  `food_type_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `is_special` bit(1) DEFAULT NULL,
  `is_hot` bit(1) DEFAULT NULL,
  `is_home` bit(1) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `sort_order` int(11) DEFAULT NULL,
  `url_slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`food_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_type`
--

LOCK TABLES `food_type` WRITE;
/*!40000 ALTER TABLE `food_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `language` (
  `language_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='Ngon ngu';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language`
--

LOCK TABLES `language` WRITE;
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
INSERT INTO `language` VALUES (8,'Spanish','es',1,NULL,NULL,NULL,NULL),(10,'English','en',1,'2018-05-04 15:52:25',NULL,NULL,NULL);
/*!40000 ALTER TABLE `language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_resource`
--

DROP TABLE IF EXISTS `language_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `language_resource` (
  `language_res_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `desc` text,
  `language_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`language_res_id`),
  KEY `fk_language_resource` (`language_id`),
  CONSTRAINT `fk_language_resource` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `language_resource`
--

LOCK TABLES `language_resource` WRITE;
/*!40000 ALTER TABLE `language_resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `language_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `media`
--

DROP TABLE IF EXISTS `media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `media` (
  `media_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `caption` varchar(100) DEFAULT NULL,
  `file_name` varchar(100) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `file_type` varchar(100) DEFAULT NULL,
  `image_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`media_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `media`
--

LOCK TABLES `media` WRITE;
/*!40000 ALTER TABLE `media` DISABLE KEYS */;
/*!40000 ALTER TABLE `media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `url_slug` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `sort_order` int(11) DEFAULT '1',
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `fk_res` (`restaurant_id`),
  KEY `fk_menu_def` (`content_dep_id`),
  CONSTRAINT `fk_menu_def` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_res` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (37,38,462,'',NULL,'',1,'2018-07-08 01:52:38',NULL,NULL,NULL,0,NULL),(38,38,463,'',NULL,'',1,'2018-07-08 01:54:34',NULL,NULL,NULL,0,NULL),(39,38,464,'',NULL,'',1,'2018-07-08 01:57:52',NULL,NULL,NULL,0,NULL),(40,38,465,'',NULL,'',1,'2018-07-08 01:58:29',NULL,NULL,NULL,0,NULL),(41,38,466,'',NULL,'',1,'2018-07-08 01:58:53',NULL,NULL,NULL,0,NULL),(42,38,467,'',NULL,'',1,'2018-07-08 01:59:14',NULL,NULL,NULL,0,NULL),(43,38,468,'',NULL,'',1,'2018-07-08 01:59:36',NULL,NULL,NULL,0,NULL),(44,38,469,'',NULL,'',1,'2018-07-08 02:00:17',NULL,NULL,NULL,0,NULL),(45,38,470,'',NULL,'',1,'2018-07-08 02:00:47',NULL,NULL,NULL,0,NULL),(46,38,471,'',NULL,'',1,'2018-07-08 02:01:11',NULL,NULL,NULL,0,NULL),(47,38,472,'',NULL,'',1,'2018-07-08 02:01:29',NULL,NULL,NULL,0,NULL),(48,38,473,'',NULL,'',1,'2018-07-08 02:01:52',NULL,NULL,NULL,0,NULL),(49,38,474,'',NULL,'',1,'2018-07-08 02:02:11',NULL,NULL,NULL,0,NULL),(50,38,475,'',NULL,'',0,'2018-07-08 02:02:29',NULL,NULL,NULL,0,NULL),(51,51,476,'',NULL,'',1,'2018-07-08 02:03:36',NULL,NULL,NULL,0,NULL),(52,51,477,'',NULL,'',1,'2018-07-08 02:04:06',NULL,NULL,NULL,0,NULL),(53,51,478,'',NULL,'',1,'2018-07-08 02:04:29',NULL,NULL,NULL,0,NULL),(54,51,479,'',NULL,'',1,'2018-07-08 02:04:51',NULL,NULL,NULL,0,NULL),(55,51,480,'',NULL,'',1,'2018-07-08 02:05:13',NULL,NULL,NULL,0,NULL),(56,51,481,'',NULL,'',1,'2018-07-08 02:05:35',NULL,NULL,NULL,0,NULL),(57,51,482,'',NULL,'',1,'2018-07-08 02:05:53',NULL,NULL,NULL,0,NULL),(58,51,483,'',NULL,'',1,'2018-07-08 02:06:17',NULL,NULL,NULL,0,NULL),(59,51,484,'',NULL,'',1,'2018-07-08 02:06:35',NULL,NULL,NULL,0,NULL),(60,51,485,'',NULL,'',1,'2018-07-08 02:06:59',NULL,NULL,NULL,0,NULL),(61,51,486,'',NULL,'',1,'2018-07-08 02:07:20',NULL,NULL,NULL,0,NULL),(62,52,487,'',NULL,'',1,'2018-07-08 02:07:59',NULL,NULL,NULL,0,NULL),(63,52,488,'',NULL,'',1,'2018-07-08 02:08:51',NULL,NULL,NULL,0,NULL),(64,52,489,'',NULL,'',1,'2018-07-08 02:09:12',NULL,NULL,NULL,0,NULL),(65,52,490,'',NULL,'',1,'2018-07-08 02:09:37',NULL,NULL,NULL,0,NULL),(66,52,491,'',NULL,'',1,'2018-07-08 02:09:58',NULL,NULL,NULL,0,NULL),(67,52,492,'',NULL,'',1,'2018-07-08 02:10:16',NULL,NULL,NULL,0,NULL),(68,52,493,'',NULL,'',1,'2018-07-08 02:10:37',NULL,NULL,NULL,0,NULL),(69,52,494,'',NULL,'',1,'2018-07-08 02:10:57',NULL,NULL,NULL,0,NULL),(70,52,495,'',NULL,'',1,'2018-07-08 02:11:14',NULL,NULL,NULL,0,NULL),(71,52,496,'',NULL,'',1,'2018-07-08 02:11:36',NULL,NULL,NULL,0,NULL),(72,53,497,'',NULL,'',1,'2018-07-08 02:12:09',NULL,NULL,NULL,0,NULL),(73,53,498,'',NULL,'',1,'2018-07-08 02:12:30',NULL,NULL,NULL,0,NULL),(74,53,499,'',NULL,'',1,'2018-07-08 02:12:52',NULL,NULL,NULL,0,NULL),(75,53,500,'',NULL,'',1,'2018-07-08 02:13:09',NULL,NULL,NULL,0,NULL),(76,53,501,'',NULL,'',1,'2018-07-08 02:13:27',NULL,NULL,NULL,0,NULL),(77,53,502,'',NULL,'',1,'2018-07-08 02:13:46',NULL,NULL,NULL,0,NULL),(78,53,503,'',NULL,'',1,'2018-07-08 02:14:02',NULL,NULL,NULL,0,NULL),(79,53,504,'',NULL,'',1,'2018-07-08 02:14:21',NULL,NULL,NULL,0,NULL),(80,53,505,'',NULL,'',1,'2018-07-08 02:14:44',NULL,NULL,NULL,0,NULL),(81,53,506,'',NULL,'',1,'2018-07-08 02:18:04',NULL,NULL,NULL,0,NULL),(82,53,507,'',NULL,'',1,'2018-07-08 02:18:24',NULL,NULL,NULL,0,NULL),(83,53,508,'',NULL,'',1,'2018-07-08 02:18:43',NULL,NULL,NULL,0,NULL),(84,55,616,'',NULL,'',1,'2018-07-14 12:54:56',NULL,'2018-07-14 13:12:19',NULL,0,NULL),(85,55,628,'',NULL,'',1,'2018-07-14 13:24:35',NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_extra_item`
--

DROP TABLE IF EXISTS `menu_extra_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `menu_extra_item` (
  `menu_extra_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_item_id` bigint(20) NOT NULL,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_extra_item_id`),
  KEY `fk_me_item` (`menu_item_id`),
  KEY `fk_me_con_def` (`content_dep_id`),
  CONSTRAINT `fk_me_con_def` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_me_item` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`menu_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_extra_item`
--

LOCK TABLES `menu_extra_item` WRITE;
/*!40000 ALTER TABLE `menu_extra_item` DISABLE KEYS */;
INSERT INTO `menu_extra_item` VALUES (68,89,522,1),(69,90,525,1),(71,107,547,2),(72,108,551,1),(73,116,561,2),(74,117,568,2),(75,118,575,2),(76,123,586,2),(78,129,601,2),(79,96,606,1),(80,130,618,1),(82,131,623,1),(83,131,625,2),(87,132,640,2),(88,132,645,1);
/*!40000 ALTER TABLE `menu_extra_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu_item`
--

DROP TABLE IF EXISTS `menu_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `menu_item` (
  `menu_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `picture_path` varchar(255) DEFAULT NULL,
  `is_combo` bit(1) DEFAULT NULL,
  `content_dep_id` bigint(20) NOT NULL,
  `url_slug` varchar(255) DEFAULT NULL,
  `sort_order` int(11) DEFAULT '1',
  `is_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_item_id`),
  KEY `fk_mitem_lang` (`content_dep_id`),
  KEY `fk_menu_mId_idx` (`menu_id`),
  CONSTRAINT `fk_mi_mId` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`),
  CONSTRAINT `fk_mitem_lang` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (77,76,204000,NULL,'\0',509,NULL,NULL,1),(78,76,204000,NULL,'\0',510,NULL,NULL,1),(79,76,259000,NULL,'\0',511,NULL,NULL,1),(80,76,248000,NULL,'\0',512,NULL,NULL,1),(81,77,303000,NULL,'\0',513,NULL,NULL,1),(82,77,391000,NULL,'\0',514,NULL,NULL,1),(83,77,391000,NULL,'\0',515,NULL,NULL,1),(84,77,710000,NULL,'\0',516,NULL,NULL,1),(85,62,20000,NULL,'\0',517,NULL,NULL,1),(86,62,15000,NULL,'\0',518,NULL,NULL,1),(87,62,15000,NULL,'\0',519,NULL,NULL,1),(88,66,25000,NULL,'\0',520,NULL,NULL,1),(89,66,35000,NULL,'\0',521,NULL,NULL,1),(90,67,35000,NULL,'\0',524,NULL,NULL,1),(91,63,30000,NULL,'\0',527,NULL,NULL,1),(92,68,5000,NULL,'\0',528,NULL,NULL,1),(93,69,20000,NULL,'\0',529,NULL,NULL,1),(94,70,5000,NULL,'\0',530,NULL,NULL,1),(95,71,18000,NULL,'\0',531,NULL,NULL,1),(96,64,NULL,NULL,'\0',532,NULL,NULL,1),(97,65,3000,NULL,'\0',536,NULL,NULL,1),(98,55,80000,NULL,'\0',537,NULL,NULL,1),(99,55,80000,NULL,'\0',538,NULL,NULL,1),(100,55,100000,NULL,'\0',539,NULL,NULL,1),(101,56,600,NULL,'\0',540,NULL,NULL,1),(102,56,550000,NULL,'\0',541,NULL,NULL,1),(103,56,700000,NULL,'\0',542,NULL,NULL,1),(104,57,40000,NULL,'\0',543,NULL,NULL,1),(105,57,30000,NULL,'\0',544,NULL,NULL,1),(106,58,210000,NULL,'\0',545,NULL,NULL,1),(107,41,0,NULL,'\0',546,NULL,NULL,1),(108,41,69000,NULL,'\0',550,NULL,NULL,1),(109,41,89000,NULL,'\0',553,NULL,NULL,1),(110,49,19000,'images/20180709/1531071388006_ViewHasingNotHeader.png','\0',554,NULL,NULL,1),(111,49,25000,NULL,'\0',555,NULL,NULL,1),(112,49,25000,NULL,'\0',556,NULL,NULL,1),(113,49,25000,NULL,'\0',557,NULL,NULL,1),(114,49,25000,NULL,'\0',558,NULL,NULL,1),(115,49,25000,NULL,'\0',559,NULL,NULL,1),(116,42,42000,NULL,'\0',560,NULL,NULL,1),(117,42,59000,NULL,'\0',567,NULL,NULL,1),(118,42,42000,NULL,'\0',574,NULL,NULL,1),(119,43,85000,NULL,'\0',581,NULL,NULL,1),(120,43,65000,NULL,'\0',582,NULL,NULL,1),(121,44,60000,NULL,'\0',583,NULL,NULL,1),(122,44,40000,NULL,'\0',584,NULL,NULL,1),(123,37,169000,NULL,'\0',585,NULL,NULL,1),(124,37,85000,'images/20180709/1531071376631_Beef Cheese Burger.png','\0',590,NULL,NULL,1),(125,45,39000,NULL,'\0',591,NULL,NULL,1),(126,45,34000,'images/20180709/1531071353303_ShouldSetDefaultforThisPage.png','\0',592,NULL,NULL,1),(127,45,34000,NULL,'\0',593,NULL,NULL,1),(128,45,99000,'images/20180709/1531071343935_SearchNotworking.png','\0',594,NULL,NULL,1),(129,46,45000,'images/20180709/1531071308326_SelectMenuItem.png','\0',595,NULL,NULL,1),(130,84,150,'images/20180714/1531548253091_Carne Asada.jpg','\0',617,NULL,NULL,1),(131,84,130,'images/20180714/1531549005924_cerdo asado.jpg','\0',620,NULL,NULL,1),(132,84,80,'images/20180730/1532922068982_index.jpeg','\0',630,NULL,NULL,1),(133,85,50,'images/20180730/1532924436848_enchilada.jpg','\0',648,NULL,NULL,1);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_action`
--

DROP TABLE IF EXISTS `module_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `module_action` (
  `module_action_id` bigint(20) NOT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `module_desc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`module_action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module_action`
--

LOCK TABLES `module_action` WRITE;
/*!40000 ALTER TABLE `module_action` DISABLE KEYS */;
INSERT INTO `module_action` VALUES (1,'Category','getAllByLanguage','getAllByLanguage'),(2,'Category','getAllSortByName','getAllSortByName'),(3,'Address','getAll','getAll'),(5,'Address','getById','getById'),(6,'Address','save','save'),(7,'Address','update','update'),(8,'Address','delete','delete'),(9,'Address','deleteMany','deleteMany'),(10,'Category','getById','getById'),(11,'Category','save','save'),(12,'Category','update','update'),(13,'Category','insert','insert'),(14,'Category','edit','edit'),(15,'Category','delete','delete'),(16,'Category','getAllByLanguage','getAllByLanguage'),(17,'Category','deleteMany','deleteMany'),(18,'ContactUs','getAll','getAll'),(20,'ContactUs','deleteMany','deleteMany'),(21,'Favourite','getAll','getAll'),(26,'Favourite','delete','delete'),(27,'Favourite','deleteMany','deleteMany'),(28,'Menu','getAll','getAll'),(29,'Menu','getById','getById'),(30,'Menu','getMenuByRestaurantId','getMenuByRestaurantId'),(32,'Menu','save','save'),(33,'Menu','update','update'),(34,'Menu','insert','insert'),(35,'Menu','edit','edit'),(37,'Menu','deleteMany','deleteMany'),(38,'Menu','delete','delete'),(39,'MeuItem','save','save'),(40,'MenuItem','edit','edit'),(41,'MenuItem','insert','insert'),(42,'MenuItem','delete','delete'),(43,'MenuItem','getById','getById'),(44,'MenuItem','getAll','getAll'),(46,'Order','getAll','getAll'),(47,'Order','getById','getById'),(50,'Order','delete','delete'),(51,'Order','deleteMany','deleteMany'),(56,'Promotion','getAll','getAll'),(58,'Promotion','getById','getById'),(59,'Promotion','getMultiCoreById','getMultiCoreById'),(60,'Promotion','save','save'),(61,'Promotion','update','update'),(62,'Promotion','delete','delete'),(64,'Rating','getAll','getAll'),(65,'Rating','getById','getById'),(66,'Rating','update','update'),(67,'Rating','delete','delete'),(68,'Rating','deleteMany','deleteMany'),(69,'Comment','getAll','getAll'),(70,'Comment','getById','getById'),(71,'Comment','getByRestaurant','getByRestaurant'),(72,'Comment','save','save'),(73,'Comment','update','update'),(74,'Comment','delete','delete'),(75,'Comment','deleteMany','deleteMany'),(76,'Restaurant','getAll','getAll'),(77,'Restaurant','getById','getById'),(78,'Restaurant','insert','insert'),(79,'Restaurant','save','save'),(80,'Restaurant','edit','edit'),(81,'Restaurant','update','update'),(82,'Restaurant','delete','delete'),(83,'Restaurant','deleteMany','deleteMany'),(85,'Restaurant','getAllRegisteredCity','getAllRegisteredCity'),(88,'Resturant','getByUserId','getByUserId'),(89,'Restaurant','getAllSortByName','getAllSortByName'),(90,'Role','getAll','getAll'),(91,'Role','getById','getById'),(92,'Role','save','save'),(93,'Role','update','update'),(94,'Role','delete','delete'),(95,'Role','deleteMany','deleteMany'),(96,'UserInfo','save','save'),(97,'UserInfo','update','update'),(98,'UserInfo','getById','getById'),(99,'UserInfo','getAll','getAll'),(100,'User','getAll','getAll'),(101,'User','getAllSortByName','getAllSortByName'),(102,'User','getById','getById'),(103,'User','save','save'),(104,'User','update','update'),(105,'User','delete','delete'),(106,'User','deleteMany','deleteMany'),(107,'Order','getFullInfo','getFullInfo'),(108,'Order','getByOwner','getByOwner'),(109,'Order','updateOrderBy','updateOrderBy'),(110,'Promotion','getAllByOwner','getAllByOwner'),(111,'Menu','getByOwner','getByOwner'),(112,'Restaurant','getByUserId','getByUserId'),(113,'MenuItem','getByOwner','getByOwner'),(114,'Order','getByRestaurant','getByRestaurant'),(115,'Comment','getByOwner','getByOwner');
/*!40000 ALTER TABLE `module_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NOT NULL,
  `restaurant_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_price` decimal(10,0) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `currency_code` varchar(3) DEFAULT NULL,
  `tax_total` decimal(10,0) DEFAULT NULL,
  `order_req` text,
  `check_sum` varchar(255) DEFAULT NULL,
  `order_code` varchar(255) DEFAULT NULL,
  `payment_with` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_res` (`restaurant_id`),
  KEY `fk_o_user_id` (`user_id`),
  CONSTRAINT `fk_o_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `fk_order_res` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (80,53,'Dennehy',66,'2019-03-26 09:45:22',463030,1,'NIO',NULL,'{\"paymentType\":1,\"restaurantId\":53,\"userId\":66,\"name\":\"Admin\",\"email\":\"qviet92@gmail.com\",\"address\":\"Marginal, Managua, Nicaragua\",\"districtId\":1,\"time\":\"13:00\",\"currencyCode\":\"NIO\",\"orderItem\":{\"totalSubPrice\":463000,\"totalPrice\":463030,\"OrderItemsRequest\":[{\"menuItemId\":79,\"menuId\":76,\"menuItemName\":\"Steak Burger\",\"urlSlug\":\"steak-burger\",\"priceOriginal\":259000,\"priceRateDisplay\":\"259,000\",\"priceRate\":259000,\"desc\":\"Imported steak topped with bacon, fried onion \\u0026 melted cheese. Choice of: Rare, Medium or Well done.\",\"currencyRate\":1.0,\"symbolLeft\":\"C$\",\"quantity\":1,\"totalPrice\":259000},{\"menuItemId\":78,\"menuId\":76,\"menuItemName\":\"BBQ Al\\u0027s Chicken Burger\",\"urlSlug\":\"bbq-al-s-chicken-burger\",\"priceOriginal\":204000,\"priceRateDisplay\":\"204,000\",\"priceRate\":204000,\"desc\":\"Grilled BBQ chicken fillet topped with cheese, lettuce, onion \\u0026 tomato.\",\"currencyRate\":1.0,\"symbolLeft\":\"C$\",\"quantity\":1,\"totalPrice\":204000}]},\"number\":\"0363770922\",\"languageCode\":\"en\",\"deliveryCost\":30,\"receiveVoucher\":false,\"paymentWith\":463030,\"addressDesc\":\"Turn left\",\"symbolLeft\":\"C$\"}','$2a$04$qGySivyp6K5q8mRXrf01j.88de5awt8mdpA0xUg4ptLvGyb62Fi3a','0VBNA01Q',463030),(81,51,'Imbir',66,'2019-03-26 10:12:58',700600,1,'NIO',NULL,'{\"paymentType\":1,\"restaurantId\":51,\"userId\":66,\"name\":\"Admin\",\"email\":\"admin@orderfood.com\",\"addressId\":32,\"address\":\"\",\"time\":\"15:00\",\"currencyCode\":\"NIO\",\"orderItem\":{\"totalSubPrice\":700600,\"totalPrice\":700600,\"OrderItemsRequest\":[{\"menuItemId\":101,\"menuId\":56,\"menuItemName\":\"Gordon\\u0027s gin 700ml\",\"urlSlug\":\"gordon-s-gin-700ml\",\"priceOriginal\":600,\"priceRateDisplay\":\"600\",\"priceRate\":600,\"desc\":\"Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.\",\"currencyRate\":1.0,\"symbolLeft\":\"C$\",\"quantity\":1,\"totalPrice\":600},{\"menuItemId\":103,\"menuId\":56,\"menuItemName\":\"Red label whisky 750ml\",\"urlSlug\":\"red-label-whisky-750ml\",\"priceOriginal\":700000,\"priceRateDisplay\":\"700,000\",\"priceRate\":700000,\"desc\":\"Choice of: [Free] Coke, [Free] Diet coke, [Free] Soda, [Free] Lavie 500ml, [Free] Tonic and more.\",\"currencyRate\":1.0,\"symbolLeft\":\"C$\",\"quantity\":1,\"totalPrice\":700000}]},\"number\":\"0363770922\",\"languageCode\":\"en\",\"deliveryCost\":0,\"receiveVoucher\":false,\"paymentWith\":700600,\"symbolLeft\":\"C$\"}','$2a$04$0liq/./WL1wPVNQ5JcfNwuU9s8mL/sM.YjRgADEM532iLuWeEI/uy','TPSWUKOI',700600);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_extra_item`
--

DROP TABLE IF EXISTS `order_extra_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_extra_item` (
  `order_extra_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_extra_item_id` bigint(20) DEFAULT NULL,
  `menu_item_id` bigint(20) DEFAULT NULL,
  `exra_item_id` bigint(20) DEFAULT NULL,
  `unit_price` decimal(10,0) DEFAULT NULL,
  `total_price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`order_extra_item_id`),
  KEY `fk_o_l_ex_i_id` (`exra_item_id`),
  KEY `fk_oeit_oli_id` (`menu_item_id`),
  KEY `fk_o_meit_oli_id` (`menu_extra_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_extra_item`
--

LOCK TABLES `order_extra_item` WRITE;
/*!40000 ALTER TABLE `order_extra_item` DISABLE KEYS */;
INSERT INTO `order_extra_item` VALUES (35,73,116,101,20000,20000),(36,73,116,104,10000,10000),(37,73,116,100,20000,20000),(38,73,116,102,10000,10000),(39,73,116,103,20000,20000),(40,75,118,113,20000,20000),(41,75,118,114,10000,10000),(42,75,118,110,20000,20000),(43,75,118,111,20000,20000),(44,75,118,112,10000,10000),(45,73,116,101,20000,20000),(46,73,116,104,10000,10000),(47,73,116,100,20000,20000),(48,73,116,102,10000,10000),(49,73,116,103,20000,20000),(50,75,118,113,20000,20000),(51,75,118,114,10000,10000),(52,75,118,110,20000,20000),(53,75,118,111,20000,20000),(54,75,118,112,10000,10000),(55,72,108,99,7000,7000),(56,72,108,99,7000,7000),(57,72,108,99,7000,7000),(58,80,130,128,30,30),(59,83,131,131,0,0),(60,83,131,132,0,0),(61,130,131,NULL,30,30),(62,131,131,NULL,0,0),(63,132,131,NULL,0,0),(64,128,130,NULL,30,30),(65,132,131,NULL,0,0),(66,131,131,NULL,0,0),(67,130,131,NULL,30,30);
/*!40000 ALTER TABLE `order_extra_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_history`
--

DROP TABLE IF EXISTS `order_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_history` (
  `order_his_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_obj` text,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_his_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_history`
--

LOCK TABLES `order_history` WRITE;
/*!40000 ALTER TABLE `order_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_info` (
  `order_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `info_name` varchar(255) DEFAULT NULL,
  `info_email` varchar(255) DEFAULT NULL,
  `info_number` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address_desc` longtext,
  PRIMARY KEY (`order_info_id`),
  KEY `fk_o_inf_id` (`order_id`),
  CONSTRAINT `fk_o_inf_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES (71,80,'Admin','qviet92@gmail.com','0363770922',NULL,'Marginal, Managua, Nicaragua',NULL,'Cau giay','13:00',NULL,'Turn left'),(72,81,'Admin','admin@orderfood.com','0363770922',NULL,'Marginal, Managua, Nicaragua','Ha Noi','Cau giay','15:00',NULL,NULL);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_line_item`
--

DROP TABLE IF EXISTS `order_line_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_line_item` (
  `order_line_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `menu_item_id` bigint(20) DEFAULT NULL,
  `unit_price` decimal(10,0) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `menu_item_name` varchar(255) DEFAULT NULL,
  `discount_total` decimal(10,0) DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`order_line_item_id`),
  KEY `fk_oli_oId` (`order_id`),
  KEY `fk_oli_miId` (`menu_item_id`),
  CONSTRAINT `fk_oli_miId` FOREIGN KEY (`menu_item_id`) REFERENCES `menu_item` (`menu_item_id`),
  CONSTRAINT `fk_oli_oId` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_line_item`
--

LOCK TABLES `order_line_item` WRITE;
/*!40000 ALTER TABLE `order_line_item` DISABLE KEYS */;
INSERT INTO `order_line_item` VALUES (129,80,79,259000,1,NULL,1,'Steak Burger',NULL,259000,'2019-03-26 09:45:22'),(130,80,78,204000,1,NULL,1,'BBQ Al\'s Chicken Burger',NULL,204000,'2019-03-26 09:45:22'),(131,81,101,600,1,NULL,1,'Gordon\'s gin 700ml',NULL,600,'2019-03-26 10:12:58'),(132,81,103,700000,1,NULL,1,'Red label whisky 750ml',NULL,700000,'2019-03-26 10:12:58');
/*!40000 ALTER TABLE `order_line_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_payment`
--

DROP TABLE IF EXISTS `order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_payment` (
  `order_payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `order_payment_type` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_payment_id`),
  KEY `fk_op_order` (`order_id`),
  CONSTRAINT `fk_op_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_payment`
--

LOCK TABLES `order_payment` WRITE;
/*!40000 ALTER TABLE `order_payment` DISABLE KEYS */;
INSERT INTO `order_payment` VALUES (71,80,1,'2019-03-26 09:45:23',NULL),(72,81,1,'2019-03-26 10:12:59',NULL);
/*!40000 ALTER TABLE `order_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_transaction`
--

DROP TABLE IF EXISTS `order_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_transaction` (
  `order_transaction_id` bigint(20) NOT NULL,
  `order_payment_id` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `transaction_identifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `currency_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `error_type` text,
  PRIMARY KEY (`order_transaction_id`),
  KEY `fk_op_orderPayment` (`order_payment_id`),
  CONSTRAINT `fk_op_orderPayment` FOREIGN KEY (`order_payment_id`) REFERENCES `order_payment` (`order_payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_transaction`
--

LOCK TABLES `order_transaction` WRITE;
/*!40000 ALTER TABLE `order_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_provider`
--

DROP TABLE IF EXISTS `payment_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `payment_provider` (
  `payment_provider_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `setting` text NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `is_status` int(11) DEFAULT '1',
  `sort_order` int(11) DEFAULT '1',
  PRIMARY KEY (`payment_provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_provider`
--

LOCK TABLES `payment_provider` WRITE;
/*!40000 ALTER TABLE `payment_provider` DISABLE KEYS */;
INSERT INTO `payment_provider` VALUES (1,'CashBack','{}','2018-05-30 14:47:22',NULL,NULL,NULL,1,1),(2,'Visa','{}','2018-05-30 14:47:18',NULL,NULL,NULL,1,1),(3,'Paypal','{}','2018-05-30 14:47:37',NULL,NULL,NULL,1,1);
/*!40000 ALTER TABLE `payment_provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `promotion` (
  `promotion_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '% or percentage',
  `value_type` int(11) DEFAULT NULL,
  `min_order` decimal(10,0) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`promotion_id`),
  KEY `fk_con_def` (`content_dep_id`),
  CONSTRAINT `fk_con_def` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (4,609,NULL,NULL,'2018-07-01 00:00:00','2018-07-30 00:00:00',10,'2018-07-11 17:06:18',NULL,'2018-07-11 17:06:18',NULL,NULL,300000,2),(5,611,NULL,NULL,'2018-07-01 00:00:00','2018-07-31 00:00:00',10,'2018-07-13 16:24:14',NULL,'2018-07-13 16:26:31',NULL,NULL,300,1),(6,612,NULL,NULL,'2018-05-01 00:00:00','2018-07-17 00:00:00',5,'2018-07-13 16:31:15',NULL,'2018-07-13 23:23:08',NULL,NULL,300,1),(7,613,NULL,NULL,'2018-06-04 00:00:00','2018-06-29 00:00:00',90,'2018-07-13 16:33:46',NULL,'2018-07-13 16:33:54',NULL,NULL,10,0),(8,629,NULL,NULL,'2018-08-01 00:00:00','2018-08-10 00:00:00',10,'2018-07-30 10:23:08',NULL,'2018-07-30 10:23:08',NULL,NULL,200,1);
/*!40000 ALTER TABLE `promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotion_lineitem`
--

DROP TABLE IF EXISTS `promotion_lineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `promotion_lineitem` (
  `promotion_lineitem_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `promotion_id` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) NOT NULL,
  `code` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`promotion_lineitem_id`),
  KEY `fk_prom_p` (`promotion_id`),
  KEY `fk_prom_f` (`restaurant_id`),
  CONSTRAINT `fk_prom_f` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_prom_p` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`promotion_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_lineitem`
--

LOCK TABLES `promotion_lineitem` WRITE;
/*!40000 ALTER TABLE `promotion_lineitem` DISABLE KEYS */;
INSERT INTO `promotion_lineitem` VALUES (3,4,38,'BURGERKINGWORLDCUP2018'),(6,5,51,'IMBIRWORDCUP'),(9,7,38,'NOTVALID'),(10,6,38,'BURU23VNFINALROUND'),(11,8,55,'AGO10');
/*!40000 ALTER TABLE `promotion_lineitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rating` (
  `rating_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `quality` double DEFAULT NULL,
  `delivery` double DEFAULT NULL,
  `rating_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_status` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rating_id`),
  KEY `fk_res_rating` (`restaurant_id`),
  KEY `fk_us_rat` (`user_id`),
  CONSTRAINT `fk_res_rating` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_us_rat` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant` (
  `restaurant_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `slogan` varchar(255) DEFAULT NULL,
  `address_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `open_time` varchar(20) DEFAULT NULL,
  `close_time` varchar(20) DEFAULT NULL,
  `phone_1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `phone_2` varchar(50) DEFAULT NULL,
  `content_dep_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `url_slug` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `ship_area` text,
  `key_search` varchar(500) DEFAULT NULL,
  `sort_order` int(11) DEFAULT '1',
  `min_price` decimal(10,0) DEFAULT NULL,
  `city` varchar(500) DEFAULT NULL,
  `district_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `delivery_cost` decimal(10,0) DEFAULT NULL,
  `estimate_delivery_time` varchar(255) DEFAULT NULL,
  `address_desc` text,
  `districtId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`restaurant_id`),
  KEY `fk_conten_con` (`content_dep_id`),
  KEY `fk_dis_res` (`key_search`),
  KEY `fk_res_dis_id` (`districtId`),
  CONSTRAINT `fk_conten_con` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_res_dis_id` FOREIGN KEY (`districtId`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (38,'Burger King','Burger King','Avenida Cristian Perez, Managua, Nicaragua',12.1404501,-86.2378986,'07:00','12:00','0977724616',NULL,446,1,'burger-king','2018-07-07 17:57:16',NULL,'2018-07-13 16:44:22',NULL,NULL,'#managua',0,20,'Managua',NULL,'images/20180709/1531101221291_burge-king-icon.png',40,'30',NULL,NULL),(51,'Imbir','Imbir','Avenida Cristian Perez, Managua, Nicaragua',12.1404501,-86.2378986,'07:00','23:00','0977724616',NULL,459,1,'imbir','2018-07-07 18:29:14',NULL,'2018-07-09 08:51:58',NULL,NULL,'#managua',0,300000,'Managua',NULL,'images/20180709/1531101118460_imbir-icon.jpg',0,'40',NULL,NULL),(52,'Piccolo','Piccolo','Avenida Cristian Perez, Managua, Nicaragua',12.1404501,-86.2378986,'07:00','23:00','0977724616',NULL,460,1,'piccolo','2018-07-07 18:40:02',NULL,'2018-07-26 16:53:02',NULL,NULL,'#managua',0,20,'Managua',NULL,'images/20180709/1531070149864_piccolo-icon.jpg',30,'30',NULL,NULL),(53,'Dennehy','Dennehy','Managua, Nicaragua',12.1424136,-86.27516020000002,'07:00','23:00','0977724616',NULL,461,1,'dennehy','2018-07-07 19:03:56',NULL,'2018-07-26 03:19:06',NULL,NULL,'#managua',0,20,'Managua',NULL,'images/20180709/1531101046725_dennehy-icon.jpg',30,'30',NULL,NULL),(54,'Need to fix this restaurant','dfdf','Avenida Roberto Vargas, Managua, Nicaragua',12.1404501,-86.2378986,'08:00','22:00','dfdf',NULL,610,1,'need-to-fix-this-restaurant','2018-07-11 23:43:04',NULL,'2018-07-26 16:52:36',NULL,NULL,'#managua',0,12,'Managua',NULL,'images/20180711/1531327408848_OFO_Images.png',23,'12','Turn on left',NULL),(55,'Carne Asada Donatello','Su fritanga mas rapida que una tortuga','43 Av Suroeste, Managua, Nicaragua',12.1512056,-86.31233329999998,'06:00','22:00','832988609','87781040',615,1,'carne-asada-donatello','2018-07-14 12:53:07',NULL,'2018-08-03 16:16:33',NULL,NULL,'#managua',0,80,'Managua',NULL,'images/20180714/1531547586789_donatello.jpg',20,'50','Esquina superior pista las brisas',NULL);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_attribute`
--

DROP TABLE IF EXISTS `restaurant_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_attribute` (
  `restaurant_attribute_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attribute_id` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`restaurant_attribute_id`),
  KEY `fk_att_id` (`attribute_id`),
  KEY `fk_rsa_res_id` (`restaurant_id`),
  CONSTRAINT `fk_att_id` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`atribute_id`),
  CONSTRAINT `fk_rsa_res_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_attribute`
--

LOCK TABLES `restaurant_attribute` WRITE;
/*!40000 ALTER TABLE `restaurant_attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_category`
--

DROP TABLE IF EXISTS `restaurant_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_category` (
  `res_category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`res_category_id`),
  KEY `fk_res_res_id` (`restaurant_id`),
  KEY `fk_res_cat_id` (`category_id`),
  CONSTRAINT `fk_res_cat_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `fk_res_res_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_category`
--

LOCK TABLES `restaurant_category` WRITE;
/*!40000 ALTER TABLE `restaurant_category` DISABLE KEYS */;
INSERT INTO `restaurant_category` VALUES (194,51,68),(195,51,66),(196,51,63),(212,38,64),(213,38,63),(214,38,61),(229,53,68),(230,53,63),(231,53,64),(233,54,76),(234,52,63),(235,52,64),(236,52,68),(240,55,84);
/*!40000 ALTER TABLE `restaurant_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_comment`
--

DROP TABLE IF EXISTS `restaurant_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_comment` (
  `res_comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurant_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `star_per_mark` double DEFAULT NULL,
  `star_quality` double DEFAULT NULL,
  `star_ship` double DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `title` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`res_comment_id`),
  KEY `fk_rs_com` (`restaurant_id`),
  KEY `fk_rs_com_uId_idx` (`user_id`),
  CONSTRAINT `fk_rs_com` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_rs_com_uId` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_comment`
--

LOCK TABLES `restaurant_comment` WRITE;
/*!40000 ALTER TABLE `restaurant_comment` DISABLE KEYS */;
INSERT INTO `restaurant_comment` VALUES (81,51,65,NULL,4,4,'2019-03-26 15:11:49',NULL,'Very delicious',NULL,0,'2019-03-26 15:11:49',NULL);
/*!40000 ALTER TABLE `restaurant_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_info`
--

DROP TABLE IF EXISTS `restaurant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_info` (
  `restaurant_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `restaurent_id` bigint(20) DEFAULT NULL,
  `time_ship` text,
  `payment` text,
  `address` text,
  `ship_area` text,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`restaurant_info_id`),
  KEY `fk_res_id` (`restaurent_id`),
  CONSTRAINT `fk_res_id` FOREIGN KEY (`restaurent_id`) REFERENCES `restaurant` (`restaurant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_info`
--

LOCK TABLES `restaurant_info` WRITE;
/*!40000 ALTER TABLE `restaurant_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant_payment_provider`
--

DROP TABLE IF EXISTS `restaurant_payment_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant_payment_provider` (
  `restaurant_payment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_provider_id` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`restaurant_payment_id`),
  KEY `fk_res_pay_pro` (`payment_provider_id`),
  KEY `fk_res_pay_id` (`restaurant_id`),
  CONSTRAINT `fk_res_pay_id` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_res_pay_pro` FOREIGN KEY (`payment_provider_id`) REFERENCES `payment_provider` (`payment_provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_payment_provider`
--

LOCK TABLES `restaurant_payment_provider` WRITE;
/*!40000 ALTER TABLE `restaurant_payment_provider` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurant_payment_provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Guest','guest',1,'2018-05-08 22:50:59',NULL,NULL,NULL),(2,'Admin','admin',1,NULL,NULL,NULL,NULL),(6,'Owner','owner',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_module`
--

DROP TABLE IF EXISTS `role_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role_module` (
  `role_module_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `module_action_id` bigint(20) DEFAULT NULL,
  `user_restaurant_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_module_id`),
  KEY `fk_m_a_m_id` (`module_action_id`),
  KEY `fk_m_a_r_id` (`role_id`),
  KEY `fk_m_a_u_r` (`user_restaurant_id`),
  CONSTRAINT `fk_m_a_m_id` FOREIGN KEY (`module_action_id`) REFERENCES `module_action` (`module_action_id`),
  CONSTRAINT `fk_m_a_r_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_m_a_u_r` FOREIGN KEY (`user_restaurant_id`) REFERENCES `user_restaurant` (`user_res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_module`
--

LOCK TABLES `role_module` WRITE;
/*!40000 ALTER TABLE `role_module` DISABLE KEYS */;
INSERT INTO `role_module` VALUES (89,2,3,NULL),(90,2,5,NULL),(91,2,6,NULL),(92,2,7,NULL),(93,2,8,NULL),(94,2,9,NULL),(95,2,1,NULL),(96,2,2,NULL),(97,2,10,NULL),(98,2,11,NULL),(99,2,12,NULL),(100,2,13,NULL),(101,2,14,NULL),(102,2,15,NULL),(103,2,16,NULL),(104,2,17,NULL),(105,2,21,NULL),(106,2,26,NULL),(107,2,27,NULL),(108,2,28,NULL),(109,2,29,NULL),(110,2,30,NULL),(111,2,32,NULL),(112,2,33,NULL),(113,2,34,NULL),(114,2,35,NULL),(115,2,37,NULL),(116,2,38,NULL),(117,2,40,NULL),(118,2,41,NULL),(119,2,42,NULL),(120,2,43,NULL),(121,2,44,NULL),(122,2,46,NULL),(123,2,47,NULL),(124,2,50,NULL),(125,2,51,NULL),(126,2,107,NULL),(127,2,108,NULL),(128,2,109,NULL),(129,2,56,NULL),(130,2,58,NULL),(131,2,59,NULL),(132,2,60,NULL),(133,2,61,NULL),(134,2,62,NULL),(135,2,110,NULL),(136,2,64,NULL),(137,2,65,NULL),(138,2,66,NULL),(139,2,67,NULL),(140,2,68,NULL),(141,2,69,NULL),(142,2,70,NULL),(143,2,71,NULL),(144,2,72,NULL),(145,2,73,NULL),(146,2,74,NULL),(147,2,75,NULL),(148,2,76,NULL),(149,2,77,NULL),(150,2,78,NULL),(151,2,79,NULL),(152,2,80,NULL),(153,2,81,NULL),(154,2,82,NULL),(155,2,83,NULL),(156,2,85,NULL),(157,2,89,NULL),(158,2,90,NULL),(159,2,91,NULL),(160,2,92,NULL),(161,2,93,NULL),(162,2,94,NULL),(163,2,95,NULL),(164,2,96,NULL),(165,2,97,NULL),(166,2,98,NULL),(167,2,99,NULL),(168,2,100,NULL),(169,2,101,NULL),(170,2,102,NULL),(171,2,103,NULL),(172,2,104,NULL),(173,2,105,NULL),(174,2,106,NULL),(199,6,28,NULL),(200,6,29,NULL),(201,6,30,NULL),(202,6,32,NULL),(203,6,33,NULL),(204,6,34,NULL),(205,6,35,NULL),(206,6,37,NULL),(207,6,38,NULL),(208,6,107,NULL),(209,6,108,NULL),(210,6,109,NULL),(211,6,58,NULL),(212,6,60,NULL),(213,6,61,NULL),(214,6,62,NULL),(215,6,110,NULL),(216,6,69,NULL),(217,6,71,NULL),(218,6,96,NULL),(219,6,97,NULL),(220,6,98,NULL),(221,6,99,NULL),(222,6,112,NULL),(223,6,111,NULL),(224,6,110,NULL),(225,6,113,NULL),(226,6,40,NULL),(227,6,114,NULL),(228,6,42,NULL),(229,6,115,NULL),(230,1,5,NULL),(231,1,6,NULL),(232,1,7,NULL),(233,1,8,NULL),(234,1,9,NULL),(235,1,107,NULL),(236,1,102,NULL),(238,1,104,NULL),(239,6,43,NULL),(240,6,41,NULL),(242,1,72,NULL);
/*!40000 ALTER TABLE `role_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smtp_config`
--

DROP TABLE IF EXISTS `smtp_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `smtp_config` (
  `smtp_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `smtp_config` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`smtp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smtp_config`
--

LOCK TABLES `smtp_config` WRITE;
/*!40000 ALTER TABLE `smtp_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `smtp_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax`
--

DROP TABLE IF EXISTS `tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tax` (
  `tax_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tax_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax`
--

LOCK TABLES `tax` WRITE;
/*!40000 ALTER TABLE `tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `user_hash` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_salt` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `is_lock` bit(1) DEFAULT NULL,
  `provider` int(11) DEFAULT NULL,
  `original_data` text,
  `reset_token` char(36) DEFAULT NULL,
  `expired_date` datetime DEFAULT NULL,
  `receive_newsletter` bit(1) DEFAULT b'0',
  `account_type` int(11) DEFAULT '1',
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (65,'User','user@orderfood.com','01687625699','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','User','2018-05-10 10:05:03',1,'\0',3,'{\"email\":\"user@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"name\":\"User OFO\"}',NULL,NULL,'',1,'2018-05-15 10:29:38',NULL),(66,'Admin','admin@orderfood.com','0363770922','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','Admin','2018-05-10 11:03:52',1,'\0',3,'{\"email\":\"admin@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"fullName\":\"Admin\",\"name\":\"Admin\"}',NULL,NULL,'',2,'2018-05-16 23:19:06',NULL),(67,'Owner','owner@orderfood.com','0977724616','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','Owner','2018-05-10 11:05:48',1,'\0',3,'{\"email\":\"owner@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"fullName\":\"owner\",\"name\":\"owner\"}',NULL,NULL,'\0',1,'2018-05-16 23:41:07',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_info` (
  `user_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `emergency_number1` varchar(255) DEFAULT NULL,
  `emergency_number2` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_info_id`),
  KEY `fk_u_i_id` (`user_id`),
  CONSTRAINT `fk_u_i_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_restaurant`
--

DROP TABLE IF EXISTS `user_restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_restaurant` (
  `user_res_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `restaurant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_res_id`),
  KEY `fk_res_us` (`restaurant_id`),
  KEY `fk_user_role` (`user_id`),
  CONSTRAINT `fk_res_us` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`restaurant_id`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_restaurant`
--

LOCK TABLES `user_restaurant` WRITE;
/*!40000 ALTER TABLE `user_restaurant` DISABLE KEYS */;
INSERT INTO `user_restaurant` VALUES (117,67,54);
/*!40000 ALTER TABLE `user_restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_ur_rId` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_ur_uId` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=193 DEFAULT CHARSET=utf8 COMMENT='Danh sach nguoi dung';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (157,66,2),(179,67,1),(180,67,6);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voucher` (
  `voucher_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `code` varchar(100) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `value` decimal(10,0) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`voucher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_lineitem`
--

DROP TABLE IF EXISTS `voucher_lineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `voucher_lineitem` (
  `voucher_lineitem_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `voucher_id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`voucher_lineitem_id`),
  KEY `fk_cc` (`voucher_id`),
  CONSTRAINT `fk_cc` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`voucher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_lineitem`
--

LOCK TABLES `voucher_lineitem` WRITE;
/*!40000 ALTER TABLE `voucher_lineitem` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher_lineitem` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-20 14:20:40
