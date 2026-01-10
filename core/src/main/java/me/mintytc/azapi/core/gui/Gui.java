package me.mintytc.azapi.core.gui;

import org.bukkit.entity.Player;

public interface Gui {

    GuiType type();

    String title(Player player);

    default int size(Player player) {
        return type().defaultSize();
    }

    default void onOpen(GuiContext ctx) {
    }

    default void onClose(GuiContext ctx) {
    }

    default void onClick(GuiClickEvent event) {
    }
}
