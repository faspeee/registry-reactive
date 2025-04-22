package com.example.stream.quarkus.registry.model.error;

import java.time.LocalDateTime;

public record AddressServerError(String message, LocalDateTime timeStamp, String classHappen) implements AddressError {
    public AddressServerError(String message, String classHappen) {
        this(message, LocalDateTime.now(), classHappen);
    }

}
