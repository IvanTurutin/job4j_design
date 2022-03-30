create table car_power(
    id serial primary key,
    car_power int
);

create table color(
    id serial primary key,
    color varchar(255)
);

create table cars(
	id serial primary key,
	model varchar(255),
	color_id int references color(id) unique,
	power_id int references car_power(id) unique
);

insert into car_power(car_power) values (140);
insert into car_power(car_power) values (170);
insert into car_power(car_power) values (300);

insert into color(color) values ('Black');
insert into color(color) values ('Red');
insert into color(color) values ('Blue');

insert into cars(model, color_id, power_id) VALUES ('Opel Astra', 1, 1);
insert into cars(model, color_id, power_id) VALUES ('VW Passat', 3, 2);
insert into cars(model, color_id) VALUES ('KIA SEED', 2);
insert into cars(model, power_id) VALUES ('Mercedes S500', 3);


select * from cars as cr
join color as cl
on cr.color_id = cl.id;

select * from cars as cr
join color as cl
on cr.color_id = cl.id
join car_power as cp
on cr.power_id = cp.id;

select cr.model, cl.color, cp.car_power
from cars as cr
join color as cl
on cr.color_id = cl.id
join car_power as cp
on cr.power_id = cp.id;


DROP TABLE cars CASCADE;
DROP TABLE color;
DROP TABLE car_power;