package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
final class AddressControllerTest {

    private static AddressRequestDto createAddressRequestDto(String personId, String streetAddress, String city, String state, String country,
                                                             String zipCode) {
        return new AddressRequestDto(personId, streetAddress, city, state, country, zipCode);
    }

    @Test
    void get_all_addresses() {
        given().when()
                .contentType(ContentType.JSON)
                .get("/address/getAllAddress")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void add_address_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("919c6db9-4e91-4cc8-9c9f-26c675e63bb3",
                "742 Evergreen Terrace", "Springfield", "IL", "USA", "62704");
        given().when()
                .contentType(ContentType.JSON)
                .body(addressRequestDto)
                .post("/address/createAddress")
                .then()
                .statusCode(201)
                .body("city", equalTo("Springfield"))
                .and()
                .body("country", equalTo("USA"));
    }

    @Test
    void add_address_not_found_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("22222222-2222-2222-2222-222222222222",
                "221B Baker Street", "London", "Greater London", "UK", "NW1 6XE");
        given().when()
                .contentType(ContentType.JSON)
                .body(addressRequestDto)
                .post("/address/createAddress")
                .then()
                .statusCode(404)
                .body("message", equalTo("Person not found"));
    }

    @Test
    void add_address_server_error_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("1232", "1600 Amphitheatre Parkway",
                "Mountain View", "CA", "USA", "94043");
        given().when()
                .contentType(ContentType.JSON)
                .body(addressRequestDto)
                .post("/address/createAddress")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 1232"));
    }

    @Test
    void update_address_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("3c4e8d42-b607-48ea-8475-1d979e502dce",
                "742 Evergreen Terrace", "New York", "NY", "USA", "62704");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "2d6d8aec-60e4-48db-b0d7-c7eaac954b86")
                .body(addressRequestDto)
                .put("/address/updateAddress")
                .then()
                .statusCode(200)
                .body("city", equalTo("New York"))
                .and()
                .body("state", equalTo("NY"));
    }

    @Test
    void update_address_not_found_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("22222222-2212-2222-2222-222222222222",
                "221B Baker Street", "London", "Greater London", "UK", "NW1 6XE");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "6dd485d4-27e6-404b-8962-733b41248ea4")
                .body(addressRequestDto)
                .put("/address/updateAddress")
                .then()
                .statusCode(404)
                .body("message", equalTo("Address not found"));
    }

    @Test
    void update_address_server_error_test() {
        AddressRequestDto addressRequestDto = createAddressRequestDto("1232", "1600 Amphitheatre Parkway",
                "Mountain View", "CA", "USA", "94043");
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "212")
                .body(addressRequestDto)
                .put("/address/updateAddress")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 212"));
    }

    @Test
    void get_address_by_id_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "6dd485d4-27e6-404b-8962-734b41248ea4")
                .get("/address/getAddressById")
                .then()
                .statusCode(200)
                .body("streetAddress", equalTo("Via Caracciolo"))
                .and()
                .body("city", equalTo("Naples"));
    }

    @Test
    void get_address_by_id_not_found_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "11111111-1111-1111-1111-111111111112")
                .get("/address/getAddressById")
                .then()
                .statusCode(404)
                .body("message", equalTo("Address not found"));
    }

    @Test
    void get_address_by_id_server_error_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", 1)
                .get("/address/getAddressById")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 1"));
    }

    @Test
    void delete_address_by_id_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "0e2af873-0ab9-4190-82a2-6e00b818a71a")
                .delete("/address/deleteAddress")
                .then()
                .statusCode(204);
    }

    @Test
    void delete_address_by_id_not_found_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", "2d6d8aec-60e4-48db-b0d7-c7eaac954b81")
                .delete("/address/deleteAddress")
                .then()
                .statusCode(404)
                .body("message", equalTo("Address not found"));
    }

    @Test
    void delete_address_by_id__server_error_test() {
        given().when()
                .contentType(ContentType.JSON)
                .queryParam("addressId", 1)
                .delete("/address/deleteAddress")
                .then()
                .statusCode(500)
                .body("message", equalTo("Invalid UUID string: 1"));

    }
}
