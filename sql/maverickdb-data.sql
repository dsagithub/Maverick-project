insert into users values('felipe', MD5('felipe'),  'felipe@maverick.com','Felipe', 'Hola me llamo Felipe');
insert into user_roles values ('felipe', 'artist');

insert into users values('ca�ada', MD5('ca�ada'),  'ca�ada@maverick.com','David Ca�ada', 'Hola me llamo David Ca�ada');
insert into user_roles values ('ca�ada', 'artist');

insert into users values('raja', MD5('raja'),  'raja@maverick.com','David Raja', 'Hola me llamo David Raja');
insert into user_roles values ('raja', 'artist');

insert into users values('david', MD5('david'),  'david@maverick.com','David', 'Hola me llamo David');
insert into user_roles values ('david', 'artist');

insert into Songs (username, song_name, album_name, description, style, likes) values('david', 'maverick', 'albert neve', 'New song', 'edm', 1);
insert into  Follow values('felipe','david');
insert into  Follow values('david','felipe');
