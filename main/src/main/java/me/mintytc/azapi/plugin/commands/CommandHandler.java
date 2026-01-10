package me.mintytc.azapi.plugin.commands;

import me.mintytc.azapi.core.classes.VOID;
import me.mintytc.azapi.core.classes.arrays.Dictionary;
import me.mintytc.azapi.core.classes.arrays.Pair;
import me.mintytc.azapi.core.output.OutputStream;
import me.mintytc.azapi.core.util.UString;
import me.mintytc.azapi.plugin.commands.annotation.Cooldown;
import me.mintytc.azapi.plugin.commands.utils.CooldownManager;
import me.mintytc.azapi.plugin.commands.utils.RateLimiter;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class CommandHandler {

    private final JavaPlugin PLUGIN;

    /**
     * @since 1.0.0-R0.1-BASE
     *
     */
    public CommandHandler(JavaPlugin plugin) {
        this.PLUGIN = plugin;
    }

    /**
     * @since 1.0.0-R0.1-BASE
     *
     */
    @Contract("_, null -> new")
    public static @NotNull Pair<CommandPreprocessResult, Object> onCommand(Player player, Command command) {

        if (command == null) {
            return new Pair<>(CommandPreprocessResult.VOIDED, VOID.a);
        }

        if (RateLimiter.getRateLimiter() != null) {
            boolean limited = RateLimiter.getRateLimiter().isRateLimited(player, command);

            RateLimiter.getRateLimiter().recordExecution(player, command);
            if (limited) {
                OutputStream.send(player, "&cYou're being rate limited.");
                return new Pair<>(CommandPreprocessResult.RATE_LIMITED, VOID.a);
            }
        }

        // Check cooldowns
        if (CooldownManager.getCooldownManager().isOnCooldown(player, command)) {
            Dictionary<String, Object> dictionary = new Dictionary<>();
            double remaining = CooldownManager.getCooldownManager().getRemaining(player, command) / 1000.0;
            dictionary.createDictionary("remaining", remaining);
            OutputStream.send(player, "&7Wait &c" + java.lang.String.format("%.1f", remaining) + "&7s.");
            return new Pair<>(CommandPreprocessResult.ON_COOLDOWN, remaining);
        }

        // Passed everything
        CooldownManager.getCooldownManager().applyCooldown(player, command);
        return new Pair<>(CommandPreprocessResult.PASSED, VOID.a);
    }

    /**
     * Register a command class.
     *
     * @param label     The label of the command '/label'
     * @param executor  The command executor (a class extending {@link ImplCommand}) that acts as a {@linkplain org.bukkit.command.CommandExecutor}
     * @param completer The command tab completer (a class extending {@link ImplTabCompleter}) that acts as a {@linkplain org.bukkit.command.TabCompleter}. This can be null
     *
     * @since 1.0.0-R0.1-BASE
     *
     */
    public void register(String label, ImplCommand executor, @Nullable ImplTabCompleter completer, Listener... listeners) {
        if (label == null || UString.isBlank(label))
            throw new NullPointerException("CommandHandler.register(String command, CommandExecutor executor, @Nullable TabCompleter completer, Listener... listeners) -> 'command' cannot be null");
        if (executor == null)
            throw new NullPointerException("CommandHandler.register(String command, CommandExecutor executor, @Nullable TabCompleter completer, Listener... listeners) -> 'executor' cannot be null");

        for (Listener listener : listeners) PLUGIN.getServer().getPluginManager().registerEvents(listener, PLUGIN);

        Command command = PLUGIN.getCommand(label);
        if (command == null)
            throw new IllegalArgumentException("Command not found in plugin.yml: " + label);

        Cooldown cooldown = executor.getClass().getAnnotation(Cooldown.class);
        if (cooldown != null) {
            CooldownManager.getCooldownManager().registerCooldown(command, cooldown.seconds());
        }

        PLUGIN.getCommand(label).setExecutor(executor);
        if (completer != null)
            PLUGIN.getCommand(label).setTabCompleter(completer);
    }

    /**
     * @since 1.0.0-R0.1-BASE
     *
     */
    public enum CommandPreprocessResult {
        PASSED,
        RATE_LIMITED,
        ON_COOLDOWN,
        VOIDED
    }
}
