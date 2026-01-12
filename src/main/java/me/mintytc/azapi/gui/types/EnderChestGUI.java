package me.mintytc.azapi.gui.types;

import me.mintytc.azapi.gui.GUIBuilder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1
 *
 */
public class EnderChestGUI extends GUIBuilder<EnderChestGUI> {

    private final OfflinePlayer owner;

    public EnderChestGUI(JavaPlugin plugin, OfflinePlayer owner) {
        super(plugin);
        this.owner = owner;
    }

    @Override
    public Inventory build(Player owner) {
        return owner.getEnderChest();
    }

    @Override
    public void update(Player owner) {

    }
}
