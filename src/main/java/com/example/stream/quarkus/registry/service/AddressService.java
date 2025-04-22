package com.example.stream.quarkus.registry.service;

import com.example.stream.quarkus.registry.converter.AddressConverter;
import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.AddressServerError;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.Success;
import com.example.stream.quarkus.registry.model.request.AddressRequestDto;
import com.example.stream.quarkus.registry.model.response.AddressResponseDto;
import com.example.stream.quarkus.registry.repository.AddressRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

import static com.example.stream.quarkus.registry.utility.UtilMunity.createUniWithError;

@ApplicationScoped
public final class AddressService {
    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;
    private final PersonService personService;

    public AddressService(AddressRepository addressRepository, AddressConverter addressConverter, PersonService personService) {
        this.addressRepository = addressRepository;
        this.addressConverter = addressConverter;
        this.personService = personService;
    }

    private static <T> Uni<T> startUniFromItem(T item) {
        return Uni.createFrom().item(item);
    }

    private static <T> Multi<T> startUni(Supplier<Multi<? extends T>> supplier) {
        return Multi.createFrom().deferred(supplier);
    }

    public Multi<Set<AddressResponseDto>> getAllAddress() {
        return startUni(() -> addressRepository.getAllAddress()
                .toMulti()
                .map(addresses -> addressConverter.toDto(new HashSet<>(addresses))));
    }
   /*  public Multi<Set<AddressResponseDto>> getAllAddressByCountry(String country) {
    }

    public Multi<Set<AddressResponseDto>> getAllAddressByState(String state) {
    }

    public Multi<Set<AddressResponseDto>> getAllAddressByCity(String city) {
    }*/

    public Uni<Either<Error, AddressResponseDto>> getAddressById(String addressId) {
        return startUniFromItem(addressId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(addressRepository::getAddressById)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())))
                .map(either -> either.map(addressConverter::toDto));
    }

    public Uni<Either<Error, AddressResponseDto>> createAddress(AddressRequestDto addressRequestDto) {
        return startUniFromItem(addressRequestDto)
                .flatMap(addressInsideUni -> personService.existPersonById(addressInsideUni.personId())
                        .flatMap(either -> either.getRight()
                                .map(ignored -> addressRepository.createOrUpdateAddress(addressConverter.toEntity(addressInsideUni))
                                        .map(innerEither -> innerEither.map(addressConverter::toDto)))
                                .orElse(createUniWithError(either))))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }

    public Uni<Either<Error, AddressResponseDto>> updateAddress(String addressId, AddressRequestDto addressRequestDto) {
        return startUniFromItem(addressId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(addressRepository::existById)
                .flatMap(response -> response.getRight()
                        .map(ignored -> addressRepository.createOrUpdateAddress(addressConverter.toEntity(addressRequestDto)))
                        .orElse(createUniWithError(response)))
                .map(either -> either.map(addressConverter::toDto))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }

    public Uni<Either<Error, Success>> deleteAddress(String addressId) {
        return startUniFromItem(addressId)
                .map(UUID::fromString) // This is now inside the chain
                .flatMap(addressRepository::deleteAddress)
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressService.class.getName())));
    }
}
