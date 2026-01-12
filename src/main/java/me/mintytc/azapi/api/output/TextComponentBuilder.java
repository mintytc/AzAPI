package me.mintytc.azapi.api.output;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 1.0.0-R0.1
 *
 */
public class TextComponentBuilder {

    private final OfflinePlayer player;
    private final List<TextComponent> parts = new ArrayList<>();
    private TextComponent current;

    public TextComponentBuilder(OfflinePlayer player, String text) {
        this.player = player;
        this.current = new TextComponent(OutputStream.f(text, player).replace("%nl%", "\n"));
    }

    public TextComponentBuilder click(String command) {
        current.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        return this;
    }

    public TextComponentBuilder suggest(String text) {
        current.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, text));
        return this;
    }

    public TextComponentBuilder url(String url) {
        current.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    public TextComponentBuilder file(String file) {
        current.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file));
        return this;
    }

    public TextComponentBuilder page(String page) {
        current.setClickEvent(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, page));
        return this;
    }

    public TextComponentBuilder hover(HoverEvent.Action action, String text) {
        current.setHoverEvent(new HoverEvent(action, new TextComponent[]{new TextComponent(OutputStream.f(text, player))}));
        return this;
    }

    public TextComponentBuilder hover(String text) {
        current.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(OutputStream.f(text, player))}));
        return this;
    }

    public TextComponentBuilder then(String text) {
        parts.add(current);
        this.current = new TextComponent(OutputStream.f(text, player));
        return this;
    }

    public TextComponent build() {
        parts.add(current);
        TextComponent base = new TextComponent();
        for (TextComponent part : parts) {
            base.addExtra(part);
        }
        return base;
    }
}