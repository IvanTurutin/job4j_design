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
	vin_numb varchar(255),
	color_id int references color(id) unique,
	power_id int references car_power(id) unique
);

DROP TABLE cars CASCADE;
DROP TABLE color;
DROP TABLE car_power;