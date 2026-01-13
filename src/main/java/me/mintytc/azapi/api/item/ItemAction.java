package me.mintytc.azapi.api.item;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
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
