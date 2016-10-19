-- DO NOT RUN THIS UNLESS YOU ARE ABSOLUTELY SURE YOU HAVE A LOCAL DATABASE
-- INSTANCE SELECTED, YOU COULD MESS UP THE ONLINE DATABASE

-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: hotelboxoffice.cornjso48dgu.us-east-1.rds.amazonaws.com    Database: HotelBoxOffice
-- ------------------------------------------------------
-- Server version	5.6.27-log

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

-- -----------------------------------------------------
-- Schema HotelBoxOffice
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `HotelBoxOffice` DEFAULT CHARACTER SET latin1 ;
USE `HotelBoxOffice` ;

--
-- Table structure for table `ACTORS`
--

DROP TABLE IF EXISTS `ACTORS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTORS` (
  `ACTOR_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ACTOR_NAME` varchar(40) NOT NULL,
  `ACTOR_IMAGE` varchar(40) NOT NULL,
  `ACTOR_BIO` varchar(255) NOT NULL,
  PRIMARY KEY (`ACTOR_ID`),
  UNIQUE KEY `ACTORS_ACTOR_ID_uindex` (`ACTOR_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACTORS`
--

LOCK TABLES `ACTORS` WRITE;
/*!40000 ALTER TABLE `ACTORS` DISABLE KEYS */;
INSERT INTO `ACTORS` VALUES (1,'Will Smith','xx','American actor'),(2,'Joseph Gordon Levitt','xx','American actor'),(3,'Brad Pitt','xx','American actor');
/*!40000 ALTER TABLE `ACTORS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CASTING`
--

DROP TABLE IF EXISTS `CASTING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CASTING` (
  `CASTING_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ACTOR_ID` int(11) unsigned NOT NULL,
  `MOVIE_ID` int(11) unsigned NOT NULL,
  PRIMARY KEY (`CASTING_ID`),
  UNIQUE KEY `CASTING_CASTING_ID_uindex` (`CASTING_ID`),
  UNIQUE KEY `CASTING_ACTOR_ID_uindex` (`ACTOR_ID`),
  KEY `CASTING_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  CONSTRAINT `CASTING_ACTORS_ID_fk` FOREIGN KEY (`ACTOR_ID`) REFERENCES `ACTORS` (`ACTOR_ID`),
  CONSTRAINT `CASTING_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CASTING`
--

LOCK TABLES `CASTING` WRITE;
/*!40000 ALTER TABLE `CASTING` DISABLE KEYS */;
INSERT INTO `CASTING` VALUES (1,1,1),(2,2,2),(3,3,3);
/*!40000 ALTER TABLE `CASTING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER` (
  `CUSTOMER_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(40) NOT NULL,
  `CUSTOMER_PASSWORD` varchar(40) NOT NULL,
  `CUSTOMER_BALANCE` decimal(6,2) NOT NULL,
  `CUSTOMER_ROOMNUM` char(4) NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `CUSTOMER_CUSTOMER_ID_uindex` (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER` DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES (1,'Vilma','abc123',12.99,'1001'),(2,'Chad','abc123',12.99,'1002'),(3,'Tyler','abc123',12.99,'1003');
/*!40000 ALTER TABLE `CUSTOMER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER_RENTALS`
--

DROP TABLE IF EXISTS `CUSTOMER_RENTALS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER_RENTALS` (
  `RENTAL_ID` int(11) unsigned NOT NULL,
  `CUSTOMER_ID` int(11) unsigned NOT NULL,
  `MOVIE_ID` int(11) unsigned NOT NULL,
  `RENTAL_DATE` date NOT NULL,
  PRIMARY KEY (`RENTAL_ID`),
  UNIQUE KEY `CUSTOMER_RENTALS_RENTAL_ID_uindex` (`RENTAL_ID`),
  KEY `CUSTOMER_RENTALS_CUSTOMER_CUSTOMER_ID_fk` (`CUSTOMER_ID`),
  KEY `CUSTOMER_RENTALS_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  CONSTRAINT `CUSTOMER_RENTALS_CUSTOMER_CUSTOMER_ID_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `CUSTOMER_RENTALS_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER_RENTALS`
--

LOCK TABLES `CUSTOMER_RENTALS` WRITE;
/*!40000 ALTER TABLE `CUSTOMER_RENTALS` DISABLE KEYS */;
INSERT INTO `CUSTOMER_RENTALS` VALUES (1,1,1,'2016-12-30'),(2,2,1,'2016-12-30');
/*!40000 ALTER TABLE `CUSTOMER_RENTALS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GENRE`
--

DROP TABLE IF EXISTS `GENRE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GENRE` (
  `GENRE_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `GENRE_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`GENRE_ID`),
  UNIQUE KEY `GENRE_GENRE_ID_uindex` (`GENRE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GENRE`
--

LOCK TABLES `GENRE` WRITE;
/*!40000 ALTER TABLE `GENRE` DISABLE KEYS */;
INSERT INTO `GENRE` VALUES (1,'Comedy'),(2,'Horror');
/*!40000 ALTER TABLE `GENRE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOVIES`
--

DROP TABLE IF EXISTS `MOVIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MOVIES` (
  `MOVIE_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `MOVIE_TITLE` varchar(100) NOT NULL,
  `MOVIE_DIRECTOR` varchar(40) NOT NULL,
  `MOVIE_DESCRIPTION` varchar(255) NOT NULL,
  `MOVIE_RELEASE_DATE` date NOT NULL,
  `MOVIE_IMAGE` varchar(40) NOT NULL,
  PRIMARY KEY (`MOVIE_ID`),
  UNIQUE KEY `MOVIES_MOVIE_ID_uindex` (`MOVIE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOVIES`
--

LOCK TABLES `MOVIES` WRITE;
/*!40000 ALTER TABLE `MOVIES` DISABLE KEYS */;
INSERT INTO `MOVIES` VALUES (1,'Rogue One: A Star Wars Story','Gareth Edwards','The Gang Gets the Death Star Plans','2016-12-16','https://i.imgur.com/rVzTVDj.png'),(2,'Magnificent Seven','Antoine Fuqua','Remake of a western remake of a Japanese movie directed by Akira Kurosawa','2016-09-23','https://i.imgur.com/se3Vqxi.png'),(3,'Captain America: Civil War','Joe Russo and Anthony Russo','Hail Hydra','2016-04-06','https://i.imgur.com/GDdLpFh.png'),(4,'Star Wars: Episode VIII','Rian Johnson','STAR WARS','2016-12-15','https://i.imgur.com/Ns9aQ4k.png'),(5,'Snowden','Oliver Stone','A dramatization of a not very dramatic event','2016-09-16','https://i.imgur.com/4RJsPfe.png'),(6,'Men in Black','Barry Sonnenfeld','The Fresh Prince fights aliens','1997-07-02','https://i.imgur.com/kxWdaeF.png'),(7,'Se7en','David Fincher','It was her head','1995-09-22','https://i.imgur.com/jVt26ig.png'),(8,'Star Wars: Episode VII: The Force Awakens','J.J. Abrams','Dude Star Wars is back who cares what it\'s about','2015-12-18','https://i.imgur.com/DLzSsiK.png'),(9,'The Shawshank Redemption','Frank Darabont','Get busy living or get busy dying','1994-10-14','https://i.imgur.com/7BR3nTY.png'),(10,'The Godfather','Francis Ford Coppola','Better than the book','1972-05-24','https://i.imgur.com/9En3ZQY.png'),(11,'The Godfather: Part II','Francis Ford Coppola','The pre-sequel','1974-12-20','https://i.imgur.com/YLxJs31.png'),(12,'12 Angry Men','Sidney Lumet','12 angry men talk in a room for 1 hour and 36 minutes','1957-04-13','https://i.imgur.com/jBj3Vrn.png'),(13,'The Good, the Bad, and the Ugly','Sergio Leone','Pretty good standoff at the end','1967-12-29','https://i.imgur.com/VePPtMI.png'),(14,'Forrest Gump','Robert Zemeckis','Stupid is as stupid does','1994-07-06','https://i.imgur.com/9H1DcGg.png'),(15,'One Flew Over the Cuckoo\'s Nest','Milos Forman','Doc from Back to the Future finally gets put in the loony bin','1975-11-19','https://i.imgur.com/awwTe7s.png'),(16,'Spirited Away','Hayao Miyazaki','Little girl fights the 7 deadly sins','2003-03-28','https://i.imgur.com/EBS9Imk.png');
/*!40000 ALTER TABLE `MOVIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RATING`
--

DROP TABLE IF EXISTS `RATING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RATING` (
  `RATING_ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` int(11) unsigned NOT NULL,
  `MOVIE_ID` int(11) unsigned NOT NULL,
  `RATING_NUM` int(11) unsigned NOT NULL,
  `RATING_DATE` date NOT NULL,
  PRIMARY KEY (`RATING_ID`),
  UNIQUE KEY `RATING_RATING_ID_uindex` (`RATING_ID`),
  KEY `RATING_CUSTOMER_CUSTOMER_ID_fk` (`CUSTOMER_ID`),
  KEY `RATING_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  CONSTRAINT `RATING_CUSTOMER_CUSTOMER_ID_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `RATING_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RATING`
--

LOCK TABLES `RATING` WRITE;
/*!40000 ALTER TABLE `RATING` DISABLE KEYS */;
INSERT INTO `RATING` VALUES (1,1,1,4,'2016-12-30'),(2,1,3,4,'2016-12-30');
/*!40000 ALTER TABLE `RATING` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-18 22:13:34
