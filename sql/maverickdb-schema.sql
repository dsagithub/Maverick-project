drop database if exists maverickdb;
create database maverickdb;
 
use maverickdb;
 
create table users (
	username	varchar(20) not null primary key,
	userpass	char(32) not null,
	email		varchar(255) not null,
	name	varchar(70) not null,
	description	varchar(500) not  null
);
 
create table user_roles (
	username	varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);

create table songs (
	songid varchar(50) not null,
	username	varchar(20) not null,
	song_name	varchar(100) not null unique,
	album_name varchar(100),
	description	varchar(500),
	style	varchar(50) not  null,
	last_modified	timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	likes int not null,
	
	foreign key(username) references users(username),
	primary key (username, song_name),
	index(songid)
);
create table comments (
	commentid int not null auto_increment unique,
	songid varchar(50) not null,
	song_name varchar(100) not null,
	username varchar(50) not null,
    text varchar(500),
    time timestamp,
    foreign key(username) references users (username) on delete cascade,
	foreign key(song_name) references songs (song_name) on delete cascade

);



create table follow(
	followingname varchar(20),
	followername varchar(20),
	foreign key(followingname) references users(username) on delete cascade,
	foreign key(followername) references users(username) on delete cascade,
	primary key (followingname, followername)
);
