DROP TABLE IF EXISTS hotel;
create table if not exists hotel (
id int not null primary key auto_increment,
name varchar(100),
address varchar(100),
state varchar(100),
zip varchar(100),
country varchar(100),
country int(24));

DROP TABLE IF EXISTS customer;
create table if not exists customer (
id int not null primary key auto_increment,
username varchar(100),
password varchar(100),
name varchar(100));

-- DROP TABLE IF EXISTS booking;
-- create table if not exists booking (
-- id int not null primary key auto_increment,
-- username varchar(100),
-- password varchar(100),
-- name varchar(100));
