package me.mintytc.azapi.output.format.replacer;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.regex.Matcher;

/**
 * @since 1.0.0-R0.1
 *
 */
public class SimpleReplacement extends FReplacement {
    private final Function<@Nullable OfflinePlayer, String> function;

    private SimpleReplacement(Closure closure, String identifier, Function<@Nullable OfflinePlayer, String> function) {
        super(closure, identifier, null);
        this.function = function;
    }

    public static SimpleReplacement create(Closure closure, String identifier, Function<@Nullable OfflinePlayer, String> function) {
        return new SimpleReplacement(closure, identifier, function);
    }

    @Override
    public String replace(@Nullable OfflinePlayer player, Matcher matcher) {
        return function.apply(player);
    }
}

