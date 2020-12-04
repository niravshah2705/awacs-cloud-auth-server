drop database awacs_cloud;
create database awacs_cloud;
use awacs_cloud;

-- set global explicit_defaults_for_timestamp=ON;
-- show global variables like '%timestamp%';

create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values (1000);

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

create table oauth_client_token (
  token_id varchar(255),
  token long varbinary,
  authentication_id varchar(255) primary key,
  user_name varchar(255),
  client_id varchar(255),
  created timestamp not null default CURRENT_TIMESTAMP,
  updated timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

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

create table oauth_refresh_token (
  token_id varchar(255),
  token long varbinary,
  authentication long varbinary,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

create table oauth_code (
  code varchar(255),
  authentication long varbinary,
  created timestamp default CURRENT_TIMESTAMP,
  updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

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

create table authority (
   id bigint not null auto_increment,
   name varchar(255),
   primary key (id),
   created timestamp default CURRENT_TIMESTAMP,
   updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP 
);

alter table authority add constraint authority_name unique(name);

create table awacs_user (
  id bigint not null auto_increment,
  email varchar(255) default null,
  msisdn varchar(255) default null,
  user_name varchar(255) default null,
  password varchar(255) default null,
  aadhar varchar (255) default null,
  account_expired tinyint(1) default null,
  account_locked tinyint(1) default null,
  credentials_expired tinyint(1) default null,
  enabled tinyint(1) default null,
  created timestamp not null default current_timestamp,
  updated timestamp not null default current_timestamp on update current_timestamp,
  primary key (id),
  unique key user_user_name (user_name),
  unique key user_email  (email),
  unique key user_msisdn (msisdn)
);

alter table awacs_user add constraint user_username_email_msisdn unique(user_name, email, msisdn, aadhar);

create table users_authorities (
   user_id bigint not null auto_increment,
   authority_id bigint not null,
   created timestamp default CURRENT_TIMESTAMP,
   updated timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
   primary key (user_id, authority_id)
);

alter table users_authorities add constraint users_authorities_authority foreign key (authority_id) references authority(id) on delete cascade;

alter table users_authorities add constraint users_authorities_awacs_user foreign key (user_id) references awacs_user(id) on delete cascade;

