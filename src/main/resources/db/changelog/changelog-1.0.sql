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
    name      VARCHAR(64) NOT NULL,
    latitude  VARCHAR(32) NOT NULL,
    longitude VARCHAR(32) NOT NULL
);

--changeset user:3
CREATE TABLE user_location
(
    user_id     BIGINT NOT NULL REFERENCES users (id),
    location_id BIGINT NOT NULL REFERENCES users (id),
    PRIMARY KEY (user_id, location_id)
);

