PGDMP                         v           coffeedb    10.4    10.4     �
           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �
           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false                       1262    24772    coffeedb    DATABASE     �   CREATE DATABASE coffeedb WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Ukrainian_Ukraine.1251' LC_CTYPE = 'Ukrainian_Ukraine.1251';
    DROP DATABASE coffeedb;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false                       0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    24773    coffee    TABLE     �   CREATE TABLE public.coffee (
    id bigint NOT NULL,
    coffeename character varying(255),
    costforcup integer,
    disabled boolean NOT NULL
);
    DROP TABLE public.coffee;
       public         postgres    false    3            �            1259    24776    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public       postgres    false    3            �            1259    24778    orderposition    TABLE        CREATE TABLE public.orderposition (
    id bigint NOT NULL,
    quantity integer,
    coffee_id bigint,
    order_id bigint
);
 !   DROP TABLE public.orderposition;
       public         postgres    false    3            �            1259    24781    orders    TABLE     �   CREATE TABLE public.orders (
    id bigint NOT NULL,
    adress character varying(255),
    clientname character varying(255),
    cost double precision,
    date timestamp without time zone
);
    DROP TABLE public.orders;
       public         postgres    false    3            �
          0    24773    coffee 
   TABLE DATA               F   COPY public.coffee (id, coffeename, costforcup, disabled) FROM stdin;
    public       postgres    false    196   �       �
          0    24778    orderposition 
   TABLE DATA               J   COPY public.orderposition (id, quantity, coffee_id, order_id) FROM stdin;
    public       postgres    false    198   F       �
          0    24781    orders 
   TABLE DATA               D   COPY public.orders (id, adress, clientname, cost, date) FROM stdin;
    public       postgres    false    199   ~                  0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 65, true);
            public       postgres    false    197            x
           2606    24788    coffee coffee_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.coffee
    ADD CONSTRAINT coffee_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.coffee DROP CONSTRAINT coffee_pkey;
       public         postgres    false    196            z
           2606    24790     orderposition orderposition_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.orderposition
    ADD CONSTRAINT orderposition_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.orderposition DROP CONSTRAINT orderposition_pkey;
       public         postgres    false    198            |
           2606    24792    orders orders_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.orders DROP CONSTRAINT orders_pkey;
       public         postgres    false    199            }
           2606    24793     orderposition fkc8c086771eeb33af    FK CONSTRAINT     �   ALTER TABLE ONLY public.orderposition
    ADD CONSTRAINT fkc8c086771eeb33af FOREIGN KEY (order_id) REFERENCES public.orders(id);
 J   ALTER TABLE ONLY public.orderposition DROP CONSTRAINT fkc8c086771eeb33af;
       public       postgres    false    199    198    2684            ~
           2606    24798     orderposition fkc8c08677e9cbb825    FK CONSTRAINT     �   ALTER TABLE ONLY public.orderposition
    ADD CONSTRAINT fkc8c08677e9cbb825 FOREIGN KEY (coffee_id) REFERENCES public.coffee(id);
 J   ALTER TABLE ONLY public.orderposition DROP CONSTRAINT fkc8c08677e9cbb825;
       public       postgres    false    196    2680    198            �
   r   x����@D�*\��5C�:�X" ql
 E�N��v�K&x�4�3���㥐�+�'^[6V.�ܭ��%�K'v���ی�:���pq�'��!���O�;��O�9���eCn      �
   (   x�33�4�4�43�23��`�	'�X�@�Ċ���� ��$      �
   8   x�33��K�MU�LL)J-.�43�420��50�56P04�24�2��377�����  O
q     