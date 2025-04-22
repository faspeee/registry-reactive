package com.example.stream.quarkus.registry.utility;


import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.*;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Utility class responsible for transforming service-layer responses into
 * HTTP responses suitable for REST APIs.
 *
 * <p>
 * This class utilizes Java's pattern matching with the {@code switch} expression,
 * allowing a more concise and type-safe way to handle various error types and success cases.
 * The switch expressions match the type of the object contained in the {@link Either},
 * improving readability by eliminating the need for explicit {@code instanceof} checks.
 * </p>
 */
public final class ProcessResponses {

    private ProcessResponses() {
    }

    /**
     * Processes a response that contains nobody but only a success or error signal.
     *
     * <p>
     * If the response is a success ({@link Either.Right}), this method returns a {@code 204 No Content} HTTP response.
     * If the response is an error ({@link Either.Left}), it returns an appropriate error code based on the error type.
     * </p>
     *
     * <p>Example:
     * <pre>
     *   // When the result is a Left (error):
     *   Either<Error, Success> left = Either.left(new AddressNotFound());
     *   processEmptyResponse(Uni.createFrom().item(left));
     *   // Returns: 404 Not Found
     * </pre>
     *
     * <pre>
     *   // When the result is a Right (success):
     *   Either<Error, Success> right = Either.right(new Success());
     *   processEmptyResponse(Uni.createFrom().item(right));
     *   // Returns: 204 No Content
     * </pre>
     *
     * @param response a {@link Uni} containing an {@link Either} of {@link Error} or {@link Success}
     * @return a {@link Uni} emitting a {@link Response} based on the error or success
     */
    public static Uni<Response> processEmptyResponse(Uni<Either<Error, Success>> response) {
        return response.flatMap(either ->
                switch (either) {
                    case Either.Left<Error, Success> left -> checkLeft(left.getLeft().orElse(new GenericError()));
                    case Either.Right<Error, Success> ignored -> Uni.createFrom().item(Response.noContent().build());
                });
    }

    /**
     * Processes a response that contains data.
     *
     * <p>
     * If the response contains a {@link Either.Left} with an error, the method returns an appropriate error response.
     * If the response contains a {@link Either.Right} with a successful result, it returns a {@code 200 OK} HTTP response
     * with the result in the body.
     * </p>
     *
     * <p>Example:
     * <pre>
     *   // When the result is a Right (success):
     *   Either<Error, Person> right = Either.right(new Person("John", "Doe"));
     *   processTheResultFromService(Uni.createFrom().item(right), Response.Status.OK);
     *   // Returns: 200 OK with Person data
     * </pre>
     *
     * <pre>
     *   // When the result is a Left (error):
     *   Either<Error, Person> left = Either.left(new PersonNotFound());
     *   processTheResultFromService(Uni.createFrom().item(left), Response.Status.OK);
     *   // Returns: 404 Not Found
     * </pre>
     *
     * @param response a {@link Uni} containing an {@link Either} of {@link Error} or a result of type {@code T}
     * @param status   the HTTP status to return on success (e.g., 200 OK, 201 CREATED)
     * @param <T>      the type of the data in the response
     * @return a {@link Uni} emitting a {@link Response} based on the error or success
     */
    public static <T> Uni<Response> processTheResultFromService(Uni<Either<Error, T>> response, Response.Status status) {
        return response.flatMap(either -> switch (either) {
            case Either.Left<Error, T> left -> checkLeft(left.getLeft().orElse(new GenericError()));
            case Either.Right<Error, T> right ->
                    Uni.createFrom().item(Response.status(status).entity(right.getRight()).build());
        });
    }

    /**
     * Checks and processes a {@link Either.Left} (error) from the response.
     *
     * <p>This method matches on the type of {@link Error} and delegates the processing
     * to a dedicated handler based on the specific error type.</p>
     *
     * <p>Example:
     * <pre>
     *   checkLeft(new AddressNotFound());
     *   // Returns: 404 Not Found response
     * </pre>
     *
     * @param error the error to process
     * @return a {@link Uni} containing the appropriate {@link Response} based on the error type
     */
    private static Uni<Response> checkLeft(Error error) {
        return switch (error) {
            case AddressError addressError -> handlerAddressError(addressError);
            case PersonError personError -> handlerPersonError(personError);
            case GenericError ignored -> createMonoError(Response.Status.INTERNAL_SERVER_ERROR, ignored);
        };
    }

    /**
     * Handles errors related to address processing.
     *
     * @param error the address-related error
     * @return a {@link Uni} containing the appropriate {@link Response} for the error
     */
    private static Uni<Response> handlerAddressError(Error error) {
        return switch (error) {
            case AddressNotFound addressNotFound -> createMonoError(Response.Status.NOT_FOUND, addressNotFound);
            case AddressServerError addressServerError ->
                    createMonoError(Response.Status.INTERNAL_SERVER_ERROR, addressServerError);
            default -> createMonoError(Response.Status.BAD_REQUEST, new GenericError());
        };
    }

    /**
     * Handles errors related to person processing.
     *
     * @param error the person-related error
     * @return a {@link Uni} containing the appropriate {@link Response} for the error
     */
    private static Uni<Response> handlerPersonError(Error error) {
        return switch (error) {
            case PersonNotFound personNotFound -> createMonoError(Response.Status.NOT_FOUND, personNotFound);
            case PersonServerError personServerError ->
                    createMonoError(Response.Status.INTERNAL_SERVER_ERROR, personServerError);
            default -> createMonoError(Response.Status.BAD_REQUEST, new GenericError());
        };
    }

    /**
     * Creates a {@link Uni} that emits an error response with the given HTTP status and error message.
     *
     * <p>Example:
     * <pre>
     *   createMonoError(Response.Status.NOT_FOUND, new PersonNotFound());
     *   // Returns: 404 Not Found response with PersonNotFound error message
     * </pre>
     *
     * @param httpStatus the HTTP status for the error response
     * @param message    the error message to include in the response
     * @return a {@link Uni} containing the error response
     */
    private static Uni<Response> createMonoError(Response.Status httpStatus, Error message) {
        return Uni.createFrom()
                .item(Response.status(httpStatus)
                        .type(MediaType.APPLICATION_JSON)
                        .entity(message)
                        .build());
    }
}
