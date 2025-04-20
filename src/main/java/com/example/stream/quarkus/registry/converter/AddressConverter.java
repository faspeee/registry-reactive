package com.example.stream.quarkus.registry.converter;

import com.example.stream.quarkus.registry.entity.Address;
import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class AddressConverter implements Converter<AddressRequestDto, AddressResponseDto, Address> {

    @Override
    public AddressResponseDto toDto(Address entity) {
        return null;
    }

    @Override
    public Address toEntity(AddressRequestDto dto) {
        return null;
    }
}
