package me.mintytc.azapi.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface GuiContext {

    Player player();

    Gui gui();

    void set(GuiSlot slot, ItemStack item);

    ItemStack get(GuiSlot slot);

    void close();
}
