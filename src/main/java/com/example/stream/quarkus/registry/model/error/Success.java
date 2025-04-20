package com.example.stream.quarkus.registry.model.error;

public sealed interface Success permits AddressSuccess, PersonSuccess {
}
