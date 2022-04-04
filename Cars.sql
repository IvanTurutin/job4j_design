create table body (
    id serial primary key,
    name varchar(255)
);

create table engine (
    id serial primary key,
    name varchar(255)
);

create table transmission (
    id serial primary key,
    name varchar(255)
);

create table cars (
    id serial primary key,
    name varchar(255),
	body_id int references body(id),
	engine_id int references engine(id),
	trans_id int references transmission(id)
);

insert into body(name) values ('Седан'), ('Хетчбэк'), ('Универсал'), ('Пикап');
insert into engine(name) values ('Бензиновый'), ('Дизельный'), ('Электрический'), ('Газовый');
insert into transmission(name) values ('Механическая'), ('Гидротрансформатор'), ('Вариатор'), ('Робот');
insert into cars(name, body_id, engine_id, trans_id) values ('Лада', 1, 1, 1);
insert into cars(name, body_id, engine_id, trans_id) values ('Ниссан', 2, 2, 3);
insert into cars(name, body_id, engine_id, trans_id) values ('Форд', 4, 1, 2);
insert into cars(name, body_id, engine_id) values ('Тесла', 1, 3);

select c.name, b.name, e.name, t.name 
from cars as c 
left join body as b
on c.body_id = b.id
left join engine as e
on c.engine_id = e.id
left join transmission as t
on c.trans_id = t.id;

select b.name from body as b left join cars as c on b.id = c.body_id where c.name is null;
select e.name from engine as e left join cars as c on e.id = c.engine_id where c.name is null;
select t.name from transmission as t left join cars as c on t.id = c.trans_id where c.name is null;

DROP TABLE cars CASCADE;
DROP TABLE transmission;
DROP TABLE engine;
DROP TABLE body;