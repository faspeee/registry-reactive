package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.converter.AddressConverter;
import com.example.stream.quarkus.registry.entity.Address;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.AddressServerError;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.Success;
import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import com.example.stream.quarkus.registry.repository.AddressRepository;
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
 * {@code AddressService} provides business logic for managing {@link Address} entities
 * and exposes operations to retrieve, create, update, and delete addresses.
 *
 * <p>This service layer coordinates with the {@link AddressRepository} for persistence,
 * the {@link AddressConverter} for DTO-entity transformation, and {@link PersonService}
 * to verify related person existence.</p>
 *
 * <p>All operations are built on top of Mutiny's {@link Uni} and {@link Multi} for
 * reactive processing. Errors are wrapped into the functional {@code Either} structure
 * to facilitate clean and composable error handling.</p>
 */
@ApplicationScoped
public final class AddressService {

    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;
    private final PersonService personService;

    /**
     * Constructs an {@code AddressService} with required dependencies.
     *
     * @param addressRepository the repository for address persistence
     * @param addressConverter  the converter to map between entity and DTO
     * @param personService     the service to validate associated person existence
     */
    public AddressService(AddressRepository addressRepository, AddressConverter addressConverter, PersonService personService) {
        this.addressRepository = addressRepository;
        this.addressConverter = addressConverter;
        this.personService = personService;
    }

    /**
     * Retrieves all address records and maps them to response DTOs.
     *
     * @return a {@link Multi} stream of address sets, converted to DTOs
     */
    public Multi<Set<AddressResponseDto>> getAllAddress() {
        return startUni(() -> addressRepository.getAllAddress()
                .toMulti()
                .map(addresses -> addressConverter.toDto(new HashSet<>(addresses))));
    }

    // Uncomment and implement if needed:
    /*
    public Multi<Set<AddressResponseDto>> getAllAddressByCountry(String country) { }

    public Multi<Set<AddressResponseDto>> getAllAddressByState(String state) { }

    public Multi<Set<AddressResponseDto>> getAllAddressByCity(String city) { }
    */

    /**
     * Fetches a single address by its unique identifier and maps it to a DTO.
     *
     * @param addressId the UUID string of the address
     * @return a {@link Uni} containing either an error or the address DTO
     */
    public Uni<Either<Error, AddressResponseDto>> getAddressById(String addressId) {
        return startUniFromItem(addressId)
                .map(UUID::fromString)
                .flatMap(addressRepository::getAddressById)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())))
                .map(either -> either.map(addressConverter::toDto));
    }

    /**
     * Creates a new address after verifying that the associated person exists.
     *
     * @param addressRequestDto the incoming address creation request
     * @return a {@link Uni} containing either an error or the created address DTO
     */
    public Uni<Either<Error, AddressResponseDto>> createAddress(AddressRequestDto addressRequestDto) {
        return startUniFromItem(addressRequestDto)
                .flatMap(addressInsideUni -> personService.existPersonById(addressInsideUni.personId())
                        .flatMap(either -> either.fold(error -> UtilMunity.<Success, AddressResponseDto>createUniWithError(either),
                                success -> addressRepository.createOrUpdateAddress(addressConverter.toEntity(addressInsideUni))
                                        .map(innerEither -> innerEither.map(addressConverter::toDto)))))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }

    /**
     * Updates an existing address, validating its existence first.
     *
     * @param addressId         the ID of the address to update
     * @param addressRequestDto the new address data
     * @return a {@link Uni} containing either an error or the updated address DTO
     */
    public Uni<Either<Error, AddressResponseDto>> updateAddress(String addressId, AddressRequestDto addressRequestDto) {
        return startUniFromItem(addressId)
                .map(UUID::fromString)
                .flatMap(addressRepository::existById)
                .flatMap(response -> response.fold(error -> UtilMunity.<Boolean, Address>createUniWithError(response),
                        success -> addressRepository.createOrUpdateAddress(addressConverter.toEntity(addressRequestDto))))
                .map(either -> either.map(addressConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }

    /**
     * Deletes an address by ID.
     *
     * @param addressId the ID of the address to delete
     * @return a {@link Uni} containing either an error or a success object
     */
    public Uni<Either<Error, Success>> deleteAddress(String addressId) {
        return startUniFromItem(addressId)
                .map(UUID::fromString)
                .flatMap(addressRepository::deleteAddress)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }
}
