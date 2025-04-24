package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.converter.PersonConverter;
import com.example.stream.quarkus.registry.entity.Person;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.PersonIsPresent;
import com.example.stream.quarkus.registry.model.error.PersonServerError;
import com.example.stream.quarkus.registry.model.error.Success;
import com.example.stream.quarkus.registry.model.request.PersonRequestDto;
import com.example.stream.quarkus.registry.model.response.PersonResponseDto;
import com.example.stream.quarkus.registry.repository.PersonRepository;
import com.example.stream.quarkus.registry.utility.UtilMunity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.stream.quarkus.registry.utility.UtilMunity.startUni;
import static com.example.stream.quarkus.registry.utility.UtilMunity.startUniFromItem;

/**
 * {@code PersonService} provides business logic for managing {@link Person} entities.
 *
 * <p>It offers reactive operations for checking existence, retrieving, creating, updating, and
 * deleting person records. It coordinates with the {@link PersonRepository} for data access
 * and uses the {@link PersonConverter} to transform between DTOs and entities.</p>
 *
 * <p>All methods are built with {@link Uni} and {@link Multi} from Mutiny to enable
 * non-blocking, asynchronous flows. Error handling is standardized through {@link Either},
 * making error propagation predictable and testable.</p>
 */
@ApplicationScoped
public final class PersonService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;

    /**
     * Constructs a {@code PersonService} with the required dependencies.
     *
     * @param personRepository the repository to interact with persistence layer
     * @param personConverter  the converter for mapping between entity and DTO representations
     */
    public PersonService(PersonRepository personRepository, PersonConverter personConverter) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
    }

    /**
     * Checks whether a person exists by their unique identifier.
     *
     * @param personId the ID of the person as a string (UUID format)
     * @return a {@link Uni} containing either an error or a {@link Success} response indicating presence
     */
    public Uni<Either<Error, Success>> existPersonById(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::existById)
                .map(either -> either.fold(Either::left,
                        success -> Either.right(new PersonIsPresent())));
    }

    /**
     * Retrieves all people and maps them to a set of response DTOs.
     *
     * @return a {@link Multi} stream of {@link PersonResponseDto} sets
     */
    public Multi<Set<PersonResponseDto>> getAllPeople() {
        return startUni(() -> personRepository.getAllPeople()
                .toMulti()
                .map(people -> personConverter.toDto(new HashSet<>(people))));
    }

    /**
     * Retrieves a person by ID and maps them to a response DTO.
     *
     * @param personId the person's ID in UUID string format
     * @return a {@link Uni} containing either an error or the mapped {@link PersonResponseDto}
     */
    public Uni<Either<Error, PersonResponseDto>> getPersonById(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::getPersonById)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())))
                .map(either -> either.map(personConverter::toDto));
    }

    /**
     * Creates a new person in the system.
     *
     * @param personRequestDto the person data to be persisted
     * @return a {@link Uni} containing either an error or the created {@link PersonResponseDto}
     */
    public Uni<Either<Error, PersonResponseDto>> createPerson(PersonRequestDto personRequestDto) {
        return personRepository.createOrUpdatePerson(personConverter.toEntity(personRequestDto))
                .map(innerEither -> innerEither.map(personConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }

    /**
     * Updates an existing person’s data after checking existence.
     *
     * @param personId         the ID of the person to update
     * @param personRequestDto the updated data
     * @return a {@link Uni} containing either an error or the updated {@link PersonResponseDto}
     */
    public Uni<Either<Error, PersonResponseDto>> updatePerson(String personId, PersonRequestDto personRequestDto) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::existById)
                .flatMap(response -> response.fold(error -> UtilMunity.<Boolean, Person>createUniWithError(response),
                        success -> personRepository.createOrUpdatePerson(personConverter.toEntity(personRequestDto))))
                .map(either -> either.map(personConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }

    /**
     * Deletes a person by their ID.
     *
     * @param personId the ID of the person to be deleted
     * @return a {@link Uni} containing either an error or a success indicator
     */
    public Uni<Either<Error, Success>> deletePerson(String personId) {
        return startUniFromItem(personId)
                .map(UUID::fromString)
                .flatMap(personRepository::deletePerson)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new PersonServerError(throwable.getMessage(), PersonService.class.getName())));
    }
}
