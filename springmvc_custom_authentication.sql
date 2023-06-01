create database springboot_custom_login;
use springboot_custom_login;

create table users(
  user_id int auto_increment,
  first_name varchar(50) not null,
  last_name varchar(50) not null,
  email varchar(255) not null,
  password varchar(255) not null,
  user_type varchar(20) not null,
  verification_token varchar(255) default null,
  active int default 0,
  verified int default 0,
  verified_on datetime null,
  created_at timestamp default current_timestamp() not null,
  update_at timestamp default current_timestamp() not null,
  constraint pk_user_id primary key(user_id)
);








