package me.mintytc.azapi.core.output.format.color;

import me.mintytc.azapi.core.events.format.EvtFChatColorApplied;
import me.mintytc.azapi.core.output.format.Format;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class FChatColor implements Format {

    public static final FChatColor INST = new FChatColor();

    private FChatColor() {
    }

    public String apply(OfflinePlayer player, String input) {
        if (input == null || input.isEmpty())
            return input;

        EvtFChatColorApplied evt = new EvtFChatColorApplied(input);
        if (evt.isCancelled()) return input;

        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
