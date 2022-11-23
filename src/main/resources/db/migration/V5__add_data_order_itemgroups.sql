insert into orders (id, final_price, fk_customer_id)
values (nextval('order_seq'),31, 1);

insert into orders (id, final_price, fk_customer_id)
values (nextval('order_seq'),305.94, 2);

insert into item_group (id, fk_item_id, amount, shipping_date, fk_order_id)
values (nextval('item_group_seq'),1,10,'2022-11-24',1);

insert into item_group (id, fk_item_id, amount, shipping_date, fk_order_id)
values (nextval('item_group_seq'),2,2,'2022-11-24', 1);

insert into item_group (id, fk_item_id, amount, shipping_date, fk_order_id)
values (nextval('item_group_seq'),3, 6, '2022-11-30', 2);