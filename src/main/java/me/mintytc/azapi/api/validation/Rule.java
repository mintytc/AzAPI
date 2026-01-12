package me.mintytc.azapi.api.validation;

/**
 * @since 1.0.0-R0.1
 *
 */
public interface Rule<T> {

    static <T> Rule<T> of(java.util.function.Predicate<T> predicate, String message) {
        return new Rule<T>() {
            @Override
            public boolean test(T value) {
                return predicate.test(value);
            }

            @Override
            public String message() {
                return message;
            }
        };
    }

    boolean test(T value);

    String message();
}
