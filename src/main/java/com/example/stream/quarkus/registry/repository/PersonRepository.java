package com.example.stream.quarkus.registry.repository;

import com.example.stream.quarkus.registry.entity.Person;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.*;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public final class PersonRepository implements PanacheRepositoryBase<Person, UUID> {
    private static Uni<Either<Error, Boolean>> personExistResponse(Uni<Boolean> person) {
        return person
                .<Either<Error, Boolean>>map(aBoolean -> Boolean.TRUE.equals(aBoolean) ? Either.right(true) : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonRepository.class.getName())));
    }

    private static Uni<Either<Error, Person>> processResponse(Uni<Person> address) {
        return address
                .<Either<Error, Person>>map(val -> val != null ? Either.right(val) : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonRepository.class.getName())));
    }

    @WithTransaction
    public Uni<Either<Error, Boolean>> existById(UUID personId) {
        return personExistResponse(findById(personId).map(Objects::nonNull)
                .replaceIfNullWith(false));
    }

    @WithTransaction
    public Uni<List<Person>> getAllPeople() {
        return findAll().list();
    }

    @WithTransaction
    public Uni<Either<Error, Person>> getPersonById(UUID personId) {
        return processResponse(findById(personId));
    }

    @WithTransaction
    public Uni<Either<Error, Person>> createOrUpdatePerson(Person person) {
        return processResponse(persist(person));
    }

    @WithTransaction
    public Uni<Either<Error, Success>> deletePerson(UUID personId) {
        return deleteById(personId)
                .<Either<Error, Success>>map(result -> Boolean.TRUE.equals(result) ? Either.right(new PersonDeleteOk()) : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(tr -> {
                    System.out.println(tr);
                    return Either.left(new PersonServerError(tr.getMessage(), PersonRepository.class.getName()));
                });
    }
}
