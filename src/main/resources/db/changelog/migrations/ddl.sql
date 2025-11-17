--liquibase formatted sql
--changeset martin:init

create table users
(
    id         varchar(36) not null primary key,
    login      varchar(255) unique,
    password   varchar(255),
    birth_date timestamp without time zone CHECK (birth_date <= CURRENT_DATE AND birth_date >= CURRENT_DATE - INTERVAL '100 years'),
    name       varchar(20),
    surname    varchar(255)
);

INSERT INTO users (id, login, password, birth_date, name, surname)
VALUES
    -- user1, password1 --
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'user1', '$2a$10$HZBB/HY.la38JLiwXe7iB.S4CM14lpnz8UVEPtx9YzBfRLfzQEMb.', '1990-01-01 00:00:00+00', 'John', 'Doe'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'admin1', 'password2', '1985-05-15 00:00:00+00', 'Jane', 'Smith'),
    -- provider1, password3 --
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'provider1', '$2a$10$Tkf3Ceh0ban.CTKCbe6d0OiJJSEhSuXbViot5.Pdd1bonbeO1A1by', '1992-10-30 00:00:00+00', 'Alice', 'Johnson'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'user2', 'password4', '1995-07-20 00:00:00+00', 'Bob', 'Brown'),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'admin2', 'password5', '1980-12-12 00:00:00+00', 'Charlie', 'Davis');

CREATE TYPE role_type AS ENUM (
    'ROLE_USER',
    'ROLE_ADMIN',
    'ROLE_PROVIDER'
);

create table role
(
    id   varchar(36) primary key,
    name role_type not null
);

INSERT INTO role (id, name)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'ROLE_USER'),
    ('22222222-2222-2222-2222-222222222222', 'ROLE_ADMIN'),
    ('33333333-3333-3333-3333-333333333333', 'ROLE_PROVIDER');

create table user_roles
(
    role_id varchar(36) not null references role on delete cascade,
    user_id varchar(36) not null references users on delete cascade,
    primary key (role_id, user_id)
);

INSERT INTO user_roles (role_id, user_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
    ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    ('33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    ('11111111-1111-1111-1111-111111111111', 'dddddddd-dddd-dddd-dddd-dddddddddddd'),
    ('22222222-2222-2222-2222-222222222222', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee');

CREATE TYPE request_status_type AS ENUM (
    'AWAITING_APPROVEMENT',
    'APPROVED',
    'DECLINED'
);

create table request
(
    id     varchar(36) primary key,
    status request_status_type not null
);

INSERT INTO request (id, status)
VALUES
    ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'AWAITING_APPROVEMENT'),
    ('22222222-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'APPROVED'),
    ('33333333-cccc-cccc-cccc-cccccccccccc', 'DECLINED'),
    ('44444444-dddd-dddd-dddd-dddddddddddd', 'APPROVED'),
    ('55555555-eeee-eeee-eeee-eeeeeeeeeeee', 'AWAITING_APPROVEMENT');


CREATE TYPE service_type AS ENUM(
    'DUMPLINGS',
    'ALCOHOL',
    'ALTUSHKA',
    'WOT',
    'LAUNDRY'
);

create table service
(
    id          varchar(36) primary key,
    title       varchar(150)                 not null,
    price       decimal check ( price >= 0 ) not null,
    user_id     varchar(36) REFERENCES users (id) on delete cascade   not null ,
    description text                         not null,
    type        service_type                 not null
);

INSERT INTO service(id, title, price, user_id, description, type)
VALUES ('327dfbc0-d0be-4f23-bdfb-c0d0be3f237f', 'Объявление 1', 1000.23, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'description', 'WOT'),
       ('53d5aebe-ebea-4da8-95ae-beebea4da887', 'Доставка бульменей', 1222.23, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'description2', 'ALCOHOL'),
       ('e406c625-64ee-4b06-86c6-2564eefb06c2', 'Только массаж!', 4322.23, 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'description3', 'LAUNDRY'),
       ('ae7ad77d-096f-4fc8-bad7-7d096f3fc86a', 'Услуга 99', 3453.23, 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'description4', 'ALTUSHKA');

create table alcohol_drink
(
    id          varchar(36) primary key,
    name        varchar(50)                  not null,
    description text                         not null,
    price       decimal check ( price >= 0 ) not null
);

INSERT INTO alcohol_drink(id, name, description, price)
VALUES ('7e869892-142a-45d9-8698-92142ab5d9c1', 'martini', 'description', 1000.23),
       ('fbd3d6bc-bf5e-499d-93d6-bcbf5ef99df6', 'marry', 'description2', 23.23),
       ('de47213d-61bd-4dea-8721-3d61bdadeacc', 'vodka', 'description3', 454534),
       ('b5a248ca-9c5c-4146-a248-ca9c5cf146b7', 'beer', 'description4', 223);

create table dumplings
(
    id          varchar(36) primary key,
    name        varchar(50)                  not null,
    description text                         not null,
    price       decimal CHECK ( price >= 0 ) not null
);

INSERT INTO dumplings(id, name, description, price)
VALUES ('f566ada3-ae66-4fce-a6ad-a3ae660fce32', 'Xiao long bao', 'description', 1000.23),
       ('f07cb723-4b6f-424f-bcb7-234b6f024fbf', 'Har gow', 'description2', 23.23),
       ('e3024db5-4fe9-4556-824d-b54fe9655636', 'Jiaozi', 'description3', 454534),
       ('f80693e2-ba52-471d-8693-e2ba52771dba', 'Wonton', 'description4', 223),
       ('a9fb92a5-73f7-4a29-bb92-a573f79a29ab', 'Mandu', 'description5', 223);

create type order_status as enum(
    'CANCELLED',
    'PAYMENT_AWAITING',
    'AWAITING_CONFIRMATION',
    'IN_PROGRESS',
    'COMPLETED'
);

create table orders
(
    id          varchar(36) primary key,
    total_price decimal CHECK ( total_price >= 0 )   not null,
    comment     text,
    status      order_status                         not null,
    service_id  varchar(36) REFERENCES service (id) on delete cascade not null,
    user_id     varchar(36) REFERENCES users (id)  on delete cascade          not null,
    address     text,
    rating      integer CHECK (rating >= 0 AND rating <= 5),
    type        text not null,
    -- Новые поля для хранения содержимого заказа в формате JSONB
    dumplings_content jsonb,
    alcohol_content   jsonb
);

INSERT INTO orders(id, total_price, comment, status, service_id, user_id, address, rating, type, dumplings_content, alcohol_content)
VALUES
    ('3aa6762c-1b94-48e9-a676-2c1b9448e9af', 12232.321, null, 'CANCELLED', 'ae7ad77d-096f-4fc8-bad7-7d096f3fc86a', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Смирнова 1', 3, 'ALTUSHKA',
     '[{"dumplingId": "e3024db5-4fe9-4556-824d-b54fe9655636", "count": 2}]'::jsonb,
     '[{"alcoholId": "7e869892-142a-45d9-8698-92142ab5d9c1", "count": 2}]'::jsonb),

    ('e6971157-861c-470b-9711-57861c370b3c', 45456, 'qweewrhrth', 'PAYMENT_AWAITING', 'e406c625-64ee-4b06-86c6-2564eefb06c2', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Суворова 19', 5, 'LAUNDRY',
     '[{"dumplingId": "a9fb92a5-73f7-4a29-bb92-a573f79a29ab", "count": 4}]'::jsonb,
     '[{"alcoholId": "de47213d-61bd-4dea-8721-3d61bdadeacc", "count": 4}]'::jsonb),

    ('39d37332-de50-40b9-9373-32de5080b981', 43, 'gbrtnertertbsdfvsdfbrfb', 'COMPLETED', 'e406c625-64ee-4b06-86c6-2564eefb06c2', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 'Ленина 33', 1, 'LAUNDRY',
     '[{"dumplingId": "e3024db5-4fe9-4556-824d-b54fe9655636", "count": 11}]'::jsonb,
     '[{"alcoholId": "b5a248ca-9c5c-4146-a248-ca9c5cf146b7", "count": 11}]'::jsonb),

    ('7ce1793a-63a3-4913-a179-3a63a32913e2', 6534, null, 'IN_PROGRESS', '53d5aebe-ebea-4da8-95ae-beebea4da887', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'Карамазовы 55', 4, 'ALCOHOL',
     '[{"dumplingId": "f566ada3-ae66-4fce-a6ad-a3ae660fce32", "count": 33}]'::jsonb,
     '[{"alcoholId": "b5a248ca-9c5c-4146-a248-ca9c5cf146b7", "count": 33}]'::jsonb);