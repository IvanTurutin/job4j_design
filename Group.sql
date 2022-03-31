create table devices(
    id serial primary key,
    name varchar(255),
    price float
);

create table people(
    id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);

insert into devices(name, price) values ('Samsung', 6000.0), ('Iphone', 9000.0), ('Xiaomi', 4000.0), ('Nokia', 2000.0), ('Sony', 8000);
insert into people(name) values ('Аня'), ('Ваня'), ('Боря');
insert into devices_people(device_id, people_id) values (1, 1), (2, 1), (3, 1);
insert into devices_people(device_id, people_id) values (2, 2), (3, 2), (4, 2);
insert into devices_people(device_id, people_id) values (3, 3), (4, 3), (5, 3);

SELECT avg(price) FROM devices;

SELECT pl.name as Имя, avg(d.price) as Средняя_цена
FROM devices_people as dp
join devices as d
ON dp.device_id = d.id
join people as pl
ON dp.people_id = pl.id
GROUP BY pl.name;

SELECT pl.name as Имя, avg(d.price) as Средняя_цена
FROM devices_people as dp
join devices as d
ON dp.device_id = d.id
join people as pl
ON dp.people_id = pl.id
GROUP BY pl.name
having avg(d.price) > 5000;


DROP TABLE devices_people CASCADE;
DROP TABLE people;
DROP TABLE devices;