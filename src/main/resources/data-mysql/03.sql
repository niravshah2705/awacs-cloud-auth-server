insert into awacs_user(id, user_name, password, account_expired, account_locked, credentials_expired, enabled)
  VALUES (1, 'admin', /*admin1234*/'$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha', false, false, false, true);

insert into awacs_user(id, user_name, password, account_expired, account_locked, credentials_expired, enabled)
  VALUES (2, 'reader', /*reader1234*/'$2a$08$dwYz8O.qtUXboGosJFsS4u19LHKW7aCQ0LXXuNlRfjjGKwj5NfKSe', false, false, false, true);

insert into users_authorities(user_id, authority_id) values (1, 1);
insert into users_authorities(user_id, authority_id) values (1, 2);
insert into users_authorities(user_id, authority_id) values (1, 3);
insert into users_authorities(user_id, authority_id) values (1, 4);
insert into users_authorities(user_id, authority_id) values (1, 5);
insert into users_authorities(user_id, authority_id) values (1, 6);
insert into users_authorities(user_id, authority_id) values (1, 7);
insert into users_authorities(user_id, authority_id) values (1, 8);
insert into users_authorities(user_id, authority_id) values (1, 9);

insert into users_authorities(user_id, authority_id) values (2, 2);
insert into users_authorities(user_id, authority_id) values (2, 6);
