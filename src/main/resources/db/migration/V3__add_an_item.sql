create table item
(
    id bigint not null primary key,
    name varchar(255),
    description varchar(510),
    price double precision,
    amount integer
);

create sequence item_seq start with 1 increment by 1;

insert into item (id, name, description, price, amount)
values (nextval('item_seq'),'Screw','Something to fix stuff to wood',1.0,100);

insert into item (id, name, description, price, amount)
values (nextval('item_seq'),'Wooden plank','To be fixed by screws to stuff',10.5, 10);

insert into item (id, name, description, price, amount)
values (nextval('item_seq'),'Bucket of paint','Something to give wood a nice colour',50.99, 5);