package com.example.stream.spring.registry.people.reactive.example.repository;

import com.example.stream.spring.registry.people.reactive.example.entity.Person;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import java.util.UUID;

public class PersonRepository implements PanacheRepositoryBase<Person, UUID> {
}
