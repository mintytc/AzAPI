package me.mintytc.azapi.api.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0-R0.1
 *
 */
public final class Validator<T> {

	private final List<Rule<T>> rules = new ArrayList<>();

	public Validator<T> addRule(Rule<T> rule) {
		rules.add(rule);
		return this;
	}

	public Validator<T> removeRule(Rule<T> rule) {
		rules.remove(rule);
		return this;
	}

	public Validator<T> removeRule(int index) {
		rules.remove(index);
		return this;
	}

	public ValidationResult validate(T value) {
		List<String> errors = new ArrayList<>();

		for (Rule<T> rule : rules) {
			if (!rule.test(value)) {
				errors.add(rule.message());
			}
		}

		return errors.isEmpty()
				? ValidationResult.ok()
				: ValidationResult.fail(errors);
	}

	public boolean isValid(T value) {
		return validate(value).isValid();
	}
}
