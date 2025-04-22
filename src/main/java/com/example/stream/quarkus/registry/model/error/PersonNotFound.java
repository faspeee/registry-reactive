package com.example.stream.quarkus.registry.model.error;

import java.time.LocalDateTime;

public record PersonNotFound(String message, LocalDateTime timeStamp, String classHappen) implements PersonError {
    public PersonNotFound(String classHappen) {
        this("Person not found", LocalDateTime.now(), classHappen);
    }
}
