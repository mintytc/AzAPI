package me.mintytc.azapi.plugin.gui;

import me.mintytc.azapi.core.gui.Gui;
import me.mintytc.azapi.core.gui.GuiContext;
import me.mintytc.azapi.core.gui.GuiSlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BukkitGuiContext implements GuiContext {

    private final Inventory inventory;
    private final Gui gui;
    private final Player player;

    public BukkitGuiContext(Inventory inventory, Gui gui, Player player) {
        this.inventory = inventory;
        this.gui = gui;
        this.player = player;
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public Gui gui() {
        return gui;
    }

    @Override
    public void set(GuiSlot slot, ItemStack item) {
        inventory.setItem(slot.index(), item);
    }

    @Override
    public ItemStack get(GuiSlot slot) {
        return inventory.getItem(slot.index());
    }

    @Override
    public void close() {
        player.closeInventory();
    }
}
