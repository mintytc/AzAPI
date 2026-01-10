package me.mintytc.azapi.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class GuiClickEvent {

    private final Player player;
    private final GuiSlot slot;
    private final ClickType clickType;

    public GuiClickEvent(Player player, GuiSlot slot, ClickType clickType) {
        this.player = player;
        this.slot = slot;
        this.clickType = clickType;
    }

    public Player player() {
        return player;
    }

    public GuiSlot slot() {
        return slot;
    }

    public ClickType clickType() {
        return clickType;
    }
}
