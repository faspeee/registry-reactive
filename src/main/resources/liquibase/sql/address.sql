--liquibase formatted sql

--changeset aspeeencinaf:10
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('bc6e89bd-08ca-4720-a47e-14b805dcf65a', 'Via Roma 12', 'Rome', '00100', 'Italy', 'Lazio',
        '919c6db9-4e91-4cc8-9c9f-26c675e63bb3');

--changeset aspeeencinaf:11
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('206638d6-9776-4bb4-8626-8ebffad8e183', 'Corso Torino 45', 'Milan', '20100', 'Italy', 'Lombardy',
        '919c6db9-4e91-4cc8-9c9f-26c675e63bb3');

--changeset aspeeencinaf:12
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('13430bde-14f2-45bb-9949-daf2ed0dc3c6', 'Piazza Navona 5', 'Rome', '00186', 'Italy', 'Lazio',
        '3c4e8d42-b607-48ea-8475-1d979e502dce');

--changeset aspeeencinaf:13
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('f192e18c-c43e-4225-8a9a-dcd1dcb6c516', 'Largo Augusto 8', 'Milan', '20122', 'Italy', 'Lombardy',
        '3c4e8d42-b607-48ea-8475-1d979e502dce');

--changeset aspeeencinaf:14
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('0e2af873-0ab9-4190-82a2-6e00b818a71a', 'Via Toledo 30', 'Naples', '80132', 'Italy', 'Campania',
        'a1a2de87-abb5-42ef-9b63-3e3244da6672');

--changeset aspeeencinaf:15
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('6dd485d4-27e6-404b-8962-734b41248ea4', 'Via Caracciolo', 'Naples', '80121', 'Italy', 'Campania',
        'a1a2de87-abb5-42ef-9b63-3e3244da6672');

--changeset aspeeencinaf:16
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('ddf18ee0-2a31-443c-b4f1-ee8be6de597b', 'Via Mazzini 22', 'Venice', '30100', 'Italy', 'Veneto',
        'd608b5b9-ac9e-4211-927c-46ea9e2f987a');

--changeset aspeeencinaf:17
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('2d6d8aec-60e4-48db-b0d7-c7eaac954b86', 'Fondamenta 10', 'Venice', '30124', 'Italy', 'Veneto',
        'd608b5b9-ac9e-4211-927c-46ea9e2f987a');

--changeset aspeeencinaf:18
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('9fd9dffd-1fbc-4981-aa5d-705f8cfe45ee', 'Piazza Dante 1', 'Turin', '10121', 'Italy', 'Piedmont',
        'd6a8d54c-7d28-4423-8633-6ceb6b7c9a6c');

--changeset aspeeencinaf:19
INSERT INTO addresses (id, street_address, city, postal_code, country, state, person_id)
VALUES ('e9c025e4-3a5d-4a36-b2bb-b5f365e26594', 'Corso Regina 50', 'Turin', '10126', 'Italy', 'Piedmont',
        'd6a8d54c-7d28-4423-8633-6ceb6b7c9a6c');
