package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import com.example.stream.quarkus.registry.repository.AddressRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public final class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Multi<Set<AddressResponseDto>> getAllAddress() {
    }

    public Uni<AddressResponseDto> getAddressById(String addressId) {
    }
   /*  public Multi<Set<AddressResponseDto>> getAllAddressByCountry(String country) {
    }

    public Multi<Set<AddressResponseDto>> getAllAddressByState(String state) {
    }

    public Multi<Set<AddressResponseDto>> getAllAddressByCity(String city) {
    }*/

    public Uni<AddressResponseDto> createAddress(AddressRequestDto addressRequestDto) {
    }

    public Uni<AddressResponseDto> updateAddress(String addressId, AddressRequestDto addressRequestDto) {
    }
}
