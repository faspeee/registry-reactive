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

@ApplicationScoped
public final class AddressRepository implements PanacheRepositoryBase<Address, UUID> {

    private static Uni<Either<Error, Address>> processResponse(Uni<Address> address) {
        return address
                .<Either<Error, Address>>map(val -> val != null ? Either.right(val) : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressRepository.class.getName())));
    }

    private static Uni<Either<Error, Boolean>> addressExistResponse(Uni<Boolean> address) {
        return address
                .<Either<Error, Boolean>>map(aBoolean -> Boolean.TRUE.equals(aBoolean) ? Either.right(true) : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(throwable -> Either.left(new AddressServerError(throwable.getMessage(), AddressRepository.class.getName())));
    }

    @WithTransaction
    public Uni<List<Address>> getAllAddress() {
        return findAll().list();
    }

    @WithTransaction
    public Uni<Either<Error, Address>> getAddressById(UUID addressId) {
        return processResponse(findById(addressId));
    }

    @WithTransaction
    public Uni<Either<Error, Boolean>> existById(UUID addressId) {
        return addressExistResponse(findById(addressId).map(Objects::nonNull)
                .replaceIfNullWith(false));
    }

    @WithTransaction
    public Uni<Either<Error, Address>> createOrUpdateAddress(Address address) {
        return processResponse(persist(address));
    }

    @WithTransaction
    public Uni<Either<Error, Success>> deleteAddress(UUID addressId) {
        return deleteById(addressId)
                .<Either<Error, Success>>map(result -> Boolean.TRUE.equals(result) ? Either.right(new AddressDeleteOk()) : Either.left(new AddressNotFound(AddressRepository.class.getName())))
                .onFailure()
                .recoverWithItem(tr -> {
                    System.out.println(tr);
                    return Either.left(new AddressServerError(tr.getMessage(), AddressRepository.class.getName()));
                });
    }
}
