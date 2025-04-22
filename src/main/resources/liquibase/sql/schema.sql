--liquibase formatted sql

--changeset aspeeencinaf:1
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


--changeset aspeeencinaf:2
CREATE TABLE IF NOT EXISTS persons
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name    VARCHAR(100),
    last_name     VARCHAR(100),
    date_of_birth DATE,
    gender        VARCHAR(20)
);


--changeset aspeeencinaf:3
CREATE TABLE IF NOT EXISTS addresses
(
    id             UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    street_address VARCHAR(255),
    city           VARCHAR(100),
    postal_code    VARCHAR(20),
    country        VARCHAR(100),
    state          VARCHAR(100),
    person_id      UUID NOT NULL,
    CONSTRAINT fk_addresses_person
        FOREIGN KEY (person_id)
            REFERENCES persons (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

--changeset aspeeencinaf:4
CREATE INDEX IF NOT EXISTS idx_addresses_person_id
    ON addresses (person_id);