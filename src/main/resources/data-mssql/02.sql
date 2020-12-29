SET IDENTITY_INSERT authority ON

insert into authority(id, name) values (1, 'order_create');
insert into authority(id, name) values (2, 'order_read');
insert into authority(id, name) values (3, 'order_update');
insert into authority(id, name) values (4, 'order_delete');

insert into authority(id, name) values (5, 'product_create');
insert into authority(id, name) values (6, 'product_read');
insert into authority(id, name) values (7, 'product_update');
insert into authority(id, name) values (8, 'product_delete');

insert into authority(id, name) values (9, 'role_product_order_reader');

insert into authority(id, name) values (10, 'SYSTEM');
insert into authority(id, name) values (11, 'USER');
insert into authority(id, name) values (12, 'implicit');

SET IDENTITY_INSERT authority OFF
