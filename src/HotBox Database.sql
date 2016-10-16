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


-- Create actors table
CREATE TABLE ACTORS
(
        ACTOR_ID			char(6) 		primary key,
        ACTOR_NAME			varchar(40) 	NOT NULL,
        ACTOR_IMAGE			varchar(40)		NOT NULL,
		ACTOR_BIO			varchar(255)	NOT NULL
)
go

-- Insert actors

INSERT INTO ACTORS VALUES ('A00001', 'Will Smith', 'xx', 'American actor')
INSERT INTO ACTORS VALUES ('A00002', 'Joseph Gordon Levitt', 'xx', 'American actor')
INSERT INTO ACTORS VALUES ('A00003', 'Brad Pitt', 'xx', 'American actor')

-- Actors search
SELECT *FROM ACTORS




-- Create customer table
CREATE TABLE CUSTOMER
(
        CUSTOMER_ID			char(6) 		primary key,
        CUSTOMER_NAME		varchar(40) 	NOT NULL,
        CUSTOMER_PASSWORD	varchar(40)		NOT NULL,
		CUSTOMER_BALANCE	decimal(6,2)	NOT NULL,
		CUSTOMER_ROOMNUM	char(4)			NOT NULL	

)
go

-- Insert customer

INSERT INTO CUSTOMER VALUES ('C00001', 'Vilma', 'abc123','12.99','1001')
INSERT INTO CUSTOMER VALUES ('C00002', 'Chad', 'abc123','12.99','1002')
INSERT INTO CUSTOMER VALUES ('C00003', 'Tyler', 'abc123','12.99','1003')


-- Customer search
SELECT *FROM CUSTOMER

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

-- Insert movies

INSERT INTO MOVIES VALUES ('M00001', 'xxx', 'aaa','bbbb','10/10/2010','xxxx',3.0)
INSERT INTO MOVIES VALUES ('M00002', 'xxx', 'aaa','bbbb','10/10/2010','xxxx',2.0)
INSERT INTO MOVIES VALUES ('M00003', 'xxx', 'aaa','bbbb','10/10/2010','xxxx',1.0)


-- Movies search
SELECT *FROM MOVIES


CREATE TABLE CASTING
(
        CASTING_ID			char(6) 		primary key,
        ACTOR_ID			char(6) 		References ACTORS,
        MOVIE_ID			char(6)			References MOVIES
)
go

-- Insert casting

INSERT INTO CASTING VALUES ('CA0001', 'A00001', 'M00003')
INSERT INTO CASTING VALUES ('CA0002', 'A00003', 'M00002')
INSERT INTO CASTING VALUES ('CA0003', 'A00001', 'M00001')


-- Casting search
SELECT *FROM CASTING

CREATE TABLE RATING
(
        RATING_ID			char(6)			primary key,
		CUSTOMER_ID			char(6)			references CUSTOMER,
		MOVIE_ID			char(6)			references MOVIES,
		RATING_NUM			int				NOT NULL,
		RATING_DATE			date			NOT NULL
)
go

--Insert Rating
INSERT INTO RATING VALUES ('R00001', 'C00001','M00001',4,'12/30/2016')
INSERT INTO RATING VALUES ('R00002', 'C00001','M00003',4,'12/30/2016')

--Search Rating
SELECT *FROM RATING

CREATE TABLE GENRE
(
        GENRE_ID			char(6)			primary key,
		GENRE_NAME			varchar(40)		NOT NULL
)
go

--Insert genre
INSERT INTO GENRE VALUES ('G00001','Comedy')
INSERT INTO GENRE VALUES ('G00002','Horror')

--Search genre
SELECT *FROM GENRE

CREATE TABLE CUSTOMER_RENTALS
(
        RENTAL_ID			char(6)			primary key,
		CUSTOMER_ID			char(6)			references CUSTOMER,
		MOVIE_ID			char(6)			references MOVIES,
		RENTAL_DATE			datetime		NOT NULL
)
go

--Insert customer rentals
INSERT INTO CUSTOMER_RENTALS VALUES ('CR0001', 'C00001','M00001','12/30/2016')
INSERT INTO CUSTOMER_RENTALS VALUES ('CR0002', 'C00001','M00001','12/30/2016')

--Search customer rentals
SELECT *FROM CUSTOMER_RENTALS