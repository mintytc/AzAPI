package me.mintytc.azapi.api.output;

import me.mintytc.azapi.api.events.EvtLog;
import me.mintytc.azapi.api.output.format.Format;
import me.mintytc.azapi.api.output.format.color.FChatColor;
import me.mintytc.azapi.api.output.format.color.FGradient;
import me.mintytc.azapi.api.output.format.color.FRgb;
import me.mintytc.azapi.api.output.format.replacer.FReplacement;
import me.mintytc.azapi.api.util.UString;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since 1.0.0-R0.1
 *
 */
public class OutputStream {

    /**
     * This contains all the default formats
     *
     */
    public static final Format[] FORMATS_DEFAULT;
    public static final Format[] FORMATS_PLACEHOLDERS;
    public static final Format[] FORMATS_COLOR;

    static {
        List<Format> formats_default = new ArrayList<>(FReplacement.getDefaultPlaceholders());
        formats_default.add(FChatColor.INST);
        formats_default.add(FRgb.INST);
        formats_default.add(FGradient.INST);

        List<Format> formats_placeholders = new ArrayList<>(FReplacement.getDefaultPlaceholders());
        List<Format> formats_color = new ArrayList<>();
        formats_color.add(FChatColor.INST);
        formats_color.add(FRgb.INST);
        formats_color.add(FGradient.INST);

        FORMATS_DEFAULT = formats_default.toArray(new Format[0]);
        FORMATS_PLACEHOLDERS = formats_placeholders.toArray(new Format[0]);
        FORMATS_COLOR = formats_color.toArray(new Format[0]);
    }

    /**
     * Formats
     *
     * @param str     The {@link String} to format.
     * @param player  The {@link OfflinePlayer} in the context.
     * @param formats The formats to use.
     *
     * @return The formatted {@link String}.
     * @since 1.0.0-R0.1
     *
     */
    @Contract("_, _, _ -> new")
    public static String f(@NotNull String str, @Nullable OfflinePlayer player, Format... formats) {
        if (UString.isBlank(str)) return "";
        if (formats.length == 0) {
            formats = FORMATS_DEFAULT;
        }

        for (Format format : formats) {
            str = format.apply(player, str);
        }
        return str;
    }

    public static boolean send(Player player, String @NotNull ... messages) {
        if (messages.length == 0 || !player.isOnline()) return false;
        for (String msg : messages) {
            if (msg == null) continue;
            for (String line : msg.split("%nl%")) {
                player.sendMessage(f(line, player, FORMATS_DEFAULT));
            }
        }
        return true;
    }

    public static boolean send(Player player, List<Format> extraFormats, String @NotNull ... messages) {
        if (messages.length == 0 || !player.isOnline()) return false;
        List<Format> finalFormats = new ArrayList<>(Arrays.asList(FORMATS_DEFAULT));
        finalFormats.addAll(extraFormats);
        for (String msg : messages) {
            if (msg == null) continue;
            for (String line : msg.split("%nl%")) {
                player.sendMessage(f(line, player, finalFormats.toArray(new Format[]{})));
            }
        }
        return true;
    }

    public static boolean send(Player player, TextComponent @NotNull ... messages) {
        if (messages.length == 0 || !player.isOnline()) return false;
        for (TextComponent msg : messages) {
            if (msg == null) continue;
            player.spigot().sendMessage(msg);
        }
        return true;
    }

    public static boolean send(Player player, Object @NotNull ... objects) {
        if (objects.length == 0 || !player.isOnline()) return false;
        for (Object obj : objects) {
            if (obj == null) continue;
            if (obj instanceof String) {
                String string = (String) obj;
                for (String line : string.split("%nl%")) {
                    player.sendMessage(f(line, player));
                }
            } else if (obj instanceof TextComponent[]) {
                player.spigot().sendMessage((TextComponent[]) obj);
            } else if (obj instanceof TextComponent) {
                TextComponent tc = (TextComponent) obj;
                player.spigot().sendMessage(tc);
            } else if (obj instanceof TextComponentBuilder) {
                TextComponentBuilder tcb = (TextComponentBuilder) obj;
                player.spigot().sendMessage(tcb.build());
            } else {
                for (String line : obj.toString().split("%nl%")) {
                    player.sendMessage(f(line, player));
                }
            }
        }
        return true;
    }

    public static boolean log(String @NotNull ... messages) {
        if (messages.length == 0) return false;
        for (String msg : messages) {
            EvtLog evt = new EvtLog(msg, null);

            if (!evt.isCancelled()) {
                if (evt.getMessage() == null) continue;
                for (String line : evt.getMessage().split("%nl%")) {
                    System.out.println(f(line, null, FORMATS_DEFAULT));
                }
            }
        }
        return true;
    }

    /**
     * Log a formatted string to the console
     * logf("$0, $1", 256, "Hello World") -> Logs "256, Hello World"
     *
     * @param message The string you would like to log.
     * @param args    The arguments to format the string with.
     *
     */
    public static boolean logf(@NotNull String message, Object... args) {
        EvtLog evt = new EvtLog(message, args);

        if (!evt.isCancelled()) {
            if (args.length > 0)
                for (int i = 0; i < args.length; i++) {
                    message.replace("$" + i, args[i].toString());
                }

            for (String line : evt.getMessage().split("%nl%")) {
                System.out.println(f(line, null, FORMATS_DEFAULT));
            }
        }
        return true;
    }
}
