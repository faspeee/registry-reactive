package com.example.stream.quarkus.registry.model.error;

public sealed interface AddressError extends Error permits AddressNotFound, AddressServerError {
}
