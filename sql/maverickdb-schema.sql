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

create table Songs (
	songid int not null auto_increment unique,
	username	varchar(20) not null,
	song_name	varchar(100) not null,
	album_name varchar(100),
	description	varchar(500),
	style	varchar(50) not  null,
	last_modified	timestamp,
	likes int not null,
	
	foreign key(username) references users(username),
	primary key (username, song_name),
	index(songid)
);
create table comments (
commentid int not null auto_increment unique,
songid int not null,
username varchar(50) not null,
text varchar(500),
time timestamp,
foreign key(username) references users (username) on delete cascade,
foreign key(songid) references songs (songid) on delete cascade

);



create table Follow(
	followingname varchar(20),
	followername varchar(20),
	foreign key(followingname) references users(username) on delete cascade,
	foreign key(followername) references users(username) on delete cascade,
	primary key (followingname, followername)
);