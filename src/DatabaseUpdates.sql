UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/fLgqSsR.jpg' WHERE
  MOVIE_ID = 1;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/CDTXtPS.jpg' WHERE
  MOVIE_ID = 2;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/ZAlO2Eq.jpg' WHERE
  MOVIE_ID = 3;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/jmQenR4.jpg' WHERE
  MOVIE_ID = 4;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/bWBzsjD.jpg' WHERE
  MOVIE_ID = 5;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/ev6eRey.jpg' WHERE
  MOVIE_ID = 6;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/SltvaDv.jpg' WHERE
  MOVIE_ID = 7;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/sY6KAhj.jpg' WHERE
  MOVIE_ID = 8;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/ldKX4wu.jpg' WHERE
  MOVIE_ID = 9;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/a7wq5iQ.jpg' WHERE
  MOVIE_ID = 10;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/BQkqHGb.jpg' WHERE
  MOVIE_ID = 11;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/9iLXaRR.jpg' WHERE
  MOVIE_ID = 12;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/RbvCZ09.jpg' WHERE
  MOVIE_ID = 13;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/VmO464D.jpg' WHERE
  MOVIE_ID = 14;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/dT1bPzC.jpg' WHERE
  MOVIE_ID = 15;
UPDATE MOVIES SET MOVIE_IMAGE = 'https://i.imgur.com/gfdSlRZ.jpg' WHERE
  MOVIE_ID = 16;

--Add genre field
ALTER TABLE MOVIES
  ADD `GENRE_ID` int(11) unsigned NOT NULL
  ADD KEY `MOVIES_GENRE_GENRE_ID_fk` (`GENRE_ID`)
  ADD CONSTRAINT MOVIES_GENRE_GENRE_ID_fk
    FOREIGN KEY (GENRE_ID) 
    REFERENCES GENRE(GENRE_ID);

-- Insert genres
INSERT INTO `GENRE` (`GENRE_NAME`)
VALUES ('Action'), ('Animation'),('Family'),('Romance'),('Drama'),('Sports'),('Science fiction'),('Fiction'); 


--Update movies genre
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 1;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 2;
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 3;
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 4;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 5;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 6;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 7;
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 8;
UPDATE MOVIES SET GENRE_ID = 1 WHERE  MOVIE_ID = 9;
UPDATE MOVIES SET GENRE_ID = 2 WHERE  MOVIE_ID = 10;
UPDATE MOVIES SET GENRE_ID = 4 WHERE  MOVIE_ID = 11;
UPDATE MOVIES SET GENRE_ID = 5 WHERE  MOVIE_ID = 12;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 13;
UPDATE MOVIES SET GENRE_ID = 1 WHERE  MOVIE_ID = 14;
UPDATE MOVIES SET GENRE_ID = 10 WHERE  MOVIE_ID = 15;
UPDATE MOVIES SET GENRE_ID = 4 WHERE  MOVIE_ID = 16;


--Update actors pictures
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/gIFFX1d.jpg' WHERE
  ACTOR_ID = 1;
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/DyzdmKG.jpg' WHERE
  ACTOR_ID = 2;
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/hES7D98.jpg' WHERE
  ACTOR_ID = 3;

--Insert actors
INSERT INTO `ACTORS` (`ACTOR_ID`,`ACTOR_NAME`,`ACTOR_IMAGE`,`ACTOR_BIO`)
VALUES (4,'David Prowse','https://i.imgur.com/mWycyfg.jpg','English actor'), 
(5,'Carrie Fisher','https://i.imgur.com/TBTyrKx.jpg','American actress'),
(6,'Mark Hamill','https://i.imgur.com/b5AEcKf.jpg','American actor'),
(7,'Harrison Ford','https://i.imgur.com/bubpcpG.jpg','American actor'),
(8,'Denzel Washington','https://i.imgur.com/xR1qirw.jpg','American actor'),
(9,'Chris Pratt','https://i.imgur.com/nBFijhV.jpg','American actor'),
(10,'Ethan Hawke','xx','American actor'),
(11,'Lee Byung-hun','xx','American actor'),
(12,'Chris Evans','xx','American actor'),
(13,'Sebastian Stan','xx','American actor'),
(14,'Hayley Atwell','xx','American actor'),
(15,'Samuel L. Jackson','xx','American actor'),
(16,'Shailene Woodley','xx','American actress'),
(17,'Zachary Quinto','xx','American actor'),
(18,'Nicolas Cage','xx','American actor'),
(19,'Tommy Lee Jones','xx','American actor'),
(20,'Linda Florentino','xx','American actress'),
(21,'Vincent Donofrio','xx','American actor'),
(22,'Rip Tom','xx','American actor'),
(23,'Morgan Freeman','xx','American actor'),
(24,'Kevin Spacey','xx','American actor'),
(25,'Tim Robbins','xx','American actor'),
(26,'Bob Gunton','xx','American actor'),
(27,'Clancy Brown','xx','American actor'),
(28,'Al Pacino','xx','American actor'),
(29,'Marlon Brando','xx','American actor'),
(30,'Robert Duvall','xx','American actor'),
(31,'Henry Fonda','xx','American actor'),
(32,'Lee J. Cobb','xx','American actor'),
(33,'Martin Balsam','xx','American actor'),
(34,'Clint Eastwood','xx','American actor'),
(35,'Eli Wallach','xx','American actor'),
(36,'Lee Van Cleef','xx','American actor'),
(37,'Tom Hanks','xx','American actor'),
(38,'Michael Connor Humpheys','xx','American actor'),
(39,'Robin Wright','xx','American actor'),
(40,'Gary Sinise','xx','American actor'),
(41,'Jack Nicholson','xx','American actor'),
(42,'Louise Fletcher','xx','American actor'),
(43,'Danny De Vito','xx','American actor'),
(44,'Rumi Hiragi','xx','Japanese actress'),
(45,'Miyu Irino','xx','Japanese actor'),
(46,'Mari Natsuki','xx','Japanese singer and actress');

--Change UNIQUE key of ACTOR_ID in CASTING table
ALTER TABLE `CASTING`
   DROP FOREIGN KEY `CASTING_ACTORS_ID_fk`;
ALTER TABLE `CASTING`
   DROP INDEX `CASTING_ACTOR_ID_uindex`
ALTER TABLE `CASTING`
   ADD KEY `CASTING_ACTOR_ID_fk` (`ACTOR_ID`)
ALTER TABLE `CASTING`
   ADD CONSTRAINT `CASTING_ACTOR_ID_fk`
       FOREIGN KEY (`ACTOR_ID`) 
       REFERENCES `ACTORS` (`ACTOR_ID`);


--Insert casting
INSERT INTO `CASTING` (`CASTING_ID`,`ACTOR_ID`,`MOVIE_ID`)
VALUES (4,4,1),(5,5,1),(6,6,1),(7,7,1),(8,8,2),(9,9,2),(10,10,2),(11,11,2),
(12,12,3),(13,13,3),(14,14,3),(15,15,3),(16,4,4),(17,5,4),(18,6,4),(19,7,4),
(20,2,5),(21,16,5),(22,17,5),(23,18,5),(24,1,6),(25,19,6),(26,20,6),(27,21,6),
(28,22,6),(29,3,7),(30,23,7),(31,24,7),(32,4,8),(33,5,8),(34,6,8),(35,7,8),
(36,25,9),(37,23,9),(38,26,9),(39,27,9),(40,28,10),(41,29,10),(42,30,10),
(43,28,11),(44,29,11),(45,30,11),(46,31,12),(47,32,12),(48,33,12),(49,34,13),
(50,35,13),(51,36,13),(52,37,14),(53,38,14),(54,39,14),(55,40,14),(56,41,15),
(57,42,15),(58,43,15),(59,44,16),(60,45,16),(61,46,16);



