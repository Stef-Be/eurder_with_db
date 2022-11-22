insert into address (id, country, housenumber, city, postal_code, streetname)
values (nextval('address_seq'),'Belgium','1','Antwerpen','2000','Kortestraat');

insert into address (id, country, housenumber, city, postal_code, streetname)
values (nextval('address_seq'),'Belgium','22','Leuven','3000','Langestraat');

insert into address (id, country, housenumber, city, postal_code, streetname)
values (nextval('address_seq'),'Belgium','5','Brussel','1000','Hogestraat');

INSERT INTO customer (id, first_name, last_name, email_address, country_code, phone_body, role, fk_address_id, password)
VALUES (nextval('customer_seq'), 'Stef', 'Bemindt', 'stef@switchfully.com', 'BELGIUM','478140321', 'ADMIN', 1, '4b633bcd0af3e2f817d8d4ca18371eab595043cc');

INSERT INTO customer (id, first_name, last_name, email_address, country_code, phone_body, role, fk_address_id, password)
VALUES (nextval('customer_seq'), 'Staf', 'Bemondt', 'staf@switchfully.com', 'FRANCE', '842123456', 'CUSTOMER', 2, '167def91ec4c064c3f0aae0e04e46624e11462cf');

INSERT INTO customer (id, first_name, last_name, email_address, country_code, phone_body, role, fk_address_id, password)
VALUES (nextval('customer_seq'), 'Stof', 'Bemandt', 'stof@switchfully.com', 'NETHERLANDS','567463211', 'CUSTOMER', 3, '5075bd647d8f2ce6c3ffea16813a97a0a344c777');