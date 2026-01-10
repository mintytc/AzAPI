package me.mintytc.azapi.plugin.gui;

import me.mintytc.azapi.core.gui.Gui;
import me.mintytc.azapi.core.gui.GuiContext;
import me.mintytc.azapi.core.gui.GuiRenderer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public final class GuiManager {

    private GuiManager() {
    }

    public static void open(Player player, Gui gui) {
        GuiRenderer renderer = GuiRenderers.get(gui.type());

        Inventory inventory = renderer.create(gui, player);
        GuiContext context = new BukkitGuiContext(inventory, gui, player);

        gui.onOpen(context);
        player.openInventory(inventory);
    }
}
