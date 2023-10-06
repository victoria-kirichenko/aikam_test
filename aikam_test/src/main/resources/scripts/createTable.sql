create table customers (
    id SERIAL primary key,
    first_name varchar(255),
    last_name varchar(255)
);

create table products (
    id serial primary key,
    name varchar(255),
    price double precision
);

create table purchases (
    id SERIAL primary key,
    customer_id int,
    product_id int,
    purchase_date date,
    foreign key (customer_id) references customers(id),
    foreign key (product_id) references products(id)
);