create or replace function searchByLastNameFunction(last_name_input varchar)
returns table (first_name varchar, last_name varchar) as $$
begin
    return query
    select customers.first_name, customers.last_name
    from customers
    where customers.last_name = last_name_input;
end;
$$ language plpgsql;

create or replace function searchByProductsAndCountFunction(product_input varchar, count_input int)
returns table (first_name varchar, last_name varchar) as $$
begin
    return query
    select customers.first_name, customers.last_name
    from purchases
    join customers on customers.id = purchases.customer_id
    join products on products.id = purchases.product_id
    where products.name = product_input
    group by customers.first_name, customers.last_name
    having count(customers.first_name) >= count_input;
end;
$$ language plpgsql;

create or replace function searchByMinAndMaxExpenses(min_expences double precision, max_expences double precision)
returns table (first_name varchar, last_name varchar) as $$
begin
    return query
    select customers.first_name, customers.last_name
    from purchases
    join customers on customers.id = purchases.customer_id
    join products on products.id = purchases.product_id
    group by customers.id, customers.first_name, customers.last_name
    having sum(products.price) between min_expences and max_expences;
end;
$$ language plpgsql;

create or replace function searchByBadCustomersFunction(bad_customers int)
returns table (first_name varchar, last_name varchar) as $$
begin
    return query
    select customers.first_name, customers.last_name
    from customers
    left join purchases on customers.id = purchases.customer_id
    group by customers.id
    order by count(purchases.id) asc
    limit bad_customers;
end;
$$ language plpgsql;