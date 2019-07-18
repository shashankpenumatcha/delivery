-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: delivery
-- ------------------------------------------------------
-- Server version	5.7.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `backlog`
--

DROP TABLE IF EXISTS `backlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `backlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inventory_log_id` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_backlog_inventory_log_id` (`inventory_log_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `backlog`
--

LOCK TABLES `backlog` WRITE;
/*!40000 ALTER TABLE `backlog` DISABLE KEYS */;
INSERT INTO `backlog` VALUES (14,4,'2018-10-05 21:56:12',40),(13,2,'2018-10-05 21:50:52',300);
/*!40000 ALTER TABLE `backlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_updated` datetime,
  `user_profile_id` bigint(20) DEFAULT NULL,
  `vendor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_cart_user_profile_id` (`user_profile_id`),
  KEY `fk_cart_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cart_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `cart_id` bigint(20) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_items_product_id` (`product_id`),
  KEY `fk_cart_items_cart_id` (`cart_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2018-10-05 21:23:11',1,'EXECUTED','7:4afb46bcf498cc2fd904f4b91e294183','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151810-1','jhipster','config/liquibase/changelog/20181005151810_added_entity_OrderTracker.xml','2018-10-05 21:23:11',2,'EXECUTED','7:728102d4dbaf571a05690764b916ed75','createTable tableName=order_tracker; dropDefaultValue columnName=date_time, tableName=order_tracker','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151811-1','jhipster','config/liquibase/changelog/20181005151811_added_entity_UserProfile.xml','2018-10-05 21:23:12',3,'EXECUTED','7:3151c4783163c6049ac37b4d93cedc18','createTable tableName=user_profile','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151812-1','jhipster','config/liquibase/changelog/20181005151812_added_entity_Product.xml','2018-10-05 21:23:12',4,'EXECUTED','7:d2eb7564b1f4b1a813d9b703b7089ac6','createTable tableName=product','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151813-1','jhipster','config/liquibase/changelog/20181005151813_added_entity_Unit.xml','2018-10-05 21:23:12',5,'EXECUTED','7:a67d5c2b1fbeec71d5ff1038195dbae8','createTable tableName=unit','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151814-1','jhipster','config/liquibase/changelog/20181005151814_added_entity_OrderStatus.xml','2018-10-05 21:23:12',6,'EXECUTED','7:c8cdecabbb15b2b14ae889cdf56c9a69','createTable tableName=order_status','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151815-1','jhipster','config/liquibase/changelog/20181005151815_added_entity_InventoryLog.xml','2018-10-05 21:23:12',7,'EXECUTED','7:8b377081c76776099ed74c73b290d020','createTable tableName=inventory_log; dropDefaultValue columnName=jhi_date, tableName=inventory_log','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151816-1','jhipster','config/liquibase/changelog/20181005151816_added_entity_Cart.xml','2018-10-05 21:23:12',8,'EXECUTED','7:1de4e8fa217d9069e26aa409918c7027','createTable tableName=cart; dropDefaultValue columnName=last_updated, tableName=cart','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151817-1','jhipster','config/liquibase/changelog/20181005151817_added_entity_CartItems.xml','2018-10-05 21:23:12',9,'EXECUTED','7:a2659187697926ce70986af4e55db18b','createTable tableName=cart_items','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151818-1','jhipster','config/liquibase/changelog/20181005151818_added_entity_OrderList.xml','2018-10-05 21:23:13',10,'EXECUTED','7:893d955bfc2bc2c160490676742b53d3','createTable tableName=order_list; dropDefaultValue columnName=last_updated, tableName=order_list','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151819-1','jhipster','config/liquibase/changelog/20181005151819_added_entity_OrderItems.xml','2018-10-05 21:23:13',11,'EXECUTED','7:514f3af245e0be80d464f4525c0f891e','createTable tableName=order_items','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151810-2','jhipster','config/liquibase/changelog/20181005151810_added_entity_constraints_OrderTracker.xml','2018-10-05 21:23:13',12,'EXECUTED','7:247ab67e6c75092f5b8e50d467b138cf','addForeignKeyConstraint baseTableName=order_tracker, constraintName=fk_order_tracker_order_list_id, referencedTableName=order_list; addForeignKeyConstraint baseTableName=order_tracker, constraintName=fk_order_tracker_order_status_id, referencedTab...','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151811-2','jhipster','config/liquibase/changelog/20181005151811_added_entity_constraints_UserProfile.xml','2018-10-05 21:23:13',13,'EXECUTED','7:e6849276b3b5d155cf88ed5d83e49116','addForeignKeyConstraint baseTableName=user_profile, constraintName=fk_user_profile_user_id, referencedTableName=jhi_user','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151812-2','jhipster','config/liquibase/changelog/20181005151812_added_entity_constraints_Product.xml','2018-10-05 21:23:13',14,'EXECUTED','7:2372861713029fb512e220423f820460','addForeignKeyConstraint baseTableName=product, constraintName=fk_product_unit_id, referencedTableName=unit','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151815-2','jhipster','config/liquibase/changelog/20181005151815_added_entity_constraints_InventoryLog.xml','2018-10-05 21:23:13',15,'EXECUTED','7:fa28e2da8825098a6e17c5d65c68e66e','addForeignKeyConstraint baseTableName=inventory_log, constraintName=fk_inventory_log_product_id, referencedTableName=product; addForeignKeyConstraint baseTableName=inventory_log, constraintName=fk_inventory_log_user_profile_id, referencedTableName...','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151816-2','jhipster','config/liquibase/changelog/20181005151816_added_entity_constraints_Cart.xml','2018-10-05 21:23:13',16,'EXECUTED','7:c347fe0081a6f760e08f67665bbb97be','addForeignKeyConstraint baseTableName=cart, constraintName=fk_cart_user_profile_id, referencedTableName=user_profile','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151817-2','jhipster','config/liquibase/changelog/20181005151817_added_entity_constraints_CartItems.xml','2018-10-05 21:23:14',17,'EXECUTED','7:835ec44c258d8f842ac44888362efdb6','addForeignKeyConstraint baseTableName=cart_items, constraintName=fk_cart_items_product_id, referencedTableName=product; addForeignKeyConstraint baseTableName=cart_items, constraintName=fk_cart_items_cart_id, referencedTableName=cart','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151818-2','jhipster','config/liquibase/changelog/20181005151818_added_entity_constraints_OrderList.xml','2018-10-05 21:23:14',18,'EXECUTED','7:417f1a3dac115b9b33c2977d3a51571f','addForeignKeyConstraint baseTableName=order_list, constraintName=fk_order_list_order_status_id, referencedTableName=order_status; addForeignKeyConstraint baseTableName=order_list, constraintName=fk_order_list_user_profile_id, referencedTableName=u...','',NULL,'3.5.4',NULL,NULL,'8754790710'),('20181005151819-2','jhipster','config/liquibase/changelog/20181005151819_added_entity_constraints_OrderItems.xml','2018-10-05 21:23:14',19,'EXECUTED','7:c9e28e0d4cd2515cc0d8c093eb64ec42','addForeignKeyConstraint baseTableName=order_items, constraintName=fk_order_items_product_id, referencedTableName=product; addForeignKeyConstraint baseTableName=order_items, constraintName=fk_order_items_order_list_id, referencedTableName=order_list','',NULL,'3.5.4',NULL,NULL,'8754790710');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fcm_token`
--

DROP TABLE IF EXISTS `fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fcm_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_profile_id` bigint(20) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_fcm_token_user_profile_id` (`user_profile_id`)
) ENGINE=MyISAM AUTO_INCREMENT=144 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcm_token`
--

LOCK TABLES `fcm_token` WRITE;
/*!40000 ALTER TABLE `fcm_token` DISABLE KEYS */;
INSERT INTO `fcm_token` VALUES (140,2,'ffTq9cO2vSY:APA91bEPDlYmtHtlIu_8a6lq7kLTDtgbnue1LpeOPo-1RlGFrUEXxBbfOCjBjEiUAiTt1LLO8MTYy6HklY16eHgPblXRRFiy6XWRcSz-zVIn0GnClpTaBhxCvMM4BBvJV8RkY5hqhMC2'),(141,8,'e5pqHTqTmpY:APA91bGSFkTq7ZaJD_9QURjs_isPlhMebmJLzTtVtGiYnl_2ELIuFKHwWfcJIN-pZGWmCTJL5lKC93RymX4E7AiDRQmvZjLhWH5T4LD08ybsUZeAGXp0hcraL1Tl_zsWyo-yVsx4mK8b'),(143,2,'fM6HWHytARo:APA91bGV4ugadPvK4vZfYvt_SerVisuyI1WBMOuiYkkYPtW1d9bjukts0YD43GwWroSsnx2aMILRWwJthvamI_MXMd-Gfej6MGpgfNzv61O_Ajje9uJClt3_1e8QW-KO8b6d5NsQ7oI7');
/*!40000 ALTER TABLE `fcm_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_log`
--

DROP TABLE IF EXISTS `inventory_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jhi_date` datetime,
  `added` bit(1) DEFAULT NULL,
  `removed` bit(1) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  `current_total` float DEFAULT NULL,
  `order_list_id` bigint(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `backlog_id` bigint(20) DEFAULT NULL,
  `vendor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_inventory_log_backlog_id` (`backlog_id`),
  KEY `fk_inventory_log_product_id` (`product_id`),
  KEY `fk_inventory_log_user_profile_id` (`user_profile_id`),
  KEY `fk_order_list_id` (`order_list_id`),
  KEY `fk_inventory_log_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_log`
--

LOCK TABLES `inventory_log` WRITE;
/*!40000 ALTER TABLE `inventory_log` DISABLE KEYS */;
INSERT INTO `inventory_log` VALUES (1,'2018-11-16 00:34:02',NULL,_binary '',5,1,2,4966,1,NULL,NULL,NULL),(2,'2019-07-19 00:37:42',NULL,_binary '',5,1,2,4961,2,NULL,NULL,NULL),(3,'2019-07-19 00:37:42',NULL,_binary '',2,2,2,336,2,NULL,NULL,NULL),(4,'2019-07-19 00:38:34',NULL,_binary '',5,1,8,4956,3,NULL,NULL,NULL),(5,'2019-07-19 00:41:07',NULL,_binary '',2,2,8,334,4,NULL,NULL,NULL);
/*!40000 ALTER TABLE `inventory_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES (''),('ROLE_ADMIN'),('ROLE_USER'),('ROLE_VENDOR');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=MyISAM AUTO_INCREMENT=342 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_event`
--

LOCK TABLES `jhi_persistent_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_event` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_event` VALUES (1,'admin','2018-10-05 15:55:52','AUTHENTICATION_SUCCESS'),(2,'admin','2018-10-05 18:26:41','AUTHENTICATION_SUCCESS'),(3,'admin','2018-10-06 06:54:59','AUTHENTICATION_SUCCESS'),(4,'admin','2018-10-06 06:55:00','AUTHENTICATION_SUCCESS'),(5,'admin','2018-10-06 09:03:55','AUTHENTICATION_SUCCESS'),(6,'admin','2018-10-06 10:40:42','AUTHENTICATION_SUCCESS'),(7,'admin','2018-10-15 22:32:59','AUTHENTICATION_SUCCESS'),(8,'admin','2018-10-16 16:38:14','AUTHENTICATION_SUCCESS'),(9,'admin','2018-10-16 19:39:45','AUTHENTICATION_SUCCESS'),(10,'admin','2018-10-16 22:03:15','AUTHENTICATION_SUCCESS'),(11,'admin','2018-10-16 22:03:17','AUTHENTICATION_SUCCESS'),(12,'admin','2018-10-17 16:09:36','AUTHENTICATION_SUCCESS'),(13,'admin','2018-10-17 18:40:32','AUTHENTICATION_SUCCESS'),(14,'admin','2018-10-17 18:55:40','AUTHENTICATION_SUCCESS'),(15,'admin','2018-10-18 15:23:32','AUTHENTICATION_SUCCESS'),(16,'admin','2018-10-18 15:23:32','AUTHENTICATION_SUCCESS'),(17,'admin','2018-10-18 15:23:32','AUTHENTICATION_SUCCESS'),(18,'admin','2018-10-18 15:23:32','AUTHENTICATION_SUCCESS'),(19,'admin','2018-10-18 15:23:32','AUTHENTICATION_SUCCESS'),(20,'admin','2018-10-18 15:23:33','AUTHENTICATION_SUCCESS'),(21,'admin','2018-10-18 15:23:37','AUTHENTICATION_SUCCESS'),(22,'admin','2018-10-18 15:57:24','AUTHENTICATION_SUCCESS'),(23,'admin','2018-10-18 16:04:33','AUTHENTICATION_SUCCESS'),(24,'admin','2018-10-18 16:20:47','AUTHENTICATION_SUCCESS'),(25,'admin','2018-10-18 17:14:25','AUTHENTICATION_SUCCESS'),(26,'admin','2018-10-18 17:21:27','AUTHENTICATION_SUCCESS'),(27,'admin','2018-10-18 17:21:50','AUTHENTICATION_SUCCESS'),(28,'admin','2018-10-18 18:28:36','AUTHENTICATION_SUCCESS'),(29,'admin','2018-10-18 18:31:55','AUTHENTICATION_SUCCESS'),(30,'admin','2018-10-18 18:47:55','AUTHENTICATION_SUCCESS'),(31,'admin','2018-10-19 16:11:39','AUTHENTICATION_SUCCESS'),(32,'admin','2018-10-19 20:28:29','AUTHENTICATION_SUCCESS'),(33,'admin','2018-10-19 20:28:29','AUTHENTICATION_SUCCESS'),(34,'admin','2018-10-19 20:33:02','AUTHENTICATION_SUCCESS'),(35,'admin','2018-10-20 16:36:31','AUTHENTICATION_SUCCESS'),(36,'admin','2018-10-20 16:36:32','AUTHENTICATION_SUCCESS'),(37,'admin','2018-10-21 13:42:37','AUTHENTICATION_SUCCESS'),(38,'admin','2018-10-21 18:50:15','AUTHENTICATION_SUCCESS'),(39,'admin','2018-10-21 18:50:16','AUTHENTICATION_SUCCESS'),(40,'user','2018-10-21 18:50:50','AUTHENTICATION_SUCCESS'),(41,'admin','2018-10-21 18:51:00','AUTHENTICATION_FAILURE'),(42,'admin','2018-10-21 18:51:05','AUTHENTICATION_SUCCESS'),(43,'us','2018-10-21 18:51:18','AUTHENTICATION_FAILURE'),(44,'user','2018-10-21 18:51:26','AUTHENTICATION_SUCCESS'),(45,'admin','2018-10-21 20:17:53','AUTHENTICATION_SUCCESS'),(46,'admin','2018-10-21 20:19:38','AUTHENTICATION_SUCCESS'),(47,'user','2018-10-22 15:22:36','AUTHENTICATION_SUCCESS'),(48,'admin','2018-10-22 15:31:12','AUTHENTICATION_SUCCESS'),(49,'admin','2018-10-22 16:37:56','AUTHENTICATION_SUCCESS'),(50,'admin','2018-10-22 16:39:59','AUTHENTICATION_SUCCESS'),(51,'admin','2018-10-22 18:12:19','AUTHENTICATION_SUCCESS'),(52,'admin','2018-10-22 18:48:10','AUTHENTICATION_SUCCESS'),(53,'admin','2018-10-22 19:01:23','AUTHENTICATION_SUCCESS'),(54,'admin','2018-10-22 21:31:26','AUTHENTICATION_SUCCESS'),(55,'admin','2018-10-22 22:04:52','AUTHENTICATION_SUCCESS'),(56,'admin','2018-10-22 22:06:21','AUTHENTICATION_SUCCESS'),(57,'admin','2018-10-22 22:08:37','AUTHENTICATION_SUCCESS'),(58,'admin','2018-10-23 07:44:08','AUTHENTICATION_SUCCESS'),(59,'admin','2018-10-23 08:01:41','AUTHENTICATION_SUCCESS'),(60,'admin','2018-10-23 08:24:16','AUTHENTICATION_SUCCESS'),(61,'admin','2018-10-23 08:34:03','AUTHENTICATION_SUCCESS'),(62,'admin','2018-10-23 09:46:47','AUTHENTICATION_SUCCESS'),(63,'admin','2018-10-23 11:44:07','AUTHENTICATION_SUCCESS'),(64,'admin','2018-10-23 12:04:00','AUTHENTICATION_SUCCESS'),(65,'admin','2018-10-23 12:42:02','AUTHENTICATION_SUCCESS'),(66,'admin','2018-10-23 16:38:22','AUTHENTICATION_SUCCESS'),(67,'admin','2018-10-23 17:05:15','AUTHENTICATION_SUCCESS'),(68,'admin','2018-10-23 17:08:35','AUTHENTICATION_SUCCESS'),(69,'admin','2018-10-23 17:18:52','AUTHENTICATION_SUCCESS'),(70,'admin','2018-10-23 18:16:24','AUTHENTICATION_SUCCESS'),(71,'admin','2018-10-23 18:52:48','AUTHENTICATION_SUCCESS'),(72,'admin','2018-10-23 18:52:49','AUTHENTICATION_SUCCESS'),(73,'admin','2018-10-23 21:32:49','AUTHENTICATION_SUCCESS'),(74,'admin','2018-10-23 21:48:58','AUTHENTICATION_SUCCESS'),(75,'admin','2018-10-23 21:55:22','AUTHENTICATION_SUCCESS'),(76,'admin','2018-10-24 11:31:48','AUTHENTICATION_SUCCESS'),(77,'admin','2018-10-24 11:39:28','AUTHENTICATION_SUCCESS'),(78,'admin','2018-10-24 11:47:52','AUTHENTICATION_SUCCESS'),(79,'admin','2018-10-24 12:03:28','AUTHENTICATION_SUCCESS'),(80,'admin','2018-10-24 12:11:35','AUTHENTICATION_SUCCESS'),(81,'admin','2018-10-24 12:17:24','AUTHENTICATION_SUCCESS'),(82,'admin','2018-10-24 12:27:07','AUTHENTICATION_SUCCESS'),(83,'admin','2018-10-24 12:29:00','AUTHENTICATION_SUCCESS'),(84,'admin','2018-10-24 13:11:52','AUTHENTICATION_SUCCESS'),(85,'admin','2018-10-24 13:53:43','AUTHENTICATION_SUCCESS'),(86,'admin','2018-10-24 14:55:56','AUTHENTICATION_SUCCESS'),(87,'admin','2018-10-27 09:10:53','AUTHENTICATION_SUCCESS'),(88,'admin','2018-10-27 09:10:55','AUTHENTICATION_SUCCESS'),(89,'admin','2018-10-27 09:25:35','AUTHENTICATION_SUCCESS'),(90,'admin','2018-10-27 09:43:43','AUTHENTICATION_SUCCESS'),(91,'admin','2018-10-27 10:44:01','AUTHENTICATION_SUCCESS'),(92,'admin','2018-10-27 11:24:07','AUTHENTICATION_SUCCESS'),(93,'admin','2018-10-27 16:11:25','AUTHENTICATION_SUCCESS'),(94,'admin','2018-10-27 16:11:26','AUTHENTICATION_SUCCESS'),(95,'admin','2018-10-27 16:49:46','AUTHENTICATION_SUCCESS'),(96,'admin','2018-10-28 13:29:08','AUTHENTICATION_SUCCESS'),(97,'admin','2018-10-28 13:29:08','AUTHENTICATION_SUCCESS'),(98,'admin','2018-10-28 14:29:27','AUTHENTICATION_SUCCESS'),(99,'admin','2018-10-28 16:38:41','AUTHENTICATION_SUCCESS'),(100,'admin','2018-10-28 19:17:38','AUTHENTICATION_SUCCESS'),(101,'admin','2018-10-28 19:22:10','AUTHENTICATION_SUCCESS'),(102,'admin','2018-10-28 21:08:45','AUTHENTICATION_SUCCESS'),(103,'admin','2018-10-28 21:08:45','AUTHENTICATION_SUCCESS'),(104,'admin','2018-10-28 22:02:52','AUTHENTICATION_SUCCESS'),(105,'admin','2018-10-29 18:37:51','AUTHENTICATION_SUCCESS'),(106,'admin','2018-10-29 18:54:04','AUTHENTICATION_SUCCESS'),(107,'admin','2018-10-29 18:56:56','AUTHENTICATION_SUCCESS'),(108,'admin','2018-10-29 19:40:18','AUTHENTICATION_SUCCESS'),(109,'admin','2018-10-30 18:05:25','AUTHENTICATION_SUCCESS'),(110,'user','2018-10-30 21:08:57','AUTHENTICATION_SUCCESS'),(111,'admin','2018-10-30 21:11:03','AUTHENTICATION_SUCCESS'),(112,'admin','2018-10-30 21:11:36','AUTHENTICATION_SUCCESS'),(113,'admin','2018-10-31 18:56:14','AUTHENTICATION_SUCCESS'),(114,'admin','2018-10-31 18:56:14','AUTHENTICATION_SUCCESS'),(115,'admin','2018-10-31 18:56:15','AUTHENTICATION_SUCCESS'),(116,'admin','2018-10-31 18:56:18','AUTHENTICATION_SUCCESS'),(117,'admin','2018-11-01 17:55:39','AUTHENTICATION_SUCCESS'),(118,'admin','2018-11-01 17:55:39','AUTHENTICATION_SUCCESS'),(119,'admin','2018-11-01 18:19:36','AUTHENTICATION_SUCCESS'),(120,'admin','2018-11-01 18:19:36','AUTHENTICATION_SUCCESS'),(121,'admin','2018-11-01 21:28:10','AUTHENTICATION_SUCCESS'),(122,'admin','2018-11-02 18:53:50','AUTHENTICATION_SUCCESS'),(123,'shashank','2018-11-02 19:48:39','AUTHENTICATION_SUCCESS'),(124,'admin','2018-11-02 19:49:30','AUTHENTICATION_SUCCESS'),(125,'admin','2018-11-02 21:47:52','AUTHENTICATION_SUCCESS'),(126,'admin','2018-11-02 21:47:53','AUTHENTICATION_SUCCESS'),(127,'admin','2018-11-03 11:03:46','AUTHENTICATION_SUCCESS'),(128,'admin','2018-11-03 11:34:27','AUTHENTICATION_SUCCESS'),(129,'admin','2018-11-03 11:39:29','AUTHENTICATION_SUCCESS'),(130,'shashank','2018-11-03 12:08:00','AUTHENTICATION_FAILURE'),(131,'shashank','2018-11-03 12:08:07','AUTHENTICATION_SUCCESS'),(132,'shashank','2018-11-03 12:10:41','AUTHENTICATION_SUCCESS'),(133,'admin','2018-11-03 12:11:02','AUTHENTICATION_SUCCESS'),(134,'shashank','2018-11-03 12:11:28','AUTHENTICATION_SUCCESS'),(135,'admin','2018-11-03 12:13:03','AUTHENTICATION_SUCCESS'),(136,'admin','2018-11-03 12:13:14','AUTHENTICATION_SUCCESS'),(137,'shashank','2018-11-03 12:13:30','AUTHENTICATION_SUCCESS'),(138,'admin','2018-11-03 12:31:35','AUTHENTICATION_SUCCESS'),(139,'shashank','2018-11-03 12:31:49','AUTHENTICATION_SUCCESS'),(140,'shashank','2018-11-03 12:38:56','AUTHENTICATION_SUCCESS'),(141,'admin','2018-11-03 12:39:19','AUTHENTICATION_SUCCESS'),(142,'shashank','2018-11-03 12:39:31','AUTHENTICATION_SUCCESS'),(143,'shashank','2018-11-03 12:39:58','AUTHENTICATION_SUCCESS'),(144,'admin','2018-11-03 12:40:21','AUTHENTICATION_SUCCESS'),(145,'shashank','2018-11-03 12:40:44','AUTHENTICATION_SUCCESS'),(146,'shashank','2018-11-03 12:42:59','AUTHENTICATION_SUCCESS'),(147,'admin','2018-11-03 12:43:15','AUTHENTICATION_SUCCESS'),(148,'shashank','2018-11-03 12:43:29','AUTHENTICATION_SUCCESS'),(149,'shashank','2018-11-03 12:44:17','AUTHENTICATION_SUCCESS'),(150,'admin','2018-11-03 12:48:14','AUTHENTICATION_SUCCESS'),(151,'shashank','2018-11-03 12:48:28','AUTHENTICATION_SUCCESS'),(152,'admin','2018-11-03 12:52:41','AUTHENTICATION_SUCCESS'),(153,'admin','2018-11-03 12:53:17','AUTHENTICATION_SUCCESS'),(154,'shashank','2018-11-03 12:53:35','AUTHENTICATION_SUCCESS'),(155,'admin','2018-11-03 12:53:55','AUTHENTICATION_SUCCESS'),(156,'shashank','2018-11-03 12:54:06','AUTHENTICATION_SUCCESS'),(157,'admin','2018-11-03 12:55:36','AUTHENTICATION_SUCCESS'),(158,'shashank','2018-11-03 12:57:19','AUTHENTICATION_FAILURE'),(159,'shashank','2018-11-03 12:57:24','AUTHENTICATION_SUCCESS'),(160,'admin','2018-11-03 12:59:05','AUTHENTICATION_SUCCESS'),(161,'shashank','2018-11-03 12:59:22','AUTHENTICATION_SUCCESS'),(162,'admin','2018-11-03 13:11:13','AUTHENTICATION_SUCCESS'),(163,'shashank','2018-11-03 13:13:27','AUTHENTICATION_FAILURE'),(164,'shashank','2018-11-03 13:13:32','AUTHENTICATION_SUCCESS'),(165,'admin','2018-11-03 13:20:38','AUTHENTICATION_SUCCESS'),(166,'admin','2018-11-03 13:21:39','AUTHENTICATION_SUCCESS'),(167,'admin','2018-11-03 13:29:14','AUTHENTICATION_SUCCESS'),(168,'admin','2018-11-03 13:40:36','AUTHENTICATION_SUCCESS'),(169,'shashank','2018-11-03 13:41:16','AUTHENTICATION_SUCCESS'),(170,'shashank','2018-11-03 13:41:32','AUTHENTICATION_SUCCESS'),(171,'admin','2018-11-03 13:41:40','AUTHENTICATION_SUCCESS'),(172,'shashank','2018-11-03 13:41:54','AUTHENTICATION_SUCCESS'),(173,'shashank','2018-11-03 14:12:38','AUTHENTICATION_SUCCESS'),(174,'shashank','2018-11-03 17:17:45','AUTHENTICATION_SUCCESS'),(175,'shashank','2018-11-03 17:32:23','AUTHENTICATION_SUCCESS'),(176,'shashank','2018-11-03 18:18:54','AUTHENTICATION_SUCCESS'),(177,'admin','2018-11-03 18:45:09','AUTHENTICATION_SUCCESS'),(178,'admin','2018-11-03 20:15:56','AUTHENTICATION_SUCCESS'),(179,'admin','2018-11-03 20:36:14','AUTHENTICATION_SUCCESS'),(180,'admin','2018-11-04 14:29:40','AUTHENTICATION_SUCCESS'),(181,'superman','2018-11-04 14:30:43','AUTHENTICATION_SUCCESS'),(182,'admin','2018-11-04 14:35:07','AUTHENTICATION_SUCCESS'),(183,'admin','2018-11-04 14:56:42','AUTHENTICATION_SUCCESS'),(184,'admin','2018-11-04 16:38:42','AUTHENTICATION_SUCCESS'),(185,'admin','2018-11-05 19:37:03','AUTHENTICATION_SUCCESS'),(186,'admin','2018-11-05 19:40:13','AUTHENTICATION_SUCCESS'),(187,'admin','2018-11-05 20:27:35','AUTHENTICATION_SUCCESS'),(188,'admin','2018-11-05 20:46:23','AUTHENTICATION_SUCCESS'),(189,'admin','2018-11-05 21:50:44','AUTHENTICATION_SUCCESS'),(190,'admin','2018-11-06 15:25:47','AUTHENTICATION_SUCCESS'),(191,'admin','2018-11-06 16:10:47','AUTHENTICATION_SUCCESS'),(192,'admin','2018-11-06 17:17:29','AUTHENTICATION_SUCCESS'),(193,'admin','2018-11-06 18:18:15','AUTHENTICATION_SUCCESS'),(194,'admin','2018-11-06 18:21:46','AUTHENTICATION_SUCCESS'),(195,'admin','2018-11-06 18:22:22','AUTHENTICATION_SUCCESS'),(196,'admin','2018-11-06 19:21:37','AUTHENTICATION_SUCCESS'),(197,'admin','2018-11-06 19:24:15','AUTHENTICATION_SUCCESS'),(198,'user','2018-11-06 20:11:11','AUTHENTICATION_SUCCESS'),(199,'admin','2018-11-06 20:19:54','AUTHENTICATION_SUCCESS'),(200,'admin','2018-11-06 20:20:19','AUTHENTICATION_SUCCESS'),(201,'admin','2018-11-06 20:24:38','AUTHENTICATION_SUCCESS'),(202,'admin','2018-11-06 20:25:15','AUTHENTICATION_SUCCESS'),(203,'admin','2018-11-06 20:25:44','AUTHENTICATION_SUCCESS'),(204,'user','2018-11-06 20:25:44','AUTHENTICATION_SUCCESS'),(205,'admin','2018-11-06 20:39:19','AUTHENTICATION_SUCCESS'),(206,'admin','2018-11-06 20:39:19','AUTHENTICATION_SUCCESS'),(207,'user','2018-11-06 20:40:28','AUTHENTICATION_SUCCESS'),(208,'admin','2018-11-06 20:41:57','AUTHENTICATION_SUCCESS'),(209,'user','2018-11-06 20:42:29','AUTHENTICATION_SUCCESS'),(210,'admin','2018-11-06 20:54:19','AUTHENTICATION_SUCCESS'),(211,'user','2018-11-06 20:56:35','AUTHENTICATION_SUCCESS'),(212,'user','2018-11-06 20:58:55','AUTHENTICATION_SUCCESS'),(213,'admin','2018-11-06 21:06:33','AUTHENTICATION_SUCCESS'),(214,'user','2018-11-06 21:07:11','AUTHENTICATION_SUCCESS'),(215,'admin','2018-11-06 21:11:30','AUTHENTICATION_SUCCESS'),(216,'user','2018-11-06 21:11:50','AUTHENTICATION_SUCCESS'),(217,'admin','2018-11-06 21:40:59','AUTHENTICATION_SUCCESS'),(218,'user','2018-11-06 22:03:28','AUTHENTICATION_SUCCESS'),(219,'admin','2018-11-06 22:04:21','AUTHENTICATION_SUCCESS'),(220,'user','2018-11-06 22:04:47','AUTHENTICATION_SUCCESS'),(221,'user','2018-11-06 23:24:47','AUTHENTICATION_SUCCESS'),(222,'admin','2018-11-06 23:25:31','AUTHENTICATION_SUCCESS'),(223,'admin','2018-11-07 18:56:01','AUTHENTICATION_SUCCESS'),(224,'admin','2018-11-07 18:56:02','AUTHENTICATION_SUCCESS'),(225,'user','2018-11-07 19:05:27','AUTHENTICATION_SUCCESS'),(226,'admin','2018-11-07 19:11:11','AUTHENTICATION_SUCCESS'),(227,'user','2018-11-07 19:11:39','AUTHENTICATION_SUCCESS'),(228,'user','2018-11-07 19:13:01','AUTHENTICATION_SUCCESS'),(229,'admin','2018-11-07 19:14:32','AUTHENTICATION_SUCCESS'),(230,'admin','2018-11-07 19:16:09','AUTHENTICATION_SUCCESS'),(231,'admin','2018-11-07 19:21:13','AUTHENTICATION_SUCCESS'),(232,'admin','2018-11-07 19:22:12','AUTHENTICATION_SUCCESS'),(233,'admin','2018-11-07 19:24:21','AUTHENTICATION_SUCCESS'),(234,'admin','2018-11-07 19:27:18','AUTHENTICATION_SUCCESS'),(235,'admin','2018-11-07 19:28:54','AUTHENTICATION_SUCCESS'),(236,'admin','2018-11-07 19:29:51','AUTHENTICATION_SUCCESS'),(237,'admin','2018-11-07 19:30:37','AUTHENTICATION_SUCCESS'),(238,'admin','2018-11-07 19:34:50','AUTHENTICATION_SUCCESS'),(239,'admin','2018-11-07 19:37:46','AUTHENTICATION_SUCCESS'),(240,'admin','2018-11-07 19:43:48','AUTHENTICATION_SUCCESS'),(241,'admin','2018-11-07 19:48:02','AUTHENTICATION_SUCCESS'),(242,'admin','2018-11-07 19:56:27','AUTHENTICATION_SUCCESS'),(243,'admin','2018-11-07 20:17:21','AUTHENTICATION_SUCCESS'),(244,'admin','2018-11-07 21:06:57','AUTHENTICATION_SUCCESS'),(245,'admin','2018-11-07 21:50:46','AUTHENTICATION_SUCCESS'),(246,'user','2018-11-07 21:51:11','AUTHENTICATION_SUCCESS'),(247,'admin','2018-11-07 21:52:24','AUTHENTICATION_SUCCESS'),(248,'admin','2018-11-07 21:56:35','AUTHENTICATION_SUCCESS'),(249,'user','2018-11-07 21:56:42','AUTHENTICATION_SUCCESS'),(250,'user','2018-11-07 21:56:59','AUTHENTICATION_SUCCESS'),(251,'user','2018-11-07 22:00:07','AUTHENTICATION_SUCCESS'),(252,'admin','2018-11-07 22:00:32','AUTHENTICATION_SUCCESS'),(253,'admin','2018-11-07 22:02:15','AUTHENTICATION_SUCCESS'),(254,'user','2018-11-07 22:05:01','AUTHENTICATION_SUCCESS'),(255,'admin','2018-11-07 22:05:17','AUTHENTICATION_FAILURE'),(256,'admin','2018-11-07 22:05:21','AUTHENTICATION_SUCCESS'),(257,'admin','2018-11-08 15:23:33','AUTHENTICATION_SUCCESS'),(258,'admin','2018-11-08 15:23:33','AUTHENTICATION_SUCCESS'),(259,'user','2018-11-08 16:34:13','AUTHENTICATION_SUCCESS'),(260,'user','2018-11-08 16:34:14','AUTHENTICATION_SUCCESS'),(261,'admin','2018-11-08 17:04:21','AUTHENTICATION_SUCCESS'),(262,'user','2018-11-08 17:05:02','AUTHENTICATION_SUCCESS'),(263,'user','2018-11-08 17:05:48','AUTHENTICATION_SUCCESS'),(264,'user','2018-11-08 17:30:09','AUTHENTICATION_SUCCESS'),(265,'user','2018-11-08 17:40:09','AUTHENTICATION_SUCCESS'),(266,'user','2018-11-08 17:40:48','AUTHENTICATION_SUCCESS'),(267,'user','2018-11-08 17:43:50','AUTHENTICATION_SUCCESS'),(268,'admin','2018-11-08 18:02:57','AUTHENTICATION_SUCCESS'),(269,'user','2018-11-08 19:10:30','AUTHENTICATION_SUCCESS'),(270,'admin','2018-11-08 19:20:10','AUTHENTICATION_SUCCESS'),(271,'user','2018-11-08 19:20:23','AUTHENTICATION_SUCCESS'),(272,'user','2018-11-08 19:21:31','AUTHENTICATION_SUCCESS'),(273,'admin','2018-11-08 19:32:49','AUTHENTICATION_SUCCESS'),(274,'admin','2018-11-08 19:39:37','AUTHENTICATION_SUCCESS'),(275,'admin','2018-11-08 20:25:46','AUTHENTICATION_SUCCESS'),(276,'admin','2018-11-08 20:25:54','AUTHENTICATION_SUCCESS'),(277,'user','2018-11-08 20:27:26','AUTHENTICATION_SUCCESS'),(278,'admin','2018-11-08 20:32:08','AUTHENTICATION_SUCCESS'),(279,'admin','2018-11-08 20:38:03','AUTHENTICATION_SUCCESS'),(280,'user','2018-11-08 20:38:28','AUTHENTICATION_SUCCESS'),(281,'user','2018-11-08 20:52:45','AUTHENTICATION_SUCCESS'),(282,'user','2018-11-08 20:52:45','AUTHENTICATION_SUCCESS'),(283,'admin','2018-11-08 20:58:22','AUTHENTICATION_SUCCESS'),(284,'admin','2018-11-08 20:59:10','AUTHENTICATION_SUCCESS'),(285,'user','2018-11-08 21:00:22','AUTHENTICATION_SUCCESS'),(286,'user','2018-11-08 21:01:49','AUTHENTICATION_FAILURE'),(287,'user','2018-11-08 21:01:52','AUTHENTICATION_FAILURE'),(288,'user','2018-11-08 21:01:57','AUTHENTICATION_FAILURE'),(289,'user','2018-11-08 21:02:02','AUTHENTICATION_FAILURE'),(290,'user','2018-11-08 21:02:11','AUTHENTICATION_FAILURE'),(291,'user','2018-11-08 21:02:13','AUTHENTICATION_FAILURE'),(292,'user','2018-11-08 21:02:28','AUTHENTICATION_SUCCESS'),(293,'admin','2018-11-08 21:03:54','AUTHENTICATION_SUCCESS'),(294,'admin','2018-11-08 21:09:22','AUTHENTICATION_SUCCESS'),(295,'user','2018-11-08 21:09:52','AUTHENTICATION_SUCCESS'),(296,'admin','2018-11-08 21:10:25','AUTHENTICATION_SUCCESS'),(297,'user','2018-11-08 21:10:32','AUTHENTICATION_SUCCESS'),(298,'user','2018-11-08 21:13:44','AUTHENTICATION_SUCCESS'),(299,'us','2018-11-08 21:15:21','AUTHENTICATION_FAILURE'),(300,'user','2018-11-08 21:15:24','AUTHENTICATION_SUCCESS'),(301,'admin','2018-11-08 21:16:38','AUTHENTICATION_SUCCESS'),(302,'admin','2018-11-08 21:16:59','AUTHENTICATION_SUCCESS'),(303,'user','2018-11-08 21:16:59','AUTHENTICATION_SUCCESS'),(304,'admin','2018-11-08 21:29:38','AUTHENTICATION_SUCCESS'),(305,'user','2018-11-08 21:29:57','AUTHENTICATION_SUCCESS'),(306,'admin','2018-11-08 21:38:19','AUTHENTICATION_SUCCESS'),(307,'admin','2018-11-08 21:41:18','AUTHENTICATION_SUCCESS'),(308,'user','2018-11-08 21:42:09','AUTHENTICATION_SUCCESS'),(309,'user','2018-11-08 21:48:09','AUTHENTICATION_SUCCESS'),(310,'user','2018-11-08 21:49:18','AUTHENTICATION_SUCCESS'),(311,'admin','2018-11-08 21:49:36','AUTHENTICATION_SUCCESS'),(312,'user','2018-11-08 21:49:53','AUTHENTICATION_SUCCESS'),(313,'user','2018-11-08 21:51:34','AUTHENTICATION_SUCCESS'),(314,'user','2018-11-10 19:02:23','AUTHENTICATION_SUCCESS'),(315,'admin','2018-11-11 14:09:44','AUTHENTICATION_SUCCESS'),(316,'admin','2018-11-11 14:11:06','AUTHENTICATION_SUCCESS'),(317,'admin','2018-11-11 14:33:39','AUTHENTICATION_SUCCESS'),(318,'admin','2018-11-11 14:57:11','AUTHENTICATION_SUCCESS'),(319,'user','2018-11-11 14:57:38','AUTHENTICATION_SUCCESS'),(320,'admin','2018-11-11 14:58:03','AUTHENTICATION_SUCCESS'),(321,'admin','2018-11-11 15:02:41','AUTHENTICATION_SUCCESS'),(322,'user','2018-11-11 15:51:50','AUTHENTICATION_SUCCESS'),(323,'admin','2018-11-11 16:00:58','AUTHENTICATION_SUCCESS'),(324,'admin','2018-11-11 17:06:02','AUTHENTICATION_SUCCESS'),(325,'admin','2018-11-11 17:31:59','AUTHENTICATION_SUCCESS'),(326,'admin','2018-11-11 17:47:49','AUTHENTICATION_SUCCESS'),(327,'admin','2018-11-11 17:48:50','AUTHENTICATION_SUCCESS'),(328,'admin','2018-11-11 17:52:41','AUTHENTICATION_SUCCESS'),(329,'admin','2018-11-11 17:56:11','AUTHENTICATION_SUCCESS'),(330,'admin','2018-11-11 17:59:55','AUTHENTICATION_SUCCESS'),(331,'admin','2018-11-11 18:05:50','AUTHENTICATION_SUCCESS'),(332,'admin','2018-11-11 18:11:26','AUTHENTICATION_SUCCESS'),(333,'admin','2018-11-11 18:16:09','AUTHENTICATION_SUCCESS'),(334,'admin','2018-11-11 18:16:40','AUTHENTICATION_SUCCESS'),(335,'admin','2018-11-11 18:21:23','AUTHENTICATION_SUCCESS'),(336,'user','2018-11-11 18:21:42','AUTHENTICATION_SUCCESS'),(337,'admin','2018-11-12 19:23:10','AUTHENTICATION_SUCCESS'),(338,'admin','2018-12-24 18:22:51','AUTHENTICATION_SUCCESS'),(339,'admin','2019-07-18 19:07:35','AUTHENTICATION_SUCCESS'),(340,'user','2019-07-18 19:08:22','AUTHENTICATION_SUCCESS'),(341,'user','2019-07-18 19:10:14','AUTHENTICATION_SUCCESS');
/*!40000 ALTER TABLE `jhi_persistent_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_evt_data`
--

LOCK TABLES `jhi_persistent_audit_evt_data` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` DISABLE KEYS */;
INSERT INTO `jhi_persistent_audit_evt_data` VALUES (41,'type','org.springframework.security.authentication.BadCredentialsException'),(41,'message','Bad credentials'),(43,'type','org.springframework.security.authentication.BadCredentialsException'),(43,'message','Bad credentials'),(130,'type','org.springframework.security.authentication.BadCredentialsException'),(130,'message','Bad credentials'),(158,'type','org.springframework.security.authentication.BadCredentialsException'),(158,'message','Bad credentials'),(163,'type','org.springframework.security.authentication.BadCredentialsException'),(163,'message','Bad credentials'),(255,'type','org.springframework.security.authentication.BadCredentialsException'),(255,'message','Bad credentials'),(286,'type','org.springframework.security.authentication.BadCredentialsException'),(286,'message','Bad credentials'),(287,'type','org.springframework.security.authentication.BadCredentialsException'),(287,'message','Bad credentials'),(288,'type','org.springframework.security.authentication.BadCredentialsException'),(288,'message','Bad credentials'),(289,'type','org.springframework.security.authentication.BadCredentialsException'),(289,'message','Bad credentials'),(290,'type','org.springframework.security.authentication.BadCredentialsException'),(290,'message','Bad credentials'),(291,'type','org.springframework.security.authentication.BadCredentialsException'),(291,'message','Bad credentials'),(299,'type','org.springframework.security.authentication.BadCredentialsException'),(299,'message','Bad credentials');
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','',_binary '','en',NULL,NULL,'system','2018-10-05 15:53:11',NULL,'admin','2018-11-04 14:30:05'),(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','',_binary '','en',NULL,NULL,'system','2018-10-05 15:53:11',NULL,'system',NULL),(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',_binary '','en',NULL,NULL,'system','2018-10-05 15:53:11',NULL,'system',NULL),(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','Usersd','User','user@localhost','',_binary '','en',NULL,NULL,'system','2018-10-05 15:53:11',NULL,'admin','2018-11-07 20:49:47'),(8,'shashank','$2a$10$Nhc5Lv0UUVtUTjHoz6CRJe3P0OM9vVU0Icsdlhf/vGcVe0bhWcUm.',NULL,NULL,'shashankpenumatcha@gmail.com',NULL,_binary '','en','08835223464331333623',NULL,'anonymousUser','2018-11-02 19:05:33',NULL,'anonymousUser','2018-11-02 19:05:33'),(9,'superman','$2a$10$7OJm0ozdm1momVHh2OtBMubvELTDNjzO9ZWitajKw/mv1MB9cDAcq','super',NULL,'super@man.com',NULL,_binary '','en','95826383515936194992',NULL,'anonymousUser','2018-11-04 14:28:57',NULL,'admin','2018-11-04 14:30:03'),(12,'qw','$2a$10$22TwRBhELGK3Fq7WvWL1hucIy1gx0mffKwe.fOWNFdnOgjZxK4yT2',NULL,NULL,'qw@w.com',NULL,_binary '','en',NULL,'62918254446610848394','admin','2018-11-07 20:50:07','2018-11-07 20:50:07','admin','2018-11-07 20:50:07');
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(3,'ROLE_ADMIN'),(3,'ROLE_USER'),(4,'ROLE_USER'),(5,'ROLE_USER'),(6,'ROLE_USER'),(7,'ROLE_USER'),(8,'ROLE_USER'),(9,'ROLE_USER'),(12,'ROLE_ADMIN');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) DEFAULT NULL,
  `order_list_id` bigint(20) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `price` bigint(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_items_product_id` (`product_id`),
  KEY `fk_order_items_order_list_id` (`order_list_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,5,30),(2,1,2,5,30),(3,2,2,2,10),(4,1,3,5,30),(5,2,4,2,10);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_list`
--

DROP TABLE IF EXISTS `order_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `last_updated` datetime,
  `order_status_id` bigint(20) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `user_address` varchar(255) DEFAULT NULL,
  `total` float DEFAULT NULL,
  `vendor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_list_order_status_id` (`order_status_id`),
  KEY `fk_order_list_user_profile_id` (`user_profile_id`),
  KEY `fk_order_list_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_list`
--

LOCK TABLES `order_list` WRITE;
/*!40000 ALTER TABLE `order_list` DISABLE KEYS */;
INSERT INTO `order_list` VALUES (1,'2018-11-16 00:34:00',5,2,'2018-11-16 00:34:02','g-11;sri mitras landmark;inner circle;whitefield',150,NULL),(2,'2019-07-19 00:37:00',5,2,'2019-07-19 00:37:42','g-11;sri mitras landmark;inner circle;whitefield',170,NULL),(3,'2019-07-19 00:38:00',5,8,'2019-07-19 00:38:34','erer;-;-;-',150,NULL),(4,'2019-07-19 00:41:00',5,8,'2019-07-19 00:41:07','erer;-;-;-',20,NULL);
/*!40000 ALTER TABLE `order_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT INTO `order_status` VALUES (1,'RECEIVED'),(2,'CONFIRMED'),(3,'DISPATCHED'),(4,'DELIVERED'),(5,'CANCELLED');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_tracker`
--

DROP TABLE IF EXISTS `order_tracker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_tracker` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_time` datetime,
  `order_list_id` bigint(20) DEFAULT NULL,
  `order_status_id` bigint(20) DEFAULT NULL,
  `user_profile_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_tracker_order_list_id` (`order_list_id`),
  KEY `fk_order_tracker_order_status_id` (`order_status_id`),
  KEY `fk_order_tracker_user_profile_id` (`user_profile_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_tracker`
--

LOCK TABLES `order_tracker` WRITE;
/*!40000 ALTER TABLE `order_tracker` DISABLE KEYS */;
INSERT INTO `order_tracker` VALUES (1,'2018-11-16 00:31:51',1,1,2),(2,'2018-11-16 00:32:33',2,1,2),(3,'2018-11-16 00:34:02',1,1,2),(4,'2019-07-19 00:37:42',2,1,2),(5,'2019-07-19 00:38:34',3,1,8),(6,'2019-07-19 00:40:08',3,2,2),(7,'2019-07-19 00:40:26',2,2,2),(8,'2019-07-19 00:40:32',1,2,2),(9,'2019-07-19 00:41:07',4,1,8),(10,'2019-07-19 00:41:39',4,2,2),(11,'2019-07-19 00:42:00',1,5,2),(12,'2019-07-19 00:42:05',2,5,2),(13,'2019-07-19 00:42:10',3,5,2),(14,'2019-07-19 00:42:15',4,5,2);
/*!40000 ALTER TABLE `order_tracker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `minimum_quantity` float DEFAULT NULL,
  `price_per_unit` float DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `unit_id` bigint(20) DEFAULT NULL,
  `vendor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_unit_id` (`unit_id`),
  KEY `fk_product_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Lettuce',5,30,_binary '',4956,1,1),(2,'Tomatoes',2,10,_binary '',334,1,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `unit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,'Kg');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_profile_id` bigint(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `vendor_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_address_user_profile_id` (`user_profile_id`),
  KEY `fk_user_address_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (1,2,'g-11;sri mitras landmark;inner circle;whitefield',NULL),(2,8,'erer;-;-;-',NULL);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `cart_id` bigint(20) DEFAULT NULL,
  `fcm_token` varchar(255) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `vendor_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_profile_user_id` (`user_id`),
  KEY `fk_user_profile_vendor_id` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (8,_binary '',4,NULL,'APA91bFubhMqBL1whwYvF2Vscw1yvFySCZiy1oZ_JBtfsQR29PUYz6KVOnD6OFjR7aj9CWNGAomu-mGijRHCTxoynFnDP0B9C-z5Kw7fYxdL6qJb7pvFu1M',NULL,NULL),(2,_binary '\0',3,NULL,'APA91bEAulz2r4PjwxbDV2yUPdNlFJYq9sNWGHtiwOWum5gr9OJ92StB7OCjhTCGIqqZcABqPTEv4IEN1oPcRT3ut6C958r3_b8MKqLZHw4xt3M0qh6t-2k',NULL,NULL),(3,_binary '',8,NULL,NULL,NULL,NULL),(4,_binary '',9,NULL,NULL,'8008790700',NULL),(5,_binary '',10,NULL,NULL,'8722445641',NULL),(6,_binary '',11,NULL,NULL,'8008790700',NULL);
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vendor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (1,'shashank','8008790700');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-19  2:24:33
