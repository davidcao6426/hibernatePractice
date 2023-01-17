create table department
(
    dept_id   int          not null
        primary key,
    dept_name varchar(255) not null,
    dept_no   varchar(20)  not null,
    location  varchar(255) null,
    constraint UKpnm6cv2ohhfjsubjt4lsdcaka
        unique (dept_no)
);

INSERT INTO sunbird_test_hibernate.department (dept_id, dept_name, dept_no, location) VALUES (1, 'test', '123', 'taipei');
INSERT INTO sunbird_test_hibernate.department (dept_id, dept_name, dept_no, location) VALUES (2, 'test2', '123456', 'taipei2');
INSERT INTO sunbird_test_hibernate.department (dept_id, dept_name, dept_no, location) VALUES (3, 'test', '1234', 'taipei');