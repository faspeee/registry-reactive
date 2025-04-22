package com.example.stream.quarkus.registry.utility;

import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.GenericError;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.function.Supplier;

/**
 * Utility class for working with Mutiny's {@link Uni} and {@link Multi}
 * in a functional and type-safe way, especially when dealing with
 * {@link io.vavr.control.Either} for error handling.
 *
 * <p>This class provides convenience methods to simplify the creation of reactive streams
 * and manage error propagation.</p>
 */
public final class UtilMunity {

    // Private constructor to prevent instantiation
    private UtilMunity() {
    }

    /**
     * Creates a {@link Uni} that emits a left {@link Either} containing an error.
     * If the left side is empty, a {@link GenericError} is used as a fallback.
     *
     * @param errorBooleanEither the either containing a possible error
     * @param <I>                the input type (ignored here)
     * @param <O>                the output type expected in the right side
     * @return a {@link Uni} emitting the error wrapped in an {@link Either#left(Object)}
     */
    public static <I, O> Uni<Either<Error, O>> createUniWithError(Either<Error, I> errorBooleanEither) {
        return Uni.createFrom().item(Either.left(errorBooleanEither.getLeft().orElse(new GenericError())));
    }

    /**
     * Wraps a single item in a {@link Uni}.
     *
     * @param item the item to wrap
     * @param <T>  the type of the item
     * @return a {@link Uni} emitting the given item
     */
    public static <T> Uni<T> startUniFromItem(T item) {
        return Uni.createFrom().item(item);
    }

    /**
     * Wraps a {@link Supplier} of a {@link Multi} stream in a deferred execution.
     * Useful for lazy evaluation of streams.
     *
     * @param supplier the supplier providing the stream
     * @param <T>      the type of elements in the stream
     * @return a lazily created {@link Multi} from the supplier
     */
    public static <T> Multi<T> startUni(Supplier<Multi<? extends T>> supplier) {
        return Multi.createFrom().deferred(supplier);
    }
}
