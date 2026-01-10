package me.mintytc.azapi.core.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public final class ValidationResult {

    private static final ValidationResult OK =
            new ValidationResult(true, Collections.emptyList());

    private final boolean valid;
    private final List<String> errors;

    private ValidationResult(boolean valid, List<String> errors) {
        this.valid = valid;
        this.errors = errors;
    }

    public static ValidationResult ok() {
        return OK;
    }

    public static ValidationResult fail(List<String> errors) {
        return new ValidationResult(false, new ArrayList<>(errors));
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> errors() {
        return Collections.unmodifiableList(errors);
    }

    public String firstError() {
        return errors.isEmpty() ? null : errors.get(0);
    }

    @Override
    public String toString() {
        return valid
                ? "ValidationResult{OK}"
                : "ValidationResult{errors=" + errors + "}";
    }
}
