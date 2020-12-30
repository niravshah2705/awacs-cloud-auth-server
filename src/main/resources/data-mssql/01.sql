
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('wakandagrpc', 'resource-server-rest-api', /*wakandagrpc*/'$2y$08$byB9gh2IvpDLA9p7URsp5OgY0ydCwmKxArUL5Jf4tJShBJW1ZlCI.',
	'read', 'implicit', 'SYSTEM', 86400, 2592000);

	
-- 	4 clients for users interaction to share with common scope
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('neo', 'resource-server-rest-api',
	/*neo*/'$2y$08$ibiorsF31N.RcCFnwHSfI.tSUmU4Nwr60Xo5e3dXCPZBkQ5N5/qgi',
	'read,write', 'password,authorization_code,refresh_token', 'USER', 86400, 2592000);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('trinity', 'resource-server-rest-api',
	/*trinity*/'$2y$04$voM1BWaievzGYiub9DcMpO1cO.sXXwI6OB2XDN.ZJBkiqnWbPvHaa',
	'read,write', 'password,authorization_code,refresh_token', 'USER', 86400, 2592000);
		
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('morpheus', 'resource-server-rest-api',
	/*morpheus*/'$2y$04$k88sd/k6PkO2XrrMc3Eq8u1oNcsT.Ti2CrzOG1suEhEPaBpt0yzDm',
	'read,write', 'password,authorization_code,refresh_token', 'USER', 86400, 2592000);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('cypher', 'resource-server-rest-api',
	/*cypher*/'$2y$08$FyH7twtnGcsUMfqewqZ0PeLms8S2vP6X5sbY/hvqHc7FG/bOhkEpG',
	'read,write', 'client_credentials,password,authorization_code,refresh_token', 'USER', 240, 240);
	
	
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
	values ('bluesky', 'resource-server-rest-api',
	/*bluesky*/'$2y$08$1k6aEldpcDovzErVENCjL.BZzqMP/0zoF9T/z859FgxPoQL90Nq2O',
	'read,write', 'client_credentials,password,authorization_code,refresh_token', 'JMETER', 180, 180);
	
