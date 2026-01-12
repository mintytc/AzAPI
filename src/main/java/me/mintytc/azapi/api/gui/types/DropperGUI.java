package me.mintytc.azapi.api.gui.types;

import me.mintytc.azapi.api.gui.GUIBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1
 *
 */
public class DropperGUI extends GUIBuilder<DropperGUI> {

    public DropperGUI(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public Inventory build(Player viewer) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.DROPPER, getTitle() == null ? "" : getTitle());

        if (getContents() != null) getContents().populate(inv, viewer);
        return inv;
    }

    @Override
    public void update(Player viewer) {
        Inventory inv = getInventory();

        if (getContents() != null) getContents().update(inv, viewer);
    }
}
