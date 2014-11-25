insert into users values('felipe', MD5('felipe'),  'felipe@maverick.com','Felipe', 'Hola me llamo Felipe');
insert into user_roles values ('felipe', 'artist');

insert into users values('cañada', MD5('cañada'),  'cañada@maverick.com','David Cañada', 'Hola me llamo David Cañada');
insert into user_roles values ('cañada', 'artist');

insert into users values('raja', MD5('raja'),  'raja@maverick.com','David Raja', 'Hola me llamo David Raja');
insert into user_roles values ('raja', 'artist');

insert into users values('david', MD5('david'),  'david@maverick.com','David', 'Hola me llamo David');
insert into user_roles values ('david', 'artist');

insert into Songs (username, song_name, album_name, description, style, likes) values('david', 'maverick', 'albert neve', 'New song', 'edm', 1);
insert into  Follow values('felipe','david');
insert into  Follow values('david','felipe');
