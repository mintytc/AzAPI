package me.mintytc.azapi.gui;

import me.mintytc.azapi.core.gui.Gui;
import me.mintytc.azapi.core.gui.GuiRenderer;
import me.mintytc.azapi.core.gui.GuiSlot;
import me.mintytc.azapi.core.output.OutputStream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftingGuiRenderer_1_8 implements GuiRenderer {

    @Override
    public Inventory create(Gui gui, Player player) {
        return Bukkit.createInventory(
                player,
                InventoryType.CRAFTING,
                OutputStream.f(gui.title(player), player)
        );
    }

    @Override
    public void updateSlot(Inventory inventory, GuiSlot slot, ItemStack item) {
        inventory.setItem(slot.index(), item);
    }
}
