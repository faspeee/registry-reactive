package com.example.stream.spring.registry.people.reactive.example.converter;

import com.example.stream.spring.registry.people.reactive.example.entity.Address;
import com.example.stream.spring.registry.people.reactive.example.model.request.AddressRequestDto;
import com.example.stream.spring.registry.people.reactive.example.model.response.AddressResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AddressConverter implements Converter<AddressRequestDto, AddressResponseDto, Address> {

    @Override
    public AddressResponseDto toDto(Address entity) {
        return null;
    }

    @Override
    public Address toEntity(AddressRequestDto dto) {
        return null;
    }
}
