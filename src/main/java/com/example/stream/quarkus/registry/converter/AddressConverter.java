package com.example.stream.quarkus.registry.converter;

import com.example.stream.quarkus.registry.entity.Address;
import com.example.stream.quarkus.registry.entity.Person;
import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public final class AddressConverter implements Converter<AddressRequestDto, AddressResponseDto, Address> {

    @Override
    public AddressResponseDto toDto(Address entity) {
        return new AddressResponseDto(entity.getId().toString(), entity.getPerson().getId().toString(), entity.getStreetAddress(),
                entity.getCity(), entity.getState(), entity.getCountry(), entity.getPostalCode());
    }

    @Override
    public Address toEntity(AddressRequestDto dto) {
        Address address = new Address();
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setCountry(dto.country());
        address.setStreetAddress(dto.streetAddress());
        address.setPostalCode(dto.zipCode());
        Person person = new Person();
        person.setId(UUID.fromString(dto.personId()));
        address.setPerson(person);
        return address;
    }
}
