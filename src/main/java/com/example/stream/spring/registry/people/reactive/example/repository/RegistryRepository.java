package com.example.stream.spring.registry.people.reactive.example.repository;

import com.example.stream.spring.registry.people.reactive.example.entity.Registry;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RegistryRepository extends ReactiveCrudRepository<Registry,Long> {
}
