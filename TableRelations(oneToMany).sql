create table color(
    id serial primary key,
    color varchar(255)
);

create table cars(
	id serial primary key,
	model varchar(255),
	color_id int references color(id)
);

insert into color(color) values ('Black');
insert into cars(model, color_id) VALUES ('Opel Astra', 1);

select * from cars;

select * from color where id in (select id from cars);

DROP TABLE color;
DROP TABLE cars;