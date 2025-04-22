package com.example.stream.quarkus.registry.model.error;

public sealed interface PersonError extends Error permits PersonNotFound, PersonServerError {
}
