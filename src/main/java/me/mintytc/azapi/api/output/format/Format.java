package me.mintytc.azapi.api.output.format;

import org.bukkit.OfflinePlayer;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
@FunctionalInterface
public interface Format {
	String apply(OfflinePlayer player, String input);
}
