package me.mintytc.azapi.gui.types;

import me.mintytc.azapi.gui.GUIBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1
 *
 */
public class DispenserGUI extends GUIBuilder<DispenserGUI> {

    public DispenserGUI(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public Inventory build(Player viewer) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.DISPENSER, getTitle() == null ? "" : getTitle());

        if (getContents() != null)
            getContents().populate(inv, viewer);
        return inv;
    }

    @Override
    public void update(Player viewer) {
        Inventory inv = getInventory();

        if (getContents() != null)
            getContents().update(inv, viewer);
    }
}
