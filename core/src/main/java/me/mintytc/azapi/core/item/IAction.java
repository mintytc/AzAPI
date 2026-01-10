package me.mintytc.azapi.core.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;

public interface IAction {
    default void onClick(InventoryClickEvent event) {
    }

    default void onDrag(InventoryDragEvent event) {
    }

    default void onInteract(PlayerInteractEvent event) {
    }

    default void onHeld(PlayerItemHeldEvent event) {
    }

    default void onConsume(PlayerItemConsumeEvent event) {
    }

    default void onBreak(PlayerItemBreakEvent event) {
    }

    default void onDamage(PlayerItemDamageEvent event) {
    }

    default void onDrop(PlayerDropItemEvent event) {
    }
}
