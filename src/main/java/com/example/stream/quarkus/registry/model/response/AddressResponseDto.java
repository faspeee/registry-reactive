package com.example.stream.quarkus.registry.model.response;

public record AddressResponseDto(String streetId, String personId, String streetAddress, String city, String state,
                                 String country, String zipCode) {
}