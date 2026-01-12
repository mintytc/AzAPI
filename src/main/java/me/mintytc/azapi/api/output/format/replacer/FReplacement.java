package me.mintytc.azapi.api.output.format.replacer;

import lombok.Getter;
import me.mintytc.azapi.api.output.format.Format;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0.0-R0.1
 *
 */
@Getter
public abstract class FReplacement implements Format {

    @Getter
    private static final Set<FReplacement> defaultPlaceholders = new HashSet<>();
    private final Closure closure;
    private final String identifier;
    private final Pattern pattern;

    protected FReplacement(Closure closure, String identifier, @Nullable Pattern customPattern) {
        this.closure = closure;
        this.identifier = identifier;
        this.pattern = customPattern != null
                ? customPattern
                : Pattern.compile(Pattern.quote(closure.head + identifier + closure.tail));
    }

    public static boolean register(FReplacement placeholder) {
        return defaultPlaceholders.add(placeholder);
    }

    public static boolean unregister(FReplacement placeholder) {
        return defaultPlaceholders.remove(placeholder);
    }

    public abstract String replace(@Nullable OfflinePlayer player, Matcher matcher);

    @Override
    public String apply(@Nullable OfflinePlayer player, String input) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replace(player, matcher) == null ? replace(player, matcher) : "")
            );
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
