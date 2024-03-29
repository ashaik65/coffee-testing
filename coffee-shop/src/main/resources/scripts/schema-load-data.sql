CREATE TABLE public.orders (
                               id uuid NOT NULL,
                               status character varying(255) NOT NULL,
                               type character varying(255) NOT NULL,
                               origin_name character varying(255) NOT NULL
);

CREATE TABLE public.origin_coffee_types (
                                            origin_name character varying(255) NOT NULL,
                                            coffee_type character varying(255) NOT NULL
);

CREATE TABLE public.origins (
    name character varying(255) NOT NULL
);

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.origin_coffee_types
    ADD CONSTRAINT origin_coffee_types_pkey PRIMARY KEY (origin_name, coffee_type);

ALTER TABLE ONLY public.origins
    ADD CONSTRAINT origins_pkey PRIMARY KEY (name);

ALTER TABLE ONLY public.origin_coffee_types
    ADD CONSTRAINT fksny5vf8j30otg213opmbp0ab9 FOREIGN KEY (origin_name) REFERENCES public.origins(name);

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkt474abpx5pxojjm61tb1d64vp FOREIGN KEY (origin_name) REFERENCES public.origins(name);

--
-- insert data

INSERT INTO origins VALUES ('Ethiopia');
INSERT INTO origin_coffee_types VALUES ('Ethiopia', 'ESPRESSO');
INSERT INTO origin_coffee_types VALUES ('Ethiopia', 'LATTE');
INSERT INTO origin_coffee_types VALUES ('Ethiopia', 'POUR_OVER');

INSERT INTO origins VALUES ('Colombia');
INSERT INTO origin_coffee_types VALUES ('Colombia', 'ESPRESSO');
INSERT INTO origin_coffee_types VALUES ('Colombia', 'POUR_OVER');
