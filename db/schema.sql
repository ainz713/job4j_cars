create table users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

create table items (
                       id serial primary key,
                       description text,
                       created TIMESTAMP NOT NULL,
                       user_id INT REFERENCES users(id) NOT NULL,
                       car_id int references cars (id)  not null,
                       model_id int references models (id)  not null,
                       kuzov_id  int references kuzov (id)   not null,
                       photo_id int references photos (id),
                       mileage int not null,
                       status boolean not null
);

create table kuzov
(
    id   serial primary key,
    name varchar(50) not null
);

create table cars
(
    id   serial primary key,
    name varchar(50) not null
);

create table models
(
    id       serial primary key,
    name     varchar(50) not null,
    car_id int references cars (id) not null
);

create table photos
(
    id   SERIAL primary key,
    name varchar(50) not null
);