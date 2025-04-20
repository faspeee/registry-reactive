package com.example.stream.quarkus.registry.repository;

import com.example.stream.quarkus.registry.entity.Person;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public final class PersonRepository implements PanacheRepositoryBase<Person, UUID> {
}
