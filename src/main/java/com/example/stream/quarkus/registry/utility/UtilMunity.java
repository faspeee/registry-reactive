package com.example.stream.quarkus.registry.utility;

import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.GenericError;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.function.Supplier;

public class UtilMunity {
    private UtilMunity() {
    }

    public static <I, O> Uni<Either<Error, O>> createUniWithError(Either<Error, I> errorBooleanEither) {
        return Uni.createFrom().item(Either.left(errorBooleanEither.getLeft().orElse(new GenericError())));
    }


    public static <T> Uni<T> startUniFromItem(T item) {
        return Uni.createFrom().item(item);
    }

    public static <T> Multi<T> startUni(Supplier<Multi<? extends T>> supplier) {
        return Multi.createFrom().deferred(supplier);
    }

}
