package com.example.stream.quarkus.registry.model.error;

import java.time.LocalDateTime;

public record PersonServerError(String message, LocalDateTime timeStamp, String classHappen) implements PersonError {
    public PersonServerError(String message, String classHappen) {
        this(message, LocalDateTime.now(), classHappen);
    }
}
