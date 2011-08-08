-- MySQL dump 10.13  Distrib 5.5.9, for osx10.6 (i386)
--
-- Host: localhost    Database: square-data
-- ------------------------------------------------------
-- Server version	5.5.9

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
-- Table structure for table `artifact`
--

DROP TABLE IF EXISTS `artifact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artifact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `artifact_version` text COLLATE utf8_unicode_ci NOT NULL,
  `link` text COLLATE utf8_unicode_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `artifact_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Stores artifacts for step 3';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artifact`
--

LOCK TABLES `artifact` WRITE;
/*!40000 ALTER TABLE `artifact` DISABLE KEYS */;
/*!40000 ALTER TABLE `artifact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asquare_case`
--

DROP TABLE IF EXISTS `asquare_case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asquare_case` (
  `id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asquare_case`
--

LOCK TABLES `asquare_case` WRITE;
/*!40000 ALTER TABLE `asquare_case` DISABLE KEYS */;
INSERT INTO `asquare_case` VALUES (1,'Case 1','The acquisition organization has the typical client role for newly developed software.'),(2,'Case 2','The acquisition organization specifies the requirements as part of the RFP for newly developed software.'),(3,'Case 3','The acquisition organization is purchasing COTS software.');
/*!40000 ALTER TABLE `asquare_case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `asset_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This represents the assets in step 2';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,'gfd',1155,'2011-07-26 17:53:12','2011-07-26 17:53:12');
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(80) COLLATE utf8_unicode_ci NOT NULL,
  `project_id` int(11) NOT NULL COMMENT 'links to the project.',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This represents the categorie of requirement(step 7)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `childd`
--

DROP TABLE IF EXISTS `childd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `childd` (
  `project_id` int(11) NOT NULL,
  `tradeoffreason` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  KEY `project_package_tradeoffreason_ibfk_50` (`project_id`),
  CONSTRAINT `project_package_tradeoffreason_ibfk_50` FOREIGN KEY (`project_id`) REFERENCES `parent` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `childd`
--

LOCK TABLES `childd` WRITE;
/*!40000 ALTER TABLE `childd` DISABLE KEYS */;
/*!40000 ALTER TABLE `childd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation_criteria`
--

DROP TABLE IF EXISTS `evaluation_criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation_criteria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `project_id` int(11) NOT NULL COMMENT 'this links technique_evaluation to the project',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `evaluation_criteria_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='These are squareqa technique evaluations.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_criteria`
--

LOCK TABLES `evaluation_criteria` WRITE;
/*!40000 ALTER TABLE `evaluation_criteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation_criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `priority` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  KEY `type` (`type`),
  CONSTRAINT `goal_ibfk_2` FOREIGN KEY (`type`) REFERENCES `goal_type` (`id`),
  CONSTRAINT `goal_ibfk_3` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This represent a business, security, or privacy goal.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal` DISABLE KEYS */;
INSERT INTO `goal` VALUES (1,'gfd',1,1,1155,'2011-07-26 17:53:06','2011-07-26 17:53:06'),(2,'gfd',1,2,1155,'2011-07-26 17:53:10','2011-07-26 17:53:10');
/*!40000 ALTER TABLE `goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal_asset`
--

DROP TABLE IF EXISTS `goal_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goal_asset` (
  `goal_id` int(11) NOT NULL COMMENT 'id of the goal ',
  `asset_id` int(11) NOT NULL COMMENT 'id of the asset',
  KEY `goal_id` (`goal_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `goal_asset_ibfk_1` FOREIGN KEY (`goal_id`) REFERENCES `goal` (`id`) ON DELETE CASCADE,
  CONSTRAINT `goal_asset_ibfk_2` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This table links one goal to one asset. ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal_asset`
--

LOCK TABLES `goal_asset` WRITE;
/*!40000 ALTER TABLE `goal_asset` DISABLE KEYS */;
INSERT INTO `goal_asset` VALUES (2,1);
/*!40000 ALTER TABLE `goal_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal_type`
--

DROP TABLE IF EXISTS `goal_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goal_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This represents the goal types.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal_type`
--

LOCK TABLES `goal_type` WRITE;
/*!40000 ALTER TABLE `goal_type` DISABLE KEYS */;
INSERT INTO `goal_type` VALUES (1,'Business','0000-00-00 00:00:00','0000-00-00 00:00:00'),(2,'Subgoal','0000-00-00 00:00:00','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `goal_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspection_technique`
--

DROP TABLE IF EXISTS `inspection_technique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inspection_technique` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `inspection_technique_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This represents inspection techniques.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspection_technique`
--

LOCK TABLES `inspection_technique` WRITE;
/*!40000 ALTER TABLE `inspection_technique` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspection_technique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent`
--

DROP TABLE IF EXISTS `parent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent`
--

LOCK TABLES `parent` WRITE;
/*!40000 ALTER TABLE `parent` DISABLE KEYS */;
INSERT INTO `parent` VALUES (1,'Unix');
/*!40000 ALTER TABLE `parent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `lite` tinyint(1) NOT NULL,
  `privacy` tinyint(1) NOT NULL,
  `lead_requirements_engineer` int(11) DEFAULT NULL COMMENT 'user id of a user',
  `acquisition_organization_engineer` int(11) DEFAULT NULL,
  `security` tinyint(1) NOT NULL,
  `ptid` int(11) DEFAULT NULL COMMENT 'privacy technique Id',
  `privacyTechniqueRationale` text COLLATE utf8_unicode_ci,
  `stid` int(11) DEFAULT NULL COMMENT 'Secruity Technique Id',
  `securityTechniqueRationale` text COLLATE utf8_unicode_ci,
  `inspectionId` int(11) DEFAULT NULL,
  `inspectionStatus` enum('Issues Found, Re-inspect','Issues Found, No Follow-up','No Issues Found, Requirements ready','Inspection in Progress') COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'This is the status of the inspection technique selected',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cases` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `lead_requirements_engineer` (`lead_requirements_engineer`),
  KEY `ptid` (`ptid`,`stid`),
  KEY `stid` (`stid`),
  KEY `inspectionId` (`inspectionId`),
  KEY `project_ibfk_9` (`cases`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`lead_requirements_engineer`) REFERENCES `user` (`id`),
  CONSTRAINT `project_ibfk_5` FOREIGN KEY (`ptid`) REFERENCES `technique` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_ibfk_6` FOREIGN KEY (`stid`) REFERENCES `technique` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_ibfk_7` FOREIGN KEY (`inspectionId`) REFERENCES `inspection_technique` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_ibfk_9` FOREIGN KEY (`cases`) REFERENCES `asquare_case` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1159 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Represents squareqa projects.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1155,'Testcase3',0,0,NULL,5,1,NULL,NULL,NULL,NULL,NULL,NULL,'2011-07-26 17:50:37','2011-07-26 17:50:37',3),(1156,'Casetest36',0,0,NULL,5,1,NULL,NULL,NULL,NULL,NULL,NULL,'2011-07-26 17:59:06','2011-07-26 17:59:06',3),(1157,'Casetest36_COPY',0,0,NULL,5,1,NULL,NULL,NULL,NULL,NULL,NULL,'2011-07-27 15:21:39','2011-07-27 15:21:39',3),(1158,'New3',0,0,NULL,5,1,NULL,NULL,NULL,NULL,NULL,NULL,'2011-08-04 21:29:20','2011-08-04 21:29:20',3);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_package_attribute_rating`
--

DROP TABLE IF EXISTS `project_package_attribute_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_package_attribute_rating` (
  `project_id` int(11) NOT NULL DEFAULT '999',
  `package_id` int(11) NOT NULL DEFAULT '999',
  `attribute_id` int(11) NOT NULL DEFAULT '999',
  `rating` int(11) NOT NULL DEFAULT '999',
  PRIMARY KEY (`project_id`,`package_id`,`attribute_id`),
  KEY `project_id` (`project_id`),
  KEY `package_id` (`package_id`),
  KEY `attribute_id` (`attribute_id`),
  CONSTRAINT `project_package_attribute_rating_ibfk_13` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_package_attribute_rating_ibfk_14` FOREIGN KEY (`package_id`) REFERENCES `software_package` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_package_attribute_rating_ibfk_15` FOREIGN KEY (`attribute_id`) REFERENCES `quality_attribute` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Links stuff together';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_package_attribute_rating`
--

LOCK TABLES `project_package_attribute_rating` WRITE;
/*!40000 ALTER TABLE `project_package_attribute_rating` DISABLE KEYS */;
INSERT INTO `project_package_attribute_rating` VALUES (1155,1,48,0),(1155,1,49,0),(1155,46,48,0),(1155,46,49,0),(1155,47,48,0),(1155,47,49,0),(1155,48,48,0),(1155,48,49,0),(1156,1,50,0),(1156,1,51,0),(1156,49,50,0),(1156,49,51,0),(1156,50,50,0),(1156,50,51,0),(1158,1,52,0),(1158,51,52,0),(1158,52,52,0);
/*!40000 ALTER TABLE `project_package_attribute_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_package_rationale`
--

DROP TABLE IF EXISTS `project_package_rationale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_package_rationale` (
  `project_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  `tradeoffreason` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`project_id`,`package_id`),
  KEY `project_package_tradeoffreason_ibfk_42` (`project_id`),
  KEY `project_package_tradeoffreason_ibfk_43` (`package_id`),
  CONSTRAINT `project_package_tradeoffreason_ibfk_42` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_package_tradeoffreason_ibfk_43` FOREIGN KEY (`package_id`) REFERENCES `software_package` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_package_rationale`
--

LOCK TABLES `project_package_rationale` WRITE;
/*!40000 ALTER TABLE `project_package_rationale` DISABLE KEYS */;
INSERT INTO `project_package_rationale` VALUES (1155,47,'This is soo awesome');
/*!40000 ALTER TABLE `project_package_rationale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_package_requirement_rating`
--

DROP TABLE IF EXISTS `project_package_requirement_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_package_requirement_rating` (
  `project_id` int(11) NOT NULL DEFAULT '999',
  `package_id` int(11) NOT NULL DEFAULT '999',
  `requirement_id` int(11) NOT NULL DEFAULT '999',
  `rating` int(11) NOT NULL DEFAULT '999',
  PRIMARY KEY (`project_id`,`package_id`,`requirement_id`),
  KEY `project_package_requirement_rating_ibfk_13` (`project_id`),
  KEY `project_package_requirement_rating_ibfk_14` (`package_id`),
  KEY `project_package_requirement_rating_ibfk_15` (`requirement_id`),
  CONSTRAINT `project_package_requirement_rating_ibfk_13` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_package_requirement_rating_ibfk_14` FOREIGN KEY (`package_id`) REFERENCES `software_package` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_package_requirement_rating_ibfk_15` FOREIGN KEY (`requirement_id`) REFERENCES `requirement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_package_requirement_rating`
--

LOCK TABLES `project_package_requirement_rating` WRITE;
/*!40000 ALTER TABLE `project_package_requirement_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_package_requirement_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_package_tradeoffreason`
--

DROP TABLE IF EXISTS `project_package_tradeoffreason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_package_tradeoffreason` (
  `project_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  `tradeoffreason` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `priority` int(11) NOT NULL,
  PRIMARY KEY (`project_id`,`package_id`),
  KEY `project_package_tradeoffreason_ibfk_1` (`project_id`),
  KEY `project_package_tradeoffreason_ibfk_2` (`package_id`),
  CONSTRAINT `project_package_tradeoffreason_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `project_package_tradeoffreason_ibfk_2` FOREIGN KEY (`package_id`) REFERENCES `software_package` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_package_tradeoffreason`
--

LOCK TABLES `project_package_tradeoffreason` WRITE;
/*!40000 ALTER TABLE `project_package_tradeoffreason` DISABLE KEYS */;
INSERT INTO `project_package_tradeoffreason` VALUES (1155,1,'this is weight',1),(1155,46,'Add new tradeoff reason...r111',1),(1155,47,'Add new tradeoff reason...2222',2),(1155,48,'Add new tradeoff reason...333',-1),(1156,1,'this is weight',1),(1156,49,'add new tradeoff reason...',1),(1156,50,'add new tradeoff reason...',2),(1158,1,'this is weight',1),(1158,51,'add new tradeoff reason...',1),(1158,52,'add new tradeoff reason...',2);
/*!40000 ALTER TABLE `project_package_tradeoffreason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_step`
--

DROP TABLE IF EXISTS `project_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_step` (
  `stepId` int(11) NOT NULL COMMENT 'foreign key to the steps.id',
  `projectId` int(11) NOT NULL COMMENT 'foreign key to the projects.id',
  `status` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Not Started' COMMENT 'True implies that this step is completed for this project.',
  UNIQUE KEY `stepId` (`stepId`,`projectId`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `project_step_ibfk_1` FOREIGN KEY (`stepId`) REFERENCES `step` (`id`) ON DELETE CASCADE,
  CONSTRAINT `project_step_ibfk_2` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This relates the project and steps. Mark step complete here.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_step`
--

LOCK TABLES `project_step` WRITE;
/*!40000 ALTER TABLE `project_step` DISABLE KEYS */;
INSERT INTO `project_step` VALUES (1,1155,'In Progress'),(1,1156,'In Progress'),(1,1157,'Not Started'),(1,1158,'Not Started'),(2,1155,'In Progress'),(2,1156,'In Progress'),(2,1157,'Not Started'),(2,1158,'Not Started'),(16,1155,'In Progress'),(16,1156,'In Progress'),(16,1157,'Not Started'),(16,1158,'Not Started'),(20,1155,'In Progress'),(20,1156,'In Progress'),(20,1157,'Not Started'),(20,1158,'In Progress'),(21,1155,'In Progress'),(21,1156,'In Progress'),(21,1157,'Not Started'),(21,1158,'Not Started'),(22,1155,'In Progress'),(22,1156,'In Progress'),(22,1157,'Not Started'),(22,1158,'Not Started'),(23,1155,'In Progress'),(23,1156,'In Progress'),(23,1157,'Not Started'),(23,1158,'In Progress');
/*!40000 ALTER TABLE `project_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quality_attribute`
--

DROP TABLE IF EXISTS `quality_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quality_attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='These are quality attributes';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quality_attribute`
--

LOCK TABLES `quality_attribute` WRITE;
/*!40000 ALTER TABLE `quality_attribute` DISABLE KEYS */;
INSERT INTO `quality_attribute` VALUES (42,'Linux','No description','2011-07-10 21:47:36','2011-07-10 21:47:36'),(43,'Name','F','2011-07-10 21:45:48','2011-07-10 21:45:48'),(44,'Performance','Performance...........','2011-07-12 00:21:36','2011-07-12 00:21:36'),(45,'Unnamed','No description','2011-07-20 13:42:39','2011-07-20 13:42:39'),(46,'Unnamed','No description','2011-07-20 13:44:36','2011-07-20 13:44:36'),(47,'Unnamed','No description','2011-07-26 17:47:06','2011-07-26 17:47:06'),(48,'Qa0','No description','2011-07-26 17:54:56','2011-07-26 17:54:56'),(49,'Performance','F','2011-07-26 17:54:48','2011-07-26 17:54:48'),(50,'Qa0','Ffds','2011-07-26 18:02:34','2011-07-26 18:02:34'),(51,'Qa1','F','2011-07-26 18:02:27','2011-07-26 18:02:27'),(52,'Unnamed','No description','2011-08-04 21:29:20','2011-08-04 21:29:20');
/*!40000 ALTER TABLE `quality_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement`
--

DROP TABLE IF EXISTS `requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text COLLATE utf8_unicode_ci NOT NULL,
  `security` tinyint(1) NOT NULL COMMENT 'True if this is a security requirement',
  `privacy` tinyint(1) NOT NULL COMMENT 'true if this is a privacy requirement',
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `priority` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `requirement_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Table of requirements';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement`
--

LOCK TABLES `requirement` WRITE;
/*!40000 ALTER TABLE `requirement` DISABLE KEYS */;
INSERT INTO `requirement` VALUES (1,'fds',1,0,'fds',0,1155,'2011-07-26 17:53:55','2011-07-26 17:53:55','Pending');
/*!40000 ALTER TABLE `requirement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_artifact`
--

DROP TABLE IF EXISTS `requirement_artifact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement_artifact` (
  `requirement_id` int(11) NOT NULL,
  `artifact_id` int(11) NOT NULL,
  KEY `requirement_id` (`requirement_id`,`artifact_id`),
  KEY `artifact_id` (`artifact_id`),
  KEY `artifact_id_2` (`artifact_id`),
  KEY `artifact_id_3` (`artifact_id`),
  CONSTRAINT `requirement_artifact_ibfk_1` FOREIGN KEY (`requirement_id`) REFERENCES `requirement` (`id`) ON DELETE CASCADE,
  CONSTRAINT `requirement_artifact_ibfk_2` FOREIGN KEY (`artifact_id`) REFERENCES `artifact` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This links requirements to artifacts';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_artifact`
--

LOCK TABLES `requirement_artifact` WRITE;
/*!40000 ALTER TABLE `requirement_artifact` DISABLE KEYS */;
/*!40000 ALTER TABLE `requirement_artifact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_category`
--

DROP TABLE IF EXISTS `requirement_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement_category` (
  `requirement_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  KEY `requirement_id` (`requirement_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `requirement_category_ibfk_1` FOREIGN KEY (`requirement_id`) REFERENCES `requirement` (`id`) ON DELETE CASCADE,
  CONSTRAINT `requirement_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_category`
--

LOCK TABLES `requirement_category` WRITE;
/*!40000 ALTER TABLE `requirement_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `requirement_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_goal`
--

DROP TABLE IF EXISTS `requirement_goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement_goal` (
  `requirement_id` int(11) NOT NULL,
  `goal_id` int(11) NOT NULL,
  KEY `requirement_id` (`requirement_id`),
  KEY `goal_id` (`goal_id`),
  CONSTRAINT `requirement_goal_ibfk_1` FOREIGN KEY (`goal_id`) REFERENCES `goal` (`id`) ON DELETE CASCADE,
  CONSTRAINT `requirement_goal_ibfk_2` FOREIGN KEY (`requirement_id`) REFERENCES `requirement` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_goal`
--

LOCK TABLES `requirement_goal` WRITE;
/*!40000 ALTER TABLE `requirement_goal` DISABLE KEYS */;
INSERT INTO `requirement_goal` VALUES (1,2);
/*!40000 ALTER TABLE `requirement_goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requirement_risk`
--

DROP TABLE IF EXISTS `requirement_risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requirement_risk` (
  `reqid` int(11) NOT NULL,
  `tid` int(11) NOT NULL,
  KEY `reqid` (`reqid`,`tid`),
  KEY `riskid` (`tid`),
  CONSTRAINT `requirement_risk_ibfk_3` FOREIGN KEY (`reqid`) REFERENCES `requirement` (`id`) ON DELETE CASCADE,
  CONSTRAINT `requirement_risk_ibfk_4` FOREIGN KEY (`tid`) REFERENCES `risk` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='requirements linked to risks';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requirement_risk`
--

LOCK TABLES `requirement_risk` WRITE;
/*!40000 ALTER TABLE `requirement_risk` DISABLE KEYS */;
/*!40000 ALTER TABLE `requirement_risk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk`
--

DROP TABLE IF EXISTS `risk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `riskTitle` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `threatSource` text COLLATE utf8_unicode_ci NOT NULL,
  `threatAction` text COLLATE utf8_unicode_ci NOT NULL,
  `currentMeasures` text COLLATE utf8_unicode_ci NOT NULL,
  `plannedMeasures` text COLLATE utf8_unicode_ci,
  `impact` int(11) NOT NULL,
  `likelihood` int(11) NOT NULL,
  `vulnerability` text COLLATE utf8_unicode_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `risk_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Represents a risk.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk`
--

LOCK TABLES `risk` WRITE;
/*!40000 ALTER TABLE `risk` DISABLE KEYS */;
/*!40000 ALTER TABLE `risk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk_artifact`
--

DROP TABLE IF EXISTS `risk_artifact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risk_artifact` (
  `riskid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  KEY `riskid` (`riskid`,`aid`),
  KEY `aid` (`aid`),
  CONSTRAINT `risk_artifact_ibfk_3` FOREIGN KEY (`riskid`) REFERENCES `risk` (`id`) ON DELETE CASCADE,
  CONSTRAINT `risk_artifact_ibfk_4` FOREIGN KEY (`aid`) REFERENCES `artifact` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Links a risk to an artifact.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_artifact`
--

LOCK TABLES `risk_artifact` WRITE;
/*!40000 ALTER TABLE `risk_artifact` DISABLE KEYS */;
/*!40000 ALTER TABLE `risk_artifact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `risk_asset`
--

DROP TABLE IF EXISTS `risk_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `risk_asset` (
  `risk_id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  KEY `risk_id` (`risk_id`,`asset_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `risk_asset_ibfk_1` FOREIGN KEY (`risk_id`) REFERENCES `risk` (`id`) ON DELETE CASCADE,
  CONSTRAINT `risk_asset_ibfk_2` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `risk_asset`
--

LOCK TABLES `risk_asset` WRITE;
/*!40000 ALTER TABLE `risk_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `risk_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This is a role(for exp: stakeholder)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (4,'Acquisition Organization Engineer','','2011-05-23 04:00:00','2011-05-23 04:00:00'),(5,'Contractor','','2011-06-10 04:00:00','2011-06-10 04:00:00'),(6,'Security Specialist','','2011-06-10 04:00:00','2011-06-10 04:00:00'),(7,'COTS Vendor','','2011-06-10 04:00:00','2011-06-10 04:00:00');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `software_package`
--

DROP TABLE IF EXISTS `software_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `software_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='the software package';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `software_package`
--

LOCK TABLES `software_package` WRITE;
/*!40000 ALTER TABLE `software_package` DISABLE KEYS */;
INSERT INTO `software_package` VALUES (1,'Weights','This row adds weights to the quality attributes above','2009-06-09 19:31:49','2009-06-09 19:31:49'),(2,'package1','package1','2009-06-09 19:31:49','2009-06-09 19:31:49'),(3,'package2','package2','2009-06-09 19:31:49','2009-06-09 19:31:49'),(4,'Yupp','12345667','2011-07-06 20:44:53','2011-07-06 20:44:53'),(10,'Linux','Linux is awesome','2011-07-07 16:16:20','2011-07-07 16:16:20'),(11,'Abc','123','2011-07-07 19:23:31','2011-07-07 19:23:31'),(15,'Kk','K','2011-07-10 18:46:56','2011-07-10 18:46:56'),(16,'Linux','How secure is the sytem','2011-07-10 18:59:26','2011-07-10 18:59:26'),(17,'Mac','Os','2011-07-10 18:59:32','2011-07-10 18:59:32'),(18,'Linux','No description','2011-07-10 19:40:50','2011-07-10 19:40:50'),(19,'Mac','D','2011-07-10 19:40:56','2011-07-10 19:40:56'),(20,'Mac','No description','2011-07-10 19:50:05','2011-07-10 19:50:05'),(21,'Linux','F','2011-07-10 19:50:11','2011-07-10 19:50:11'),(22,'Mac','Os','2011-07-10 19:54:58','2011-07-10 19:54:58'),(23,'Linux','F','2011-07-10 19:55:03','2011-07-10 19:55:03'),(24,'Linux','No description','2011-07-10 20:02:51','2011-07-10 20:02:51'),(25,'Unix','F','2011-07-10 20:02:56','2011-07-10 20:02:56'),(26,'Mac','G','2011-07-10 20:34:31','2011-07-10 20:34:31'),(27,'Linux','F','2011-07-10 20:34:36','2011-07-10 20:34:36'),(28,'Mac','Os','2011-07-10 20:43:02','2011-07-10 20:43:02'),(29,'Linux','F','2011-07-10 20:42:55','2011-07-10 20:42:55'),(30,'Mac','No description','2011-07-10 21:12:59','2011-07-10 21:12:59'),(31,'Linux','F','2011-07-10 21:12:30','2011-07-10 21:12:30'),(32,'Unnamed','No description','2011-07-10 21:35:43','2011-07-10 21:35:43'),(35,'Windows','F','2011-07-10 21:45:32','2011-07-10 21:45:32'),(36,'Unix','F','2011-07-10 21:45:37','2011-07-10 21:45:37'),(44,'Anew','Aaa','2011-07-12 04:14:07','2011-07-12 04:14:07'),(45,'Bb','Bb','2011-07-12 04:21:58','2011-07-12 04:21:58'),(46,'Package0','F','2011-07-26 17:54:23','2011-07-26 17:54:23'),(47,'Package1','F','2011-07-26 17:54:33','2011-07-26 17:54:33'),(48,'Package 3','D','2011-07-26 17:54:41','2011-07-26 17:54:41'),(49,'Pa00','F','2011-07-26 18:02:42','2011-07-26 18:02:42'),(50,'Pa1','Fds','2011-07-26 18:02:51','2011-07-26 18:02:51'),(51,'Package1','Fds','2011-08-04 21:32:09','2011-08-04 21:32:09'),(52,'Package 2','Fds','2011-08-04 21:32:14','2011-08-04 21:32:14');
/*!40000 ALTER TABLE `software_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `step`
--

DROP TABLE IF EXISTS `step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `step` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id of the step',
  `description` text COLLATE utf8_unicode_ci NOT NULL COMMENT 'Brief description of the step, the purpose',
  `security` tinyint(1) NOT NULL,
  `privacy` tinyint(1) NOT NULL,
  `date_created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='This contains the SQUARE steps';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `step`
--

LOCK TABLES `step` WRITE;
/*!40000 ALTER TABLE `step` DISABLE KEYS */;
INSERT INTO `step` VALUES (1,'Step 1: Agree On Definitions',1,1,'2009-07-08 21:15:00','2009-05-21 18:46:28'),(2,'Step 2: Identify Assets and Goals',1,1,'2009-07-08 21:15:24','2009-05-21 18:46:28'),(13,'Step 3: Collect Artifacts',0,0,'2009-05-22 19:57:09','2009-05-22 19:57:09'),(14,'Step 4: Review Of Requirements By Acquisition Organization',1,0,'2009-05-22 19:57:09','2009-05-22 19:57:09'),(15,'Step 5: Select Elicitation Technique',0,0,'2009-05-22 19:59:12','2009-05-22 19:57:09'),(16,'Step 3: Identify Preliminary Security Requirements',1,1,'2009-05-22 19:59:12','2009-05-22 19:57:09'),(17,'Step 7: Categorize Requirements',0,0,'2009-05-22 19:59:12','2009-05-22 19:57:09'),(18,'Step 8: Prioritize Requirements',0,0,'2009-05-22 19:59:12','2009-05-22 19:57:09'),(19,'Step 9: Inspect Requirements',0,0,'2009-05-22 19:59:12','2009-05-22 19:57:09'),(20,'Step 4: Review COTS software package information and specifications',0,1,'2009-05-22 19:59:12','2009-05-22 19:59:12'),(21,'Step 5: Review and finalize security requirements',0,1,'2009-05-22 19:59:12','2009-05-22 19:59:12'),(22,'Step 6: Perform tradeoff analysis',0,1,'2009-05-22 19:59:12','2009-05-22 19:59:12'),(23,'Step 7: Final product selection',0,1,'2009-05-22 19:59:12','2009-05-22 19:59:12');
/*!40000 ALTER TABLE `step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technique`
--

DROP TABLE IF EXISTS `technique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technique` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `type` tinyint(1) NOT NULL COMMENT 'true if security. false otherwise',
  `project_id` int(11) NOT NULL COMMENT 'This links the technique to the project.',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `technique_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Elicitation technique';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technique`
--

LOCK TABLES `technique` WRITE;
/*!40000 ALTER TABLE `technique` DISABLE KEYS */;
/*!40000 ALTER TABLE `technique` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technique_evaluation_criteria`
--

DROP TABLE IF EXISTS `technique_evaluation_criteria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technique_evaluation_criteria` (
  `pid` int(11) NOT NULL,
  `technique_id` int(11) NOT NULL,
  `evaluation_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  KEY `technique_id` (`technique_id`,`evaluation_id`),
  KEY `evaluation_id` (`evaluation_id`),
  KEY `pid` (`pid`),
  CONSTRAINT `technique_evaluation_criteria_ibfk_1` FOREIGN KEY (`technique_id`) REFERENCES `technique` (`id`) ON DELETE CASCADE,
  CONSTRAINT `technique_evaluation_criteria_ibfk_2` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation_criteria` (`id`) ON DELETE CASCADE,
  CONSTRAINT `technique_evaluation_criteria_ibfk_3` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technique_evaluation_criteria`
--

LOCK TABLES `technique_evaluation_criteria` WRITE;
/*!40000 ALTER TABLE `technique_evaluation_criteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `technique_evaluation_criteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `term` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `term` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `definition` text COLLATE utf8_unicode_ci NOT NULL,
  `pid` int(11) NOT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `pid` (`pid`),
  CONSTRAINT `term_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `project` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Terms for agree on definitions.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
INSERT INTO `term` VALUES (1,'Access Control','Access control ensures that resources are only granted to those users who are entitled to them [SANS 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(2,'Artifact','The remnants of an intruder attack or incident activity. These could be software used by intruder(s), a collection of tools, malicious code, logs, files, output from tools, status of asystem after an attack or intrusion [West-Brown 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(3,'Attack','An action conducted by an adversary, the attacker, on a potential victim. A set of events that an observer believes to have information assurance consequences on some entity,the target of the attack [Ellison 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(4,'Authentication','The process of determining whether someone or something is, in fact, who or what it is declared to be [SearchSecurity 2008].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(5,'Authorization','The process of granting a person, computer process, or device access to certain information, services, or functionality. Authorization is derived from the identity of the person, computer process, or device requesting access, which is verified through authentication [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(6,'Availability','The property of a system or a system resource that ensures it is accessible and usable upon demand by an authorized system user. Availability is one of the core characteristics of a secure system [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(7,'Back door','A hardware or software-based hidden entrance to a computer system that can be used to bypass the system\'s security policies [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(8,'Breach','Any intentional event where an intruder gains access that compromises the confidentiality, integrity, or availability of computers, networks, or the data residing on them [CERT/CC 2004].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(9,'Brute force','A cryptanalysis technique or other kind of attack method involving an exhaustive procedure that tries all possibilities, one by one [SANS 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(10,'Cache poisoning','Malicious or misleading data from a remote name server is saved [cached] by another name server. Typically used with DNS cache poisoning attacks [SANS 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(11,'Confidentiality','The property that information is not made available or disclosed to unauthorized individuals, entities, or processes (i.e., to any unauthorized system entity) [SANS 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(12,'Control','An action, device, procedure, or technique that removes or reduces vulnerability.',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(13,'Corruption','A threat action that undesirably alters system operation by adversely modifying system functions or data [sans 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(14,'Cracker','Someone who breaks into someone elseâ€™s computer system, often on a network, bypasses passwords or licenses in computer programs, or in other ways intentionally breaches computer security [SearchSecurity 2008].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(15,'Denial-of-service (DoS) attack','An attempt by a malicious (or unwitting) user, process, or system to prevent legitimate users from accessing a resource (usually a network service) by exploiting a weakness or design limitation in an information system. Examples of DoS attacks include flooding network connections, filling disk storage, disabling ports, and removing power [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(16,'Disaster recovery plan','A plan that helps a company recover data and restore services after a disaster [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(17,'Disclosure','A component of the notice principle, wherein a company should make available its data handling practices, including notices on how it collects, uses, and shares personally identifiable information [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(18,'Disgruntled employee','A person in an organization who deliberately abuses or misuses computer systems and their information [Alberts 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(19,'Encryption','The cryptographic transformation of data (called plaintext) into a form (called cipher text) that conceals the data\'s original meaning to prevent it from being known or used [SANS 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(20,'Fault tolerance','A computer system or component designed so that, in the event that a component fails, a backup component or procedure can immediately take its place with no loss of service. Fault tolerance can be provided with software, embedded in hardware, or provided by some combination [SearchSecurity 2008].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(21,'Firewall','A security solution that segregates one portion of a network from another portion, allowing only authorized network traffic to pass through according to traffic-filtering rules [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(22,'Hacker','Someone who engages in the activity of hacking computer programs, systems, or networks [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(23,'Integrity','For data, the property that data has not been changed, destroyed, or lost in an unauthorized or accidental manner [Allen 1999].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(24,'Intrusion','An attempt to compromise a system or network [Tulloch 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(25,'Intrusion detection system','A combination of hardware and software that monitors and collects system and network information and analyzes it to determine if an attack or an intrusion has occurred. Some ID systems can automatically respond to an intrusion [Allen 1999].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(26,'Liability','The responsibility of someone for damage or loss [West-Brown 2003].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(27,'Luring attack','A type of elevation of privilege attack where the attacker lures a more highly privileged component to do something on his or her behalf. The most straightforward technique is to convince the target to run the attacker’s code in a more privileged security context [Brown 2005].',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(28,'Mail relaying','A practice in which an attacker sends email messages from another system\'s email server in order to use its resources and/or make it appear that the messages originated from the other system.',1158,'2011-08-04 21:29:20','2011-08-04 21:29:20'),(29,'Malicious code','Software that fulfills the deliberately harmful intent of an attacker when run. For example,viruses, worms, and Trojan horses are malicious code.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(30,'Malicious user','A user who accesses a system with the intent to cause harm to the system or to use it in an unauthorized manner.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(31,'Malware','Programming or files that are developed for the purpose of doing harm. Thus, malware includes computer viruses, worms, and Trojan horses [Webopedia 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(32,'Man-in-the-middle attack','A computer attack during which the cyber criminal funnels communication between a consumer and a legitimate organization through a fake website. In these attacks, neither the consumer nor the organization is aware that the communication is being illegally monitored. The criminal is, in effect, in the middle of a transaction between the consumer and his or her bank, credit card company, or retailer [BSA 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(33,'Masquerade','Attempts to fool other machines on the network into accepting the imposter as an original, either to lure the other machines into sending it data or to allow it to alter data [Howard 1998].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(34,'Modification','Situation in which an unauthorized party not only gains access to but tampers with an asset [Howard 1997].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(35,'Non-essential services','Services to users of a system that can be temporarily suspended to permit delivery of essential services while the system is dealing with intrusions and compromises [Ellison 1997].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(36,'Non-repudiation','A technique used to ensure that someone performing an action on a computer cannot falsely deny that they performed that action [Tulloch 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(37,'Patch','A small update released by a software manufacturer to fix bugs in an existing program [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(38,'Patching','The process of updating software to a new version that fixes bugs in a previous version [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(39,'Penetration','Intrusion, trespassing, or unauthorized entry into a system [RUsecure 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(40,'Penetration testing','The execution of a testing plan, the sole purpose of which is to attempt to hack into a system using known tools and techniques [RUsecure 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(41,'Permissions','Authorization to perform operations associated with a specific shared resource, such as a file, directory, or printer. Permissions must be granted by the system administrator to individual user accounts or administrative groups.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(42,'Physical security','Security measures taken to protect systems, buildings, and related supporting infrastructure against threats associated with their physical environment [Guttman 1995].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(43,'Port scanning','The act of systematically scanning a computer\'s ports [Webopedia 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(44,'Privacy','The quality or condition of being secluded from the presence or view of others [Dictionary.com 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(45,'Procedure','The implementation of a policy in the form of workflows, orders, or mechanisms [West-Brown 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(46,'Recognition','The capability of a system to recognize attacks or the probing that precedes attacks [Ellison 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(47,'Recovery','A system’s ability to restore services after an intrusion has occurred. Recovery also contributes to a system’s ability to maintain essential services during intrusion [Ellison 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(48,'Replay attack','The interception of communications, such as an authentication communication, and subsequent impersonation of the sender by retransmitting the intercepted communication [FFIEC 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(49,'Request for collaboration (RFC)','A request for development engagement where Product Support Services (PSS) is technically blocked; also used to formalize and track support statement requests and to review proposed action plans. The request process was introduced to help reduce the time it takes to provide a solution to a customer. An RFC may become a DCR [design change request], CDCR [critical design change request], or hotfix request [a request to address a problem in a product] [Microsoft 2005].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(50,'Resilience','The ability of a computer or system to both withstand a range of load fluctuations and remain stable under continuous and/or adverse conditions [RUsecure 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(51,'Resistance','The capability of a system to resist attacks [Ellison 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(52,'Risk','The product of the level of threat with the level of vulnerability. It establishes the likelihood of a successful attack [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(53,'Risk assessment','The process by which risks are identified and the impact of those risks determined [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(54,'SQL injection','A type of input validation attack specific to database-driven applications where SQL code is inserted into application queries to manipulate the database [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(55,'Script kiddie','The more immature but unfortunately often dangerous exploiter of security lapses on the Internet. The typical script kiddie uses existing and frequently well-known and easy-tofind techniques and programs or scripts to search for and exploit weaknesses in other computers on the Internet often randomly and with little regard or perhaps even understanding of the potentially harmful consequences [SearchSecurity 2008].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(56,'Security policy','A policy that addresses security issues [West-Brown 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(57,'Spoof','Making a transmission appear to come from a user other than the user who performed the action [Microsoft 2005].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(58,'Stakeholder','Anyone who is a direct user, indirect user, manager of users, senior manager, operations staff member, support (help desk) staff member, developer working on other systems that integrate or interact with the one under development, or maintenance professionals potentially affected by the development and/or deployment of a software project [Ambler 2004].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(59,'Stealthing','A term that refers to approaches used by malicious code to conceal its presence on an infected system [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(60,'Survivability','The capability of a system to complete its mission in a timely manner, even if significant portions are compromised by attack or accident. The system should provide essential services in the presence of successful intrusion and recover compromised services in a timely manner after intrusion occurs [Mead 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(61,'Target','The object of an attack, especially host, computer, network, system, site, person, organization, nation, company, government, or other group [Allen 1999].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(62,'Threat','A potential for violation of security, which exists when there is a circumstance, capability, action, or event that could breach security and cause harm [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(63,'Threat assessment','The identification of the types of threats that an organization might be exposed to [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(64,'Threat model','Used to describe a given threat and the harm it could to do a system if it has a vulnerability [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(65,'Toolkits','A collection of tools with related purposes or functions, e.g., antivirus toolkit, disk toolkit [RUsecure].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(66,'Trojan','A program that appears to be useful or harmless but that contains hidden code designed to exploit or damage the system on which it is run. Trojan horse programs are most commonly delivered to users through e-mail messages that misrepresent the program’s purpose and function. Also called Trojan code [Microsoft 2005].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(67,'Trust','Determines which permissions other systems or users have and what actions they can perform on remote machines [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(68,'Upgrade','A software package that replaces an installed version with a newer version of the same software. The upgrade process typically leaves existing customer data and preferences intact while replacing the existing software with the newer version.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(69,'User profile','Settings that define customization preferences for a particular user, such as desktop settings, persistent network connections, personally identifiable information, website use,or other behaviors and demographics data.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(70,'User rights','Tasks that a user is permitted to perform on a Windows-based computer or domain. There are two types of user rights: privileges and logon rights. An example of a privilege is the right to shut down the system. An example of a logon right is the right to log on to a computer interactively. Both types are assigned by administrators to individual users or groups as part of the security settings for the computer.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(71,'Victim','That which is the target of an attack. An entity may be a victim of either a successful or unsuccessful attack [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(72,'Virus','A hidden, self-replicating section of computer software, usually malicious logic, that propagates by infecting i.e., inserting a copy of itself into and becoming part of another program. A virus cannot run by itself; it requires that its host program be run to make it active [SANS 2003].',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(73,'Vulnerability','Anything that gives an attacker the opportunity to perform an exploit.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21'),(74,'Worm','Self-propagating malicious code that can automatically distribute itself from one computer to another through network connections. A worm can take harmful action, such as consuming network or local system resources, possibly causing a denial-of-service attack [Microsoft 2005]. Compare virus.',1158,'2011-08-04 21:29:21','2011-08-04 21:29:21');
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `isAdmin` tinyint(1) NOT NULL COMMENT 'Tells whether this user is an administrator for the system or not.',
  `locked` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Flag for determining whether user''s account is locked',
  `skip_teach_step` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'This field indicates whether the teach step should be skipped in the step navigation',
  `full_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `organization` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `department` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date_modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `username_2` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20124 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='These are the users of the squareqa system.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'demouser0','password',0,0,1,'Marco A Len','demo1@blah.com','4123456789','Carnegie Mellon University','MSE','','2009-06-15 16:40:02','2009-06-15 04:00:00'),(2,'admin','yessir',1,0,1,'Administrator','demo2@blah.com','','','','','2009-05-25 04:00:00','2009-05-25 04:00:00'),(3,'chennan','123',0,0,1,'chennan','demo7@blah.com',NULL,'asquare',NULL,NULL,'2009-08-04 14:21:57','2009-08-04 14:21:57'),(4,'security','yessir',0,0,0,'Security Specialist','fds@fds.com','','CMU','MSE','','2011-07-10 04:00:00','2011-07-10 04:00:00'),(5,'aoe','yessir',0,0,0,'Acquisition Organization Engineer','fds2@fds.com','','CMU','MSE','','2011-07-10 04:00:00','2011-07-10 04:00:00'),(6,'cots','yessir',0,0,1,'COTS Vendor','fds3@fds.com',NULL,'CMU','MSE',NULL,'2011-07-10 04:00:00','2011-07-10 04:00:00'),(7,'contractor','yessir',0,0,1,'Contractor','fds4@fds.com',NULL,'CMU','MSE',NULL,'2011-07-10 04:00:00','2011-07-10 04:00:00'),(19,'demouser1','password',1,0,1,'Loomi Liao','demo3@blah.com','','Square Root','Quality Assurance','','2009-05-25 19:58:42','2009-05-26 04:00:00'),(53,'demouser2','password',1,0,0,'Abin Shahab','demo4@blah.com','4123334567','Square Root','Architecture','Arizona','2009-05-26 13:36:20','2009-05-26 13:36:20'),(355,'demouser3','password',1,0,1,'Nancy R Mead','demo5@blah.com','4120000000','Carnegie Mellon University','CERT','Pittsburgh, PA','2009-05-29 04:00:00','2009-05-29 04:00:00'),(20118,'demouser4','123',0,0,0,'Michael Keeling','demo6@blah.com','','Carnegie Mellon University','Square Root','','2009-08-04 14:21:57','2009-08-04 14:21:57'),(20119,'fdsfds','bKtIqddgktL%',0,0,0,'fds','ghjfds@fds.com',NULL,NULL,NULL,NULL,'2011-07-27 17:35:38','2011-07-27 17:35:38'),(20120,'hhh','jY&3uBfWws?r',0,0,0,'hhh','ghj@gfd.com',NULL,NULL,NULL,NULL,'2011-07-27 17:53:43','2011-07-27 17:53:43'),(20121,'fdsffsdf','CW8RdKFLQduD',0,0,0,'ffffsd','nonames@fastmail.fm',NULL,NULL,NULL,NULL,'2011-07-27 17:56:48','2011-07-27 17:56:48'),(20122,'fdsfdsfds','lM6HB1v6fyzq',0,0,0,'fdsfdsfds','noone__@fastmail.fm',NULL,NULL,NULL,NULL,'2011-07-27 19:36:31','2011-07-27 19:36:31'),(20123,'test12345','KQNJNXdcXxx!',0,0,0,'ffds','fds@fdsf.com',NULL,NULL,NULL,NULL,'2011-07-27 19:40:55','2011-07-27 19:40:55');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_ahp`
--

DROP TABLE IF EXISTS `user_ahp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ahp` (
  `uid` int(11) NOT NULL,
  `ridA` int(11) NOT NULL,
  `costA` float NOT NULL,
  `valueA` float NOT NULL,
  `ridB` int(11) NOT NULL,
  `costB` float NOT NULL,
  `valueB` float NOT NULL,
  KEY `uid` (`uid`),
  KEY `rid` (`ridA`),
  KEY `ridA` (`ridA`),
  KEY `ridB` (`ridB`),
  CONSTRAINT `user_ahp_ibfk_4` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_ahp_ibfk_5` FOREIGN KEY (`ridA`) REFERENCES `requirement` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_ahp_ibfk_6` FOREIGN KEY (`ridB`) REFERENCES `requirement` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_ahp`
--

LOCK TABLES `user_ahp` WRITE;
/*!40000 ALTER TABLE `user_ahp` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_ahp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_project_role`
--

DROP TABLE IF EXISTS `user_project_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project_role` (
  `user_id` int(11) NOT NULL DEFAULT '999',
  `project_id` int(11) NOT NULL DEFAULT '999',
  `role_id` int(11) NOT NULL DEFAULT '999',
  KEY `user_id` (`user_id`),
  KEY `project_id` (`project_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_project_role_ibfk_13` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_project_role_ibfk_14` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_project_role_ibfk_15` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Links the users to a project and roles.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_project_role`
--

LOCK TABLES `user_project_role` WRITE;
/*!40000 ALTER TABLE `user_project_role` DISABLE KEYS */;
INSERT INTO `user_project_role` VALUES (5,1155,4),(4,1155,6),(5,1156,4),(4,1156,6),(5,1157,4),(5,1158,4),(4,1158,6);
/*!40000 ALTER TABLE `user_project_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-04 18:03:32
