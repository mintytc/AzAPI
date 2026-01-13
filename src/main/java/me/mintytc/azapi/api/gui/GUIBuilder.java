package me.mintytc.azapi.api.gui;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import me.mintytc.azapi.api.gui.types.EnderChestGUI;
import me.mintytc.azapi.api.gui.types.FurnaceGUI;
import me.mintytc.azapi.api.gui.types.GUI;
import me.mintytc.azapi.api.gui.types.HopperGUI;
import me.mintytc.azapi.api.scheduler.Task;
import me.mintytc.azapi.api.scheduler.TaskRunnable;

/**
 * @since 1.0.0-R0.1
 *
 */
@Getter
@SuppressWarnings("unchecked")
public abstract class GUIBuilder<T extends GUIBuilder<T>> implements Listener {

	/*
	 *
	 * GUI.java
	 *
	 * */
	private final JavaPlugin plugin;
	private Player owner;
	private String title;
	private GUIAction GUIAction;
	private GUIContentsBuilder contents;
	@Setter
	private Inventory inventory;

	public GUIBuilder(JavaPlugin plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public static GUI chest(JavaPlugin plugin) {
		return new GUI(plugin);
	}

	public static EnderChestGUI enderChest(JavaPlugin plugin, OfflinePlayer owner) {
		return new EnderChestGUI(plugin, owner);
	}

	public static FurnaceGUI furnace(JavaPlugin plugin) {
		return new FurnaceGUI(plugin);
	}

	public static HopperGUI hopper(JavaPlugin plugin) {
		return new HopperGUI(plugin);
	}

	public T title(String title) {
		this.title = title;
		return (T) this;
	}

	public T owner(Player owner) {
		this.owner = owner;
		return (T) this;
	}

	public T action(GUIAction action) {
		this.GUIAction = action;
		return (T) this;
	}

	public T contents(GUIContentsBuilder contents) {
		this.contents = contents;
		return (T) this;
	}

	public abstract Inventory build(Player owner);

	public abstract void update(Player owner);

	public void open(Player... extras) {
		if (inventory == null)
			this.inventory = build(this.owner);
		else update(this.owner);

		this.owner.openInventory(this.inventory);
		for (Player player : extras)
			player.openInventory(this.inventory);
		Task.builder(plugin).runnable(new TaskRunnable() {
			@Override
			public void run() {
				onTick(inventory);
				if (inventory.getViewers().isEmpty())
					cancel();
			}
		}).interval(1).build();
	}

	@EventHandler
	public void onClick(@NotNull InventoryClickEvent event) {
		if (this.inventory == event.getInventory()) {
			event.setCancelled(true);
			if (GUIAction != null)
				GUIAction.onClick(event, (Player) event.getWhoClicked());
		}
	}

	@EventHandler
	public void onDrag(@NotNull InventoryDragEvent event) {
		if (this.inventory == event.getInventory()) {
			event.setCancelled(true);
			if (GUIAction != null)
				GUIAction.onDrag(event, (Player) event.getWhoClicked());
		}
	}

	@EventHandler
	public void onClose(@NotNull InventoryCloseEvent event) {
		if (this.inventory == event.getInventory())
			if (GUIAction != null)
				GUIAction.onClose(event, (Player) event.getPlayer());
	}

	@EventHandler
	public void onOpen(@NotNull InventoryOpenEvent event) {
		if (this.inventory == event.getInventory())
			if (GUIAction != null)
				GUIAction.onOpen(event, (Player) event.getPlayer());
	}

	@EventHandler
	public void onMove(@NotNull InventoryMoveItemEvent event) {
		if (this.inventory == event.getSource() || this.inventory == event.getInitiator() || this.inventory == event.getDestination())
			if (GUIAction != null)
				GUIAction.onMove(event);
	}

	@EventHandler
	public void onCreative(@NotNull InventoryCreativeEvent event) {
		if (this.inventory == event.getInventory())
			if (GUIAction != null)
				GUIAction.onCreative(event, (Player) event.getWhoClicked());
	}

	@EventHandler
	public void onInventory(@NotNull InventoryEvent event) {
		if (this.inventory == event.getInventory())
			if (GUIAction != null)
				GUIAction.onInventory(event);
	}

	public void onTick(Inventory inventory) {
		if (this.inventory == inventory) {
			for (HumanEntity viewer : this.inventory.getViewers()) {
				if (viewer instanceof Player) {
					Player player = (Player) viewer;
					if (GUIAction != null)
						GUIAction.onTick(inventory, player);
				}
			}
		}
	}
}
