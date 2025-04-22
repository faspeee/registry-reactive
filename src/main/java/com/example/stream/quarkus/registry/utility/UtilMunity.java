package com.example.stream.quarkus.registry.utility;

import com.example.stream.quarkus.registry.functional.Either;
import com.example.stream.quarkus.registry.model.error.Error;
import com.example.stream.quarkus.registry.model.error.GenericError;
import io.smallrye.mutiny.Uni;

public class UtilMunity {
    private UtilMunity() {
    }

    public static <I, O> Uni<Either<Error, O>> createUniWithError(Either<Error, I> errorBooleanEither) {
        return Uni.createFrom().item(Either.left(errorBooleanEither.getLeft().orElse(new GenericError())));
    }
}
