insert into users values('felipe', MD5('felipe'),  'felipe@maverick.com','Felipe', 'Hola me llamo Felipe');
insert into user_roles values ('felipe', 'artist');

insert into users values('canada', MD5('canada'),  'cañada@maverick.com','David Cañada', 'Hola me llamo David Cañada');
insert into user_roles values ('canada', 'artist');

insert into users values('raja', MD5('raja'),  'raja@maverick.com','David Raja', 'Hola me llamo David Raja');
insert into user_roles values ('raja', 'artist');

insert into users values('david', MD5('david'),  'david@maverick.com','David', 'Hola me llamo David');
insert into user_roles values ('david', 'artist');


insert into comments (commentid, songid, song_name, username, text) values (1, 'id', 'maverick', 'felipe', 'Estamos dentro');

insert into  follow values('felipe','david');
insert into  follow values('felipe','canada');
insert into  follow values('canada','felipe');
insert into  follow values('canada','raja');
insert into  follow values('david','felipe');
insert into  follow values('david','raja');
insert into  follow values('raja','david');
insert into  follow values('raja','felipe');
