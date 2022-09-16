create table employees(
    id serial primary key,
    name varchar(255),
	department varchar(255),
	salary int
);

insert into employees(name, department, salary) values ('Ivan', 'Design', 150);
insert into employees(name, department, salary) values ('Nikolay', 'IT', 180);
insert into employees(name, department, salary) values ('Tatiana', 'HR', 120);

BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

SELECT * FROM employees;

insert into employees(name, department, salary) values ('Den', 'Design', 130);
insert into employees(name, department, salary) values ('Mike', 'IT', 170);

commit;
