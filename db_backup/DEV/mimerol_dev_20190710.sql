-- MySQL dump 10.13  Distrib 8.0.11, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ofo
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (51,105,'',NULL,2,9,'',NULL,NULL,NULL,'2019-07-10 10:38:09'),(52,111,'',NULL,2,2,'',NULL,NULL,NULL,'2019-07-10 10:38:20'),(53,112,'',NULL,2,3,'',NULL,NULL,NULL,'2019-07-10 10:38:21'),(54,113,'',NULL,2,4,'',NULL,NULL,NULL,'2019-07-10 10:38:22'),(57,116,'',NULL,2,7,'',NULL,NULL,NULL,'2019-07-10 10:38:22'),(58,128,'',NULL,2,1,'',NULL,NULL,NULL,'2019-07-10 10:38:21'),(59,387,NULL,NULL,2,5,NULL,NULL,NULL,NULL,'2019-07-10 10:38:21'),(60,388,NULL,NULL,2,6,NULL,NULL,NULL,NULL,'2019-07-10 10:38:22'),(61,447,NULL,NULL,2,8,NULL,NULL,NULL,NULL,'2019-07-10 10:38:22'),(62,704,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_us`
--

LOCK TABLES `contact_us` WRITE;
/*!40000 ALTER TABLE `contact_us` DISABLE KEYS */;
INSERT INTO `contact_us` VALUES (22,'dfdfdf',3,'dfdfdf','êrer','dinhmao.it@gmail.com','dfdfdf','2018-05-16 23:47:01',1),(23,'Need to support to register my restaurant into your system.',3,'Nguyen Dinh Mao','098776545','dinhmao.it@hotmail.com','Please help me and call me if you can.','2018-05-17 00:23:28',1),(24,'mao help you',1,'Nguyen Dinh Mao','097777','dinhmao.it@gmail.com','dfdfdf','2018-06-14 15:10:02',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=705 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_definition`
--

LOCK TABLES `content_definition` WRITE;
/*!40000 ALTER TABLE `content_definition` DISABLE KEYS */;
INSERT INTO `content_definition` VALUES (102,'Order Italian & Pizza'),(103,'Café'),(104,'Nha hang pho Cau giay'),(105,''),(110,'restaurant tes1 new 1'),(111,''),(112,''),(113,''),(114,''),(115,''),(116,''),(120,'Free ship'),(123,'attribute_name'),(124,'Joma Bakery Cafe'),(125,'Pepperonis'),(126,''),(127,''),(128,''),(129,'qweqweqwe'),(133,'pizaa super hot'),(135,'delivery_time'),(136,'payment'),(137,'attribute_name'),(138,'attribute_name'),(139,'attribute_name'),(140,'Menu_item'),(141,'Menu_item'),(142,'Menu_item'),(143,'Menu_item'),(144,'Menu_item'),(145,'Menu_item'),(146,''),(147,''),(148,''),(149,''),(150,''),(151,''),(152,''),(153,''),(154,''),(155,''),(156,''),(157,''),(158,''),(159,''),(160,''),(161,''),(162,''),(163,''),(164,''),(165,''),(166,''),(167,''),(168,''),(169,'Menu_item'),(170,'Menu_extral_item'),(171,'Extra_item'),(172,'Menu_item'),(173,'Menu_extral_item'),(174,'Extra_item'),(175,'Extra_item'),(176,'Extra_item'),(177,'Menu_item'),(182,'Menu_item'),(183,'Menu_extral_item'),(184,'Extra_item'),(185,'Extra_item'),(186,'Extra_item'),(202,'Menu_item'),(203,'Menu_extral_item'),(204,'Extra_item'),(205,'Extra_item'),(206,'Extra_item'),(207,'Menu_item'),(208,'Menu_extral_item'),(209,'Extra_item'),(210,'Extra_item'),(211,'Extra_item'),(212,'Menu_item'),(213,'Menu_extral_item'),(214,'Extra_item'),(215,'Extra_item'),(216,'Extra_item'),(217,'Menu_item'),(219,'Menu_item'),(220,'Menu_extral_item'),(221,'Extra_item'),(222,'Extra_item'),(263,'Menu_item'),(272,'Burger King'),(273,'Promotion_Name'),(292,'Menu_item'),(293,'Menu_extral_item'),(294,'Extra_item'),(295,'Extra_item'),(296,'Menu_extral_item'),(297,'Extra_item'),(298,'Menu_extral_item'),(299,'Extra_item'),(300,'Menu_item'),(301,'Menu_extral_item'),(302,'Extra_item'),(303,'Menu_extral_item'),(304,'Extra_item'),(305,'Menu_extral_item'),(306,'Extra_item'),(309,'Not here'),(311,'Not here'),(312,'Menu_item'),(313,'Menu_extral_item'),(314,'Extra_item'),(315,'Menu_item'),(316,'Menu_extral_item'),(317,'Extra_item'),(318,'Menu_item'),(319,'Menu_extral_item'),(320,'Extra_item'),(321,'Extra_item'),(322,'Menu_extral_item'),(323,'Extra_item'),(324,'Menu_item'),(325,'Menu_item'),(326,'Menu_extral_item'),(327,'Extra_item'),(328,'Menu_item'),(329,'Menu_extral_item'),(330,'Extra_item'),(331,'Extra_item'),(332,'Menu_extral_item'),(333,'Extra_item'),(336,'Menu_item'),(337,'Menu_extral_item'),(338,'Extra_item'),(339,'Extra_item'),(340,'Menu_extral_item'),(341,'Extra_item'),(342,'Extra_item'),(347,'BMBSoft Food'),(348,'BMBSoft Food'),(349,'BMBSoft Food'),(350,'Menu_item'),(351,'Menu_extral_item'),(352,'Extra_item'),(353,'Extra_item'),(354,'Menu_extral_item'),(355,'Extra_item'),(356,'Extra_item'),(357,'Menu_extral_item'),(358,'Extra_item'),(359,'Menu_item'),(360,'Menu_extral_item'),(361,'Extra_item'),(362,'Extra_item'),(363,'Menu_extral_item'),(364,'Extra_item'),(365,'Extra_item'),(366,'Menu_item'),(367,'Menu_extral_item'),(368,'Extra_item'),(369,'Extra_item'),(370,'Menu_extral_item'),(371,'Extra_item'),(372,'Extra_item'),(373,'Menu_item'),(374,'Menu_extral_item'),(375,'Extra_item'),(376,'Extra_item'),(377,'Menu_extral_item'),(378,'Extra_item'),(379,'Extra_item'),(380,'Tai Test 01'),(381,'Menu_item'),(382,'Menu_item'),(383,'Menu_item'),(384,'Menu_item'),(385,'Menu_extral_item'),(386,'Extra_item'),(387,'Category_Name'),(388,'Category_Name'),(389,'Menu_item'),(390,'Menu_extral_item'),(391,'Extra_item'),(392,'Menu_item'),(393,'Menu_extral_item'),(394,'Extra_item'),(395,'Menu_item'),(396,'Menu_extral_item'),(397,'Extra_item'),(398,'Menu_item'),(399,'Menu_extral_item'),(400,'Extra_item'),(401,'Menu_item'),(402,'Menu_extral_item'),(403,'Extra_item'),(418,'Menu_item'),(423,'Menu_item'),(424,'Menu_extral_item'),(425,'Extra_item'),(426,'Menu_item'),(427,'Menu_extral_item'),(428,'Extra_item'),(429,'Menu_extral_item'),(430,'Extra_item'),(443,'Menu_extral_item'),(444,'Extra_item'),(445,'Menu_extral_item'),(446,'Extra_item'),(447,'Category_Name'),(448,'May Salad'),(450,'May Salad'),(451,'Viet Quynh Restaurant'),(452,''),(453,'Menu_extral_item'),(454,'Extra_item'),(455,'Menu_extral_item'),(456,'Extra_item'),(457,'Menu_item'),(538,'Promotion_Name'),(539,'Promotion_Name'),(540,'Promotion_Name'),(541,'Menu_extral_item'),(542,'Extra_item'),(543,'Extra_item'),(544,'Menu_extral_item'),(545,'Extra_item'),(546,'Extra_item'),(547,'Promotion_Name'),(548,''),(549,'Promotion_Name'),(550,'Promotion_Name'),(551,'Promotion_Name'),(552,'Promotion_Name'),(553,'Promotion_Name'),(554,'Menu_extral_item'),(555,'Extra_item'),(566,'Menu_extral_item'),(567,'Extra_item'),(568,'Menu_item'),(601,'Menu_extral_item'),(602,'Extra_item'),(603,'Menu_extral_item'),(604,'Extra_item'),(605,''),(606,''),(607,''),(608,''),(621,'Menu_extral_item'),(622,'Extra_item'),(623,'Extra_item'),(624,'Menu_extral_item'),(625,'Extra_item'),(626,'Menu_extral_item'),(627,'Extra_item'),(628,''),(629,''),(630,''),(631,''),(632,''),(633,''),(634,''),(635,''),(636,''),(637,''),(638,''),(639,''),(640,''),(673,'Menu_extral_item'),(674,'Extra_item'),(675,'Menu_extral_item'),(676,'Extra_item'),(677,'Menu_item'),(678,'Menu_extral_item'),(679,'Extra_item'),(680,'Menu_extral_item'),(681,'Extra_item'),(682,'Piccolo22'),(683,'Menu_item'),(684,'Menu_item'),(685,'Piccolo224'),(686,'Sanji'),(687,''),(688,'Promotion_Name'),(696,'Menu_item'),(697,'Menu_item'),(698,'Zoro'),(699,'Menu_item'),(700,'Menu_item'),(701,'Menu_item'),(704,'Category_Name');
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
) ENGINE=InnoDB AUTO_INCREMENT=2301 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_entry`
--

LOCK TABLES `content_entry` WRITE;
/*!40000 ALTER TABLE `content_entry` DISABLE KEYS */;
INSERT INTO `content_entry` VALUES (468,10,112,'category_name','Coffee & Milk Tea'),(469,8,112,'category_name','Té de café y leche'),(470,8,113,'category_name','Sopas de fideos'),(471,10,113,'category_name','Noodle soups '),(476,8,111,'category_name','Japonesa / Sushi'),(477,10,111,'category_name','Japanese/Sushi'),(511,10,133,'name','pizaa super hot'),(512,8,133,'name','pizza super caliente'),(517,10,120,'g_title','Delivery costs'),(518,8,120,'g_title','Delivery costs'),(519,10,135,'g_title','Delivery times'),(520,8,135,'g_title','Delivery times'),(521,10,136,'g_title','payment'),(522,8,136,'g_title','payment'),(523,8,123,'a_title','Free'),(524,10,123,'a_title','Free'),(525,8,137,'a_title','20.000 EUR or less'),(526,10,137,'a_title','20.000 $ or less'),(527,8,138,'a_title','20 min'),(528,10,138,'a_title','20 min'),(529,8,139,'a_title','30 min'),(530,10,139,'a_title','30 min'),(533,10,141,'menu_item_name','pizaa super hot 2'),(534,8,141,'menu_item_name','pizza super caliente 2'),(535,10,142,'menu_item_name','pizaa super hot 3'),(536,8,142,'menu_item_name','pizza super caliente 3'),(537,10,143,'menu_item_name','pizaa super hot 4'),(538,8,143,'menu_item_name','pizza super caliente 4'),(539,10,144,'menu_item_name','pizaa super hot 4'),(540,8,144,'menu_item_name','pizza super caliente 4'),(541,10,145,'menu_item_name','pizaa super hot 4'),(542,8,145,'menu_item_name','pizza super caliente 4'),(543,10,140,'menu_item_name','pizaa super hot 4'),(544,8,140,'menu_item_name','pizza super caliente 4'),(545,8,146,'menu_name','Desayuno'),(546,10,146,'menu_name','Breakfast'),(547,8,147,'menu_name','Wraps y sándwiches'),(548,10,147,'menu_name','Wraps & sandwiches'),(551,8,149,'menu_name','Sopa'),(552,10,149,'menu_name','Soup'),(553,8,150,'menu_name','Ensaladas y aperitivos'),(554,10,150,'menu_name','Salads and appetizers'),(555,8,151,'menu_name','Sandwiches'),(556,10,151,'menu_name','Sandwiches'),(557,8,152,'menu_name','Pasta'),(558,10,152,'menu_name','Pasta'),(559,8,153,'menu_name','Pizza'),(560,10,153,'menu_name','Pizza'),(561,8,154,'menu_name','Salchicha a la parrilla'),(562,10,154,'menu_name','Grilled Sausage'),(563,8,155,'menu_name','Zalamero'),(564,10,155,'menu_name','Smoothie'),(565,8,156,'menu_name','Zumo de frutas'),(566,10,156,'menu_name','Fruit Juice'),(567,8,157,'menu_name','Bebidas sin alcohol'),(568,10,157,'menu_name','Soft Drinks'),(569,8,158,'menu_name','Cerveza'),(570,10,158,'menu_name','Beer'),(571,8,159,'menu_name','Espagueti - fideos - Arroz'),(572,10,159,'menu_name','Spaghetti - Noodles - Rice'),(573,8,160,'menu_name','Hamburguesa - Sandwiches'),(574,10,160,'menu_name','Burger - Sandwiches'),(575,8,161,'menu_name','Establecer barbacoa'),(576,10,161,'menu_name','Set BBQ'),(577,8,162,'menu_name','Sushi - Nigiri Sushi'),(578,10,162,'menu_name','Sushi - Nigiri Sushi'),(579,8,163,'menu_name','Makimono'),(580,10,163,'menu_name','Makimono'),(581,8,164,'menu_name','Burritos'),(582,10,164,'menu_name','Burritos'),(583,8,165,'menu_name','Comida mexicana'),(584,10,165,'menu_name','Mexican Food'),(585,8,166,'menu_name','Selección de ensalada'),(586,10,166,'menu_name','Salad Selection'),(587,8,167,'menu_name','Vino'),(588,10,167,'menu_name','Wine'),(589,8,168,'menu_name','Botella de bebidas espirituosas'),(590,10,168,'menu_name','Spirits Bottle'),(591,10,169,'name','pizaa super hot'),(592,8,169,'name','pizza super caliente'),(593,10,170,'menu_extra_item_name','How to cook'),(594,8,170,'menu_extra_item_name','Cómo cocinar'),(595,10,171,'extra_item_name','xao lan EN'),(596,8,171,'extra_item_name','xao lan ES'),(597,10,172,'name','Fillet of beef'),(598,8,172,'name','Filete de ternera'),(599,10,173,'menu_extra_item_name','How to cook'),(600,8,173,'menu_extra_item_name','Cómo cocinar'),(601,10,174,'extra_item_name','xao lan EN'),(602,8,174,'extra_item_name','xao lan ES'),(603,10,175,'extra_item_name','chien gion EN'),(604,8,175,'extra_item_name','chien gion ES'),(605,10,176,'extra_item_name','tai chanh EN'),(606,8,176,'extra_item_name','tai chanh ES'),(616,10,182,'name','Fillet of beef'),(617,10,183,'menu_extra_item_name','How to cook'),(618,8,183,'menu_extra_item_name','Cómo cocinar'),(619,10,184,'extra_item_name','xao lan EN'),(620,8,184,'extra_item_name','xao lan ES'),(621,10,185,'extra_item_name','chien gion EN'),(622,8,185,'extra_item_name','chien gion ES'),(623,10,186,'extra_item_name','tai chanh EN'),(624,8,186,'extra_item_name','tai chanh ES'),(636,10,202,'name','Fillet of beef'),(637,8,202,'name','Filete de ternera'),(638,10,203,'menu_extra_item_name','How to cook'),(639,8,203,'menu_extra_item_name','Cómo cocinar'),(640,10,204,'extra_item_name','xao lan EN'),(641,8,204,'extra_item_name','xao lan ES'),(642,10,205,'extra_item_name','chien gion EN'),(643,8,205,'extra_item_name','chien gion ES'),(644,10,206,'extra_item_name','tai chanh EN'),(645,8,206,'extra_item_name','tai chanh ES'),(646,10,207,'name','Fillet of beef'),(647,8,207,'name','Filete de ternera'),(648,10,208,'menu_extra_item_name','How to cook'),(649,8,208,'menu_extra_item_name','Cómo cocinar'),(650,10,210,'extra_item_name','chien gion EN'),(651,8,210,'extra_item_name','chien gion ES'),(652,10,211,'extra_item_name','tai chanh EN'),(653,8,211,'extra_item_name','tai chanh ES'),(654,10,212,'name','Fillet of beef'),(655,8,212,'name','Filete de ternera'),(656,10,213,'menu_extra_item_name','How to cook'),(657,8,213,'menu_extra_item_name','Cómo cocinar'),(658,10,215,'extra_item_name','chien gion EN'),(659,8,215,'extra_item_name','chien gion ES'),(660,10,216,'extra_item_name','tai chanh EN'),(661,8,216,'extra_item_name','tai chanh ES'),(662,10,219,'name','Fillet of beef'),(663,8,219,'name','Filete de ternera'),(664,10,220,'menu_extra_item_name','How to cook'),(665,8,220,'menu_extra_item_name','Cómo cocinar'),(666,10,222,'extra_item_name','chien gion EN'),(667,8,222,'extra_item_name','chien gion ES'),(819,8,292,'menu_item_name','Menu item name  01'),(820,8,292,'menu_item_description','Menu item desc 01'),(821,10,292,'menu_item_name','Menu item name  01 eng'),(822,10,292,'menu_item_description','Menu item desc 01 eng'),(823,8,293,'menu_extra_item_name','Menu extra item single choice name 01'),(824,10,293,'menu_extra_item_name','Menu extra item single choice name 02 eng'),(825,8,294,'menu_extra_item_title','Menu extra item title 01'),(826,10,294,'menu_extra_item_title','Menu extra item title 02 eng'),(827,8,295,'menu_extra_item_title','Menu extra item title 02'),(828,10,295,'menu_extra_item_title','Menu extra item title 02 eng'),(829,8,296,'menu_extra_item_name','Menu extra item single choice name 01'),(830,10,296,'menu_extra_item_name','Menu extra item single choice name 02 eng'),(831,8,297,'menu_extra_item_title','Menu extra item title 01'),(832,10,297,'menu_extra_item_title','Menu extra item title 02 eng'),(833,8,298,'menu_extra_item_name','Menu extra item single choice name 01'),(834,10,298,'menu_extra_item_name','Menu extra item single choice name 02 eng'),(835,8,299,'menu_extra_item_title','Menu extra item title 01'),(836,10,299,'menu_extra_item_title','Menu extra item title 02 eng'),(837,8,300,'menu_item_name','Menu item name  01'),(838,8,300,'menu_item_description','Menu item desc 01'),(839,10,300,'menu_item_name','Menu item name  01 eng'),(840,10,300,'menu_item_description','Menu item desc 01 eng'),(841,8,301,'menu_extra_item_name','TTT'),(842,10,301,'menu_extra_item_name','EEEE'),(843,8,302,'menu_extra_item_title','ZZZ'),(844,10,302,'menu_extra_item_title','EEEEE'),(845,8,303,'menu_extra_item_name','Qqwqw'),(846,10,303,'menu_extra_item_name','qweqwe'),(847,8,304,'menu_extra_item_title','qweqwe'),(848,10,304,'menu_extra_item_title','qweqwe'),(849,8,305,'menu_extra_item_name','qwdeqwe'),(850,10,305,'menu_extra_item_name','weqweqwe'),(851,8,306,'menu_extra_item_title','qweqwe'),(852,10,306,'menu_extra_item_title','sdsf'),(859,8,312,'menu_item_name','pizza super caliente 2'),(860,10,312,'menu_item_name','pizaa super hot 2'),(861,8,313,'menu_extra_item_name','1212'),(862,10,313,'menu_extra_item_name','qweqwe'),(863,8,314,'menu_extra_item_title','qweqwe'),(864,10,314,'menu_extra_item_title',''),(865,10,315,'menu_item_name','pizaa super hot 2'),(866,8,315,'menu_item_name','pizza super caliente 2'),(867,8,316,'menu_extra_item_name','werwr'),(868,10,316,'menu_extra_item_name','123123'),(869,8,317,'menu_extra_item_title','werwer'),(870,10,317,'menu_extra_item_title',''),(871,10,318,'menu_item_name','pizaa super hot 2'),(872,8,318,'menu_item_name','pizza super caliente 2'),(873,8,319,'menu_extra_item_name','werwr'),(874,10,319,'menu_extra_item_name','123123'),(875,8,320,'menu_extra_item_title','werwer'),(876,10,320,'menu_extra_item_title',''),(877,8,321,'menu_extra_item_title','qweqweqwe'),(878,10,321,'menu_extra_item_title',''),(879,8,322,'menu_extra_item_name','qwewe'),(880,10,322,'menu_extra_item_name',''),(881,8,323,'menu_extra_item_title','qweqwe'),(882,10,323,'menu_extra_item_title',''),(883,8,324,'menu_item_name','pizza super caliente 2'),(884,10,324,'menu_item_name','pizaa super hot 2'),(885,10,325,'menu_item_name','pizaa super hot 2'),(886,8,325,'menu_item_name','pizza super caliente 2'),(887,8,326,'menu_extra_item_name','TTT'),(888,10,326,'menu_extra_item_name','erer'),(889,8,327,'menu_extra_item_title','werwer'),(890,10,327,'menu_extra_item_title',''),(891,8,328,'menu_item_name','pizza super caliente 2'),(892,10,328,'menu_item_name','pizaa super hot 2'),(893,8,329,'menu_extra_item_name','wqeqwe'),(894,10,329,'menu_extra_item_name',''),(895,8,330,'menu_extra_item_title','qweqwe'),(896,10,330,'menu_extra_item_title',''),(897,8,331,'menu_extra_item_title','qweqwe'),(898,10,331,'menu_extra_item_title',''),(899,8,332,'menu_extra_item_name','qweqwe'),(900,10,332,'menu_extra_item_name',''),(901,8,333,'menu_extra_item_title','qweqwe'),(902,10,333,'menu_extra_item_title',''),(903,8,336,'menu_item_name','pizza super caliente 2'),(904,10,336,'menu_item_name','pizaa super hot 2'),(905,8,337,'menu_extra_item_name','TTT'),(906,10,337,'menu_extra_item_name',''),(907,8,338,'menu_extra_item_title','wqeqw'),(908,10,338,'menu_extra_item_title',''),(909,8,339,'menu_extra_item_title','GGGDDDD'),(910,10,339,'menu_extra_item_title','wwe'),(911,8,340,'menu_extra_item_name','wqeqwe'),(912,10,340,'menu_extra_item_name',''),(913,8,341,'menu_extra_item_title','qweqwe'),(914,10,341,'menu_extra_item_title',''),(915,8,342,'menu_extra_item_title','qweqwe'),(916,10,342,'menu_extra_item_title',''),(924,8,350,'menu_item_name','pizza super caliente 2'),(925,10,350,'menu_item_name','pizaa super hot 2'),(926,8,351,'menu_extra_item_name',''),(927,10,351,'menu_extra_item_name',''),(928,8,352,'menu_extra_item_title',''),(929,10,352,'menu_extra_item_title',''),(930,8,353,'menu_extra_item_title',''),(931,10,353,'menu_extra_item_title',''),(932,8,354,'menu_extra_item_name',''),(933,10,354,'menu_extra_item_name',''),(934,8,355,'menu_extra_item_title',''),(935,10,355,'menu_extra_item_title',''),(936,8,356,'menu_extra_item_title',''),(937,10,356,'menu_extra_item_title',''),(938,8,357,'menu_extra_item_name','ưerwer'),(939,10,357,'menu_extra_item_name',''),(940,8,358,'menu_extra_item_title','ưerewr'),(941,10,358,'menu_extra_item_title',''),(942,8,359,'menu_item_name','pizza super caliente 2'),(943,10,359,'menu_item_name','pizaa super hot 2'),(944,8,360,'menu_extra_item_name',''),(945,10,360,'menu_extra_item_name',''),(946,8,361,'menu_extra_item_title',''),(947,10,361,'menu_extra_item_title',''),(948,8,362,'menu_extra_item_title',''),(949,10,362,'menu_extra_item_title',''),(950,8,363,'menu_extra_item_name',''),(951,10,363,'menu_extra_item_name',''),(952,8,364,'menu_extra_item_title',''),(953,10,364,'menu_extra_item_title',''),(954,8,365,'menu_extra_item_title',''),(955,10,365,'menu_extra_item_title',''),(956,10,366,'menu_item_name','pizaa super hot 2 123123'),(957,8,366,'menu_item_name','pizza super caliente 2'),(958,8,367,'menu_extra_item_name',''),(959,10,367,'menu_extra_item_name',''),(960,8,368,'menu_extra_item_title',''),(961,10,368,'menu_extra_item_title',''),(962,8,369,'menu_extra_item_title',''),(963,10,369,'menu_extra_item_title',''),(964,8,370,'menu_extra_item_name',''),(965,10,370,'menu_extra_item_name',''),(966,8,371,'menu_extra_item_title',''),(967,10,371,'menu_extra_item_title',''),(968,8,372,'menu_extra_item_title',''),(969,10,372,'menu_extra_item_title',''),(970,10,373,'menu_item_name','pizaa super hot 2 123123'),(971,8,373,'menu_item_name','pizza super caliente 2'),(972,8,374,'menu_extra_item_name',''),(973,10,374,'menu_extra_item_name',''),(974,8,375,'menu_extra_item_title',''),(975,10,375,'menu_extra_item_title',''),(976,8,376,'menu_extra_item_title',''),(977,10,376,'menu_extra_item_title',''),(978,8,377,'menu_extra_item_name',''),(979,10,377,'menu_extra_item_name',''),(980,8,378,'menu_extra_item_title',''),(981,10,378,'menu_extra_item_title',''),(982,8,379,'menu_extra_item_title',''),(983,10,379,'menu_extra_item_title',''),(991,8,382,'menu_item_name','Salad ES'),(992,8,382,'menu_item_description','Nice Salad ES'),(993,10,382,'menu_item_name','Salad'),(994,10,382,'menu_item_description','Nice Salad'),(1017,10,380,'restaurant_description','Piccolo EN 1'),(1018,8,380,'restaurant_description','Piccolo ES'),(1024,10,104,'restaurant_description','Mr Dennehy\'s Desc EN'),(1025,8,104,'restaurant_description','Mr Dennehy\'s Desc ES'),(1026,10,349,'restaurant_description','El Jardin Desc EN'),(1036,10,347,'restaurant_description','Toritos EN'),(1037,8,347,'restaurant_description','Toritos ES'),(1044,10,348,'restaurant_description','Burger King EN'),(1045,8,348,'restaurant_description','Burger King ES'),(1048,10,125,'restaurant_description','Jollibee EN'),(1049,8,125,'restaurant_description','Jollibee ES'),(1050,10,309,'restaurant_description','Food shop EN'),(1051,8,309,'restaurant_description','Food shop ES'),(1052,10,311,'restaurant_description','Oregano EN'),(1053,8,311,'restaurant_description','Oregano ES'),(1056,8,129,'restaurant_description','Spagetty Teddy ES'),(1057,10,129,'restaurant_description','Spagetty Teddy EN'),(1068,10,177,'menu_item_name','Cocktail EN'),(1069,8,177,'menu_item_name','Cóctel ES'),(1070,8,385,'menu_extra_item_name',''),(1071,10,385,'menu_extra_item_name',''),(1072,8,386,'menu_extra_item_title',''),(1073,10,386,'menu_extra_item_title',''),(1078,10,114,'category_name','Sticky rice'),(1079,8,114,'category_name','Arroz pegajoso'),(1096,8,115,'category_name','Arroz pegajoso'),(1097,10,115,'category_name','Sticky rice'),(1116,8,128,'category_name','Mariscos'),(1117,10,128,'category_name','Seafood'),(1128,8,389,'menu_item_name','pizaa super hot 2 123123'),(1129,8,389,'menu_item_description','ryttyty'),(1130,10,389,'menu_item_name',''),(1131,10,389,'menu_item_description',''),(1132,8,390,'menu_extra_item_name','qưeqweqw'),(1133,10,390,'menu_extra_item_name',''),(1134,8,391,'menu_extra_item_title','io88'),(1135,10,391,'menu_extra_item_title',''),(1136,8,392,'menu_item_name','pizaa super hot 2 123123'),(1137,8,392,'menu_item_description','ryttyty'),(1138,10,392,'menu_item_name',''),(1139,10,392,'menu_item_description',''),(1140,8,393,'menu_extra_item_name','qưeqweqw'),(1141,10,393,'menu_extra_item_name',''),(1142,8,394,'menu_extra_item_title','io88'),(1143,10,394,'menu_extra_item_title',''),(1144,8,395,'menu_item_name','pizaa super hot 2 123123'),(1145,8,395,'menu_item_description','ryttyty'),(1146,10,395,'menu_item_name',''),(1147,10,395,'menu_item_description',''),(1148,8,396,'menu_extra_item_name','qưeqweqw'),(1149,10,396,'menu_extra_item_name',''),(1150,8,397,'menu_extra_item_title','io88'),(1151,10,397,'menu_extra_item_title',''),(1152,8,398,'menu_item_name','pizaa super hot 2 123123'),(1153,8,398,'menu_item_description','ryttyty'),(1154,10,398,'menu_item_name',''),(1155,10,398,'menu_item_description',''),(1156,8,399,'menu_extra_item_name','qưeqweqw'),(1157,10,399,'menu_extra_item_name',''),(1158,8,400,'menu_extra_item_title','io88'),(1159,10,400,'menu_extra_item_title',''),(1160,8,401,'menu_item_name','Tai Test'),(1161,8,401,'menu_item_description','Tai'),(1162,10,401,'menu_item_name',''),(1163,10,401,'menu_item_description',''),(1164,8,402,'menu_extra_item_name','qưeqweqw'),(1165,10,402,'menu_extra_item_name',''),(1166,8,403,'menu_extra_item_title','io88'),(1167,10,403,'menu_extra_item_title',''),(1208,10,381,'menu_item_name','Tai Test'),(1209,10,381,'menu_item_description','Nice Salad'),(1210,8,381,'menu_item_description','Nice Salad ES'),(1211,8,381,'menu_item_name','Salad ES'),(1228,8,424,'menu_extra_item_name','werwer'),(1229,10,424,'menu_extra_item_name','werwer'),(1230,8,425,'menu_extra_item_title','werewr'),(1231,10,425,'menu_extra_item_title','werewrwer'),(1236,8,427,'menu_extra_item_name','qweqweqwe'),(1237,10,427,'menu_extra_item_name',''),(1238,8,428,'menu_extra_item_title','qwewqewqe'),(1239,10,428,'menu_extra_item_title',''),(1240,8,429,'menu_extra_item_name','qweqwe'),(1241,10,429,'menu_extra_item_name',''),(1242,8,430,'menu_extra_item_title','qweqwe'),(1243,10,430,'menu_extra_item_title',''),(1308,8,444,'menu_extra_item_title','wwww'),(1309,10,444,'menu_extra_item_title',''),(1314,10,418,'menu_item_description','Menu item 1 EN Desc'),(1315,10,418,'menu_item_name','Menu item 1 EN'),(1316,8,418,'menu_item_name','Menu item 1 ES'),(1317,8,418,'menu_item_description','Menu item 1 EN Desc'),(1334,10,423,'menu_item_description','Menu item 3 EN Desc'),(1335,10,423,'menu_item_name','Menu item 3 EN'),(1336,8,423,'menu_item_name','Menu item 3 ES'),(1337,8,423,'menu_item_description','Menu item 3 ES Desc'),(1338,10,446,'menu_extra_item_title','werewrwer'),(1339,8,446,'menu_extra_item_title','werewr'),(1344,8,272,'restaurant_description','Salad Bistro ES'),(1345,10,272,'restaurant_description','Salad Bistro EN'),(1348,8,116,'category_name','Almuerzo de oficina'),(1349,10,116,'category_name','Ensalada'),(1357,10,450,'restaurant_description','Nha hang chuyen ban pho'),(1358,10,448,'restaurant_description','Nha hang chuyen ban pho'),(1371,8,452,'menu_name','Salad1'),(1372,10,452,'menu_name','Salad2'),(1389,8,454,'menu_extra_item_title','Coke'),(1390,10,454,'menu_extra_item_title','Coca'),(1395,8,456,'menu_extra_item_title','Coke'),(1396,10,456,'menu_extra_item_title','Coca'),(1637,8,539,'promotion_name','ABC'),(1638,8,539,'promotion_desc','ABC'),(1639,10,539,'promotion_name','XYZ'),(1640,10,539,'promotion_desc','XYZ'),(1641,10,457,'menu_item_description','The good ol\' BURGER KING ® Cheeseburger. Crunchy pickles, red ketchup, yellow mustard, and fittingly, cheese - all on a sesame seed bun. Choice of: Add patty, Add Fish patty, Add Chic\'n crisp patty, Add Bacon, Add American cheese (x2) and more.'),(1642,10,457,'menu_item_name','WHOPPER (Meal) bbbbbb'),(1643,8,457,'menu_item_name','WHOPPER (comida) nnn'),(1644,8,457,'menu_item_description','Trae papas fritas + agua'),(1645,10,541,'menu_extra_item_name','Choice of'),(1646,8,541,'menu_extra_item_name','Elección de'),(1647,10,542,'menu_extra_item_title','Add whopper patty'),(1648,8,542,'menu_extra_item_title','Agregue la empanada whopper'),(1649,10,543,'menu_extra_item_title','Add Tender grill patty'),(1650,8,543,'menu_extra_item_title','Añadir Tender grill patty'),(1651,10,544,'menu_extra_item_name','Choice of drink'),(1652,8,544,'menu_extra_item_name','Elección de bebida'),(1653,10,545,'menu_extra_item_title','Fanta'),(1654,8,545,'menu_extra_item_title','Fanta'),(1655,10,546,'menu_extra_item_title','Coca'),(1656,8,546,'menu_extra_item_title','Coke'),(1661,8,548,'menu_name','Salad'),(1662,10,548,'menu_name','Popular Dishes'),(1667,8,127,'menu_name','Menú de Egger'),(1668,10,127,'menu_name','Egger Menu'),(1669,10,148,'menu_name','Salad1'),(1670,8,148,'menu_name','Ensalada1'),(1673,8,549,'promotion_name','q'),(1674,8,549,'promotion_desc','q'),(1675,10,549,'promotion_name',''),(1676,10,549,'promotion_desc',''),(1677,8,547,'promotion_name','Promotion name es'),(1678,8,547,'promotion_desc','Promotion name desc es'),(1679,10,547,'promotion_desc','Promotion name desc en'),(1680,10,547,'promotion_name','Promotion name en'),(1683,10,447,'category_name','Seafood 1'),(1684,8,447,'category_name','Mariscos 1'),(1697,8,551,'promotion_name','Promotion name es 2'),(1698,8,551,'promotion_desc','Promotion name es desc 2'),(1699,10,551,'promotion_name','Promotion name en 2'),(1700,10,551,'promotion_desc','Promotion name en desc 2'),(1705,8,552,'promotion_name','Promotion name es 2'),(1706,8,552,'promotion_desc','Promotion name es desc 2'),(1707,10,552,'promotion_name','Promotion name en 2'),(1708,10,552,'promotion_desc','Promotion name en desc 2'),(1721,8,553,'promotion_name','Promotion name es'),(1722,8,553,'promotion_desc','Promotion name es desc'),(1723,10,553,'promotion_name','Promotion name en'),(1724,10,553,'promotion_desc','Promotion name en desc'),(1725,8,426,'menu_item_description','Menu item 2 ES Desc'),(1726,8,426,'menu_item_name','Menu item 2 ES'),(1727,10,426,'menu_item_description','Menu item 2 EN Desc'),(1728,10,426,'menu_item_name','Menu item 2 EN'),(1729,8,554,'menu_extra_item_name','Elección de'),(1730,10,554,'menu_extra_item_name','Choice of'),(1731,8,555,'menu_extra_item_title','Coke'),(1732,10,555,'menu_extra_item_title','Coca'),(1763,10,263,'menu_item_name','Matcha milk 1'),(1764,8,263,'menu_item_name','Matha milk es 1'),(1765,10,566,'menu_extra_item_name','Extra 1'),(1766,8,566,'menu_extra_item_name','Extra ES 1'),(1767,10,567,'extra_item','add Chan Trau EN 111'),(1768,8,567,'extra_item','add Chan Trau ES 111'),(1793,10,540,'promotion_name','XYZ'),(1794,10,540,'promotion_desc','XYZ'),(1795,8,540,'promotion_desc','ABC'),(1796,8,540,'promotion_name','ABC'),(1797,10,550,'promotion_desc','Promotion name en desc 1'),(1798,10,550,'promotion_name','Promotion name en 1'),(1799,8,550,'promotion_name','Promotion name es 1'),(1800,8,550,'promotion_desc','Promotion name es desc 1'),(1821,8,451,'restaurant_description','Viet Nam Restaurant ES'),(1822,10,451,'restaurant_description','Viet Nam Restaurant EN'),(1859,10,126,'menu_name','Popular Dishes'),(1860,8,126,'menu_name','Platos populares'),(1885,10,384,'menu_item_description','Nice Salad'),(1886,10,384,'menu_item_name','Salad'),(1887,8,384,'menu_item_description','Nice Salad ES'),(1888,8,384,'menu_item_name','Salad ES'),(1889,10,601,'menu_extra_item_name','Elección de bebida'),(1890,8,601,'menu_extra_item_name','Choice of drink'),(1891,10,602,'menu_extra_item_title','Agregue la empanada whopper'),(1892,8,602,'menu_extra_item_title','Add whopper patty'),(1893,10,603,'menu_extra_item_name','Elección de'),(1894,8,603,'menu_extra_item_name','Choice of'),(1895,10,604,'menu_extra_item_title','Coke'),(1896,8,604,'menu_extra_item_title','Coca'),(1897,8,605,'menu_name','Menu 1 ES'),(1898,10,605,'menu_name','Menu 1 EN'),(1899,8,606,'menu_name','Menu 2 ES'),(1900,10,606,'menu_name','Menu 2 EN'),(1901,8,607,'menu_name','Menu 3 ES'),(1902,10,607,'menu_name','Menu 3 EN'),(1903,8,608,'menu_name','Menu 4 ES'),(1904,10,608,'menu_name','Menu 4 EN'),(1941,8,383,'menu_item_description','Nice Salad ES'),(1942,8,383,'menu_item_name','Salad ES'),(1943,10,383,'menu_item_description','Nice Salad'),(1944,10,383,'menu_item_name','Salad'),(1945,8,621,'menu_extra_item_name','Elección de bebida'),(1946,10,621,'menu_extra_item_name','Choice of drink'),(1947,8,622,'menu_extra_item_title','Agregue la empanada whopper'),(1948,10,622,'menu_extra_item_title','Add whopper patty (x2)'),(1949,8,623,'menu_extra_item_title','Agregue la empanada whopper 1'),(1950,10,623,'menu_extra_item_title','Add whopper patty patty patty  2 (x2)'),(1951,8,624,'menu_extra_item_name','Elección de bebida'),(1952,10,624,'menu_extra_item_name','Choice of drink'),(1953,8,625,'menu_extra_item_title','Agregue la empanada whopper'),(1954,10,625,'menu_extra_item_title','Add Tender grill patty (x2)'),(1955,8,626,'menu_extra_item_name','Elección de bebida'),(1956,10,626,'menu_extra_item_name','Choice of drink'),(1957,8,627,'menu_extra_item_title','Agregue la empanada whopper'),(1958,10,627,'menu_extra_item_title','Add whopper patty patty patty  (x2)'),(1959,8,628,'menu_name','Menu 5 ES'),(1960,10,628,'menu_name','Menu 5 EN'),(1961,8,629,'menu_name','Menu 6 ES'),(1962,10,629,'menu_name','Menu 6 EN'),(1963,8,630,'menu_name','Menu 7 ES'),(1964,10,630,'menu_name','Menu 7 EN'),(1965,8,631,'menu_name','Menu 7 ES'),(1966,10,631,'menu_name','Menu 7 EN'),(1967,8,632,'menu_name','Menu 8 ES'),(1968,10,632,'menu_name','Menu 8 EN'),(1971,8,388,'category_name','Arroz pegajoso 1'),(1972,10,388,'category_name','Sticky rice 2'),(1973,8,387,'category_name','Arroz pegajoso 3'),(1974,10,387,'category_name','Sticky rice 3'),(1975,8,633,'menu_name','Platos populares'),(1976,10,633,'menu_name','Popular Dishes'),(1977,8,634,'menu_name','Menu 9 ES'),(1978,10,634,'menu_name','Menu 9 EN'),(1979,8,635,'menu_name','Menu 10 ES'),(1980,10,635,'menu_name','Menu 10 EN'),(1981,8,636,'menu_name','Menu 11 ES'),(1982,10,636,'menu_name','Menu 11 EN'),(1983,8,637,'menu_name','Menu 12 ES'),(1984,10,637,'menu_name','Menu 12 EN'),(1985,8,638,'menu_name','Menu 13 ES'),(1986,10,638,'menu_name','Menu 13 EN'),(1987,8,639,'menu_name','Menu 14 ES'),(1988,10,639,'menu_name','Menu 14 EN'),(1989,8,640,'menu_name',''),(1990,10,640,'menu_name',''),(2087,8,568,'menu_item_description','Menu Item 2 ES Desc'),(2088,8,568,'menu_item_name','Menu Item 2 ES'),(2089,10,568,'menu_item_name','Menu Item 2 EN'),(2090,10,568,'menu_item_description','Menu Item 2 EN Desc'),(2091,8,673,'menu_extra_item_name','Elección de'),(2092,10,673,'menu_extra_item_name','Choice of'),(2093,8,674,'menu_extra_item_title','Coke'),(2094,10,674,'menu_extra_item_title','Coca'),(2095,8,675,'menu_extra_item_name','Elección de:'),(2096,10,675,'menu_extra_item_name','Choice of drink'),(2097,8,676,'menu_extra_item_title','Agregue la empanada whopper'),(2098,10,676,'menu_extra_item_title','Add whopper patty'),(2099,8,677,'menu_item_name','Menu item 4 ES'),(2100,8,677,'menu_item_description','Menu item 4 ES Desc'),(2101,10,677,'menu_item_name','Menu item 4 EN'),(2102,10,677,'menu_item_description','Menu item 4 EN Desc'),(2103,8,678,'menu_extra_item_name','Elección de'),(2104,10,678,'menu_extra_item_name','Choice of'),(2105,8,679,'menu_extra_item_title','Coke'),(2106,10,679,'menu_extra_item_title','Coca'),(2107,8,680,'menu_extra_item_name','Elección de bebida'),(2108,10,680,'menu_extra_item_name','Choice of drink'),(2109,8,681,'menu_extra_item_title','Agregue la empanada whopper'),(2110,10,681,'menu_extra_item_title','Add whopper patty'),(2113,8,683,'menu_item_name',''),(2114,8,683,'menu_item_description',''),(2115,10,683,'menu_item_name',''),(2116,10,683,'menu_item_description',''),(2117,8,684,'menu_item_name',''),(2118,8,684,'menu_item_description',''),(2119,10,684,'menu_item_name',''),(2120,10,684,'menu_item_description',''),(2123,10,685,'restaurant_description','Piccolo EN 1'),(2124,8,685,'restaurant_description','Piccolo ES'),(2127,10,105,'category_name','Italian & Pizza'),(2128,8,105,'category_name','Italiano y Pizza'),(2135,8,686,'restaurant_description','aaaaaa'),(2136,10,686,'restaurant_description','bbbbbbbbbb'),(2145,8,687,'menu_name','Menu 13 ES'),(2146,10,687,'menu_name','Menu 13 EN'),(2151,8,688,'promotion_name','Promotion name es'),(2152,8,688,'promotion_desc','aaaaaaaaaaaa'),(2153,10,688,'promotion_name','Promotion name en'),(2154,10,688,'promotion_desc','aaa'),(2187,8,697,'menu_item_name','Cóctel ES'),(2188,8,697,'menu_item_description','aaa'),(2189,10,697,'menu_item_name','Cocktail EN'),(2190,10,697,'menu_item_description','aaa'),(2191,8,696,'menu_item_description','2'),(2192,8,696,'menu_item_name','Salad ES'),(2193,10,696,'menu_item_description','4'),(2194,10,696,'menu_item_name','Salad EN'),(2207,8,698,'restaurant_description','aaaaaaaaa'),(2208,10,698,'restaurant_description','aaaaaaaaaaa'),(2215,8,699,'menu_item_name','Menu item 20 ES'),(2216,8,699,'menu_item_description','Menu item 20 ES Desc'),(2217,10,699,'menu_item_name',''),(2218,10,699,'menu_item_description',''),(2219,8,700,'menu_item_name','Menu item 21 ES'),(2220,8,700,'menu_item_description','Menu item 21 ES Desc'),(2221,10,700,'menu_item_name',''),(2222,10,700,'menu_item_description',''),(2223,8,701,'menu_item_name','Menu item 20 ES'),(2224,8,701,'menu_item_description','Menu item 20 ES Desc'),(2225,10,701,'menu_item_name',''),(2226,10,701,'menu_item_description',''),(2255,10,538,'promotion_desc','Promotion name en 1 desc'),(2256,10,538,'promotion_name','Promotion name en 1'),(2257,8,538,'promotion_desc','Promotion name es 1 desc'),(2258,8,538,'promotion_name','Promotion name es 1'),(2259,10,273,'promotion_desc','Promotion name es desc'),(2260,10,273,'promotion_name','Promotion name en'),(2261,8,273,'promotion_name','Promotion name es'),(2262,8,273,'promotion_desc','Promotion name es desc'),(2285,10,110,'restaurant_description','Imbir Desc EN'),(2286,8,110,'restaurant_description','Imbir Desc ES'),(2295,10,124,'restaurant_description','Marsella Desc EN'),(2296,8,124,'restaurant_description','Marsella Desc ES'),(2297,10,682,'restaurant_description','Piccolo EN Desc 1'),(2298,8,682,'restaurant_description','Piccolo ES Desc 1'),(2299,8,704,'category_name','Comida rápida'),(2300,10,704,'category_name','Fast Food');
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
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_item`
--

LOCK TABLES `extra_item` WRITE;
/*!40000 ALTER TABLE `extra_item` DISABLE KEYS */;
INSERT INTO `extra_item` VALUES (41,29,294,2),(42,29,295,3),(43,30,297,3),(44,31,299,4),(45,32,302,3),(46,33,304,1),(47,34,306,3),(48,35,314,2),(49,36,317,2),(50,37,320,2),(51,37,321,3),(52,38,323,4),(53,39,327,1),(54,40,330,3),(55,40,331,1),(56,41,333,3),(57,42,338,1),(58,42,339,3),(59,43,341,1),(60,43,342,2),(61,44,352,3),(62,44,353,1),(63,45,355,2),(64,45,356,1),(65,46,358,1),(66,47,361,3),(67,47,362,1),(68,48,364,1),(69,48,365,2),(70,49,368,2),(71,49,369,1),(72,50,371,1),(73,50,372,3),(74,51,375,2),(75,51,376,1),(76,52,378,1),(77,52,379,3),(78,53,386,5),(99,80,444,4),(155,112,542,30),(156,112,543,40),(157,113,545,20),(158,113,546,10),(159,114,555,5),(165,120,567,5),(182,137,602,4),(183,138,604,6),(190,145,622,10000),(191,145,623,0),(192,146,625,1000),(193,147,627,2000000),(210,164,674,10),(211,165,676,20),(212,166,679,15),(213,167,681,25);
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
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
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
INSERT INTO `media` VALUES (40,'files','1528271625079_download.png',5968,'image/png','/upload/20180606/1528271625079_download.png',NULL,NULL),(41,'files','1528272452080_joma_bakery_cafe_logo.png',82219,'image/png','/upload/20180606/1528272452080_joma_bakery_cafe_logo.png',NULL,NULL),(42,'files','1528292803651_Untitled-1.jpg',71600,'image/jpeg','/upload/20180606/1528292803651_Untitled-1.jpg',NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (12,24,126,'',NULL,'',2,'2018-06-05 18:56:08',NULL,'2019-07-10 09:58:13',NULL,0,NULL),(13,40,127,'',NULL,'',2,'2018-06-05 19:02:36',NULL,'2019-07-10 09:52:54',NULL,0,NULL),(14,24,146,'',NULL,'',2,'2018-06-07 22:24:30',NULL,'2019-07-10 09:59:33',NULL,0,''),(15,24,147,'',NULL,'',2,'2018-06-07 22:27:12',NULL,'2019-07-10 09:59:33',NULL,0,''),(16,40,148,'',NULL,'',2,'2018-06-07 22:27:42',NULL,'2019-07-10 09:58:13',NULL,0,NULL),(17,26,149,'',NULL,'',2,'2018-06-07 22:30:15',NULL,'2019-07-10 09:58:14',NULL,0,''),(18,26,150,'',NULL,'',2,'2018-06-07 22:30:46',NULL,'2019-07-10 09:52:53',NULL,0,''),(19,26,151,'',NULL,'',2,'2018-06-07 22:31:08',NULL,'2019-07-10 09:58:16',NULL,0,''),(20,26,152,'',NULL,'',2,'2018-06-07 22:31:27',NULL,'2019-07-10 09:59:34',NULL,0,''),(21,26,153,'',NULL,'',2,'2018-06-07 22:31:47',NULL,'2019-07-10 09:59:34',NULL,0,''),(22,25,154,'',NULL,'',2,'2018-06-07 22:32:57',NULL,'2019-07-10 09:58:32',NULL,0,''),(23,25,155,'',NULL,'',2,'2018-06-07 22:35:31',NULL,'2019-07-10 09:58:33',NULL,0,''),(24,25,156,'',NULL,'',2,'2018-06-07 22:36:08',NULL,'2019-07-10 09:58:43',NULL,0,''),(25,25,157,'',NULL,'',2,'2018-06-07 22:36:55',NULL,'2019-07-10 09:58:44',NULL,0,''),(26,25,158,'',NULL,'',2,'2018-06-07 22:37:19',NULL,'2019-07-10 09:58:14',NULL,0,''),(27,18,159,'',NULL,'',2,'2018-06-07 22:38:37',NULL,'2019-07-10 09:58:16',NULL,0,''),(28,18,160,'',NULL,'',2,'2018-06-07 22:39:19',NULL,'2019-07-10 09:58:53',NULL,0,''),(29,18,161,'',NULL,'',2,'2018-06-07 22:39:47',NULL,'2019-07-10 09:58:54',NULL,0,''),(30,18,162,'',NULL,'',2,'2018-06-07 22:40:42',NULL,'2019-07-10 09:59:04',NULL,0,''),(31,18,163,'',NULL,'',2,'2018-06-07 22:41:13',NULL,'2019-07-10 09:59:05',NULL,0,''),(32,23,164,'',NULL,'',2,'2018-06-07 22:42:55',NULL,'2019-07-10 09:58:32',NULL,0,''),(33,23,165,'',NULL,'',2,'2018-06-07 22:43:37',NULL,'2019-07-10 09:59:06',NULL,0,''),(34,23,166,'',NULL,'',2,'2018-06-07 22:48:34',NULL,'2019-07-10 09:58:14',NULL,0,''),(35,23,167,'',NULL,'',2,'2018-06-07 22:48:57',NULL,'2019-07-10 09:59:12',NULL,0,''),(36,23,168,'',NULL,'',2,'2018-06-07 22:49:21',NULL,'2019-07-10 09:58:43',NULL,0,''),(37,33,452,'',NULL,'',2,'2018-07-09 09:48:01',NULL,'2019-07-10 09:58:44',NULL,0,NULL),(38,40,548,'',NULL,'',2,'2018-07-11 22:18:48',NULL,'2019-07-10 09:59:18',NULL,0,NULL),(39,36,605,'',NULL,'',2,'2018-07-17 11:34:01',NULL,'2019-07-10 09:59:31',NULL,0,NULL),(40,31,606,'',NULL,'',2,'2018-07-17 11:40:41',NULL,'2019-07-10 09:58:53',NULL,0,NULL),(41,23,607,'',NULL,'',2,'2018-07-17 11:47:00',NULL,'2019-07-10 09:58:33',NULL,0,NULL),(42,24,608,'',NULL,'',2,'2018-07-17 11:57:18',NULL,'2019-07-10 09:58:15',NULL,0,NULL),(43,25,628,'',NULL,'',2,'2018-07-17 16:19:31',NULL,'2019-07-10 09:58:54',NULL,0,NULL),(44,31,629,'',NULL,'',2,'2018-07-17 16:33:29',NULL,'2019-07-10 09:59:31',NULL,0,NULL),(45,40,630,'',NULL,'',2,'2018-07-17 16:48:35',NULL,'2019-07-10 09:59:32',NULL,0,NULL),(46,26,631,'',NULL,'',2,'2018-07-17 22:28:50',NULL,'2019-07-10 09:58:44',NULL,0,NULL),(47,24,632,'',NULL,'',2,'2018-07-17 22:30:55',NULL,'2019-07-10 09:59:04',NULL,0,NULL),(48,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,1,NULL),(49,36,633,'',NULL,'',2,'2018-07-19 11:31:14',NULL,'2019-07-10 09:59:05',NULL,0,NULL),(50,35,634,'',NULL,'',2,'2018-07-19 11:42:12',NULL,'2019-07-10 09:59:32',NULL,0,NULL),(51,38,635,'',NULL,'',2,'2018-07-19 11:45:06',NULL,'2019-07-10 09:58:15',NULL,0,NULL),(52,24,636,'',NULL,'',2,'2018-07-19 11:48:08',NULL,'2019-07-10 09:58:33',NULL,0,NULL),(53,24,637,'',NULL,'',2,'2018-07-19 11:48:21',NULL,'2018-07-24 23:16:50',NULL,0,NULL),(54,18,638,'',NULL,'',2,'2018-07-19 11:51:07',NULL,'2019-07-10 09:59:33',NULL,0,NULL),(55,18,639,'',NULL,'',2,'2018-07-19 11:51:21',NULL,'2019-07-10 09:58:54',NULL,0,NULL),(56,18,640,'',NULL,'',2,'2018-07-19 11:51:23',NULL,'2019-07-10 09:58:13',NULL,0,NULL),(57,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,1,NULL),(58,24,687,'',NULL,'',2,'2018-07-24 23:22:52',NULL,'2019-07-10 09:59:33',NULL,0,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_extra_item`
--

LOCK TABLES `menu_extra_item` WRITE;
/*!40000 ALTER TABLE `menu_extra_item` DISABLE KEYS */;
INSERT INTO `menu_extra_item` VALUES (29,54,293,1),(30,54,296,1),(31,54,298,1),(32,55,301,1),(33,55,303,1),(34,55,305,1),(35,56,313,1),(36,57,316,1),(37,58,319,1),(38,58,322,1),(39,60,326,1),(40,61,329,1),(41,61,332,1),(42,62,337,1),(43,62,340,1),(44,63,351,1),(45,63,354,1),(46,63,357,1),(47,64,360,1),(48,64,363,1),(49,65,367,1),(50,65,370,1),(51,66,374,1),(52,66,377,1),(53,12,385,1),(80,76,443,2),(112,79,541,2),(113,79,544,1),(114,78,554,1),(120,48,566,1),(137,70,601,1),(138,70,603,2),(145,69,621,2),(146,69,624,1),(147,69,626,2),(164,80,673,1),(165,80,675,2),(166,81,678,1),(167,81,680,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu_item`
--

LOCK TABLES `menu_item` WRITE;
/*!40000 ALTER TABLE `menu_item` DISABLE KEYS */;
INSERT INTO `menu_item` VALUES (2,27,10,'','\0',133,'pizaa-super-hot',1,2),(4,27,1000,'','',140,NULL,2,2),(5,27,2,'','',141,NULL,2,2),(6,27,2,'','',142,NULL,2,2),(7,13,1000,'','',143,NULL,2,2),(8,12,1000,'','',144,NULL,2,2),(9,13,1000,'','',145,NULL,2,2),(12,12,20,'','\0',177,NULL,1,2),(28,12,10,'','\0',217,NULL,1,1),(48,12,10,NULL,'\0',263,NULL,1,2),(54,18,5,'','\0',292,NULL,NULL,2),(55,25,3,'','\0',300,NULL,NULL,2),(56,28,2,'','',312,NULL,2,2),(57,27,2,'','',315,NULL,2,2),(58,27,2,'','',318,NULL,2,2),(59,28,2,'','',324,NULL,2,2),(60,27,2,'','',325,NULL,2,2),(61,27,2,'','',328,NULL,2,2),(62,27,2,'','',336,NULL,2,2),(63,27,2,'','',350,NULL,2,2),(64,31,2,'','',359,NULL,2,2),(65,27,2,'','',366,NULL,2,2),(66,27,2,'','',373,NULL,2,2),(67,31,4,'','\0',381,NULL,NULL,2),(68,31,4,'','\0',382,NULL,NULL,2),(69,31,4,NULL,'\0',383,NULL,NULL,2),(70,36,4,NULL,'\0',384,NULL,NULL,2),(76,17,1000,NULL,'\0',418,NULL,NULL,2),(77,21,2,NULL,'\0',423,NULL,NULL,2),(78,21,1,'images/20180708/1531068696794_menu-item.png','\0',426,NULL,NULL,2),(79,20,10000,'images/20180709/1531126891975_pizza.jpg','\0',457,NULL,NULL,2),(80,13,6,'images/20180713/1531474509273_menu-item.png','\0',568,NULL,NULL,2),(81,37,20,'images/20180719/1531989985486_menu-item.png','\0',677,NULL,NULL,2),(82,37,0,NULL,'\0',683,NULL,NULL,2),(83,37,0,NULL,'\0',684,NULL,NULL,2),(91,14,10000,NULL,'\0',696,NULL,NULL,2),(92,58,10000,NULL,'\0',697,NULL,NULL,2),(93,39,8,'images/20180731/1533025299201_1532962066210.JPEG','\0',699,NULL,NULL,2),(94,43,6,'images/20180731/1533026165079_1532962146447.JPEG','\0',700,NULL,NULL,2),(95,39,8,NULL,'\0',701,NULL,NULL,2);
/*!40000 ALTER TABLE `menu_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module_action`
--

DROP TABLE IF EXISTS `module_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `module_action` (
  `module_action_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `module_desc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`module_action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
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
  `menu_item_id` bigint(20) DEFAULT NULL,
  `menu_extra_item_id` bigint(20) DEFAULT NULL,
  `exra_item_id` bigint(20) DEFAULT NULL,
  `unit_price` decimal(10,0) DEFAULT NULL,
  `total_price` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`order_extra_item_id`),
  KEY `fk_o_l_ex_i_id` (`exra_item_id`),
  KEY `fk_oeit_oli_id` (`menu_item_id`),
  KEY `fk_o_e_m_i_ex_id` (`menu_extra_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_extra_item`
--

LOCK TABLES `order_extra_item` WRITE;
/*!40000 ALTER TABLE `order_extra_item` DISABLE KEYS */;
INSERT INTO `order_extra_item` VALUES (98,70,63,88,3,3),(99,70,64,89,1,1),(100,70,65,90,2,2),(102,70,63,88,3,3),(103,70,64,89,1,1),(104,70,65,90,2,2),(105,70,64,89,1,1),(106,70,65,90,2,2),(107,70,63,88,3,3),(108,25,9,12,43,43),(109,25,9,13,323,323),(110,25,9,11,23,23),(111,11,2,3,33,33),(112,11,2,2,12,12),(113,11,2,4,22,22),(114,70,65,90,2,2),(115,70,63,88,3,3),(116,70,64,89,1,1),(117,70,65,90,2,2),(118,70,63,88,3,3),(119,70,64,89,1,1),(120,70,65,90,2,2),(121,70,63,88,3,3),(122,70,64,89,1,1),(123,64,47,66,3,3),(124,64,47,67,1,1),(125,64,48,68,1,1),(126,64,48,69,2,2),(127,70,65,90,2,2),(128,70,63,88,3,3),(129,70,64,89,1,1),(130,64,48,68,1,1),(131,64,48,69,2,2),(132,64,47,67,1,1),(133,64,47,66,3,3),(134,70,182,NULL,4,4),(135,70,183,NULL,6,6),(136,64,69,NULL,2,2),(137,64,68,NULL,1,1),(138,64,66,NULL,3,3),(139,64,67,NULL,1,1),(172,69,191,NULL,0,0),(173,69,190,NULL,10000,10000),(174,69,192,NULL,1000,1000),(175,69,193,NULL,2000000,2000000),(176,69,191,NULL,0,0),(177,69,190,NULL,10000,10000),(178,69,192,NULL,1000,1000),(179,69,193,NULL,2000000,2000000),(180,78,159,NULL,5,5);
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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_history`
--

LOCK TABLES `order_history` WRITE;
/*!40000 ALTER TABLE `order_history` DISABLE KEYS */;
INSERT INTO `order_history` VALUES (51,'{\"orderId\":81,\"orderCode\":\"A9993C56\",\"orderDate\":\"Jul 10, 2018 9:46:07 PM\",\"restaurantName\":\"Marsella\",\"cityName\":\"Managua\",\"status\":1,\"totalPrice\":4149,\"userName\":\"anonymous\",\"checkSum\":\"$2a$04$Gq/6i/qGMc0n8yndm4qlROlrEfdvj4.WEdgnG8.sV9eohuqU9DwsW\",\"email\":\"nguyentai.fit@gmail.com\",\"openTime\":\"00:00\",\"closeTime\":\"09:00\"}','2018-07-23 15:00:44'),(52,'{\"orderId\":81,\"orderCode\":\"A9993C56\",\"orderDate\":\"Jul 10, 2018 9:46:07 PM\",\"restaurantName\":\"Marsella\",\"cityName\":\"Managua\",\"status\":2,\"totalPrice\":4149,\"userName\":\"anonymous\",\"checkSum\":\"$2a$04$Gq/6i/qGMc0n8yndm4qlROlrEfdvj4.WEdgnG8.sV9eohuqU9DwsW\",\"email\":\"nguyentai.fit@gmail.com\",\"openTime\":\"00:00\",\"closeTime\":\"09:00\"}','2018-07-23 15:04:11'),(53,'{\"orderId\":81,\"orderCode\":\"A9993C56\",\"orderDate\":\"Jul 10, 2018 9:46:07 PM\",\"restaurantName\":\"Marsella\",\"cityName\":\"Managua\",\"status\":3,\"totalPrice\":4149,\"userName\":\"anonymous\",\"checkSum\":\"$2a$04$Gq/6i/qGMc0n8yndm4qlROlrEfdvj4.WEdgnG8.sV9eohuqU9DwsW\",\"email\":\"nguyentai.fit@gmail.com\",\"openTime\":\"00:00\",\"closeTime\":\"09:00\"}','2018-07-23 15:04:17'),(54,'{\"orderId\":83,\"orderCode\":\"99997E16\",\"orderDate\":\"Jul 11, 2018 2:14:29 PM\",\"restaurantName\":\"Marsella\",\"cityName\":\"Managua\",\"status\":1,\"totalPrice\":500,\"userName\":\"Admin\",\"checkSum\":\"$2a$04$QN2JXHmlJyOp4SkFDBZRI.u6PGVpyL.3hox1PMY30fDaNzYV/Psf.\",\"email\":\"New_order_to_admin@orderfood.com\",\"openTime\":\"08:00\",\"closeTime\":\"10:00\"}','2018-07-30 11:09:26'),(55,'{\"orderId\":81,\"orderCode\":\"A9993C56\",\"orderDate\":\"Jul 10, 2018 9:46:07 PM\",\"restaurantName\":\"Marsella\",\"cityName\":\"Managua\",\"status\":2,\"totalPrice\":4149,\"userName\":\"anonymous\",\"checkSum\":\"$2a$04$Gq/6i/qGMc0n8yndm4qlROlrEfdvj4.WEdgnG8.sV9eohuqU9DwsW\",\"email\":\"nguyentai.fit@gmail.com\",\"openTime\":\"08:00\",\"closeTime\":\"10:00\"}','2018-07-30 11:09:31');
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
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_line_item`
--

LOCK TABLES `order_line_item` WRITE;
/*!40000 ALTER TABLE `order_line_item` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_payment`
--

LOCK TABLES `order_payment` WRITE;
/*!40000 ALTER TABLE `order_payment` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion`
--

LOCK TABLES `promotion` WRITE;
/*!40000 ALTER TABLE `promotion` DISABLE KEYS */;
INSERT INTO `promotion` VALUES (3,273,NULL,NULL,'2018-09-01 00:00:00','2018-09-29 00:00:00',11,'2018-06-25 15:22:12',NULL,'2019-07-10 10:09:27',NULL,NULL,200000,2),(4,538,NULL,NULL,'2018-07-30 00:00:00','2018-08-30 00:00:00',30,'2018-07-10 15:13:37',NULL,'2019-07-10 10:09:31',NULL,NULL,10000,2),(5,539,NULL,NULL,'2018-07-10 00:00:00','2018-07-28 00:00:00',30,'2018-07-10 16:41:17',NULL,'2019-07-10 10:09:36',NULL,NULL,10000,2),(6,540,NULL,NULL,'2018-07-10 00:00:00','2018-07-28 00:00:00',20,'2018-07-10 16:45:40',NULL,'2019-07-10 10:09:40',NULL,NULL,20000,2),(7,547,NULL,NULL,'2018-07-13 00:00:00','2018-07-29 00:00:00',25,'2018-07-11 08:53:34',NULL,'2019-07-10 10:09:45',NULL,NULL,10001,2),(8,549,NULL,NULL,'2018-07-09 00:00:00','2018-07-27 00:00:00',1,'2018-07-12 00:50:32',NULL,'2019-07-10 10:09:48',NULL,NULL,1,2),(9,550,NULL,NULL,'2018-07-13 00:00:00','2018-07-21 00:00:00',69,'2018-07-13 10:52:21',NULL,'2019-07-10 10:09:52',NULL,NULL,10,2),(10,551,NULL,NULL,'2018-07-14 00:00:00','2018-07-15 00:00:00',10,'2018-07-13 10:57:41',NULL,'2019-07-10 10:09:55',NULL,NULL,10000,2),(11,552,NULL,NULL,'2018-07-11 00:00:00','2018-07-15 00:00:00',10,'2018-07-13 11:13:09',NULL,'2019-07-10 10:09:59',NULL,NULL,10000,2),(12,553,NULL,NULL,'2018-07-13 00:00:00','2018-07-28 00:00:00',25,'2018-07-13 15:46:31',NULL,'2019-07-10 10:10:02',NULL,NULL,10000,2),(13,688,NULL,NULL,'2018-07-24 00:00:00','2018-07-31 00:00:00',30,'2018-07-24 23:25:23',NULL,'2019-07-10 10:10:05',NULL,NULL,10000,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotion_lineitem`
--

LOCK TABLES `promotion_lineitem` WRITE;
/*!40000 ALTER TABLE `promotion_lineitem` DISABLE KEYS */;
INSERT INTO `promotion_lineitem` VALUES (5,5,38,'BANANHMAO1'),(7,8,23,'11qw'),(8,7,31,'BANANHDAVID1'),(11,10,34,'BANANHMAO1'),(13,11,34,'BANANHMAO1111'),(17,12,40,'VIETNAM'),(18,6,38,'BANANHMAO1'),(19,9,40,'BANANHMAO1'),(21,13,24,'WorldCup2022'),(28,4,24,'PROMOTION 1'),(29,3,18,'PROMOTION');
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  KEY `fk_res_dis_id_idx` (`districtId`),
  CONSTRAINT `fk_conten_con` FOREIGN KEY (`content_dep_id`) REFERENCES `content_definition` (`content_dep_id`),
  CONSTRAINT `fk_res_dis_id` FOREIGN KEY (`districtId`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (18,'Mr Dennehy\'s','','Managua, Nicaragua',12.127086,-86.30042960000003,'08:00','00:00','0987654321','0987654322',104,2,'mr-dennehy-s','2018-06-04 07:00:00',NULL,'2019-07-10 10:07:50',NULL,'','#managua',1,2000,'Managua',NULL,'images/20180703/1530593263133_dennehy-icon.jpg',200,'20',NULL,NULL),(23,'Imbir','','Quinta Samaria, NN-175, Managua, Nicaragua',12.0721267,-86.21005249999996,'00:00','12:00','0123456789','',110,2,'imbir','2018-06-04 07:00:00',NULL,'2019-07-10 10:07:22',NULL,'','#',2,4000,'Madrid','Madrid','images/20180703/1530593892585_imbir-icon.jpg',400,'40','Turn left',2),(24,'Marsella',NULL,'Managua, Nicaragua',12.127086,-86.30042960000003,'08:00','22:00','01687625699',NULL,124,2,'marsella','2018-06-05 15:59:41',NULL,'2019-07-10 10:07:21',NULL,NULL,'cau-giay#ha-noi',3,5000,'Madrid','Madrid','images/20180703/1530601387789_mersella-icon.jpg',500,'50','Turn left',2),(25,'Jollibee','Delicious is my advantage','Carretera Norte, Managua, Nicaragua',12.1479345,-86.19409969999998,'00:00','06:00','01687625697',NULL,125,2,'jollibee','2018-06-05 16:16:21',NULL,'2019-07-10 10:07:49',NULL,NULL,'#managua',4,450,'Managua',NULL,'images/20180703/1530602924701_jollibee-icon.png',222,'32',NULL,NULL),(26,'Spagetty Teddy','','Avenida Cristian Perez, Managua, Nicaragua',12.1404597,-86.2378986,'08:00','21:00','0987654321','',129,2,'spagetty-teddy','2018-06-05 23:14:24',NULL,'2019-07-10 10:07:48',NULL,NULL,'#managua',5,1000,'Managua','','images/20180703/1530603257132_Spaghetty.png',200,'20',NULL,NULL),(28,'Salad Bistro',NULL,'Boulevard Don Bosco, Managua, Nicaragua',12.1304803,-86.2467623,'00:00','06:00',NULL,NULL,272,2,'salad-bistro','2018-06-19 10:49:00',NULL,'2019-07-10 10:07:38',NULL,NULL,'#managua',0,1000,'Managua','','images/20180703/1530603408092_salad-bistro.png',0,'10',NULL,NULL),(31,'Food shop','My slogan','Avenida Cristian Perez, Managua, Nicaragua',12.1404501,-86.2378986,'03:00','08:00','34535435','345345435',309,2,'food-shop','2018-06-26 23:55:55',NULL,'2019-07-10 10:07:31',NULL,'234324','#managua',0,12,'Managua',NULL,'images/20180703/1530603123343_food-shop-icon.png',1212,'12',NULL,NULL),(33,'Oregano','My slogan','Avenida Roberto Vargas, Managua, Nicaragua',12.1117485,-86.29716510000003,'03:00','08:00','34535435','345345435',311,2,'oregano','2018-06-27 00:34:04',NULL,'2019-07-10 10:07:49',NULL,'234324','#managua',0,12,'Managua',NULL,'images/20180703/1530603186996_oregano.png',1212,'12',NULL,NULL),(34,'Toritos','No Girl, No gambling ','Avenida Cristian Perez, Managua, Nicaragua',12.1404512,-86.23790810000003,'04:00','08:00','12','12',347,2,'toritos','2018-06-27 01:35:56',NULL,'2019-07-10 10:07:49',NULL,NULL,'#managua',0,12,'Managua',NULL,'images/20180703/1530601748760_toritos-icon.jpg',12,'12',NULL,NULL),(35,'Burger King','12','Boulevard Don Bosco, Managua, Nicaragua',12.1302957,-86.2465651,'05:00','08:00','12','12',348,2,'burger-king','2018-06-27 01:39:59',NULL,'2019-07-10 10:07:37',NULL,NULL,'#managua',0,12,'Managua',NULL,'images/20180703/1530602791260_burge-king-icon.png',12,'12',NULL,NULL),(36,'El Jardin','No girl, no smoking','6 6 Calle Noroeste, Managua, Nicaragua',12.1574676,-86.28737209999997,'04:00','07:00','0977724616','0977724614',349,2,'el-jardin','2018-06-27 01:46:50',NULL,'2019-07-10 10:07:50',NULL,NULL,'#managua',0,3000,'Managua',NULL,'images/20180703/1530593667384_eljardin-icon.jpg',300,'30',NULL,NULL),(37,'Piccolo','Delicious is my advantage','Pista de La Solidaridad, Managua, Nicaragua',12.1269205,-86.2383805,'01:00','06:00','01687625699',NULL,380,2,'piccolo','2018-06-29 22:13:58',NULL,'2019-07-10 10:07:51',NULL,NULL,'#managua',0,1000,'Managua',NULL,'images/20180703/1530590022940_piccolo-icon.jpg',100,'10',NULL,NULL),(38,'May Salad','Pho ngon','Edificio Escala, Pista Jean Paul Genie, Managua, Nicaragua',12.1014363,-86.258828,'08:00','22:00','09xx','019xx',448,2,'may-salad','2018-07-08 21:50:57',NULL,'2019-07-10 10:07:38',NULL,'','#managua',1,450,'Managua','','images/20180708/1531068462731_Spaghetty.png',0,'20',NULL,NULL),(40,'Viet Nam Restaurant','Delicious is my advantage','Avenida Cristian Perez, Managua, Nicaragua',12.1404501,-86.2378986,'08:00','22:00',NULL,NULL,451,2,'viet-nam-restaurant','2018-07-08 23:50:37',NULL,'2019-07-10 10:07:38',NULL,NULL,'#managua',0,450,'Managua',NULL,'images/20180709/1531103091439_Spaghetty.png',222,'20',NULL,NULL),(41,'Poccollo','Delicious is my advantage','Pista de La Solidaridad, Managua, Nicaragua',12.1269205,-86.2383805,'01:00','06:00','01687625699',NULL,682,2,'poccollo','2018-07-19 15:48:05',NULL,'2019-07-10 10:07:21',NULL,NULL,'madrid#madrid',0,1000,'Ha Noi','Cau giay',NULL,100,'10','Turn right',1),(42,'Songoku','Delicious is my advantage','Pista de La Solidaridad, Managua, Nicaragua',12.1269205,-86.2383805,'01:00','06:00','01687625699',NULL,685,2,'songoku','2018-07-19 15:49:30',NULL,'2019-07-10 10:07:31',NULL,NULL,'#managua',0,1000,'Managua',NULL,NULL,100,'10',NULL,NULL),(43,'Sanji',NULL,'Avenida Belmonte, Managua, Nicaragua',12.1258151,-86.30771649999997,'06:00','08:00',NULL,NULL,686,2,'sanji','2018-07-23 11:43:32',NULL,'2019-07-10 10:07:31',NULL,NULL,'#managua',0,450,'Managua',NULL,'images/20180723/1532321012348_pizza.jpg',222,'20',NULL,NULL),(44,'Zoro',NULL,'Avenida Belmonte, Managua, Nicaragua',12.1258151,-86.30771649999997,'07:00','09:00',NULL,NULL,698,2,'zoro','2018-07-26 11:13:55',NULL,'2019-07-10 10:07:30',NULL,NULL,'#managua',0,450,'Managua',NULL,'images/20180726/1532578434827_menu-item.png',3444,'20','Go to left',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_category`
--

LOCK TABLES `restaurant_category` WRITE;
/*!40000 ALTER TABLE `restaurant_category` DISABLE KEYS */;
INSERT INTO `restaurant_category` VALUES (84,37,58),(91,18,51),(92,36,58),(97,34,51),(98,34,58),(102,35,52),(103,35,58),(105,25,53),(106,31,54),(107,33,54),(109,26,51),(113,28,51),(118,38,54),(119,38,51),(134,40,58),(137,42,58),(142,43,53),(153,44,53),(168,23,51),(173,24,52),(174,41,61);
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
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant_comment`
--

LOCK TABLES `restaurant_comment` WRITE;
/*!40000 ALTER TABLE `restaurant_comment` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Guest','guest',2,'2018-05-08 22:50:59',NULL,'2019-07-10 10:10:53',NULL),(2,'Admin','admin',2,NULL,NULL,'2019-07-10 10:10:53',NULL),(12,'Owner','owner',2,'2018-07-19 00:01:48',NULL,'2019-07-10 10:10:54',NULL);
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
  KEY `fk_m_a_r_id` (`role_id`),
  KEY `fk_m_a_m_id` (`module_action_id`),
  KEY `fk_m_a_r_m` (`user_restaurant_id`),
  CONSTRAINT `fk_m_a_m_id` FOREIGN KEY (`module_action_id`) REFERENCES `module_action` (`module_action_id`),
  CONSTRAINT `fk_m_a_r_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `fk_m_a_r_m` FOREIGN KEY (`user_restaurant_id`) REFERENCES `user_restaurant` (`user_res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_module`
--

LOCK TABLES `role_module` WRITE;
/*!40000 ALTER TABLE `role_module` DISABLE KEYS */;
INSERT INTO `role_module` VALUES (89,2,3,NULL),(90,2,5,NULL),(91,2,6,NULL),(92,2,7,NULL),(93,2,8,NULL),(94,2,9,NULL),(95,2,1,NULL),(96,2,2,NULL),(97,2,10,NULL),(98,2,11,NULL),(99,2,12,NULL),(100,2,13,NULL),(101,2,14,NULL),(102,2,15,NULL),(103,2,16,NULL),(104,2,17,NULL),(105,2,21,NULL),(106,2,26,NULL),(107,2,27,NULL),(108,2,28,NULL),(109,2,29,NULL),(110,2,30,NULL),(111,2,32,NULL),(112,2,33,NULL),(113,2,34,NULL),(114,2,35,NULL),(115,2,37,NULL),(116,2,38,NULL),(117,2,40,NULL),(118,2,41,NULL),(119,2,42,NULL),(120,2,43,NULL),(121,2,44,NULL),(122,2,46,NULL),(123,2,47,NULL),(124,2,50,NULL),(125,2,51,NULL),(126,2,107,NULL),(127,2,108,NULL),(128,2,109,NULL),(129,2,56,NULL),(130,2,58,NULL),(131,2,59,NULL),(132,2,60,NULL),(133,2,61,NULL),(134,2,62,NULL),(135,2,110,NULL),(136,2,64,NULL),(137,2,65,NULL),(138,2,66,NULL),(139,2,67,NULL),(140,2,68,NULL),(141,2,69,NULL),(142,2,70,NULL),(143,2,71,NULL),(144,2,72,NULL),(145,2,73,NULL),(146,2,74,NULL),(147,2,75,NULL),(148,2,76,NULL),(149,2,77,NULL),(150,2,78,NULL),(151,2,79,NULL),(152,2,80,NULL),(153,2,81,NULL),(154,2,82,NULL),(155,2,83,NULL),(156,2,85,NULL),(157,2,89,NULL),(158,2,90,NULL),(159,2,91,NULL),(160,2,92,NULL),(161,2,93,NULL),(162,2,94,NULL),(163,2,95,NULL),(164,2,96,NULL),(165,2,97,NULL),(166,2,98,NULL),(167,2,99,NULL),(168,2,100,NULL),(169,2,101,NULL),(170,2,102,NULL),(171,2,103,NULL),(172,2,104,NULL),(173,2,105,NULL),(174,2,106,NULL),(199,12,28,NULL),(200,12,29,NULL),(201,12,30,NULL),(202,12,32,NULL),(203,12,33,NULL),(204,12,34,NULL),(205,12,35,NULL),(206,12,37,NULL),(207,12,38,NULL),(208,12,107,NULL),(209,12,108,NULL),(210,12,109,NULL),(211,12,58,NULL),(212,12,60,NULL),(213,12,61,NULL),(214,12,62,NULL),(215,12,110,NULL),(216,12,69,NULL),(217,12,71,NULL),(218,12,96,NULL),(219,12,97,NULL),(220,12,98,NULL),(221,12,99,NULL),(222,12,112,NULL),(223,12,111,NULL),(224,12,110,NULL),(225,12,113,NULL),(226,12,40,NULL),(227,12,114,NULL),(228,12,42,NULL),(229,12,115,NULL),(230,1,5,NULL),(231,1,6,NULL),(232,1,7,NULL),(233,1,8,NULL),(234,1,9,NULL),(235,1,107,NULL),(236,1,102,NULL),(238,1,104,NULL),(239,12,43,NULL),(240,12,41,NULL),(241,1,72,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=276 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (65,'User','user@orderfood.com','01687625699','$2a$04$K/oIhy8Qo4S2pUN3H/dNkOX4pu2R/OM.XWQ7Dt7.URv438Wg/mE/u','$2a$04$K/oIhy8Qo4S2pUN3H/dNkOX4pu2R/OM.XWQ7Dt7.URv438Wg/mE/u','Order 1','2018-05-10 10:05:03',2,'\0',3,'{\"email\":\"user@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"name\":\"User OFO\"}',NULL,NULL,'',1,'2019-07-10 10:10:41',NULL),(66,'Admin','admin@orderfood.com','0987654321','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','Admin','2018-05-10 11:03:52',2,'\0',3,'{\"email\":\"admin@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"fullName\":\"Admin\",\"name\":\"Admin\"}',NULL,NULL,'',1,'2019-07-10 10:10:40',NULL),(67,'Owner','owner@orderfood.com','0977724616','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','$2a$04$NlbzqFhZgLMWVa9fdgbBNuhMiXLzPlOBnUWvZGZwyFJnYZt9A8HF2','Owner','2018-05-10 11:05:48',2,'\0',3,'{\"email\":\"owner@orderfood.com\",\"password\":\"xxxxxxxx\",\"provider\":\"NORMAL\",\"fullName\":\"owner\",\"name\":\"owner\"}',NULL,NULL,'\0',1,'2019-07-10 10:10:40',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_restaurant`
--

LOCK TABLES `user_restaurant` WRITE;
/*!40000 ALTER TABLE `user_restaurant` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8 COMMENT='Danh sach nguoi dung';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (177,67,1),(178,66,1),(179,65,1);
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
INSERT INTO `voucher` VALUES (2,'1-6','1-6','2018-06-19 07:00:00','2018-07-19 07:00:00',1,'string',10,'2018-06-19 23:50:07',NULL,NULL,NULL);
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
INSERT INTO `voucher_lineitem` VALUES (21,1,'370A0D75',2,'2018-06-20 11:08:28',NULL,'2018-06-20 11:08:28',NULL),(22,1,'8EF10579',2,'2018-06-20 11:08:28',NULL,'2018-06-20 11:08:28',NULL),(23,1,'EEA6F6C0',2,'2018-06-20 11:10:17',NULL,'2018-06-20 11:10:17',NULL),(24,1,'BB0C2C31',2,'2018-06-20 11:10:17',NULL,'2018-06-20 11:10:17',NULL),(25,1,'DFB0C4FD',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(26,1,'7C817E7C',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(27,1,'3A1BE501',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(28,1,'E80B8BB3',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(29,1,'D2C55414',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(30,1,'B985675B',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(31,1,'98F3D53F',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(32,1,'55514270',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(33,1,'3A3F95BD',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL),(34,1,'7CAA6EED',2,'2018-06-20 11:11:04',NULL,'2018-06-20 11:11:04',NULL);
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

-- Dump completed on 2019-07-10 14:39:42
