insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('spring-security-oauth2-read-client', 'resource-server-rest-api',
	/*spring-security-oauth2-read-client-password1234*/'$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km',
	'read', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('spring-security-oauth2-read-write-client', 'resource-server-rest-api',
	/*spring-security-oauth2-read-write-client-password1234*/'$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);

	
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('neo', 'resource-server-rest-api',
	/*neo*/'$2y$08$ibiorsF31N.RcCFnwHSfI.tSUmU4Nwr60Xo5e3dXCPZBkQ5N5/qgi',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);
	
	
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('bluesky', 'resource-server-rest-api',
	/*bluesky*/'$2y$08$1k6aEldpcDovzErVENCjL.BZzqMP/0zoF9T/z859FgxPoQL90Nq2O',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);