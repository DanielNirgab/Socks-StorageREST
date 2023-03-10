-- liquibase formatted sql

-- changeset LB:1
create table socks_storage
(
    id          bigint primary key,
    cotton_part int    not null,
    quantity    int    not null,
    color varchar unique not null
);