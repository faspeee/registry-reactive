package com.example.stream.quarkus.registry.model.error;

public sealed interface Error permits AddressError, GenericError, PersonError {
}
