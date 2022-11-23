create table orders
(
    id bigint not null primary key,
    final_price double precision,
    fk_customer_id bigint constraint fk_customer_id references customer
);

create sequence order_seq start with 1 increment by 1;

create table item_group
(
    id            bigint not null primary key,
    fk_item_id    bigint
        constraint fk_item_id references item,
    amount        integer,
    shipping_date date,
    fk_order_id   bigint references orders
);

create sequence item_group_seq start with 1 increment by 1;