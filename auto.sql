create table model(
	id serial primary key,
	model varchar(255),
	year integer,
	passengerCar boolean
);
insert into model(model, year, passengerCar) values('ВАЗ 2101', 1970, false);
select * from model;
update model set passengerCar = true;
select * from model;
delete from model;
select * from model;