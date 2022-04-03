
-- (1)
create table departments (
    id serial primary key,
    name varchar(255)
);

create table employees(
    id serial primary key,
    name varchar(255),
	dep_id int references departments(id)
);

insert into departments(name) values ('Dep1');
insert into departments(name) values ('Dep2');
insert into departments(name) values ('Dep3');
insert into departments(name) values ('Dep4');

insert into employees(name, dep_id) values ('Иванов', 1);
insert into employees(name, dep_id) values ('Кузнецов', 2);
insert into employees(name, dep_id) values ('Николаев', 3);
insert into employees(name) values ('Васин');

-- (2)
select * from departments as d left join employees as e on d.id = e.dep_id;
select * from employees as e left join departments as d on e.dep_id = d.id;

select * from departments as d right join employees as e on d.id = e.dep_id;
select * from employees as e right join departments as d on e.dep_id = d.id;

select * from employees as e full join departments as d on e.dep_id = d.id;

select * from employees as e cross join departments as d;

--(3)
select * from departments as d left join employees as e on d.id = e.dep_id where e.name is null;

--(4)
select d.name, e.name from departments as d left join employees as e on d.id = e.dep_id;
select d.name, e.name from employees as e right join departments as d on e.dep_id = d.id;

--(5)
create table teens (
    id serial primary key,
    name varchar(255),
	gender varchar(255)
);

insert into teens(name, gender) values ('Коля', 'м');
insert into teens(name, gender) values ('Вася', 'м');
insert into teens(name, gender) values ('Петя', 'м');
insert into teens(name, gender) values ('Катя', 'ж');
insert into teens(name, gender) values ('Маша', 'ж');
insert into teens(name, gender) values ('Таня', 'ж');

SELECT t1.name, t2.name from teens as t1 cross join teens as t2 where t1.gender = 'м' and t1.gender != t2.gender;

DROP TABLE employees CASCADE;
DROP TABLE departments;
DROP TABLE teens;
