package me.mintytc.azapi.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

/**
 * @since 1.0.0-R0.1
 *
 */
public interface GUIAction {

    default void onClick(InventoryClickEvent event, Player player) {
    }

    default void onDrag(InventoryDragEvent event, Player player) {
    }

    default void onClose(InventoryCloseEvent event, Player player) {
    }

    default void onOpen(InventoryOpenEvent event, Player player) {
    }

    default void onMove(InventoryMoveItemEvent event) {
    }

    default void onCreative(InventoryCreativeEvent event, Player player) {
    }

    default void onInventory(InventoryEvent event) {
    }

    default void onTick(Inventory inventory, Player player) {
    }
}
