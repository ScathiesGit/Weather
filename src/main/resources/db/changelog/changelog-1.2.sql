--liquibase formatted sql

--changeset user:1
ALTER TABLE user_location
    DROP CONSTRAINT user_location_location_id_fkey;

--changeset user:2
ALTER TABLE user_location
    ADD CONSTRAINT location_id_fkey FOREIGN KEY (location_id) REFERENCES location (id);
