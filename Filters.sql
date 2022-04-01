create table type(
    id serial primary key,
    name varchar(255)
);

create table product(
    id serial primary key,
    name varchar(255),
    price float,
	expired_date timestamp,
	type_id int references type(id)
);

insert into type(name) values ('Сыр'), ('Мясо'), ('Молоко');
insert into product(type_id, name,  price, expired_date) values (1, 'Российский', 400, '2022-04-10');
insert into product(type_id, name, price, expired_date) values (1, 'Мааcдам', 500, '2022-03-20');
insert into product(type_id, name, price, expired_date) values (2, 'мороженое', 600, '2022-04-20');
insert into product(type_id, name, price, expired_date) values (3, 'деревенское', 100, '2022-04-15');

SELECT t.name as Тип, p.name as Наименование, p.price as Цена,  p.expired_date as Срок_годности
FROM product as p
join type as t
on p.type_id = t.id;

SELECT t.name as Тип, p.name as Наименование, p.price as Цена,  p.expired_date as Срок_годности
FROM product as p
join type as t
on p.type_id = t.id
WHERE t.name = 'Сыр';

SELECT t.name as Тип, p.name as Наименование, p.price as Цена,  p.expired_date as Срок_годности
FROM product as p
join type as t
on p.type_id = t.id
WHERE p.name LIKE '%мороженое%';

SELECT t.name as Тип, p.name as Наименование, p.price as Цена,  p.expired_date as Срок_годности
FROM product as p
join type as t
on p.type_id = t.id
WHERE p.expired_date < CURRENT_DATE;

SELECT MAX(p.price) as Max_Цена
FROM product as p
join type as t
on p.type_id = t.id;

SELECT t.name, COUNT(*)
FROM product as p
join type as t
on p.type_id = t.id
GROUP BY t.name;

SELECT t.name as Тип, p.name as Наименование, p.price as Цена,  p.expired_date as Срок_годности
FROM product as p
join type as t
on p.type_id = t.id
WHERE t.name = 'Сыр' OR t.name = 'Молоко';

SELECT t.name, COUNT(t.name)
FROM product as p
join type as t
on p.type_id = t.id
GROUP BY t.name
having COUNT(t.name) < 10;

SELECT t.name as Тип, p.name as Наименование
FROM product as p
join type as t
on p.type_id = t.id;

DROP TABLE product CASCADE;
DROP TABLE type;