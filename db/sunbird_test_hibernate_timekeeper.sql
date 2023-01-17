create table timekeeper
(
    timekeeper_id varchar(36) not null
        primary key,
    date_time     datetime(6) not null,
    in_out        char        not null,
    emp_id        bigint      not null,
    constraint FKm4pvx5q2jquthtsen0xus95bm
        foreign key (emp_id) references employee (emp_id)
);

