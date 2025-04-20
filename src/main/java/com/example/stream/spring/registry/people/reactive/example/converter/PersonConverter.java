package com.example.stream.spring.registry.people.reactive.example.converter;

import com.example.stream.spring.registry.people.reactive.example.entity.Person;
import com.example.stream.spring.registry.people.reactive.example.model.request.PersonRequestDto;
import com.example.stream.spring.registry.people.reactive.example.model.response.PersonResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonConverter implements Converter<PersonRequestDto, PersonResponseDto, Person> {

    @Override
    public PersonResponseDto toDto(Person entity) {
        return null;
    }

    @Override
    public Person toEntity(PersonRequestDto dto) {
        return null;
    }
}
