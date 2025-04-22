package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.converter.PersonConverter;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.GenericError;
import com.example.stream.quarkus.registry.model.error.PersonIsPresent;
import com.example.stream.quarkus.registry.model.error.Success;
import com.example.stream.quarkus.registry.repository.PersonRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public final class PersonService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;

    public PersonService(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    private static <T> Uni<T> startUniFromItem(T item) {
        return Uni.createFrom().item(item);
    }

    public Uni<Either<Error, Success>> existPersonById(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::existById)
                .map(either -> either.getRight()
                        .<Either<Error, Success>>map(aBoolean -> Either.right(new PersonIsPresent()))
                        .orElse(Either.left(either.getLeft().orElse(new GenericError()))));
    }
}
