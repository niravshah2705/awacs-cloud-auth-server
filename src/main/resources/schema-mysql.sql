
drop database awacs_cloud;
create database awacs_cloud;
use awacs_cloud;

-- set ON for https://dev.mysql.com/doc/refman/8.0/en/server-system-variables.html#sysvar_explicit_defaults_for_timestamp

drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id varchar(255) primary key, 
  resource_ids varchar(255), 
  client_secret varchar(255), 
  scope varchar(255), 
  authorized_grant_types varchar(255), 
  web_server_redirect_uri varchar(255), 
  authorities varchar(255), 
  access_token_validity integer, 
  refresh_token_validity integer, 
  additional_information varchar(4096), 
  autoapprove varchar(255),
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);
drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id varchar(255), 
  token long varbinary, 
  authentication_id varchar(255) primary key, 
  user_name varchar(255), 
  client_id varchar(255),
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);
drop table if exists oauth_access_token;
create table oauth_access_token (
  token_id varchar(255), 
  token long varbinary, 
  authentication_id varchar(255) primary key, 
  user_name varchar(255), 
  client_id varchar(255), 
  authentication long varbinary, 
  refresh_token varchar(255),
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);
drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  token_id varchar(255), 
  token long varbinary, 
  authentication long varbinary,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

drop table if exists users;
create table users (
  id bigint not null,
  account_non_expired bit(1) default null,
  account_non_locked bit(1) default null,
  credentials_non_expired bit(1) default null,
  email varchar(255) default null,
  enabled bit(1) default null,
  password varchar(255) default null,
  username varchar(255) default null,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  primary key (id)
);

ALTER TABLE users ADD INDEX (username);
ALTER TABLE users ADD INDEX (email);


drop table if exists oauth_code;
create table oauth_code (
  code varchar(255), 
  authentication long varbinary,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

drop table if exists oauth_approvals;
create table oauth_approvals (
  userid varchar(255), 
  clientid varchar(255), 
  scope varchar(255), 
  status varchar(10), 
  expiresat timestamp, 
  lastmodifiedat timestamp,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

drop table if exists clientdetails;

create table clientdetails (
  appid varchar(255) primary key, 
  resourceids varchar(255), 
  appsecret varchar(255), 
  scope varchar(255), 
  granttypes varchar(255), 
  redirecturl varchar(255), 
  authorities varchar(255), 
  access_token_validity integer, 
  refresh_token_validity integer, 
  additionalinformation varchar(4096), 
  autoapprovescopes varchar(255),
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

drop table if exists permission;

create table permission (
  id bigint not null,
  name varchar(255) default null,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP, 	
  primary key (id)
);



drop table if exists role;
create table role (
  id bigint not null,
  name varchar(255) default null,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP, 
  primary key (id)
);

drop table if exists role_user;

create table role_user (
  user_id bigint not null,
  role_id bigint not null,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP, 
  key (role_id),
  key (user_id),
  constraint foreign key (user_id) references users (id),
  constraint foreign key (role_id) references role (id)
);

drop table if exists permission_role;
 create table permission_role (
  role_id bigint not null,
  permission_id bigint not null,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  key (permission_id),
  key (role_id),
  constraint foreign key (permission_id) references permission (id),
  constraint foreign key (role_id) references role (id)
);

--drop table if exists user_authorities;
--create table user_authorities (
--    id        bigint(11) unsigned NOT NULL AUTO_INCREMENT,
--    user_id   bigint(11) unsigned NOT NULL,
--    authority varchar(50) not null,
--    created timestamp default CURRENT_TIMESTAMP,
--    updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
--    PRIMARY KEY (id),
--    UNIQUE KEY username_authorities_unique (user_id, authority)
--);
--
--ALTER TABLE user_authorities ADD CONSTRAINT fk_authorities FOREIGN KEY (user_id) REFERENCES users (id);

drop table if exists hibernate_sequence;

create table hibernate_sequence (next_val bigint);

insert into hibernate_sequence values (1);
