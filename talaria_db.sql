CREATE DATABASE  IF NOT EXISTS `talaria_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `talaria_db`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: talaria_db
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `authentication`
--

DROP TABLE IF EXISTS `authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authentication` (
  `id` int(11) NOT NULL DEFAULT '9',
  `rolename` varchar(45) DEFAULT NULL,
  `purchaseProduct` int(11) DEFAULT NULL,
  `reviewProduct` int(11) DEFAULT NULL,
  `addProduct` int(11) DEFAULT NULL,
  `editProduct` int(11) DEFAULT NULL,
  `deleteProduct` int(11) DEFAULT NULL,
  `viewSales` int(11) DEFAULT NULL,
  `createAccount` int(11) DEFAULT NULL,
  `assignPassword` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_type_idx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authentication`
--

LOCK TABLES `authentication` WRITE;
/*!40000 ALTER TABLE `authentication` DISABLE KEYS */;
INSERT INTO `authentication` VALUES (1,'Customer',1,1,1,0,0,0,0,0),(2,'Administrator',0,0,0,0,0,0,1,1),(3,'Product Manager',0,0,0,1,1,0,0,0),(4,'Accounting Manager',0,0,0,0,0,1,0,0);
/*!40000 ALTER TABLE `authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `idlog` int(11) NOT NULL AUTO_INCREMENT,
  `log_desc` varchar(200) NOT NULL,
  `log_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`idlog`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,' Product list was accessed.','2016-08-29 00:30:47'),(2,'annetok logged into the system.','2016-08-29 00:30:56'),(3,'annetok logged into the system.','2016-08-29 00:31:24'),(4,'annetok logged into the system.','2016-08-29 00:31:31'),(5,' Product list was accessed.','2016-08-29 00:31:32'),(6,'annetok got salt.','2016-08-29 00:31:44'),(7,'annetok changed his/her password.','2016-08-29 00:31:45'),(8,' Product list was accessed.','2016-08-29 00:31:45'),(9,'annetok logged into the system.','2016-08-29 00:31:52'),(10,'annetok logged into the system.','2016-08-29 00:31:59'),(11,' Product list was accessed.','2016-08-29 00:31:59'),(12,'annetok Latest transaction was accessed for display.','2016-08-29 00:32:09'),(13,' Product list was accessed.','2016-08-29 00:32:09'),(14,'johncaingles logged into the system.','2016-08-29 00:33:06'),(15,'annetok logged into the system.','2016-08-29 00:33:31'),(16,' Product list was accessed.','2016-08-29 00:33:31'),(17,'annetok Latest transaction was accessed for display.','2016-08-29 00:33:36'),(18,' Product list was accessed.','2016-08-29 00:33:39'),(19,' Admin created a product manager account; prodmgr was added into the system.','2016-08-29 00:34:04'),(20,' Product list was accessed.','2016-08-29 00:34:04'),(21,' Product list was accessed.','2016-08-29 00:34:10'),(22,'prodmgr logged into the system.','2016-08-29 00:35:12'),(23,' Product list was accessed.','2016-08-29 00:35:12'),(24,'prodmgr got salt.','2016-08-29 00:35:43'),(25,'prodmgr changed his/her password.','2016-08-29 00:35:43'),(26,' Product list was accessed.','2016-08-29 00:35:43'),(27,'prodmgr logged into the system.','2016-08-29 00:38:18'),(28,' Product list was accessed.','2016-08-29 00:38:18'),(29,'prodmgr got salt.','2016-08-29 00:38:34'),(30,'prodmgr changed his/her password.','2016-08-29 00:38:34'),(31,' Product list was accessed.','2016-08-29 00:38:36'),(32,'prodmgr logged into the system.','2016-08-29 00:39:43'),(33,' Product list was accessed.','2016-08-29 00:39:43'),(34,' Product list was accessed.','2016-08-29 00:39:52'),(35,'annetok logged into the system.','2016-08-29 00:40:10'),(36,'annetok logged into the system.','2016-08-29 00:40:19'),(37,' Product list was accessed.','2016-08-29 00:40:19'),(38,'annetok logged into the system.','2016-08-29 00:40:46'),(39,' Product list was accessed.','2016-08-29 00:40:46'),(40,' Admin created an accounting manager account; acctgmgr was added into the system.','2016-08-29 00:41:21'),(41,' Product list was accessed.','2016-08-29 00:41:21'),(42,' Product list was accessed.','2016-08-29 00:41:29'),(43,'acctgmgr logged into the system.','2016-08-29 00:41:55'),(44,' Product list was accessed.','2016-08-29 00:41:55'),(45,' List of total sales per product was displayed.','2016-08-29 00:42:19'),(46,' Product list was accessed and arranged by id.','2016-08-29 00:42:19'),(47,' Product list was accessed and arranged by id.','2016-08-29 00:42:20'),(48,' List of total sales per product was displayed.','2016-08-29 00:53:57'),(49,' Product list was accessed and arranged by id.','2016-08-29 00:53:57'),(50,' Product list was accessed and arranged by id.','2016-08-29 00:53:57'),(51,' Product list was accessed.','2016-08-29 03:04:47'),(52,'kingstonkoa logged into the system.','2016-08-29 03:05:01'),(53,' Product list was accessed.','2016-08-29 03:05:01'),(54,'kingstonkoa Latest transaction was accessed for display.','2016-08-29 03:05:20'),(55,' Product list was accessed.','2016-08-29 03:07:03'),(56,' Product list was accessed.','2016-08-29 03:07:08'),(57,'annetok logged into the system.','2016-08-29 03:07:18'),(58,' Product list was accessed.','2016-08-29 03:07:18'),(59,'annetok Latest transaction was accessed for display.','2016-08-29 03:08:08'),(60,'annetok  attempted to purchase a product - failed: not allowed to do this.','2016-08-29 03:08:13'),(61,' Product list was accessed.','2016-08-29 03:37:59'),(62,' Product list was accessed.','2016-08-29 03:38:11'),(63,'d : Authenticating user - failed.','2016-08-29 03:38:28'),(64,' Product list was accessed.','2016-08-29 03:41:51'),(65,' Product list was accessed.','2016-08-29 03:42:16'),(66,'kingstonkoa logged into the system.','2016-08-29 03:42:27'),(67,'kingstonkoa logged into the system.','2016-08-29 03:42:32'),(68,' Product list was accessed.','2016-08-29 03:42:32'),(69,' Product list was accessed.','2016-08-29 03:43:05'),(70,' Product list was accessed.','2016-08-29 03:43:09'),(71,'annetok logged into the system.','2016-08-29 03:43:18'),(72,' Product list was accessed.','2016-08-29 03:43:19'),(73,' Product list was accessed.','2016-08-29 03:45:14'),(74,' Product list was accessed.','2016-08-29 03:46:12'),(75,'annetok logged into the system.','2016-08-29 03:46:23'),(76,' Product list was accessed.','2016-08-29 03:46:24'),(77,'annetok Latest transaction was accessed for display.','2016-08-29 03:46:27'),(78,'annetok : Authenticating user - success!','2016-08-29 03:46:32');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `prod_id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_type` int(11) NOT NULL,
  `prod_name` varchar(45) NOT NULL,
  `prod_desc` varchar(300) DEFAULT NULL,
  `prod_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `adder_username` varchar(45) NOT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (102,2,'Sapatos ni Jolo','YEEEEEEEE',100.00,'james'),(103,4,'Slipper1','',500.00,'aa'),(104,4,'Tsinelas for Pokemon Go','Water Type daw bulbasaur',500.00,'aa'),(105,3,'Sandals ni Pikachu','sandals po ito',300.00,'c');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prod_id` int(11) NOT NULL,
  `stars` int(11) DEFAULT NULL,
  `details` varchar(500) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,444,5,'ssadsd','sdsasd'),(2,102,3,'Amazeballs shoes, must buy!','g'),(3,102,1,'Joke lang, wag na.','g'),(4,102,5,'AYOS MGA SER','jolosimeon'),(5,102,5,'wow','jolosimeon'),(6,102,5,'luto review','g'),(7,102,2,'mahal ata masyado ser','a');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tlineitem`
--

DROP TABLE IF EXISTS `tlineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tlineitem` (
  `tline_id` int(11) NOT NULL AUTO_INCREMENT,
  `trans_id` int(11) NOT NULL,
  `prod_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  `line_total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`tline_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tlineitem`
--

LOCK TABLES `tlineitem` WRITE;
/*!40000 ALTER TABLE `tlineitem` DISABLE KEYS */;
INSERT INTO `tlineitem` VALUES (1,120,100,5,30.00,150.00),(2,122,101,8,50.00,400.00),(3,122,100,5,30.00,150.00),(4,-1,100,24,30.00,720.00),(5,1,100,12,30.00,360.00),(6,1,101,33,50.00,1650.00),(7,123,101,24,50.00,1200.00),(8,123,102,2,100.00,200.00),(9,124,104,500,500.00,250000.00),(10,126,104,10,500.00,5000.00),(11,127,102,50,100.00,5000.00),(12,128,102,12,100.00,1200.00),(13,129,102,4,100.00,400.00);
/*!40000 ALTER TABLE `tlineitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `trans_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`trans_id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'a',0.00),(120,'james',150.00),(122,'james',550.00),(123,'a',1400.00),(124,'b',250000.00),(125,'c',0.00),(126,'g',5000.00),(127,'g',5000.00),(128,'jolosimeon',1200.00),(129,'g',400.00);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'Boots'),(2,'Shoes'),(3,'Sandals'),(4,'Slippers');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_type` int(11) NOT NULL,
  `fname` varchar(45) DEFAULT NULL,
  `minitial` varchar(4) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `billing_addr` varchar(300) DEFAULT NULL,
  `shipping_addr` varchar(300) DEFAULT NULL,
  `card_no` varchar(45) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `temppw_status` int(11) NOT NULL DEFAULT '0',
  `temppw_timestamp` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `user_role_idx` (`user_type`),
  CONSTRAINT `user_role` FOREIGN KEY (`user_type`) REFERENCES `log` (`idlog`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a','b',4,'a','a','a','a@y.com','a a, a, a, a a','a a, a, a, a a','122222222222222222222',NULL,0,NULL),('aa','aa',3,'a','a','a','aa',NULL,NULL,NULL,NULL,0,NULL),('acc','acc',3,'acc','acc','cc','acc',NULL,NULL,NULL,NULL,0,NULL),('acctgmgr','5MHB17aAFtWOP0uC63Taj46DVSonPzZlT6fUeugwrHQ=',4,'accounting','m','anager','a@y.com',NULL,NULL,NULL,'X7N5GS8ePpHIoPA+4iUwfQ==',1,'2016-08-29 00:46:20.472000'),('annetok','8trd2fIIoOaiOR/m8/kZr2ky1JuBKmJWA5AYjBGFSi4=',2,'Anne','U','Bagamaspad','amb@gmail.com','42 P Sherman St, Wallaby, Sydney, 1700 Australia','42 P Sherman St., Walla, Sydney, 1700 Australia','82910361891','XoQUXIOHDPVdrVDhDjzh7w==',0,NULL),('b','b',2,'b','b','b','b@y.c','b b, b, b, b b','b b, b, b, b b','333333333333333333',NULL,0,NULL),('c','c',3,'c','c','c','c@y.c',NULL,NULL,NULL,NULL,0,NULL),('d','d',3,'d','d','d','d@g.c',NULL,NULL,NULL,NULL,0,NULL),('e','e',4,'a','a','a','e',NULL,NULL,NULL,NULL,0,NULL),('g','g',1,'g','g','g','g@y.c','g g, g, g, g g','g g, g, g, g g','123455555555555555',NULL,0,NULL),('james','abc123',3,'James','K','Sy','james_sy@yahoo.com',NULL,NULL,NULL,NULL,0,NULL),('johncaingles','F+XrfHp9KvMqZJhQ4+cnCzmpHP9VETRRNCnWJPfyl8g=',4,'John','D.','Caingles','jc@gmail.coom','1 bjkbj, kbjkbjk, bjkb, jkb jkb','jkb jk, bjk, bjkbj, kbjk bjk','bjk','spvEyP7nr8sG13zn+rvwdw==',0,NULL),('jolosimeon','abc123',1,'Juan Lorenzo','L','Simeon','jolo_simoen@gmail.com','85 blah st.','85 blah st.','1234567891234567',NULL,0,NULL),('kingstonkoa','SmES156PlRqVo2ZJh34tLtfWc6NHR57o7BjF1V+jOeo=',1,'Kingston','T.','Koa','kingstonkoa@gmail.com','87 S. Tuano, wla, San Juan, 1500 Philippines','87 S. Tuano, wla, San Juan, 1500 Philippines','165156156156156156156','MxnmA4W2v5AgScftn+pSIQ==',0,NULL),('p','p',3,'Prod','p','p','p',NULL,NULL,NULL,NULL,0,NULL),('prodmgr','dNj7XXQy5c01B5lui1jFVdoF7Os0dfOHjjBoz0wAf5I=',3,'prod','m','anager','p@y.com',NULL,NULL,NULL,'y8sOn975MMj2pynedpX62w==',0,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-29 11:51:57
