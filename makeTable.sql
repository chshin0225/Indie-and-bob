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