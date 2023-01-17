create table employee
(
    emp_id    bigint      not null
        primary key,
    emp_name  varchar(50) not null,
    emp_no    varchar(20) not null,
    hire_date date        not null,
    image     longblob    null,
    job       varchar(30) not null,
    salary    float       not null,
    dept_id   int         not null,
    constraint UK1turlfhhctglkjwykbn5yv94a
        unique (emp_no),
    constraint FKaqchbcb8i6nvtl9g6c72yba0p
        foreign key (dept_id) references department (dept_id)
);

INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (1, 'test_employee', '1', '2022-10-14', null, 'test', 5, 1);
INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (2, 'test_employee2', '2', '2022-10-14', null, 'test', 4, 1);
INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (3, 'test_employee3', '3', '2022-10-14', null, 'test', 3, 2);
INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (4, 'test_employee3', '4', '2022-10-14', null, 'test', 2, 3);
INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (5, 'test_employee5', '5', '2022-10-14', null, 'test2', 1, 3);
INSERT INTO sunbird_test_hibernate.employee (emp_id, emp_name, emp_no, hire_date, image, job, salary, dept_id) VALUES (6, 'test_employee34543', '534534', '2022-10-14', null, 'test', 3, 2);