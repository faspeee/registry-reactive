package com.example.stream.spring.registry.people.reactive.example.repository;

import com.example.stream.spring.registry.people.reactive.example.entity.Address;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import java.util.UUID;

public class AddressRepository implements PanacheRepositoryBase<Address, UUID> {
}
