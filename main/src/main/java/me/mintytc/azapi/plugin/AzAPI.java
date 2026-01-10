package me.mintytc.azapi.plugin;

import me.mintytc.azapi.core.UpdateChecker;
import me.mintytc.azapi.core.classes.arrays.Enumerator;
import me.mintytc.azapi.core.output.OutputStream;
import me.mintytc.azapi.core.scheduler.Task;
import me.mintytc.azapi.core.scheduler.TaskGroup;
import me.mintytc.azapi.core.scheduler.TaskRunnable;
import me.mintytc.azapi.core.util.UString;
import me.mintytc.azapi.plugin.archivum.Archivum;
import me.mintytc.azapi.plugin.archivum.ArchivumFileType;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @since 1.0.0-R0.1-BASE
 */
public final class AzAPI extends JavaPlugin {

    private static final List<ExtensionInstance> extensions = new ArrayList<>();
    public static AzAPI INSTANCE;
    public static PluginManager PLUGIN_MANAGER;

    /**
     * Registers the calling plugin as an ExtensionInstance.
     *
     * @return the created ExtensionInstance
     */
    public static @NotNull ExtensionInstance registerAddon() {
        return registerAddon((JavaPlugin) detectCallingPlugin(), null);
    }

    /**
     * Registers the calling plugin with optional extra info.
     */
    public static @NotNull ExtensionInstance registerAddon(String extraInfo) {
        return registerAddon((JavaPlugin) detectCallingPlugin(), extraInfo);
    }

    /**
     * Explicitly registers a plugin.
     */
    public static @NotNull ExtensionInstance registerAddon(JavaPlugin plugin) {
        return registerAddon(plugin, null);
    }

    /**
     * Explicitly registers a plugin with extra info.
     */
    public static @NotNull ExtensionInstance registerAddon(JavaPlugin plugin, String extraInfo) {
        ExtensionInstance instance = new ExtensionInstance(plugin, Optional.ofNullable(extraInfo));
        extensions.add(instance);
        return instance;
    }

    /**
     * Detect the calling plugin using the call stack.
     */
    private static @NotNull Plugin detectCallingPlugin() {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (plugin.getClass().getName().equals(className) || plugin.getClass().getName().startsWith(className)) {
                return plugin;
            }
        }
        throw new IllegalStateException("Could not detect calling plugin.");
    }

    @Override
    public void onEnable() {

        // You can find the plugin id of your plugins on
        // the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 28793;
        Metrics metrics = new Metrics(this, pluginId);
        // charts later

        INSTANCE = AzAPI.getPlugin(AzAPI.class);
        PLUGIN_MANAGER = getServer().getPluginManager();

        Enumerator<Error> errors = new Enumerator<>();

        TaskGroup taskGroup = new TaskGroup();

        taskGroup.track(Task.builder(this).delay(1).runnable(new TaskRunnable() {
            @Override
            public void run() {
                new UpdateChecker(INSTANCE, 10000).getVersion(version -> {
                    if (getDescription().getVersion().equals(version)) {
                        getLogger().info("There is not a new update available.");
                    } else {
                        getLogger().info("There is a new update available.");
                    }
                });

                List<String> msg_addons = new ArrayList<>();
                List<String> dependencies = new ArrayList<>();
                List<String> msg_dependencies = new ArrayList<>();

                dependencies.add("PlaceholderAPI");

                for (ExtensionInstance plugin : extensions) {
                    msg_addons.add("| " + plugin.getPlugin().getDescription().getName() + ":%nl%" +
                            "|   authors // " + plugin.getPlugin().getDescription().getAuthors() +
                            "%nl%|   version // " + plugin.getPlugin().getDescription().getVersion() +
                            (plugin.hasExtraInfo() ? "%nl%|   extras  // " + plugin.getExtraInfo().get() : null) +
                            "%nl%|__ enabled // " + plugin.getPlugin().isEnabled());
                }
                for (String pl : dependencies) {
                    Plugin plugin = PLUGIN_MANAGER.getPlugin(pl);
                    if (plugin != null && plugin.isEnabled()) {
                        msg_dependencies.add("| " + plugin.getDescription().getName() + ":%nl%" +
                                "|    enabled // " + plugin.isEnabled() +
                                "%nl%|    version // " + plugin.getDescription().getVersion() +
                                "%nl%|__ authors // " + plugin.getDescription().getAuthors());
                    }
                }

                OutputStream.log(UString.center(" AzAPI ", "=", 80),
                        !msg_addons.isEmpty() ? "%nl%Addons loaded:" : "%nl%No addons loaded.",
                        !msg_addons.isEmpty() ? String.join("%nl%", msg_addons) : null,
                        !msg_dependencies.isEmpty() ? "%nl%Dependencies loaded:" : "%nl%No dependencies loaded.",
                        !msg_dependencies.isEmpty() ? String.join("%nl%", msg_dependencies) : null,
                        "",
                        errors.isEmpty() ? "AzAPI Loaded successfully." : "AzAPI did not load successfully.",
                        "",
                        UString.center(" " + getDescription().getVersion() + " ", "=", 80));
            }
        }).build());

        // Register default .yml file type
        Archivum.registerFileType(new ArchivumFileType("yml", false) {
            @Override
            public String name() {
                return "yml";
            }

            @Override
            public void start() {
                getLogger().info("Default YAML type initialized.");
            }

            @Override
            public void load(@NotNull Archivum archivum) {
                getLogger().info("Loaded file: " + archivum.getFilename());
            }

            @Override
            public void unload(@NotNull Archivum archivum) {
                getLogger().info("Unloaded file: " + archivum.getFilename());
            }

            @Override
            public void onSave(@NotNull Archivum archivum) {
                getLogger().info("Saved file: " + archivum.getFilename());
            }
        });
    }

    @Override
    public void onDisable() {
        for (Archivum archivum : Archivum.cachedFiles.values()) {
            archivum.unload();
            archivum.save();
        }
    }
}
