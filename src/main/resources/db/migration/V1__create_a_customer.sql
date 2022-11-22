create table address
(
    id          bigint not null primary key,
    streetname  varchar(255),
    housenumber varchar(255),
    postal_code varchar(255),
    city        varchar(255),
    country     varchar(255)
);

create sequence address_seq start with 1 increment by 1;

create table customer
(
    id            bigint not null primary key,
    email_address varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    phone_number  varchar(255),
    role varchar(255),
    fk_address_id bigint constraint fk_address_id references address
);