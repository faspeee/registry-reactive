package com.example.stream.quarkus.registry.converter;

import com.example.stream.quarkus.registry.entity.Person;
import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class PersonConverter implements Converter<PersonRequestDto, PersonResponseDto, Person> {

    @Override
    public PersonResponseDto toDto(Person entity) {
        return new PersonResponseDto(entity.getId().toString(), entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), entity.getGender());
    }

    @Override
    public Person toEntity(PersonRequestDto dto) {
        Person entity = new Person();
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setDateOfBirth(dto.birthday());
        entity.setGender(dto.gender());
        return entity;
    }
}
