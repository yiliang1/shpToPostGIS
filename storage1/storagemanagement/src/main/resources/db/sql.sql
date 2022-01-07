create table user
(
  id   INTEGER not null
  primary key
  autoincrement,
  username varchar(50),
	password  varchar(100),
	realname  varchar(50)
);

DROP TABLE IF EXISTS `common_user`;
CREATE TABLE `common_user` (
  id   INTEGER not null
  primary key
  autoincrement,
  username varchar(50),
	password  varchar(100),
	realname  varchar(50),
  state varchar(255),
  gender varchar(255),
  birth varchar(255)
);

DROP TABLE IF EXISTS `common_role`;
CREATE TABLE `common_role` (
  id INTEGER not null  primary key autoincrement,
  name varchar(255) not null
);

DROP TABLE IF EXISTS `common_user_role`;
CREATE TABLE `common_user_role` (
  id INTEGER NOT NULL primary key autoincrement,
  user_id INTEGER not null,
  role_id INTEGER not null,
  CONSTRAINT `common_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `common_role` (`id`),
  CONSTRAINT `common_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `common_user` (`id`)
) ;


create table storage_record
(
  id   INTEGER not null
  primary key
  autoincrement,
  shpname varchar(100),
	layer_name  varchar(100),
	file_position varchar(500),
	encoding varchar(20),
	time varchar(30),
	status INTEGER
);

select * from user where id = ?