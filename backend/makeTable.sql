create database indie;

use indie;
#reward genre follow like
create table reward (
	reward_id varchar(50) NOT NULL,
    game_id varchar(50) NOT NULL,
    reward_name varchar(300),
    r_img varchar(300),
    left_price int,
    price int,
    content varchar(4000),
    primary key(`reward_id`)
); 

create table genre(
	genre_id varchar(50) NOT NULL,
    genre_name varchar(200) NOT NULL,
    primary key(`genre_id`)
);

create table game_genre(
	game_id varchar(50) NOT NULL,
    genre_id varchar(50) NOT NULL
);

create table follow(
	follower varchar(100) NOT NULL,
    followed varchar(100) NOT NULL
);

create table game_like(
	email varchar(100) NOT NULL,
    game_id varchar(50) NOT NULL
);

create table game_detail(
	game_id varchar(50) NOT NULL,
    content varchar(4000),
    d_stage int NOT NULL,
    primary key(`game_id`)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
	`email` varchar(50) NOT NULL,
    `name` varchar(20) NOT NULL,
    `password` varchar(20) NOT NULL,
    `address` varchar(100),
    `account` varchar(20),
    `is_developer` boolean NOT NULL,
    `phone_number` varchar(20),
    `profile` varchar(50),
    `introduction` varchar(100),
    primary key(`email`)
);

DROP TABLE IF EXISTS `funding`;
CREATE TABLE `funding`(
	`funding_id` varchar(10) NOT NULL,
    `game_id` varchar(10) NOT NULL,
    `email` varchar(50) NOT NULL,
    `money` int,
    `funded_at` datetime default now(),
    primary key(`funding_id`)
);

DROP TABLE IF EXISTS `game`;
CREATE TABLE `game`(
	`game_id` varchar(10) NOT NULL,
    `name` varchar(30) NOT NULL,
    `deadline` datetime NOT NULL,
    `aim` int,
    `left_price` int,
    `is_end` boolean,
    `email` varchar(50) NOT NULL,
    `thumbnail` varchar(100) NOT NULL,
    `created_at` datetime default now(),
    `updated_at` datetime default now(),
    primary key(`game_id`)
);