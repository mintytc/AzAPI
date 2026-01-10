package me.mintytc.azapi.core.registry.impl;

import me.mintytc.azapi.core.validation.Validator;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class ValidatingRegistry<K, T> extends SimpleKeyedRegistry<K, T> {

    private final Validator<T> validator;

    public ValidatingRegistry(Validator<T> validator) {
        this.validator = validator;
    }

    @Override
    public void register(K key, T value) {
        validator.validate(value);
        super.register(key, value);
    }
}
