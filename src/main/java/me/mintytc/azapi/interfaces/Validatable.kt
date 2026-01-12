package me.mintytc.azapi.interfaces

/**
 * @since 1.0.0-R0.1
 */
interface Validatable {
	/**
	 * @return true if this object’s state is valid, false otherwise
	 */
	val isValid: Boolean
}
