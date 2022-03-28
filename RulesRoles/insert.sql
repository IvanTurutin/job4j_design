insert into role(role) values ('User');
insert into users(name, role_id) values ('Ivan', 1);
insert into rules(rules) values ('Read');
insert into role_rules(role_id, rules_id) values (1, 1);
insert into category(category) values ('Телефон');
insert into states(states) values ('В работе');
insert into item(item, users_id, category_id, states_id) values (12345, 1, 1, 1);
insert into comments(comments, item_id) values ('Samsung', 1);
insert into attachs(attach_path, item_id) values ('c:\Ivan.txt', 1);
