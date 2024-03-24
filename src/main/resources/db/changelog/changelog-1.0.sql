--liquibase formatted sql

--changeset user:1
CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)        NOT NULL
);

--changeset user:2
CREATE TABLE location
(
    id        BIGSERIAL PRIMARY KEY,
    user_id   BIGINT      NOT NULL REFERENCES users (id),
    name      VARCHAR(64) NOT NULL,
    latitude  VARCHAR(32) NOT NULL,
    longitude VARCHAR(32) NOT NULL,
    CONSTRAINT unique_user_location UNIQUE (user_id, latitude, longitude)
);

