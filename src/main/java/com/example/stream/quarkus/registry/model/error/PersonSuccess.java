package com.example.stream.quarkus.registry.model.error;

public sealed interface PersonSuccess extends Success permits PersonDeleteOk {
}
