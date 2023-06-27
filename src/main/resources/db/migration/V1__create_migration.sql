create schema if not exists users;
create table role
(
    id   bigserial
        primary key,
    name varchar(255)
);

create table user_app
(
    id       bigserial
        primary key,
    name     varchar(255),
    password varchar(255),
    username varchar(255)
);


create table user_app_roles
(
    user_app_id bigint not null
        constraint fki3ssx7dg70gsctusbyaosfy6m
            references user_app,
    roles_id    bigint not null
        constraint fkppiewfvnu7rsn6epfx5khbjja
            references role
);

alter table user_app_roles
    owner to my_user;