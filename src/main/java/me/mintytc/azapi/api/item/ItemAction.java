package me.mintytc.azapi.api.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

public interface ItemAction {
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

    default void onMend(@NotNull PlayerItemMendEvent event) {
    }

    default void onBreak(PlayerItemBreakEvent event) {
    }

    default void onDamage(PlayerItemDamageEvent event) {
    }

    default void onDrop(PlayerDropItemEvent event) {
    }

    default void onSwapHand(PlayerSwapHandItemsEvent event) {
    }
}
