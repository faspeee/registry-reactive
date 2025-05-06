package com.example.stream.quarkus.registry.functional;


import java.util.Optional;
import java.util.function.Function;

/**
 * A functional data type representing a value of one of two possible types (a disjoint union).
 * Instances of {@code Either} are either an instance of {@link Left} or {@link Right}.
 *
 * <p>Conventionally, {@link Left} is used to represent an error or exceptional case,
 * while {@link Right} is used for a successful result.</p>
 *
 * <p>This pattern allows functional-style error handling while preserving type safety.
 * Inspired by functional programming constructs from Scala, Haskell, and Vavr.</p>
 *
 * <p>This interface is a {@code sealed} type, enabling exhaustive {@code switch} expressions
 * in Java 17+ via pattern matching.</p>
 *
 * <p><strong>Example usage:</strong></p>
 * <pre>{@code
 * Either<Throwable, String> result = doSomething()
 *     .map(response -> "Success: " + response)
 *     .flatMap(response -> response.isEmpty()
 *         ? Either.left(new IllegalStateException("Empty result"))
 *         : Either.right(response));
 *
 * result.fold(
 *     error -> log.error("Failure", error),
 *     success -> log.info(success)
 * );
 * }</pre>
 *
 * @param <L> the type of the {@code Left} value
 * @param <R> the type of the {@code Right} value
 */
public sealed interface Either<L, R> permits Either.Left, Either.Right {

    /**
     * Creates a new {@link Left} instance representing a failure or error case.
     *
     * @param value the left value
     * @param <L>   the type of the left value
     * @param <R>   the type of the right value
     * @return a new {@link Left} instance
     */
    static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    /**
     * Creates a new {@link Right} instance representing a success case.
     *
     * @param value the right value
     * @param <L>   the type of the left value
     * @param <R>   the type of the right value
     * @return a new {@link Right} instance
     */
    static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }

    /**
     * Returns {@code true} if this is a {@link Left} value.
     *
     * @return true if this is a Left, false otherwise
     */
    boolean isLeft();

    /**
     * Returns {@code true} if this is a {@link Right} value.
     *
     * @return true if this is a Right, false otherwise
     */
    boolean isRight();

    /**
     * Returns the left value if present.
     *
     * @return an {@link Optional} containing the left value, or empty if this is a Right
     */
    Optional<L> getLeft();

    /**
     * Returns the right value if present.
     *
     * @return an {@link Optional} containing the right value, or empty if this is a Left
     */
    Optional<R> getRight();

    /**
     * Maps the {@link Right} value using the provided function, returning a new {@link Either}.
     * If this is a {@link Left}, the value is preserved.
     *
     * @param mapper the function to apply to the right value
     * @param <T>    the type of the new right value
     * @return a new {@link Either} with the mapped right value, or the original left
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * Either<Error, Integer> right = Either.right(2);
     * Either<Error, String> mapped = right.map(i -> "Value: " + i);
     * }</pre>
     */
    default <T> Either<L, T> map(Function<? super R, ? extends T> mapper) {
        return switch (this) {
            case Right<L, R>(R value) -> right(mapper.apply(value));
            case Left<L, R>(L value) -> left(value);
        };
    }

    /**
     * Flat-maps the {@link Right} value using the provided function, returning a new {@link Either}.
     * If this is a {@link Left}, the value is preserved.
     *
     * @param mapper the function to apply to the right value
     * @param <T>    the type of the new right value
     * @return a new {@link Either} from the result of the mapper, or the original left
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * Either<Error, Integer> result = Either.right(10);
     * Either<Error, String> flatMapped = result.flatMap(i -> i > 5
     *     ? Either.right("OK")
     *     : Either.left(new Error("Too small")));
     * }</pre>
     */
    default <T> Either<L, T> flatMap(Function<? super R, ? extends Either<L, T>> mapper) {
        return switch (this) {
            case Right<L, R>(R value) -> mapper.apply(value);
            case Left<L, R>(L value) -> left(value);
        };
    }

    /**
     * Applies a function to the value depending on whether it is a {@link Left} or {@link Right}.
     * This method is often used as a final terminal operation to extract a unified result.
     *
     * @param leftMapper  function to apply to left value
     * @param rightMapper function to apply to right value
     * @param <T>         the result type
     * @return the result of applying either mapper
     *
     * <p><strong>Example:</strong></p>
     * <pre>{@code
     * String result = either.fold(
     *     err -> "Error: " + err.getMessage(),
     *     val -> "Result: " + val
     * );
     * }</pre>
     */
    default <T> T fold(Function<? super L, T> leftMapper, Function<? super R, T> rightMapper) {
        return switch (this) {
            case Right<L, R>(R value) -> rightMapper.apply(value);
            case Left<L, R>(L value) -> leftMapper.apply(value);
        };
    }

    /**
     * Implementation of {@link Either} representing the left value.
     *
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     */
    record Left<L, R>(L value) implements Either<L, R> {

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public Optional<L> getLeft() {
            return Optional.of(value);
        }

        @Override
        public Optional<R> getRight() {
            return Optional.empty();
        }
    }

    /**
     * Implementation of {@link Either} representing the right value.
     *
     * @param <L> the type of the left value
     * @param <R> the type of the right value
     */
    record Right<L, R>(R value) implements Either<L, R> {

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public Optional<L> getLeft() {
            return Optional.empty();
        }

        @Override
        public Optional<R> getRight() {
            return Optional.of(value);
        }
    }
}
