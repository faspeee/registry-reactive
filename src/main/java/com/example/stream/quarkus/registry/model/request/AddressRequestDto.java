package com.example.stream.quarkus.registry.model.request;

public record AddressRequestDto(String personId, String streetAddress, String city, String state, String country,
                                String zipCode) {
}
