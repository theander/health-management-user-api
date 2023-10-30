create schema if not exists users;

create table roles
(
    id   bigserial
        primary key
                      not null,
    name varchar(255) not null
);

create table user_app
(
    id           bigserial
        primary key,
    name         varchar(255),
    password     varchar(255)        not null,
    username     varchar(255)        not null unique,
    email        varchar(255) unique not null,
    registerDate DATE

);


create table user_app_roles
(

        user_id INT REFERENCES user_app(id),
        role_id INT REFERENCES roles(id),
        PRIMARY KEY (user_id, role_id)

);

alter table user_app_roles
    owner to my_user;