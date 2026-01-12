package me.mintytc.azapi.output.format

import org.bukkit.OfflinePlayer

/**
 * @since 1.0.0-R0.1
 */
fun interface Format {
	fun apply(player: OfflinePlayer?, input: String?): String?
}
