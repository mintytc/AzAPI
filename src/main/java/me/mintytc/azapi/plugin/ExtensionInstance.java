package me.mintytc.azapi.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

import me.mintytc.azapi.api.commands.CommandHandler;
import me.mintytc.azapi.api.commands.utils.CooldownManager;
import me.mintytc.azapi.api.commands.utils.RateLimiter;

/**
 * An instance of an extension giving developers features such as the ability to host {@linkplain CommandHandler}'s, and providing the api simple QoL tools, also providing data.
 *
 * @since 1.0.0-R0.1
 *
 */
class ExtensionInstance {

	private final JavaPlugin plugin;
	private final Optional<String> extraInfo;

	/**
	 * Create an instance of an extension ({@linkplain ExtensionInstance}) giving developers features such as the ability to host {@linkplain CommandHandler}'s, and providing the api simple QoL tools, also providing data.
	 *
	 * @since 1.0.0-R0.1
	 *
	 */
	ExtensionInstance(JavaPlugin plugin, @Nullable Optional<String> extraInfo) {
		this.plugin = plugin;
		if (extraInfo == null) this.extraInfo = Optional.empty();
		else this.extraInfo = extraInfo;
	}

	/**
	 * Host a command handler, you can have multiple of these each hosting different {@linkplain RateLimiter}'s and {@linkplain CooldownManager}'s.
	 *
	 * @return The {@linkplain CommandHandler} being hosted.
	 * @since 1.0.0-R0.1
	 */
	public CommandHandler hostCommandHandler(@Nullable RateLimiter rateLimiter, @Nullable CooldownManager cooldownManager) {
		return new CommandHandler(this.plugin, rateLimiter, cooldownManager);
	}

	/**
	 * Logs a formatted API message.
	 */
	public void log(String message, Object... objs) {
		plugin.getLogger().info(String.format("[" + plugin.getName() + "] " + message, objs));
	}

	/**
	 * Logs a warning.
	 */
	public void warn(String message, Object... objs) {
		plugin.getLogger().warning(String.format("[" + plugin.getName() + "] " + message, objs));
	}

	/**
	 * Logs an error.
	 */
	public void error(String message, @Nullable Throwable throwable, Object... objs) {
		plugin.getLogger().severe(String.format("[" + plugin.getName() + "] " + message, objs));
		if (throwable != null) throwable.printStackTrace();
	}

	/**
	 * Resolves a file inside the plugin data folder.
	 */
	public File resolveDataFile(String path) {
		return new File(plugin.getDataFolder(), path);
	}

	/**
	 * Ensures the plugin data folder exists.
	 */
	public void ensureDataFolder() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}
	}

	/**
	 * @return plugin instance
	 */
	public JavaPlugin getPlugin() {
		return plugin;
	}

	/**
	 * @return plugin name
	 */
	public String getPluginName() {
		return plugin.getName();
	}

	/**
	 * @return plugin version
	 */
	public String getPluginVersion() {
		return plugin.getDescription().getVersion();
	}

	/**
	 * @return whether the plugin is enabled
	 */
	public boolean isEnabled() {
		return plugin.isEnabled();
	}

	/**
	 * @return optional extension metadata
	 */
	public Optional<String> getExtraInfo() {
		return extraInfo;
	}

	/**
	 * @return whether extension metadata exists
	 */
	public boolean hasExtraInfo() {
		return extraInfo.isPresent();
	}

	/**
	 * Disables the plugin safely.
	 */
	public void disable() {
		Bukkit.getPluginManager().disablePlugin(plugin);
	}
}