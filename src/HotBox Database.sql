-- Creates database
if db_id('HotBox') is not null 
begin 
	use master 
	drop database HotBox
end

create database HotBox
go

USE HotBox
go




-- Crear las Tablas
CREATE TABLE ACTORS
(
        ACTOR_ID			char(6) 		primary key,
        ACTOR_NAME			varchar(40) 	NOT NULL,
        ACTOR_IMAGE			varchar(40)		NOT NULL,
		ACTOR_BIO			varchar(255)	NOT NULL
)
go


CREATE TABLE CUSTOMER
(
        CUSTOMER_ID			char(6) 		primary key,
        CUSTOMER_NAME		varchar(40) 	NOT NULL,
        CUSTOMER_PASSWORD	varchar(40)		NOT NULL,
		CUSTOMER_BALANCE	decimal(6,2)	NOT NULL,
		CUSTOMER_ROOMNUM	char(4)			NOT NULL	

)
go

CREATE TABLE MOVIES
(
        MOVIE_ID			char(6) 		primary key,
        MOVIE_TITLE			varchar(40) 	NOT NULL,
        MOVIE_DIRECTOR		varchar(40)		NOT NULL,
		MOVIE_DESCRIPTION	varchar(255)	NOT NULL,
		MOVIE_RELEASE_DATE	date			NOT NULL,
		MOVIE_IMAGE			varchar(40)		NOT NULL,
		MOVIE_AVG_RATING	decimal(2,1)	NOT NULL
)
go

CREATE TABLE CASTING
(
        CASTING_ID			char(6) 		primary key,
        ACTOR_ID			char(6) 		References ACTORS,
        MOVIE_ID			char(6)			References MOVIES
)
go

CREATE TABLE RATING
(
        RATING_ID			char(6)			primary key,
		CUSTOMER_ID			char(6)			references CUSTOMER,
		MOVIE_ID			char(6)			references MOVIES,
		RATING_NUM			int				NOT NULL,
		RATING_DATE			date			NOT NULL
)
go

CREATE TABLE GENRE
(
        GENRE_ID			char(6)			primary key,
		GENRE_NAME			varchar(40)		NOT NULL
)
go

CREATE TABLE CUSTOMER_RENTALS
(
        RENTAL_ID			char(6)			primary key,
		CUSTOMER_ID			char(6)			references CUSTOMER,
		MOVIE_ID			char(6)			references MOVIES,
		RENTAL_DATE			datetime		NOT NULL
)
go