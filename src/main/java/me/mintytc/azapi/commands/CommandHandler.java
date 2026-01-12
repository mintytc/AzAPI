package me.mintytc.azapi.commands;

import lombok.Setter;
import me.mintytc.azapi.classes.VOID;
import me.mintytc.azapi.classes.arrays.Dictionary;
import me.mintytc.azapi.classes.arrays.Pair;
import me.mintytc.azapi.commands.annotation.Cooldown;
import me.mintytc.azapi.commands.utils.CooldownManager;
import me.mintytc.azapi.commands.utils.RateLimiter;
import me.mintytc.azapi.output.OutputStream;
import me.mintytc.azapi.util.UString;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0-R0.1
 */
public class CommandHandler {

    private final JavaPlugin plugin;

    @Setter
    private RateLimiter rateLimiter;
    @Setter
    private CooldownManager cooldownManager;

    private static Map<Command, CommandHandler> connections = new HashMap<>();

    /**
     * @since 1.0.0-R0.1
     */
    public CommandHandler(JavaPlugin plugin, RateLimiter rateLimiter, CooldownManager cooldownManager) {
        this.plugin = plugin;
        this.rateLimiter = rateLimiter;
        this.cooldownManager = cooldownManager;
    }

    /**
     * @since 1.0.0-R0.1
     */
    @Contract("_, null -> new")
    static @NotNull Pair<CommandPreprocessResult, Object> onCommand(Player player, Command command) {

        if (command == null) {
            return new Pair<>(CommandPreprocessResult.VOIDED, VOID.a);
        }

        RateLimiter rateLimiter = connections.get(command).rateLimiter;
        CooldownManager cooldownManager = connections.get(command).cooldownManager;
        if (rateLimiter != null) {
            boolean limited = rateLimiter.isRateLimited(player, command);

            rateLimiter.recordExecution(player, command);
            if (limited) {
                OutputStream.send(player, "&cYou're being rate limited.");
                return new Pair<>(CommandPreprocessResult.RATE_LIMITED, VOID.a);
            }
        }

        if (cooldownManager.isOnCooldown(player, command)) {
            Dictionary<String, Object> dictionary = new Dictionary<>();
            double remaining = cooldownManager.getRemaining(player, command) / 1000.0;
            dictionary.createDictionary("remaining", remaining);
            OutputStream.send(player, "&7Wait &c" + java.lang.String.format("%.1f", remaining) + "&7s.");
            return new Pair<>(CommandPreprocessResult.ON_COOLDOWN, remaining);
        }

        cooldownManager.applyCooldown(player, command);
        return new Pair<>(CommandPreprocessResult.PASSED, VOID.a);
    }

    /**
     * Register a command.
     *
     * @param label     The label of the command '/label'
     * @param executor  The command executor (a class extending {@link ImplCommand}) that acts as a {@linkplain org.bukkit.command.CommandExecutor}
     * @param completer The command tab completer (a class extending {@link ImplTabCompleter}) that acts as a {@linkplain org.bukkit.command.TabCompleter}. This can be null
     *
     * @since 1.0.0-R0.1
     */
    public void register(String label, ImplCommand executor, @Nullable ImplTabCompleter completer, Listener... listeners) {
        if (label == null || UString.isBlank(label))
            throw new NullPointerException("CommandHandler.register(String command, CommandExecutor executor, @Nullable TabCompleter completer, Listener... listeners) -> 'command' cannot be null");
        if (executor == null)
            throw new NullPointerException("CommandHandler.register(String command, CommandExecutor executor, @Nullable TabCompleter completer, Listener... listeners) -> 'executor' cannot be null");

        for (Listener listener : listeners) plugin.getServer().getPluginManager().registerEvents(listener, plugin);

        Command command = plugin.getCommand(label);
        if (command == null) throw new IllegalArgumentException("Command not found in plugin.yml: " + label);

        connections.put(command, this);

        Cooldown cooldown = executor.getClass().getAnnotation(Cooldown.class);
        if (cooldown != null) {
            cooldownManager.registerCooldown(command, cooldown.seconds());
        }

        plugin.getCommand(label).setExecutor(executor);
        if (completer != null) plugin.getCommand(label).setTabCompleter(completer);
    }

    /**
     * @since 1.0.0-R0.1
     */
    public enum CommandPreprocessResult {
        PASSED, RATE_LIMITED, ON_COOLDOWN, VOIDED
    }
}
