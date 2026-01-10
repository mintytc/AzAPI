package me.mintytc.azapi.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class ExtensionInstance {

    private final JavaPlugin plugin;
    private final Optional<String> extraInfo;

    public ExtensionInstance(JavaPlugin plugin, Optional<String> extraInfo) {
        this.plugin = plugin;
        this.extraInfo = extraInfo;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Optional<String> getExtraInfo() {
        return extraInfo;
    }

    // Example feature: log info using plugin logger
    public void logInfo(String message) {
        plugin.getLogger().info("[AzAPI] " + message);
    }

    // Example feature: get plugin name
    public String getPluginName() {
        return plugin.getName();
    }

    // Example feature: get plugin version
    public String getPluginVersion() {
        return plugin.getDescription().getVersion();
    }

    // Example feature: is plugin enabled
    public boolean isPluginEnabled() {
        return plugin.isEnabled();
    }

    // Example feature: check if extraInfo exists
    public boolean hasExtraInfo() {
        return extraInfo.isPresent();
    }

    // Example feature: run a task synchronously
    public void runSync(Runnable task) {
        plugin.getServer().getScheduler().runTask(plugin, task);
    }

    // Example feature: run a task asynchronously
    public void runAsync(Runnable task) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
    }

    // Example feature: get the plugin data folder
    public java.io.File getDataFolder() {
        return plugin.getDataFolder();
    }

    // Example feature: disable the plugin
    public void disablePlugin() {
        plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    // Example feature: enable the plugin
    public void enablePlugin() {
        plugin.getServer().getPluginManager().enablePlugin(plugin);
    }
}
