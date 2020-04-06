DROP TABLE IF EXISTS USER;
create table if not exists USER (
USER_ID int not null primary key auto_increment,
USER_NAME varchar(100),
USER_AGE int,
CREATE_TIME DATE,
MODIFY_TIME DATE,
USER_STATUS VARCHAR(1));
