package me.mintytc.azapi.api.output.format.color;

import me.mintytc.azapi.api.events.format.EvtFRgbApplied;
import me.mintytc.azapi.api.output.format.Format;
import org.bukkit.OfflinePlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0.0-R0.1
 *
 */
public class FRgb implements Format {

    public static final FRgb INST = new FRgb();

    private FRgb() {
    }

    public String apply(OfflinePlayer player, String input) {
        if (input == null || input.isEmpty())
            return input;

        Pattern pattern = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            EvtFRgbApplied evt = new EvtFRgbApplied(input, matcher);
            if (evt.isCancelled()) continue;

            String hex = input.substring(matcher.start(), matcher.end());
            String replaceSharp = hex.replace('#', 'x').replace("<", "").replace(">", "");

            StringBuilder builder = new StringBuilder();
            for (char c : replaceSharp.toCharArray()) {
                builder.append("&").append(c);
            }
            input = input.replace(hex, builder.toString());
        }
        return input;
    }
}
