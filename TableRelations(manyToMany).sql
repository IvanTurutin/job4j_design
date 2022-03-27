create table car_power(
    id serial primary key,
    car_power int
);

create table car_model(
    id serial primary key,
    model varchar(255)
);

create table cars(
	id serial primary key,
	model_id int references car_model(id),
	power_id int references car_power(id)
);

DROP TABLE cars CASCADE;
DROP TABLE car_model;
DROP TABLE car_power;