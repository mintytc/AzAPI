package me.mintytc.azapi.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface GuiRenderer {

    Inventory create(Gui gui, Player player);

    void updateSlot(Inventory inventory, GuiSlot slot, org.bukkit.inventory.ItemStack item);
}
