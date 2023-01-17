create table tutorials
(
    id          bigint       not null
        primary key,
    description varchar(255) null,
    published   bit          null,
    title       varchar(255) null
);

