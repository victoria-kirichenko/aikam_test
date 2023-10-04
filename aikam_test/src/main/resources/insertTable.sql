insert into customers (first_name, last_name) values
    ('John', 'Doe'),
    ('Jane', 'Smith'),
    ('Michael', 'Johnson');

insert into products (name, price) values
    ('Laptop', 999.99),
    ('Smartphone', 499.99),
    ('Tablet', 299.99),
    ('Headphones', 79.99);

insert into purchases (customer_id, product_id, purchase_date) values
    (1, 1, '2023-10-01'),
    (2, 2, '2023-10-02'),
    (3, 1, '2023-10-03'),
    (1, 3, '2023-10-04'),
    (2, 4, '2023-10-05');