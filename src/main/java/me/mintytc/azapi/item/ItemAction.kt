package me.mintytc.azapi.item

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.*

/**
 * @since 1.0.0-R0.1
 */
interface ItemAction {
	fun onClick(event: InventoryClickEvent?) {
	}
	
	fun onDrag(event: InventoryDragEvent?) {
	}
	
	fun onInteract(event: PlayerInteractEvent?) {
	}
	
	fun onHeld(event: PlayerItemHeldEvent?) {
	}
	
	fun onConsume(event: PlayerItemConsumeEvent?) {
	}
	
	fun onMend(event: PlayerItemMendEvent?) {
	}
	
	fun onBreak(event: PlayerItemBreakEvent?) {
	}
	
	fun onDamage(event: PlayerItemDamageEvent?) {
	}
	
	fun onDrop(event: PlayerDropItemEvent?) {
	}
	
	fun onSwapHand(event: PlayerSwapHandItemsEvent?) {
	}
}
