package com.example.stream.quarkus.registry.model.response;

import java.time.LocalDate;

public record PersonResponseDto(String personId, String firstName, String lastName, LocalDate birthday, String gender) {
}
