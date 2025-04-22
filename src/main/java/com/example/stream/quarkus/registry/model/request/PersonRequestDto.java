package com.example.stream.quarkus.registry.model.request;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PersonRequestDto(String firstName, String lastName, @Past LocalDate birthday, String gender) {
}