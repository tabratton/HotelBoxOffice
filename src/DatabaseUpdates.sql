USE `HotelBoxOffice` ;
-- Add genre field
ALTER TABLE HotelBoxOffice.MOVIES ADD GENRE_ID INT(11) UNSIGNED NOT NULL;
ALTER TABLE HotelBoxOffice.MOVIES
  ADD CONSTRAINT MOVIES_GENRE_GENRE_ID_fk
FOREIGN KEY (GENRE_ID) REFERENCES GENRE (GENRE_ID);

-- Insert genres
INSERT INTO `GENRE` (`GENRE_NAME`)
VALUES
  ('Action'),
  ('Animation'),
  ('Family'),
  ('Romance'),
  ('Drama'),
  ('Sports'),
  ('Science fiction'),
  ('Fiction');


-- Update movies genre
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 1;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 2;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 3;
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 4;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 5;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 6;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 7;
UPDATE MOVIES SET GENRE_ID = 9 WHERE  MOVIE_ID = 8;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 9;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 10;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 11;
UPDATE MOVIES SET GENRE_ID = 7 WHERE  MOVIE_ID = 12;
UPDATE MOVIES SET GENRE_ID = 3 WHERE  MOVIE_ID = 13;
UPDATE MOVIES SET GENRE_ID = 1 WHERE  MOVIE_ID = 14;
UPDATE MOVIES SET GENRE_ID = 1 WHERE  MOVIE_ID = 15;
UPDATE MOVIES SET GENRE_ID = 4 WHERE  MOVIE_ID = 16;


-- Update actors pictures
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/3xGWVcy.jpg' WHERE
  ACTOR_ID = 1;
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/xXYWqrn.jpg' WHERE
  ACTOR_ID = 2;
UPDATE ACTORS SET ACTOR_IMAGE = 'https://i.imgur.com/tEr10ut.jpg' WHERE
  ACTOR_ID = 3;

-- Update Joseph Gordan-Levitt's name to include the '-'.
UPDATE ACTORS SET ACTOR_NAME = 'Joseph Gordan-Levitt' WHERE ACTOR_ID = 2;

-- Insert actors
INSERT INTO `ACTORS` (`ACTOR_NAME`,`ACTOR_IMAGE`,`ACTOR_BIO`)
VALUES
  -- Rogue One Actors
  ('Felicity Jones','https://i.imgur.com/2sa3kwY.jpg','English actress'),
  ('Mads Mikkelsen','https://i.imgur.com/wntU1fk.jpg','Danish actor'),
  ('Donnie Yen','https://i.imgur.com/FhNubJG.jpg','Hong Kong actor'),
  ('Forest Whitaker','https://i.imgur.com/7HyDJng.jpg','American actor'),
  ('Diego Luna', 'https://i.imgur.com/5CEIiw3.jpg', 'Mexican actor'),
  ('James Earl Jones', 'https://i.imgur.com/3eR8Ik9.jpg', 'American actor'),
  -- Magnificent Seven Actors
  ('Denzel Washington','https://i.imgur.com/f601H7X.jpg','American actor'),
  ('Chris Pratt','https://i.imgur.com/JgDhBL7.jpg','American actor'),
  ('Ethan Hawke','https://i.imgur.com/WY3y5Ab.jpg','American actor'),
  ('Lee Byung-hun','https://i.imgur.com/oLxwXDD.jpg','South Korean actor'),
  ('Vincent D''Onofrio', 'https://i.imgur.com/iy08bbq.jpg', 'American actor'),
  -- Captain America: Civil War Actors
  ('Chris Evans','https://i.imgur.com/TCMNXRn.jpg','American actor'),
  ('Sebastian Stan','https://i.imgur.com/lKT7u1f.jpg','Romanian-American actor'),
  ('Robert Downey Jr.','https://i.imgur.com/1a7xOdt.jpg','American actor'),
  ('Scarlett Johansson','https://i.imgur.com/W1POt4y.jpg','American actress'),
  ('Jeremy Renner','https://i.imgur.com/AdxFHGf.jpg','American actor'),
  -- Star Wars Episode VIII
  ('Daisy Ridley','https://i.imgur.com/UcETFMW.jpg','English actress'),
  ('Oscar Isaac','https://i.imgur.com/FVDblYH.jpg','Guatemalan-American actor'),
  ('Adam Driver','https://i.imgur.com/tenvO0M.jpg','American actor'),
  ('Mark Hamill','https://i.imgur.com/S3iOaah.jpg','American actor'),
  ('John Boyega','https://i.imgur.com/XfaD3lo.jpg','English actor'),
  -- Snowden
  ('Shailene Woodley','https://i.imgur.com/3p55zsC.jpg','American actress'),
  ('Zachary Quinto','https://i.imgur.com/KR2x8BO.jpg','American actor'),
  ('Nicolas Cage','https://i.imgur.com/2Qkwe2i.jpg','American actor'),
  ('Melissa Leo','https://i.imgur.com/ksbTmti.jpg','American actress'),
  -- Men in Black
  ('Tommy Lee Jones','https://i.imgur.com/bFSQgzO.jpg','American actor'),
  ('Linda Fiorentino','https://i.imgur.com/MS0r2ML.jpg','American actress'),
  ('Rip Torn','https://i.imgur.com/iM11WQz.png','American actor'),
  -- Se7en
  ('Morgan Freeman','https://i.imgur.com/qVf9umF.jpg','American actor'),
  ('Kevin Spacey','https://i.imgur.com/6oLcbmK.jpg','American actor'),
  ('Gwyneth Paltrow','https://i.imgur.com/d1FvIxc.jpg','American actress'),
  ('John C. McGinley','https://i.imgur.com/Mi9DTFj.jpg','American actor'),
  -- Star Wars: Episode VII: The Force Awakens
  ('Harrison Ford','https://i.imgur.com/0fCjAhr.jpg','American actor'),
  -- The Shawshank Redemption
  ('Tim Robbins','https://i.imgur.com/2ZnOLGW.jpg','American actor'),
  ('Bob Gunton','https://i.imgur.com/QY3kFry.jpg','American actor'),
  ('Clancy Brown','https://i.imgur.com/hawKFoG.jpg','American actor'),
  ('William Sadler','https://i.imgur.com/xE9voRx.jpg','American actor'),
  -- The Godfather
  ('Al Pacino','https://i.imgur.com/7RyxS6Z.jpg','American actor'),
  ('Marlon Brando','https://i.imgur.com/MUHgqZ8.jpg','American actor'),
  ('Robert Duvall','https://i.imgur.com/XzPUTYv.jpg','American actor'),
  ('James Caan','https://i.imgur.com/UFeREju.jpg','American actor'),
  ('Talia Shire', 'https://i.imgur.com/vPTrL5X.jpg', 'Italian-American actress'),
  -- The Godfather Part II
  ('Robert De Niro', 'https://i.imgur.com/Ljpaahk.jpg', 'American actor'),
  ('Diane Keaton','https://i.imgur.com/uYd3TJs.jpg','American actress'),
  -- 12 Angry Men
  ('Henry Fonda','https://i.imgur.com/iP0HJ09.jpg','American actor'),
  ('Lee J. Cobb','https://i.imgur.com/Qe34stE.jpg','American actor'),
  ('Martin Balsam','https://i.imgur.com/2xpvBnf.jpg','American actor'),
  ('Jack Warden','https://i.imgur.com/5J4KHlI.jpg','American actor'),
  ('E.G. Marshall','https://i.imgur.com/Ov4NReH.jpg','American actor'),
  -- The Good, the Bad, and the Ugly
  ('Clint Eastwood','https://i.imgur.com/mpda8MZ.jpg','American actor'),
  ('Eli Wallach','https://i.imgur.com/RDM4lnx.jpg','American actor'),
  ('Lee Van Cleef','https://i.imgur.com/IHYAXKw.jpg','American actor'),
  -- Forrest Gump
  ('Tom Hanks','https://i.imgur.com/vW1xhq1.jpg','American actor'),
  ('Sally Field','https://i.imgur.com/QhTWtte.jpg','American actress'),
  ('Robin Wright','https://i.imgur.com/4k92kXo.jpg','American actress'),
  ('Gary Sinise','https://i.imgur.com/4VIDlit.jpg','American actor'),
  ('Mykelti Williamson','https://i.imgur.com/EbHNRGa.jpg','American actor'),
  -- One Flew Over the Cuckoo's Nest
  ('Jack Nicholson','https://i.imgur.com/CtmXuOn.jpg','American actor'),
  ('Louise Fletcher','https://i.imgur.com/v0HQmhz.png','American actor'),
  ('Danny DeVito','https://i.imgur.com/WWDdVoO.jpg','American actor'),
  ('Christopher Lloyd','https://i.imgur.com/IAneYME.jpg','American actor'),
  ('William Redfield','https://i.imgur.com/sSlpe63.jpg','American actor'),
  -- Spirited Away
  ('Rumi Hiiragi','https://i.imgur.com/h8ahGD7.jpg','Japanese actress'),
  ('Miyu Irino','https://i.imgur.com/Qic2X9C.png','Japanese actor'),
  ('Mari Natsuki','https://i.imgur.com/qhnuTEM.png','Japanese actress'),
  ('Bunta Sugawara','https://i.imgur.com/OZeuqf6.jpg','Japanese actor'),
  ('Tatsuya Gashuin','https://i.imgur.com/rEslV4T.png','Japanese actor');

-- Change UNIQUE key of ACTOR_ID in CASTING table
ALTER TABLE `CASTING`
  DROP FOREIGN KEY `CASTING_ACTORS_ID_fk`;
ALTER TABLE `CASTING`
  DROP INDEX `CASTING_ACTOR_ID_uindex`;
ALTER TABLE `CASTING`
  ADD KEY `CASTING_ACTOR_ID_fk` (`ACTOR_ID`);
ALTER TABLE `CASTING`
  ADD CONSTRAINT `CASTING_ACTOR_ID_fk`
FOREIGN KEY (`ACTOR_ID`)
REFERENCES `ACTORS` (`ACTOR_ID`);

-- Update already added actors to have correct movie references
UPDATE CASTING SET MOVIE_ID = 6 WHERE CASTING_ID = 1;
UPDATE CASTING SET MOVIE_ID = 5 WHERE CASTING_ID = 2;
UPDATE CASTING SET MOVIE_ID = 7 WHERE CASTING_ID = 3;

-- Insert casting
INSERT INTO `CASTING` (`ACTOR_ID`,`MOVIE_ID`)
VALUES
  -- Rogue One
  (4,1),
  (5,1),
  (6,1),
  (7,1),
  (8,1),
  (9,1),
  -- Magnificent Seven
  (10,2),
  (11,2),
  (12,2),
  (13,2),
  (14,2),
  -- Captain America: Civil War
  (15,3),
  (16,3),
  (17,3),
  (18,3),
  (19,3),
  -- Star Wars: Episode VIII
  (20,4),
  (21,4),
  (22,4),
  (23,4),
  (24,4),
  -- Snowden
  (25,5),
  (26,5),
  (27,5),
  (28,5),
  -- Men in Black
  (29,6),
  (30,6),
  (31,6),
  (14,6),
  -- Se7en
  (32,7),
  (33,7),
  (34,7),
  (35,7),
  -- Star Wars: Episode VII: The Force Awakens
  (20,8),
  (21,8),
  (22,8),
  (24,8),
  (36,8),
  -- The Shawshank Redemption
  (32,9),
  (37,9),
  (38,9),
  (39,9),
  (40,9),
  -- The Godfather
  (41,10),
  (42,10),
  (43,10),
  (44,10),
  (45,10),
  -- The Godfather Part II
  (46,11),
  (41,11),
  (43,11),
  (45,11),
  (47,11),
  -- 12 Angry Men
  (48,12),
  (49,12),
  (50,12),
  (51,12),
  (52,12),
  -- The Good, the Bad, and the Ugly
  (53,13),
  (54,13),
  (55,13),
  -- Forrest Gump
  (56,14),
  (57,14),
  (58,14),
  (59,14),
  (60,14),
  -- One Flew Over the Cuckoo's Nest
  (61,15),
  (62,15),
  (63,15),
  (64,15),
  (65,15),
  -- Spirited Away
  (66,16),
  (67,16),
  (68,16),
  (69,16),
  (70,16);
