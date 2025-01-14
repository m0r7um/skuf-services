drop table if exists content_of_order_of_dumplings cascade;
drop table if exists content_of_order_of_alcohol cascade;
drop table if exists orders cascade;
drop table if exists dumplings cascade;
drop table if exists alcohol_drink cascade;
drop table if exists service cascade;
drop table if exists request cascade;
drop table if exists user_roles cascade;
drop table if exists role cascade;
drop table if exists users cascade;

create table users
(
    id         uuid not null primary key,
    login      varchar(255) unique,
    password   varchar(255),
    birth_date timestamp without time zone,
    name       varchar(255),
    surname    varchar(255)
);

INSERT INTO users (id, login, password, birth_date, name, surname)
VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user1', 'password1', '1990-01-01 00:00:00+00', 'John', 'Doe'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'admin1', 'password2', '1985-05-15 00:00:00+00', 'Jane', 'Smith'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'provider1', 'password3', '1992-10-30 00:00:00+00', 'Alice', 'Johnson'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'user2', 'password4', '1995-07-20 00:00:00+00', 'Bob', 'Brown'),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'admin2', 'password5', '1980-12-12 00:00:00+00', 'Charlie', 'Davis');

create table role
(
    id   uuid not null
        primary key,
    name varchar(20) check ((name)::text = ANY ((ARRAY ['ROLE_USER'::character varying, 'ROLE_ADMIN'::character varying, 'ROLE_PROVIDER'::character varying])::text[]))
    );

INSERT INTO role (id, name)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'ROLE_USER'),
    ('22222222-2222-2222-2222-222222222222', 'ROLE_ADMIN'),
    ('33333333-3333-3333-3333-333333333333', 'ROLE_PROVIDER');

create table user_roles
(
    role_id uuid not null references role,
    user_id uuid not null references users,
    primary key (role_id, user_id)
);

INSERT INTO user_roles (role_id, user_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
    ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    ('33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    ('11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
    ('22222222-2222-2222-2222-222222222222', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee');

create table request
(
    id   uuid not null primary key,
    status varchar(20) check ((status)::text = ANY ((ARRAY ['AWAITING_APPROVEMENT'::character varying, 'APPROVED'::character varying, 'DECLINED'::character varying])::text[]))
    );

INSERT INTO request (id, status)
VALUES
    ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'AWAITING_APPROVEMENT'),
    ('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'APPROVED'),
    ('33333333-cccc-cccc-cccc-cccccccccccc', 'DECLINED'),
    ('44444444-dddd-dddd-dddd-dddddddddddd', 'APPROVED'),
    ('55555555-eeee-eeee-eeee-eeeeeeeeeeee', 'AWAITING_APPROVEMENT');

create table service
(
    id          varchar(255) primary key,
    title       varchar(150)               not null,
    price       decimal                    not null,
    user_id     uuid REFERENCES users (id) not null,
    description text                       not null,
    type        varchar(20)                not null
);

INSERT INTO service(id, title, price, user_id, description, type)
VALUES ('327dfbc0-d0be-4f23-bdfb-c0d0be3f237f', 'Объявление 1', 1000.23, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'description', 'WOT'),
       ('53d5aebe-ebea-4da8-95ae-beebea4da887', 'Доставка бульменей', 1222.23, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'description2', 'ALCOHOL'),
       ('e406c625-64ee-4b06-86c6-2564eefb06c2', 'Только массаж!', 4322.23, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'description3', 'LAUNDRY'),
       ('ae7ad77d-096f-4fc8-bad7-7d096f3fc86a', 'Услуга 99', 3453.23, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'description4', 'ALTUSHKA');

create table alcohol_drink
(
    id          uuid primary key,
    name        varchar(50) not null,
    description text        not null,
    price       decimal     not null
);

INSERT INTO alcohol_drink(id, name, description, price)
VALUES ('7e869892-142a-45d9-8698-92142ab5d9c1', 'martini', 'description', 1000.23),
       ('fbd3d6bc-bf5e-499d-93d6-bcbf5ef99df6', 'marry', 'description2', 23.23),
       ('de47213d-61bd-4dea-8721-3d61bdadeacc', 'vodka', 'description3', 454534),
       ('b5a248ca-9c5c-4146-a248-ca9c5cf146b7', 'beer', 'description4', 223);

create table dumplings
(
    id          uuid primary key,
    name        varchar(50) not null,
    description text        not null,
    price       decimal     not null
);

INSERT INTO dumplings(id, name, description, price)
VALUES ('f566ada3-ae66-4fce-a6ad-a3ae660fce32', 'Xiao long bao', 'description', 1000.23),
       ('f07cb723-4b6f-424f-bcb7-234b6f024fbf', 'Har gow', 'description2', 23.23),
       ('e3024db5-4fe9-4556-824d-b54fe9655636', 'Jiaozi', 'description3', 454534),
       ('f80693e2-ba52-471d-8693-e2ba52771dba', 'Wonton', 'description4', 223),
       ('a9fb92a5-73f7-4a29-bb92-a573f79a29ab', 'Mandu', 'description5', 223);

create table orders
(
    id          uuid primary key,
    total_price decimal                              not null,
    comment     text,
    status      varchar(20)                          not null,
    service_id  varchar(255) REFERENCES service (id) not null,
    user_id     uuid REFERENCES users (id)           not null,
    address     text                                 not null,
    rating      integer CHECK (rating >= 0 AND rating <= 5)
);

INSERT INTO orders(id, total_price, comment, status, service_id, user_id, address, rating)
VALUES ('3aa6762c-1b94-48e9-a676-2c1b9448e9af', 12232.321, null, 'CANCELED', 'ae7ad77d-096f-4fc8-bad7-7d096f3fc86a', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Смирнова 1', 3),
       ('e6971157-861c-470b-9711-57861c370b3c', 45456, 'qweewrhrth', 'PAYMENT_AWAITING', 'e406c625-64ee-4b06-86c6-2564eefb06c2', 'dddddddd-dddd-dddd-dddd-dddddddddddd',  'Суворова 19', 5),
       ('39d37332-de50-40b9-9373-32de5080b981', 43, 'gbrtnertertbsdfvsdfbrfb', 'COMPLETED', 'e406c625-64ee-4b06-86c6-2564eefb06c2', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Ленина 33', 1),
       ('7ce1793a-63a3-4913-a179-3a63a32913e2', 6534, null, 'IN_PROGRESS', '53d5aebe-ebea-4da8-95ae-beebea4da887', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Карамазовы 55', 4);

create table content_of_order_of_alcohol
(
    id         uuid primary key,
    order_id   uuid REFERENCES orders (id)        not null,
    alcohol_id uuid REFERENCES alcohol_drink (id) not null,
    count      integer                            not null
);

INSERT INTO content_of_order_of_alcohol(id, order_id, alcohol_id, count) VALUES
                                                                             ('8c4a4cce-47a3-45f0-8a4c-ce47a3e5f0e4', '3aa6762c-1b94-48e9-a676-2c1b9448e9af', '7e869892-142a-45d9-8698-92142ab5d9c1', 2),
                                                                             ('9d31549d-ffef-4221-b154-9dffefd221b2', '7ce1793a-63a3-4913-a179-3a63a32913e2', 'b5a248ca-9c5c-4146-a248-ca9c5cf146b7', 33),
                                                                             ('d1e48a9b-ec0f-49e7-a48a-9bec0fd9e7ce', '39d37332-de50-40b9-9373-32de5080b981', 'b5a248ca-9c5c-4146-a248-ca9c5cf146b7', 11),
                                                                             ('48e1e747-8cb8-4b9c-a1e7-478cb80b9c97', 'e6971157-861c-470b-9711-57861c370b3c', 'de47213d-61bd-4dea-8721-3d61bdadeacc', 4);


create table content_of_order_of_dumplings
(
    id          uuid primary key,
    order_id    uuid REFERENCES orders (id)    not null,
    dumpling_id uuid REFERENCES dumplings (id) not null,
    count       integer                        not null
);

INSERT INTO content_of_order_of_dumplings(id, order_id, dumpling_id, count) VALUES
                                                                                ('65eb9450-b8a5-4308-ab94-50b8a50308ee', '3aa6762c-1b94-48e9-a676-2c1b9448e9af', 'e3024db5-4fe9-4556-824d-b54fe9655636', 2),
                                                                                ('53d5aebe-ebea-4da8-95ae-beebea4da887', '7ce1793a-63a3-4913-a179-3a63a32913e2', 'f566ada3-ae66-4fce-a6ad-a3ae660fce32', 33),
                                                                                ('e406c625-64ee-4b06-86c6-2564eefb06c2', '39d37332-de50-40b9-9373-32de5080b981', 'e3024db5-4fe9-4556-824d-b54fe9655636', 11),
                                                                                ('ae7ad77d-096f-4fc8-bad7-7d096f3fc86a', 'e6971157-861c-470b-9711-57861c370b3c', 'a9fb92a5-73f7-4a29-bb92-a573f79a29ab', 4);





-- Триггер для обеспечения целостности при вставке в таблицу content_of_order_of_alcohol
CREATE OR REPLACE FUNCTION check_alcohol_order_integrity()
    RETURNS TRIGGER AS $$
BEGIN
    -- Проверка: существует ли указанный заказ
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = NEW.order_id) THEN
        RAISE EXCEPTION 'Order ID % does not exist', NEW.order_id;
END IF;

    -- Проверка: существует ли указанный алкогольный напиток
    IF NOT EXISTS (SELECT 1 FROM alcohol_drink WHERE id = NEW.alcohol_id) THEN
        RAISE EXCEPTION 'Alcohol ID % does not exist', NEW.alcohol_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_alcohol_order
    BEFORE INSERT ON content_of_order_of_alcohol
    FOR EACH ROW
    EXECUTE FUNCTION check_alcohol_order_integrity();



-- Триггер для обеспечения целостности при вставке в таблицу content_of_order_of_dumplings
CREATE OR REPLACE FUNCTION check_dumplings_order_integrity()
    RETURNS TRIGGER AS $$
BEGIN
    -- Проверка: существует ли указанный заказ
    IF NOT EXISTS (SELECT 1 FROM orders WHERE id = NEW.order_id) THEN
        RAISE EXCEPTION 'Order ID % does not exist', NEW.order_id;
END IF;

    -- Проверка: существует ли указанное пельмени
    IF NOT EXISTS (SELECT 1 FROM dumplings WHERE id = NEW.dumpling_id) THEN
        RAISE EXCEPTION 'Dumpling ID % does not exist', NEW.dumpling_id;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_dumplings_order
    BEFORE INSERT ON content_of_order_of_dumplings
    FOR EACH ROW
    EXECUTE FUNCTION check_dumplings_order_integrity();



-- Функция для массового добавления алкоголя в заказ
CREATE OR REPLACE FUNCTION add_alcohol_to_order(p_order_id UUID, p_alcohol_ids UUID[], p_counts INT[])
    RETURNS VOID AS $$
DECLARE
i INT;
BEGIN
FOR i IN 1..array_length(p_alcohol_ids, 1) LOOP
            INSERT INTO content_of_order_of_alcohol (id, order_id, alcohol_id, count)
            VALUES (gen_random_uuid(), p_order_id, p_alcohol_ids[i], p_counts[i]);
END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Функция для массового добавления пельменей в заказ
CREATE OR REPLACE FUNCTION add_dumplings_to_order(p_order_id UUID, p_dumpling_ids UUID[], p_counts INT[])
    RETURNS VOID AS $$
DECLARE
i INT;
BEGIN
FOR i IN 1..array_length(p_dumpling_ids, 1) LOOP
            INSERT INTO content_of_order_of_dumplings (id, order_id, dumpling_id, count)
            VALUES (gen_random_uuid(), p_order_id, p_dumpling_ids[i], p_counts[i]);
END LOOP;
END;
$$ LANGUAGE plpgsql;




-- Функция для проверки статуса заказа перед выставлением рейтинга
CREATE OR REPLACE FUNCTION check_order_status_for_rating()
    RETURNS TRIGGER AS $$
DECLARE
order_status VARCHAR(20);
BEGIN
    -- Получение текущего статуса заказа
SELECT status INTO order_status
FROM request
WHERE id = NEW.order_id;

-- Проверка, что статус заказа "COMPLETED" или "CANCELED"
IF order_status NOT IN ('COMPLETED', 'CANCELED') THEN
        RAISE EXCEPTION 'Rating can only be added to orders with status COMPLETED or CANCELED. Current status: %', order_status;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера на таблицу orders
CREATE TRIGGER before_rating_insert
    BEFORE INSERT OR UPDATE ON orders
                         FOR EACH ROW
                         EXECUTE FUNCTION check_order_status_for_rating();







CREATE OR REPLACE FUNCTION check_order_time()
    RETURNS TRIGGER AS $$
BEGIN
    IF CURRENT_TIME >= '22:00:00' THEN
        RAISE EXCEPTION 'Orders cannot be placed after 10 PM';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_order_time_check
    BEFORE INSERT OR UPDATE ON orders
                         FOR EACH ROW
                         EXECUTE FUNCTION check_order_time();
