--liquibase formatted sql

--changeset aspeeencinaf:5
INSERT INTO persons (id, first_name, last_name, date_of_birth, gender)
VALUES ('919c6db9-4e91-4cc8-9c9f-26c675e63bb3', 'Luca', 'Bianchi', DATE '1992-08-15', 'M');

--changeset aspeeencinaf:6
INSERT INTO persons (id, first_name, last_name, date_of_birth, gender)
VALUES ('3c4e8d42-b607-48ea-8475-1d979e502dce', 'Francesca', 'Ricci', DATE '1983-03-22', 'F');

--changeset aspeeencinaf:7
INSERT INTO persons (id, first_name, last_name, date_of_birth, gender)
VALUES ('a1a2de87-abb5-42ef-9b63-3e3244da6672', 'Giovanni', 'Esposito', DATE '1958-11-07', 'M');

--changeset aspeeencinaf:8
INSERT INTO persons (id, first_name, last_name, date_of_birth, gender)
VALUES ('d608b5b9-ac9e-4211-927c-46ea9e2f987a', 'Martina', 'Lombardi', DATE '1998-01-30', 'F');

--changeset aspeeencinaf:9
INSERT INTO persons (id, first_name, last_name, date_of_birth, gender)
VALUES ('d6a8d54c-7d28-4423-8633-6ceb6b7c9a6c', 'Alessandro', 'Moretti', DATE '2003-06-12', 'M');
