package me.mintytc.azapi.interfaces

/**
 * @since 1.0.0-R0.1
 */
interface Weightable {
	/**
	 * @return a numeric weight, higher usually = more important
	 */
	fun weight(): Double
}