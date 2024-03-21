--liquibase formatted sql

--changeset user:1
ALTER TABLE location
    ADD CONSTRAINT unique_constraint UNIQUE (latitude, longitude);