package me.mintytc.azapi.api.registry.impl;

import me.mintytc.azapi.api.validation.Validator;

/**
 * @since 1.0.0-R0.1
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
