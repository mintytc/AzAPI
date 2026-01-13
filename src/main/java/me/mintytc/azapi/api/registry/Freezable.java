package me.mintytc.azapi.api.registry;

/**
 * @since 1.0.0-R0.1
 *
 */
public interface Freezable {
	void freeze();

	boolean isFrozen();
}
