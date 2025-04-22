package com.example.stream.quarkus.registry.model.error;

import java.time.LocalDateTime;

public record AddressNotFound(String message, LocalDateTime timeStamp, String classHappen) implements AddressError {

    public AddressNotFound(String classHappen) {
        this("Address not found", LocalDateTime.now(), classHappen);
    }
}
