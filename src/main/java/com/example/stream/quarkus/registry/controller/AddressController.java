package com.example.stream.quarkus.registry.controller;

import com.example.stream.quarkus.registry.service.AddressService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
}
