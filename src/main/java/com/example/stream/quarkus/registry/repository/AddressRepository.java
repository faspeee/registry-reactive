package com.example.stream.quarkus.registry.repository;

import com.example.stream.quarkus.registry.entity.Address;
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
 * Repository class that manages persistence operations for the {@link Address} entity using
 * the Quarkus Panache reactive repository model.
 *
 * <p>This class provides transactional methods for CRUD operations and wraps responses
 * using {@link Either} to encapsulate success and error scenarios.</p>
 *
 * <p>Error responses are wrapped using {@link AddressNotFound}, {@link AddressServerError}, or other
 * types extending from {@link Error}. The class uses helper methods like {@link #processResponse(Uni)}
 * and {@link #addressExistResponse(Uni)} to convert raw results into structured, domain-aware responses.</p>
 *
 * <p>
 * Pattern matching and concise null-handling logic ensure robustness and improved readability.
 * </p>
 */
@ApplicationScoped
public final class AddressRepository implements PanacheRepositoryBase<Address, UUID> {

    /**
     * Converts a {@link Uni} of {@link Address} into a {@link Uni} of {@link Either}, handling nulls and errors.
     *
     * <p>If the address is found, wraps it in {@link Either.Right}. If null, wraps an {@link AddressNotFound}.
     * In case of failure, wraps an {@link AddressServerError}.</p>
     *
     * @param address the asynchronous address result
     * @return a {@link Uni} with either an error or a found address
     * @see #getAddressById(UUID)
     * @see #createOrUpdateAddress(Address)
     */
    private static Uni<Either<Error, Address>> processResponse(Uni<Address> address) {
        return address
                .<Either<Error, Address>>map(val -> val != null
                        ? Either.right(val)
                        : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressRepository.class.getName())));
    }

    /**
     * Converts a {@link Uni} of {@link Boolean} into a {@link Uni} of {@link Either},
     * interpreting {@code true} as success and {@code false} as {@link AddressNotFound}.
     *
     * @param address the asynchronous boolean result
     * @return a {@link Uni} of {@link Either} with error or success
     * @see #existById(UUID)
     */
    private static Uni<Either<Error, Boolean>> addressExistResponse(Uni<Boolean> address) {
        return address
                .<Either<Error, Boolean>>map(aBoolean -> Boolean.TRUE.equals(aBoolean)
                        ? Either.right(true)
                        : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressRepository.class.getName())));
    }

    /**
     * Retrieves all addresses from the database.
     *
     * <p>This method uses Panache's {@link #findAll()} API to load the full list.</p>
     *
     * @return a {@link Uni} emitting the list of {@link Address} entities
     */
    @WithTransaction
    public Uni<List<Address>> getAllAddress() {
        return findAll().list();
    }

    /**
     * Retrieves a single {@link Address} by its ID, wrapping the result in an {@link Either}.
     *
     * @param addressId the UUID of the address
     * @return a {@link Uni} with either a found address or an error
     * @see #processResponse(Uni)
     */
    @WithTransaction
    public Uni<Either<Error, Address>> getAddressById(UUID addressId) {
        return processResponse(findById(addressId));
    }

    /**
     * Checks whether an address with the given ID exists.
     *
     * @param addressId the UUID of the address
     * @return a {@link Uni} with {@link Either#right(Boolean)} if found, or {@link AddressNotFound} otherwise
     * @see #addressExistResponse(Uni)
     */
    @WithTransaction
    public Uni<Either<Error, Boolean>> existById(UUID addressId) {
        return addressExistResponse(findById(addressId)
                .map(Objects::nonNull)
                .replaceIfNullWith(false));
    }

    /**
     * Persists or updates an {@link Address} and returns the result wrapped in an {@link Either}.
     *
     * <p>Delegates to {@link #processResponse(Uni)} to handle success and failure cases.</p>
     *
     * @param address the address to create or update
     * @return a {@link Uni} of {@link Either} representing success or error
     */
    @WithTransaction
    public Uni<Either<Error, Address>> createOrUpdateAddress(Address address) {
        return processResponse(persist(address));
    }

    /**
     * Deletes an {@link Address} by ID.
     *
     * <p>If the deletion is successful, returns {@link AddressDeleteOk}. If not found, returns {@link AddressNotFound}.
     * In case of failure, returns {@link AddressServerError}.</p>
     *
     * <p>Example:
     * <pre>
     *   UUID id = UUID.randomUUID();
     *   deleteAddress(id).subscribe().with(result -> {
     *       result.fold(
     *           error -> System.err.println("Failed: " + error),
     *           success -> System.out.println("Deleted OK")
     *       );
     *   });
     * </pre>
     *
     * @param addressId the UUID of the address to delete
     * @return a {@link Uni} with {@link Either} of {@link Error} or {@link Success}
     */
    @WithTransaction
    public Uni<Either<Error, Success>> deleteAddress(UUID addressId) {
        return deleteById(addressId)
                .<Either<Error, Success>>map(result -> Boolean.TRUE.equals(result)
                        ? Either.right(new AddressDeleteOk())
                        : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressRepository.class.getName())));
    }
}
