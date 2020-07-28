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
)default charset=utf8 collate utf8_general_ci;

create table genre(
	genre_id varchar(50) NOT NULL,
    genre_name varchar(200) NOT NULL,
    primary key(`genre_id`)
)default charset=utf8 collate utf8_general_ci;

create table game_genre(
	game_id varchar(50) NOT NULL,
    genre_id varchar(50) NOT NULL
)default charset=utf8 collate utf8_general_ci;

create table follow(
	follower varchar(100) NOT NULL,
    followed varchar(100) NOT NULL
)default charset=utf8 collate utf8_general_ci;

create table game_like(
	email varchar(100) NOT NULL,
    game_id varchar(50) NOT NULL
)default charset=utf8 collate utf8_general_ci;

create table game_detail(
	game_id varchar(50) NOT NULL,
    content varchar(4000),
    d_stage int NOT NULL,
    primary key(`game_id`)
)default charset=utf8 collate utf8_general_ci;

drop table `user`;
CREATE TABLE `user`(
	`email` varchar(50) NOT NULL,
    `name` varchar(20) NOT NULL,
    `nickname` varchar(20) NOT NULL,
    `password` varchar(20) NOT NULL,
    `postcode` int NOT NULL,
    `address` varchar(50) NOT NULL,
    `extra_address` varchar(50) NOT NULL,
    `bank_name` varchar(20),
    `bank_account` varchar(20),
    `is_developer` boolean NOT NULL,
    `phone_number` varchar(20),
    `profile` varchar(50),
    `introduction` varchar(100),
    primary key(`email`)
) default charset=utf8 collate utf8_general_ci;

DROP TABLE IF EXISTS `funding`;
CREATE TABLE `funding`(
	`funding_id` varchar(10) NOT NULL,
    `game_id` varchar(10) NOT NULL,
    `email` varchar(50) NOT NULL,
    `money` int,
    `funded_at` datetime default now(),
    primary key(`funding_id`)
)default charset=utf8 collate utf8_general_ci;

DROP TABLE IF EXISTS `game`;
CREATE TABLE `game`(
    `game_id` int auto_increment,
    `name` varchar(30) NOT NULL,
    `deadline` datetime NOT NULL,
    `aim` int,
    `left_price` int,
    `is_end` boolean,
    `is_approve` boolean default false,
    `email` varchar(50) NOT NULL,
    `thumbnail` varchar(100) NOT NULL,
    `created_at` datetime default now(),
    `updated_at` datetime default now(),
    `started_at` datetime,
    primary key(`game_id`)
)default charset=utf8 collate utf8_general_ci;