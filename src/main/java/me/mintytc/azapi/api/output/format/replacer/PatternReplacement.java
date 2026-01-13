package me.mintytc.azapi.api.output.format.replacer;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0.0-R0.1
 *
 */
public class PatternReplacement extends FReplacement {
	private final Replacer replacer;

	public PatternReplacement(Closure closure, Pattern pattern, Replacer replacer) {
		super(closure, "PATTERN", pattern);
		this.replacer = replacer;
	}

	public static PatternReplacement create(Closure closure, Pattern pattern, Replacer replacer) {
		return new PatternReplacement(closure, pattern, replacer);
	}

	public static PatternReplacement create(Closure closure, String pattern, Replacer replacer) {
		return create(closure, Pattern.compile(pattern), replacer);
	}

	@Override
	public String replace(@Nullable OfflinePlayer player, Matcher matcher) {
		return replacer.apply(player, matcher);
	}

	@FunctionalInterface
	public interface Replacer {
		String apply(@Nullable OfflinePlayer player, Matcher matcher);
	}
}
