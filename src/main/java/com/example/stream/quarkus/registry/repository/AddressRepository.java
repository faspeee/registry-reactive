package com.example.stream.quarkus.registry.repository;

import com.example.stream.quarkus.registry.entity.Address;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public final class AddressRepository implements PanacheRepositoryBase<Address, UUID> {

    public Uni<List<Address>> getAllAddress() {
        return findAll().list();
    }

    public Uni<Address> getAddressById(UUID id) {
        return findById(id);
    }

    public Uni<Address> createOrUpdateAddress(Address address) {
        return persist(address);
    }
}
