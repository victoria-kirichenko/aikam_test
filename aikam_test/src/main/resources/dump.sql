--
-- PostgreSQL database dump
--

-- Dumped from database version 10.22
-- Dumped by pg_dump version 14.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: searchbybadcustomersfunction(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.searchbybadcustomersfunction(bad_customers integer) RETURNS TABLE(first_name character varying, last_name character varying)
    LANGUAGE plpgsql
    AS $$
begin
    return query
    select customers.first_name, customers.last_name
    from customers
    left join purchases on customers.id = purchases.customer_id
    group by customers.id
    order by count(purchases.id) asc
    limit bad_customers;
end;
$$;


ALTER FUNCTION public.searchbybadcustomersfunction(bad_customers integer) OWNER TO postgres;

--
-- Name: searchbylastnamefunction(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.searchbylastnamefunction(last_name_input character varying) RETURNS TABLE(first_name character varying, last_name character varying)
    LANGUAGE plpgsql
    AS $$
begin
    return query
    select customers.first_name, customers.last_name
    from customers
    where customers.last_name = last_name_input;
end;
$$;


ALTER FUNCTION public.searchbylastnamefunction(last_name_input character varying) OWNER TO postgres;

--
-- Name: searchbyminandmaxexpenses(double precision, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.searchbyminandmaxexpenses(min_expences double precision, max_expences double precision) RETURNS TABLE(first_name character varying, last_name character varying)
    LANGUAGE plpgsql
    AS $$
begin
    return query
    select customers.first_name, customers.last_name
    from purchases
    join customers on customers.id = purchases.customer_id
    join products on products.id = purchases.product_id
    group by customers.id, customers.first_name, customers.last_name
    having sum(products.price) between min_expences and max_expences;
end;
$$;


ALTER FUNCTION public.searchbyminandmaxexpenses(min_expences double precision, max_expences double precision) OWNER TO postgres;

--
-- Name: searchbyproductsandcountfunction(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.searchbyproductsandcountfunction(product_input character varying, count_input integer) RETURNS TABLE(first_name character varying, last_name character varying)
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.searchbyproductsandcountfunction(product_input character varying, count_input integer) OWNER TO postgres;

--
-- Name: statfunction(date, date); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.statfunction(startdate date, enddate date) RETURNS TABLE(first_name character varying, last_name character varying, product_name character varying, price double precision)
    LANGUAGE plpgsql
    AS $$
begin
    return query
    select c.first_name, c.last_name, p.name, p.price
    from customers c
    join purchases pu on c.id = pu.customer_id
    join products p on pu.product_id = p.id
    where pu.purchase_date between startDate and endDate
    and extract(dow from pu.purchase_date) not in (0, 6)
    group by c.first_name, c.last_name, p.name, p.price
    order by c.last_name, c.first_name;
end;
$$;


ALTER FUNCTION public.statfunction(startdate date, enddate date) OWNER TO postgres;

SET default_tablespace = '';

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    id integer NOT NULL,
    first_name character varying(255),
    last_name character varying(255)
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customers_id_seq OWNER TO postgres;

--
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customers_id_seq OWNED BY public.customers.id;


--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id integer NOT NULL,
    name character varying(255),
    price double precision
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO postgres;

--
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- Name: purchases; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.purchases (
    id integer NOT NULL,
    customer_id integer,
    product_id integer,
    purchase_date date
);


ALTER TABLE public.purchases OWNER TO postgres;

--
-- Name: purchases_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchases_id_seq OWNER TO postgres;

--
-- Name: purchases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;


--
-- Name: customers id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers ALTER COLUMN id SET DEFAULT nextval('public.customers_id_seq'::regclass);


--
-- Name: products id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- Name: purchases id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (id, first_name, last_name) FROM stdin;
1	John	Doe
2	Jane	Smith
3	Michael	Johnson
4	Alice	Johnson
5	Robert	Smith
6	Emily	Davis
7	William	Wilson
8	Olivia	Anderson
9	James	Martinez
10	Sophia	Lee
11	Daniel	Taylor
12	Emma	Brown
13	Michael	Garcia
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, name, price) FROM stdin;
1	Laptop	999.99000000000001
2	Smartphone	499.99000000000001
3	Tablet	299.99000000000001
4	Headphones	79.989999999999995
5	Desktop	1199.99
6	Monitor	299.99000000000001
7	Keyboard	49.990000000000002
8	Mouse	19.989999999999998
9	Printer	199.99000000000001
10	Scanner	149.99000000000001
11	External Hard Drive	79.989999999999995
12	Webcam	39.990000000000002
13	Speakers	59.990000000000002
14	Microphone	29.989999999999998
\.


--
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.purchases (id, customer_id, product_id, purchase_date) FROM stdin;
1	1	1	2023-10-01
2	2	2	2023-10-02
3	3	1	2023-10-03
4	1	3	2023-10-04
5	2	4	2023-10-05
6	4	5	2023-10-06
7	5	6	2023-10-07
8	6	7	2023-10-08
9	7	8	2023-10-09
10	8	9	2023-10-10
11	9	10	2023-10-11
12	10	1	2023-10-12
13	1	2	2023-10-13
14	2	3	2023-10-14
15	3	4	2023-10-15
\.


--
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_id_seq', 13, true);


--
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.products_id_seq', 14, true);


--
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.purchases_id_seq', 15, true);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: purchases purchases_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);


--
-- Name: purchases purchases_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(id);


--
-- Name: purchases purchases_product_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_product_id_fkey FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- PostgreSQL database dump complete
--

