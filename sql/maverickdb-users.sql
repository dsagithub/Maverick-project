drop user 'maverick'@'localhost';
create user 'maverick'@'localhost' identified by 'maverick';
grant all privileges on maverickdb.* to 'maverick'@'localhost';
flush privileges;