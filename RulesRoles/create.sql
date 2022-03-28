CREATE TABLE role (
	id serial primary key,
    role varchar(255)
);

create table users(
    id serial primary key,
    name varchar(255),
	role_id int references role(id)
);

CREATE TABLE rules (
    id serial primary key,
	rules varchar(255)
);

CREATE TABLE role_rules (
    id serial primary key,
	role_id int references role(id),
	rules_id int references rules(id)
);

CREATE TABLE category (
    id serial primary key,
	category varchar(255)
);

CREATE TABLE states (
    id serial primary key,
	states varchar(255)
);

CREATE TABLE item (
    id serial primary key,
	item int,
	users_id int references users(id),
	category_id int REFERENCES category(id),
	states_id int references states(id)
);

CREATE TABLE comments (
    id serial primary key,
	comments text,
	item_id int references item(id)
);

CREATE TABLE attachs (
    id serial primary key,
	attach_path text,
	item_id int references item(id)
);
