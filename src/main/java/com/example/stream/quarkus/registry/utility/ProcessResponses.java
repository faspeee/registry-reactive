package com.example.stream.quarkus.registry.utility;


import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.*;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public final class ProcessResponses {

    private ProcessResponses() {
    }


    public static Uni<Response> processEmptyResponse(Uni<Either<Error, Success>> response) {
        return response.flatMap(either ->
                switch (either) {
                    case Either.Left<Error, Success> left -> checkLeft(left.getLeft().orElse(new GenericError()));
                    case Either.Right<Error, Success> ignored -> Uni.createFrom().item(Response.noContent().build());
                });
    }


    public static <T> Uni<Response> processTheResultFromService(Uni<Either<Error, T>> response, Response.Status status) {
        return response.flatMap(either -> switch (either) {
            case Either.Left<Error, T> left -> checkLeft(left.getLeft().orElse(new GenericError()));
            case Either.Right<Error, T> right ->
                    Uni.createFrom().item(Response.status(status).entity(right.getRight()).build());
        });
    }


    private static Uni<Response> checkLeft(Error error) {
        return switch (error) {
            case AddressError addressError -> handlerAddressError(addressError);
            case PersonError personError -> handlerPersonError(personError);
            case GenericError ignored -> createMonoError(Response.Status.INTERNAL_SERVER_ERROR, ignored);
        };
    }

    private static Uni<Response> handlerAddressError(Error error) {
        return switch (error) {
            case AddressNotFound addressNotFound -> createMonoError(Response.Status.NOT_FOUND, addressNotFound);
            case AddressServerError addressServerError ->
                    createMonoError(Response.Status.INTERNAL_SERVER_ERROR, addressServerError);
            default -> createMonoError(Response.Status.BAD_REQUEST, new GenericError());
        };
    }

    private static Uni<Response> handlerPersonError(Error error) {
        return switch (error) {
            case PersonNotFound personNotFound -> createMonoError(Response.Status.NOT_FOUND, personNotFound);
            case PersonServerError personServerError ->
                    createMonoError(Response.Status.INTERNAL_SERVER_ERROR, personServerError);
            default -> createMonoError(Response.Status.BAD_REQUEST, new GenericError());
        };
    }

    private static Uni<Response> createMonoError(Response.Status httpStatus, Error message) {
        return Uni.createFrom()
                .item(Response.status(httpStatus)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(message)
                        .build());
    }
}
