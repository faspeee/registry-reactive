package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
class PersonaControllerTest {
    private static PersonRequestDto createPersonaRequestDto(String firstName, String lastName, LocalDate birthday, String gender) {
        return new PersonRequestDto(firstName, lastName, birthday, gender);
    }

    @Test
    void get_all_people() {
        given().when()
                .contentType(ContentType.JSON)
                .get("/person/getAllPeople")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void add_person_test() {
        PersonRequestDto personaRequestDto = createPersonaRequestDto("Louis", "Vitton",
                LocalDate.of(1940, 1, 10), "M");
        given().when()
                .contentType(ContentType.JSON)
                .body(personaRequestDto)
                .post("/person/createPerson")
                .then()
                .statusCode(201)
                .body("firstName", equalTo("Louis"))
                .and()
                .body("lastName", equalTo("Vitton"));
    }

    @Test
    void add_person_server_error_test() {
        PersonRequestDto personaRequestDto = createPersonaRequestDto("Pedro", "Urdemales",
                LocalDate.of(2026, 11, 21), "T");
        given().when()
                .contentType(ContentType.JSON)
                .body(personaRequestDto)
                .post("/person/createPerson")
                .then()
                .statusCode(400)
                .body("violations[0].message", equalTo("must be a past date"));
    }

    @Test
    void update_person_test() {
        PersonRequestDto personaRequestDto = createPersonaRequestDto("Louis", "Vitton",
                LocalDate.of(1940, 1, 10), "M");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "919c6db9-4e91-4cc8-9c9f-26c675e63bb3")
                .body(personaRequestDto)
                .put("/person/updatePerson")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Louis"))
                .and()
                .body("lastName", equalTo("Vitton"));
    }

    @Test
    void update_person_not_found_test() {
        PersonRequestDto personaRequestDto = createPersonaRequestDto("Louis", "Vitton",
                LocalDate.of(1940, 1, 10), "M");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "919c6db9-4e91-4cc8-9c9f-26c675e63bb2")
                .body(personaRequestDto)
                .put("/person/updatePerson")
                .then()
                .statusCode(404)
                .body("message", equalTo("Person not found"));
    }

    @Test
    void update_person_server_error_test() {
        PersonRequestDto personaRequestDto = createPersonaRequestDto("Louis", "Vitton",
                LocalDate.of(1940, 1, 10), "M");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "212")
                .body(personaRequestDto)
                .put("/person/updatePerson")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 212"));
    }

    @Test
    void get_person_by_id_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "3c4e8d42-b607-48ea-8475-1d979e502dce")
                .get("/person/getPersonById")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Francesca"))
                .and()
                .body("lastName", equalTo("Ricci"));
    }

    @Test
    void get_person_by_id_not_found_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "3c4e8d42-b607-48ea-8475-1d979e502dc1")
                .get("/person/getPersonById")
                .then()
                .statusCode(404)
                .body("message", equalTo("Person not found"));
    }

    @Test
    void get_person_by_id_server_error_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", 1)
                .get("/person/getPersonById")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 1"));
    }

    @Test
    void delete_person_by_id_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "d608b5b9-ac9e-4211-927c-46ea9e2f987a")
                .delete("/person/deletePerson")
                .then()
                .statusCode(204);
    }

    @Test
    void delete_person_by_id_not_found_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", "2d6d8aec-60e4-48db-b0d7-c7eaac954b81")
                .delete("/person/deletePerson")
                .then()
                .statusCode(404)
                .body("message", equalTo("Person not found"));
    }

    @Test
    void delete_person_by_id__server_error_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("personId", 1)
                .delete("/person/deletePerson")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 1"));

    }
}