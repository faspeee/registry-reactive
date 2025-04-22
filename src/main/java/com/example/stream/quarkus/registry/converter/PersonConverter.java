package com.example.stream.quarkus.registry.converter;

import com.example.stream.quarkus.registry.entity.Person;
import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class PersonConverter implements Converter<PersonRequestDto, PersonResponseDto, Person> {

    @Override
    public PersonResponseDto toDto(Person entity) {
        return null;
    }

    @Override
    public Person toEntity(PersonRequestDto dto) {
        return null;
    }
}
