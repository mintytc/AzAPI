package me.mintytc.azapi.gui.contents;

import me.mintytc.azapi.gui.GUIContentsBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @since 1.0.0-R0.1
 *
 */
public class EnderChestContents implements GUIContentsBuilder {

    /**
     * Builds the initial item layout into the provided inventory.
     *
     * @param inventory
     * @param viewer
     */
    @Override
    public void populate(Inventory inventory, Player viewer) {
        inventory.setContents(viewer.getEnderChest().getContents());
    }

    /**
     * Called to refresh the layout, e.g., animations or dynamic states.
     *
     * @param inventory
     * @param viewer
     */
    @Override
    public void update(Inventory inventory, Player viewer) {
        inventory.setContents(viewer.getEnderChest().getContents());
    }

    /**
     * Returns total usable slots for this layout.
     */
    @Override
    public int getSize() {
        return 27;
    }
}
