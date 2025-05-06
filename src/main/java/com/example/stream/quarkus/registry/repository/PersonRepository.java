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

/**
 * Repository class that provides reactive database operations for the {@link Person} entity
 * using the Quarkus Panache Reactive pattern.
 *
 * <p>All methods are transactional and return results wrapped in {@link io.smallrye.mutiny.Uni} and
 * {@link Either} to consistently propagate both success and domain-level error cases.</p>
 *
 * <p>This repository encapsulates common behavior like checking existence, retrieving, persisting,
 * and deleting {@link Person} records. It handles error conditions using custom error types such as
 * {@link PersonNotFound} and {@link PersonServerError}, wrapped in an {@link Either.Left} container.</p>
 *
 * <p>
 * Pattern matching (via `switch` expressions in service or controller layers) is typically used to
 * extract and process the {@link Either} result in a declarative and safe way.
 * </p>
 */
@ApplicationScoped
public final class PersonRepository implements PanacheRepositoryBase<Person, UUID> {

    /**
     * Wraps a {@link Uni} containing a boolean result in an {@link Either}.
     *
     * <p>Used for checking the existence of a {@link Person}. If the value is {@code true},
     * wraps it in {@link Either.Right}. If {@code false}, returns a {@link PersonNotFound}.
     * If an exception occurs, it is caught and wrapped in a {@link PersonServerError}.</p>
     *
     * @param person a {@link Uni} emitting a boolean value
     * @return a {@link Uni} of {@link Either} indicating existence or an error
     * @see #existById(UUID)
     */
    private static Uni<Either<Error, Boolean>> personExistResponse(Uni<Boolean> person) {
        return person
                .<Either<Error, Boolean>>map(aBoolean ->
                        Boolean.TRUE.equals(aBoolean)
                                ? Either.right(true)
                                : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable ->
                        Either.left(new PersonServerError(throwable.getMessage(), PersonRepository.class.getName())));
    }

    /**
     * Wraps a {@link Uni} of {@link Person} in an {@link Either}, checking for nulls or exceptions.
     *
     * <p>If a non-null {@link Person} is found, wraps it in {@link Either.Right}. If null,
     * returns a {@link PersonNotFound}. If an error occurs, wraps it in {@link PersonServerError}.</p>
     *
     * @param person a {@link Uni} containing a {@link Person} entity
     * @return a {@link Uni} of {@link Either} indicating success or error
     * @see #getPersonById(UUID)
     * @see #createOrUpdatePerson(Person)
     */
    private static Uni<Either<Error, Person>> processResponse(Uni<Person> person) {
        return person
                .<Either<Error, Person>>map(val ->
                        val != null
                                ? Either.right(val)
                                : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable ->
                        Either.left(new PersonServerError(throwable.getMessage(), PersonRepository.class.getName())));
    }

    /**
     * Checks whether a {@link Person} exists by ID.
     *
     * @param personId the UUID of the person to check
     * @return a {@link Uni} of {@link Either} containing {@code true} if found, or an error
     * @see #personExistResponse(Uni)
     */
    @WithTransaction
    public Uni<Either<Error, Boolean>> existById(UUID personId) {
        return personExistResponse(
                findById(personId)
                        .map(Objects::nonNull)
                        .replaceIfNullWith(false)
        );
    }

    /**
     * Retrieves all people from the database.
     *
     * @return a {@link Uni} emitting a {@link List} of all {@link Person} entities
     */
    @WithTransaction
    public Uni<List<Person>> getAllPeople() {
        return findAll().list();
    }

    /**
     * Fetches a {@link Person} by ID.
     *
     * <p>Returns an {@link Either.Right} with the entity if found, or {@link PersonNotFound} / {@link PersonServerError} otherwise.</p>
     *
     * @param personId the UUID of the person to retrieve
     * @return a {@link Uni} of {@link Either} with the {@link Person} or an error
     * @see #processResponse(Uni)
     */
    @WithTransaction
    public Uni<Either<Error, Person>> getPersonById(UUID personId) {
        return processResponse(findById(personId));
    }

    /**
     * Persists or updates a {@link Person} in the database.
     *
     * <p>If the persistence succeeds, returns an {@link Either.Right} with the saved entity.
     * On error, returns {@link PersonServerError}.</p>
     *
     * @param person the person entity to create or update
     * @return a {@link Uni} of {@link Either} with the saved {@link Person} or error
     */
    @WithTransaction
    public Uni<Either<Error, Person>> createOrUpdatePerson(Person person) {
        return processResponse(persist(person));
    }

    /**
     * Deletes a {@link Person} by ID.
     *
     * <p>If the person exists and deletion succeeds, returns {@link PersonDeleteOk}.
     * If not found, returns {@link PersonNotFound}.
     * If an exception occurs, returns {@link PersonServerError}.</p>
     *
     * <p><strong>Example:</strong>
     * <pre>{@code
     * UUID personId = UUID.randomUUID();
     * repository.deletePerson(personId).subscribe().with(result ->
     *     result.fold(
     *         err -> System.err.println("Error: " + err),
     *         ok -> System.out.println("Deleted successfully")
     *     )
     * );
     * }</pre>
     *
     * @param personId the UUID of the person to delete
     * @return a {@link Uni} of {@link Either} with {@link Success} or an {@link Error}
     */
    @WithTransaction
    public Uni<Either<Error, Success>> deletePerson(UUID personId) {
        return deleteById(personId)
                .<Either<Error, Success>>map(result ->
                        Boolean.TRUE.equals(result)
                                ? Either.right(new PersonDeleteOk())
                                : Either.left(new PersonNotFound(PersonRepository.class.getName())))
                .onFailure()
                .recoverWithItem(tr -> {
                    System.out.println(tr); // debug output
                    return Either.left(new PersonServerError(tr.getMessage(), PersonRepository.class.getName()));
                });
    }
}
