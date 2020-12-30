SET IDENTITY_INSERT awacs_user ON

-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled) VALUES (1, '9168116879','9999888877776666', 'dummy1', 'nobody1@nowhere.com', /*dummy*/'$2y$08$DBZWADccV5RFUuuyZXwMIO9y/TYt5PLE/D9w23UhgF8uvzdYd6Yv.', 1, 1, 1, 0);

-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled)  VALUES (2, '9168116880','9999888877776607','dummy2', 'nobody2@nowhere.com', /*dummy*/'$2y$08$DBZWADccV5RFUuuyZXwMIO9y/TYt5PLE/D9w23UhgF8uvzdYd6Yv.', 1, 1, 1, 0);

-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled)  VALUES (3, '9168116881','9999888877776608','dummy3', 'nobody3@nowhere.com', /*dummy*/'$2y$08$DBZWADccV5RFUuuyZXwMIO9y/TYt5PLE/D9w23UhgF8uvzdYd6Yv.', 1, 1, 1, 0);

-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled)  VALUES (100,'9168116882','9999888877776609', 'wakandagrpc', 'implicit@aiocdawacs.com', /*wakandagrpc*/'$2y$08$byB9gh2IvpDLA9p7URsp5OgY0ydCwmKxArUL5Jf4tJShBJW1ZlCI.', 0, 0, 0, 1);
  
-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled)  VALUES (101,'9168116883','9999888877776610', 'admin', 'girish.mahajan@aiocdawacs.com', /*admin1234*/'$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha', 0, 0, 0, 1);

-- insert into awacs_user(id, msisdn, aadhar, user_name, email, password, account_expired, account_locked, credentials_expired, enabled)  VALUES (102, '9168116884','9999888877776611','reader', 'balkrishna@aiocdawacs.com',/*reader1234*/'$2a$08$dwYz8O.qtUXboGosJFsS4u19LHKW7aCQ0LXXuNlRfjjGKwj5NfKSe', 0, 0, 0, 1);


SET IDENTITY_INSERT awacs_user OFF
  
SET IDENTITY_INSERT users_authorities ON

insert into users_authorities(user_id, authority_id) values (1, 12);		
insert into users_authorities(user_id, authority_id) values (1, 1);
insert into users_authorities(user_id, authority_id) values (1, 2);
insert into users_authorities(user_id, authority_id) values (1, 3);
insert into users_authorities(user_id, authority_id) values (1, 4);
insert into users_authorities(user_id, authority_id) values (1, 5);
insert into users_authorities(user_id, authority_id) values (1, 6);
insert into users_authorities(user_id, authority_id) values (1, 7);
insert into users_authorities(user_id, authority_id) values (1, 8);
insert into users_authorities(user_id, authority_id) values (1, 9);
insert into users_authorities(user_id, authority_id) values (1, 10);
insert into users_authorities(user_id, authority_id) values (1, 11);
insert into users_authorities(user_id, authority_id) values (2, 2);
insert into users_authorities(user_id, authority_id) values (2, 6);


SET IDENTITY_INSERT users_authorities OFF


-- SET IDENTITY_INSERT dbo.Users ON

-- INSERT INTO [dbo].[Users]([Id] ,[Username],	[Password], [Role], [Name],[Address1],[Address2],[Address3],[AwacsMTD]) VALUES ('1','admin','$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha','Customer','Girish Mahajan', '501 Nakshtra Galaxy', 'CHSL Majiwada', 'Maharashtra', 1);

-- INSERT INTO [dbo].[Users]([Id] ,[Username],	[Password], [Role], [Name],[Address1],[Address2],[Address3],[AwacsMTD]) VALUES ('2','wakandagrpc','$2y$08$byB9gh2IvpDLA9p7URsp5OgY0ydCwmKxArUL5Jf4tJShBJW1ZlCI.','Implicit','Grpc Internal User', 'Nobody', 'Nowhere', 'Maharashtra', 1);

-- SET IDENTITY_INSERT dbo.Users OFF
