package me.mintytc.azapi.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Represents the item layout and update logic for a GUI.
 * Implementations define how items are placed depending on inventory type.
 *
 * @since 1.0.0-R0.1
 */
public interface GUIContentsBuilder {

    /**
     * Builds the initial item layout into the provided inventory.
     */
    void populate(Inventory inventory, Player viewer);

    /**
     * Called to refresh the layout, e.g., animations or dynamic states.
     */
    void update(Inventory inventory, Player viewer);

    /**
     * Returns total usable slots for this layout.
     */
    int getSize();

}