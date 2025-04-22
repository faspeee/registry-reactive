package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.converter.PersonConverter;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.*;
import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import com.example.stream.quarkus.registry.repository.PersonRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.stream.quarkus.registry.utility.UtilMunity.*;

@ApplicationScoped
public final class PersonService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;

    public PersonService(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    public Uni<Either<Error, Success>> existPersonById(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::existById)
                .map(either -> either.getRight()
                        .<Either<Error, Success>>map(aBoolean -> Either.right(new PersonIsPresent()))
                        .orElse(Either.left(either.getLeft().orElse(new GenericError()))));
    }

    public Multi<Set<PersonResponseDto>> getAllPeople() {
        return startUni(() -> personRepository.getAllPeople()
                .toMulti()
                .map(addresses -> personConverter.toDto(new HashSet<>(addresses))));
    }

    public Uni<Either<Error, PersonResponseDto>> getPersonById(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(personRepository::getPersonById)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())))
                .map(either -> either.map(personConverter::toDto));
    }

    public Uni<Either<Error, PersonResponseDto>> createPerson(PersonRequestDto personRequestDto) {
        return personRepository.createOrUpdatePerson(personConverter.toEntity(personRequestDto))
                .map(innerEither -> innerEither.map(personConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }

    public Uni<Either<Error, PersonResponseDto>> updatePerson(String personId, PersonRequestDto personRequestDto) {
        return startUniFromItem(personId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(personRepository::existById)
                .flatMap(response -> response.getRight()
                        .map(ignored -> personRepository.createOrUpdatePerson(personConverter.toEntity(personRequestDto)))
                        .orElse(createUniWithError(response)))
                .map(either -> either.map(personConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }

    public Uni<Either<Error, Success>> deletePerson(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(personRepository::deletePerson)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }
}
