-- DO NOT RUN THIS UNLESS YOU ARE ABSOLUTELY SURE YOU HAVE A LOCAL DATABASE
-- INSTANCE SELECTED, YOU COULD MESS UP THE ONLINE DATABASE

-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: hotelboxoffice.cornjso48dgu.us-east-1.rds.amazonaws.com    Database: HotelBoxOffice
-- ------------------------------------------------------
-- Server version	5.6.27-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `ACTORS`
--

DROP TABLE IF EXISTS `ACTORS`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACTORS` (
  `ACTOR_ID`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ACTOR_NAME`  VARCHAR(40)      NOT NULL,
  `ACTOR_IMAGE` VARCHAR(40)      NOT NULL,
  `ACTOR_BIO`   VARCHAR(255)     NOT NULL,
  PRIMARY KEY (`ACTOR_ID`),
  UNIQUE KEY `ACTORS_ACTOR_ID_uindex` (`ACTOR_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 71
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACTORS`
--

LOCK TABLES `ACTORS` WRITE;
/*!40000 ALTER TABLE `ACTORS`
  DISABLE KEYS */;
INSERT INTO `ACTORS`
VALUES (1, 'Will Smith', 'https://i.imgur.com/3xGWVcy.jpg', 'American actor'),
  (2, 'Joseph Gordon-Levitt', 'https://i.imgur.com/xXYWqrn.jpg', 'American actor'),
  (3, 'Brad Pitt', 'https://i.imgur.com/tEr10ut.jpg', 'American actor'),
  (4, 'Felicity Jones', 'https://i.imgur.com/2sa3kwY.jpg', 'English actress'),
  (5, 'Mads Mikkelsen', 'https://i.imgur.com/wntU1fk.jpg', 'Danish actor'),
  (6, 'Donnie Yen', 'https://i.imgur.com/FhNubJG.jpg', 'Hong Kong actor'),
  (7, 'Forest Whitaker', 'https://i.imgur.com/7HyDJng.jpg', 'American actor'),
  (8, 'Diego Luna', 'https://i.imgur.com/5CEIiw3.jpg', 'Mexican actor'),
  (9, 'James Earl Jones', 'https://i.imgur.com/3eR8Ik9.jpg', 'American actor'),
  (10, 'Denzel Washington', 'https://i.imgur.com/f601H7X.jpg', 'American actor'),
  (11, 'Chris Pratt', 'https://i.imgur.com/JgDhBL7.jpg', 'American actor'),
  (12, 'Ethan Hawke', 'https://i.imgur.com/WY3y5Ab.jpg', 'American actor'),
  (13, 'Lee Byung-hun', 'https://i.imgur.com/oLxwXDD.jpg', 'South Korean actor'),
  (14, 'Vincent D\'Onofrio', 'https://i.imgur.com/iy08bbq.jpg', 'American actor'),
  (15, 'Chris Evans', 'https://i.imgur.com/TCMNXRn.jpg', 'American actor'),
  (16, 'Sebastian Stan', 'https://i.imgur.com/lKT7u1f.jpg', 'Romanian-American actor'),
  (17, 'Robert Downey Jr.', 'https://i.imgur.com/1a7xOdt.jpg', 'American actor'),
  (18, 'Scarlett Johansson', 'https://i.imgur.com/W1POt4y.jpg', 'American actress'),
  (19, 'Jeremy Renner', 'https://i.imgur.com/AdxFHGf.jpg', 'American actor'),
  (20, 'Daisy Ridley', 'https://i.imgur.com/UcETFMW.jpg', 'English actress'),
  (21, 'Oscar Isaac', 'https://i.imgur.com/FVDblYH.jpg', 'Guatemalan-American actor'),
  (22, 'Adam Driver', 'https://i.imgur.com/tenvO0M.jpg', 'American actor'),
  (23, 'Mark Hamill', 'https://i.imgur.com/S3iOaah.jpg', 'American actor'),
  (24, 'John Boyega', 'https://i.imgur.com/XfaD3lo.jpg', 'English actor'),
  (25, 'Shailene Woodley', 'https://i.imgur.com/3p55zsC.jpg', 'American actress'),
  (26, 'Zachary Quinto', 'https://i.imgur.com/KR2x8BO.jpg', 'American actor'),
  (27, 'Nicolas Cage', 'https://i.imgur.com/2Qkwe2i.jpg', 'American actor'),
  (28, 'Melissa Leo', 'https://i.imgur.com/ksbTmti.jpg', 'American actress'),
  (29, 'Tommy Lee Jones', 'https://i.imgur.com/bFSQgzO.jpg', 'American actor'),
  (30, 'Linda Fiorentino', 'https://i.imgur.com/MS0r2ML.jpg', 'American actress'),
  (31, 'Rip Torn', 'https://i.imgur.com/iM11WQz.png', 'American actor'),
  (32, 'Morgan Freeman', 'https://i.imgur.com/qVf9umF.jpg', 'American actor'),
  (33, 'Kevin Spacey', 'https://i.imgur.com/6oLcbmK.jpg', 'American actor'),
  (34, 'Gwyneth Paltrow', 'https://i.imgur.com/d1FvIxc.jpg', 'American actress'),
  (35, 'John C. McGinley', 'https://i.imgur.com/Mi9DTFj.jpg', 'American actor'),
  (36, 'Harrison Ford', 'https://i.imgur.com/0fCjAhr.jpg', 'American actor'),
  (37, 'Tim Robbins', 'https://i.imgur.com/2ZnOLGW.jpg', 'American actor'),
  (38, 'Bob Gunton', 'https://i.imgur.com/QY3kFry.jpg', 'American actor'),
  (39, 'Clancy Brown', 'https://i.imgur.com/hawKFoG.jpg', 'American actor'),
  (40, 'William Sadler', 'https://i.imgur.com/xE9voRx.jpg', 'American actor'),
  (41, 'Al Pacino', 'https://i.imgur.com/7RyxS6Z.jpg', 'American actor'),
  (42, 'Marlon Brando', 'https://i.imgur.com/MUHgqZ8.jpg', 'American actor'),
  (43, 'Robert Duvall', 'https://i.imgur.com/XzPUTYv.jpg', 'American actor'),
  (44, 'James Caan', 'https://i.imgur.com/UFeREju.jpg', 'American actor'),
  (45, 'Talia Shire', 'https://i.imgur.com/vPTrL5X.jpg', 'Italian-American actress'),
  (46, 'Robert De Niro', 'https://i.imgur.com/Ljpaahk.jpg', 'American actor'),
  (47, 'Diane Keaton', 'https://i.imgur.com/uYd3TJs.jpg', 'American actress'),
  (48, 'Henry Fonda', 'https://i.imgur.com/iP0HJ09.jpg', 'American actor'),
  (49, 'Lee J. Cobb', 'https://i.imgur.com/Qe34stE.jpg', 'American actor'),
  (50, 'Martin Balsam', 'https://i.imgur.com/2xpvBnf.jpg', 'American actor'),
  (51, 'Jack Warden', 'https://i.imgur.com/5J4KHlI.jpg', 'American actor'),
  (52, 'E.G. Marshall', 'https://i.imgur.com/Ov4NReH.jpg', 'American actor'),
  (53, 'Clint Eastwood', 'https://i.imgur.com/mpda8MZ.jpg', 'American actor'),
  (54, 'Eli Wallach', 'https://i.imgur.com/RDM4lnx.jpg', 'American actor'),
  (55, 'Lee Van Cleef', 'https://i.imgur.com/IHYAXKw.jpg', 'American actor'),
  (56, 'Tom Hanks', 'https://i.imgur.com/vW1xhq1.jpg', 'American actor'),
  (57, 'Sally Field', 'https://i.imgur.com/QhTWtte.jpg', 'American actress'),
  (58, 'Robin Wright', 'https://i.imgur.com/4k92kXo.jpg', 'American actress'),
  (59, 'Gary Sinise', 'https://i.imgur.com/4VIDlit.jpg', 'American actor'),
  (60, 'Mykelti Williamson', 'https://i.imgur.com/EbHNRGa.jpg', 'American actor'),
  (61, 'Jack Nicholson', 'https://i.imgur.com/CtmXuOn.jpg', 'American actor'),
  (62, 'Louise Fletcher', 'https://i.imgur.com/v0HQmhz.png', 'American actor'),
  (63, 'Danny DeVito', 'https://i.imgur.com/WWDdVoO.jpg', 'American actor'),
  (64, 'Christopher Lloyd', 'https://i.imgur.com/IAneYME.jpg',
   'American actor'),
  (65, 'William Redfield', 'https://i.imgur.com/sSlpe63.jpg', 'American actor'),
  (66, 'Rumi Hiiragi', 'https://i.imgur.com/h8ahGD7.jpg', 'Japanese actress'),
  (67, 'Miyu Irino', 'https://i.imgur.com/Qic2X9C.png', 'Japanese actor'),
  (68, 'Mari Natsuki', 'https://i.imgur.com/qhnuTEM.png', 'Japanese actress'),
  (69, 'Bunta Sugawara', 'https://i.imgur.com/OZeuqf6.jpg', 'Japanese actor'),
  (70, 'Tatsuya Gashuin', 'https://i.imgur.com/rEslV4T.png', 'Japanese actor');
/*!40000 ALTER TABLE `ACTORS`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CASTING`
--

DROP TABLE IF EXISTS `CASTING`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CASTING` (
  `CASTING_ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ACTOR_ID`   INT(11) UNSIGNED NOT NULL,
  `MOVIE_ID`   INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`CASTING_ID`),
  UNIQUE KEY `CASTING_CASTING_ID_uindex` (`CASTING_ID`),
  KEY `CASTING_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  KEY `CASTING_ACTOR_ID_fk` (`ACTOR_ID`),
  CONSTRAINT `CASTING_ACTOR_ID_fk` FOREIGN KEY (`ACTOR_ID`) REFERENCES `ACTORS` (`ACTOR_ID`),
  CONSTRAINT `CASTING_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 80
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CASTING`
--

LOCK TABLES `CASTING` WRITE;
/*!40000 ALTER TABLE `CASTING`
  DISABLE KEYS */;
INSERT INTO `CASTING`
VALUES (1, 1, 6), (2, 2, 5), (3, 3, 7), (4, 4, 1), (5, 5, 1), (6, 6, 1),
  (7, 7, 1), (8, 8, 1), (9, 9, 1), (10, 10, 2), (11, 11, 2), (12, 12, 2),
  (13, 13, 2), (14, 14, 2), (15, 15, 3), (16, 16, 3), (17, 17, 3), (18, 18, 3),
  (19, 19, 3), (20, 20, 4), (21, 21, 4), (22, 22, 4), (23, 23, 4), (24, 24, 4),
  (25, 25, 5), (26, 26, 5), (27, 27, 5), (28, 28, 5), (29, 29, 6), (30, 30, 6),
  (31, 31, 6), (32, 14, 6), (33, 32, 7), (34, 33, 7), (35, 34, 7), (36, 35, 7),
  (37, 20, 8), (38, 21, 8), (39, 22, 8), (40, 24, 8), (41, 36, 8), (42, 32, 9),
  (43, 37, 9), (44, 38, 9), (45, 39, 9), (46, 40, 9), (47, 41, 10),
  (48, 42, 10), (49, 43, 10), (50, 44, 10), (51, 45, 10), (52, 46, 11),
  (53, 41, 11), (54, 43, 11), (55, 45, 11), (56, 47, 11), (57, 48, 12),
  (58, 49, 12), (59, 50, 12), (60, 51, 12), (61, 52, 12), (62, 53, 13),
  (63, 54, 13), (64, 55, 13), (65, 56, 14), (66, 57, 14), (67, 58, 14),
  (68, 59, 14), (69, 60, 14), (70, 61, 15), (71, 62, 15), (72, 63, 15),
  (73, 64, 15), (74, 65, 15), (75, 66, 16), (76, 67, 16), (77, 68, 16),
  (78, 69, 16), (79, 70, 16);
/*!40000 ALTER TABLE `CASTING`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER`
--

DROP TABLE IF EXISTS `CUSTOMER`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER` (
  `CUSTOMER_ID`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME`     VARCHAR(40)      NOT NULL,
  `CUSTOMER_PASSWORD` VARCHAR(40)      NOT NULL,
  `CUSTOMER_BALANCE`  DECIMAL(6, 2)    NOT NULL,
  `CUSTOMER_ROOMNUM`  CHAR(4)          NOT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `CUSTOMER_CUSTOMER_ID_uindex` (`CUSTOMER_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER`
--

LOCK TABLES `CUSTOMER` WRITE;
/*!40000 ALTER TABLE `CUSTOMER`
  DISABLE KEYS */;
INSERT INTO `CUSTOMER` VALUES (1, 'Vilma', 'abc123', 12.99, '1001'),
  (2, 'Chad', 'abc123', 12.99, '1002'), (3, 'Tyler', 'abc123', 12.99, '1003');
/*!40000 ALTER TABLE `CUSTOMER`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CUSTOMER_RENTALS`
--

DROP TABLE IF EXISTS `CUSTOMER_RENTALS`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CUSTOMER_RENTALS` (
  `RENTAL_ID`   INT(11) UNSIGNED NOT NULL,
  `CUSTOMER_ID` INT(11) UNSIGNED NOT NULL,
  `MOVIE_ID`    INT(11) UNSIGNED NOT NULL,
  `RENTAL_DATE` DATE             NOT NULL,
  PRIMARY KEY (`RENTAL_ID`),
  UNIQUE KEY `CUSTOMER_RENTALS_RENTAL_ID_uindex` (`RENTAL_ID`),
  KEY `CUSTOMER_RENTALS_CUSTOMER_CUSTOMER_ID_fk` (`CUSTOMER_ID`),
  KEY `CUSTOMER_RENTALS_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  CONSTRAINT `CUSTOMER_RENTALS_CUSTOMER_CUSTOMER_ID_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `CUSTOMER_RENTALS_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CUSTOMER_RENTALS`
--

LOCK TABLES `CUSTOMER_RENTALS` WRITE;
/*!40000 ALTER TABLE `CUSTOMER_RENTALS`
  DISABLE KEYS */;
INSERT INTO `CUSTOMER_RENTALS`
VALUES (1, 1, 1, '2016-12-30'), (2, 2, 1, '2016-12-30');
/*!40000 ALTER TABLE `CUSTOMER_RENTALS`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GENRE`
--

DROP TABLE IF EXISTS `GENRE`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GENRE` (
  `GENRE_ID`   INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `GENRE_NAME` VARCHAR(40)      NOT NULL,
  PRIMARY KEY (`GENRE_ID`),
  UNIQUE KEY `GENRE_GENRE_ID_uindex` (`GENRE_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GENRE`
--

LOCK TABLES `GENRE` WRITE;
/*!40000 ALTER TABLE `GENRE`
  DISABLE KEYS */;
INSERT INTO `GENRE`
VALUES (1, 'Comedy'), (2, 'Horror'), (3, 'Action'), (4, 'Animation'),
  (5, 'Family'), (6, 'Romance'), (7, 'Drama'), (8, 'Sports'),
  (9, 'Science fiction'), (10, 'Fiction');
/*!40000 ALTER TABLE `GENRE`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MOVIES`
--

DROP TABLE IF EXISTS `MOVIES`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MOVIES` (
  `MOVIE_ID`           INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `MOVIE_TITLE`        VARCHAR(100)     NOT NULL,
  `MOVIE_DIRECTOR`     VARCHAR(40)      NOT NULL,
  `MOVIE_DESCRIPTION`  VARCHAR(255)     NOT NULL,
  `MOVIE_RELEASE_DATE` DATE             NOT NULL,
  `MOVIE_IMAGE`        VARCHAR(40)      NOT NULL,
  `GENRE_ID`           INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (`MOVIE_ID`),
  UNIQUE KEY `MOVIES_MOVIE_ID_uindex` (`MOVIE_ID`),
  KEY `MOVIES_GENRE_GENRE_ID_fk` (`GENRE_ID`),
  CONSTRAINT `MOVIES_GENRE_GENRE_ID_fk` FOREIGN KEY (`GENRE_ID`) REFERENCES `GENRE` (`GENRE_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 17
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MOVIES`
--

LOCK TABLES `MOVIES` WRITE;
/*!40000 ALTER TABLE `MOVIES`
  DISABLE KEYS */;
INSERT INTO `MOVIES` VALUES
  (1, 'Rogue One: A Star Wars Story', 'Gareth Edwards', 'The Gang Gets the Death Star Plans', '2016-12-16', 'https://i.imgur.com/fLgqSsR.jpg', 9),
  (2, 'Magnificent Seven', 'Antoine Fuqua', 'Remake of a western remake of a Japanese movie directed by Akira Kurosawa', '2016-09-23', 'https://i.imgur.com/CDTXtPS.jpg', 3),
  (3, 'Captain America: Civil War', 'Joe Russo and Anthony Russo', 'Hail Hydra', '2016-04-06', 'https://i.imgur.com/ZAlO2Eq.jpg', 3),
  (4, 'Star Wars: Episode VIII', 'Rian Johnson', 'STAR WARS', '2016-12-15', 'https://i.imgur.com/jmQenR4.jpg', 9),
  (5, 'Snowden', 'Oliver Stone', 'A dramatization of a not very dramatic event', '2016-09-16', 'https://i.imgur.com/bWBzsjD.jpg', 7),
  (6, 'Men in Black', 'Barry Sonnenfeld', 'The Fresh Prince fights aliens', '1997-07-02', 'https://i.imgur.com/ev6eRey.jpg', 3),
  (7, 'Se7en', 'David Fincher', 'It was her head', '1995-09-22', 'https://i.imgur.com/SltvaDv.jpg', 7),
  (8, 'Star Wars: Episode VII: The Force Awakens', 'J.J. Abrams', 'Dude Star Wars is back who cares what it\'s about', '2015-12-18', 'https://i.imgur.com/sY6KAhj.jpg', 9),
  (9, 'The Shawshank Redemption', 'Frank Darabont', 'Get busy living or get busy dying', '1994-10-14', 'https://i.imgur.com/ldKX4wu.jpg', 7),
  (10, 'The Godfather', 'Francis Ford Coppola', 'Better than the book', '1972-05-24', 'https://i.imgur.com/a7wq5iQ.jpg', 7),
  (11, 'The Godfather: Part II', 'Francis Ford Coppola', 'The pre-sequel', '1974-12-20', 'https://i.imgur.com/BQkqHGb.jpg', 7),
  (12, '12 Angry Men', 'Sidney Lumet', '12 angry men talk in a room for 1 hour and 36 minutes', '1957-04-13', 'https://i.imgur.com/9iLXaRR.jpg', 7),
  (13, 'The Good, the Bad, and the Ugly', 'Sergio Leone', 'Pretty good standoff at the end', '1967-12-29', 'https://i.imgur.com/RbvCZ09.jpg', 3),
  (14, 'Forrest Gump', 'Robert Zemeckis', 'Stupid is as stupid does', '1994-07-06', 'https://i.imgur.com/VmO464D.jpg', 1),
  (15, 'One Flew Over the Cuckoo\'s Nest', 'Milos Forman', 'Doc from Back to the Future finally gets put in the loony bin', '1975-11-19', 'https://i.imgur.com/dT1bPzC.jpg', 1),
  (16, 'Spirited Away', 'Hayao Miyazaki', 'Little girl fights the 7 deadly sins', '2003-03-28', 'https://i.imgur.com/gfdSlRZ.jpg', 4);
/*!40000 ALTER TABLE `MOVIES`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RATING`
--

DROP TABLE IF EXISTS `RATING`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RATING` (
  `RATING_ID`   INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` INT(11) UNSIGNED NOT NULL,
  `MOVIE_ID`    INT(11) UNSIGNED NOT NULL,
  `RATING_NUM`  INT(11) UNSIGNED NOT NULL,
  `RATING_DATE` DATE             NOT NULL,
  PRIMARY KEY (`RATING_ID`),
  UNIQUE KEY `RATING_RATING_ID_uindex` (`RATING_ID`),
  KEY `RATING_CUSTOMER_CUSTOMER_ID_fk` (`CUSTOMER_ID`),
  KEY `RATING_MOVIES_MOIVE_ID_fk` (`MOVIE_ID`),
  CONSTRAINT `RATING_CUSTOMER_CUSTOMER_ID_fk` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `CUSTOMER` (`CUSTOMER_ID`),
  CONSTRAINT `RATING_MOVIES_MOIVE_ID_fk` FOREIGN KEY (`MOVIE_ID`) REFERENCES `MOVIES` (`MOVIE_ID`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RATING`
--

LOCK TABLES `RATING` WRITE;
/*!40000 ALTER TABLE `RATING`
  DISABLE KEYS */;
INSERT INTO `RATING`
VALUES (1, 1, 1, 4, '2016-12-30'), (2, 1, 3, 4, '2016-12-30');
/*!40000 ALTER TABLE `RATING`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2016-10-23  1:36:20
