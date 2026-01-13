package me.mintytc.azapi.api.gui.types;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.mintytc.azapi.api.gui.GUIBuilder;
import me.mintytc.azapi.api.util.UGUI;

/**
 * @since 1.0.0-R0.1
 *
 */
public class GUI extends GUIBuilder<GUI> {

	private int size;

	public GUI(JavaPlugin plugin) {
		super(plugin);
		this.size = 3;
	}

	public GUI size(int rows) {
		this.size = rows;
		return this;
	}

	@Override
	public Inventory build(Player viewer) {
		Inventory inv = Bukkit.createInventory(null, UGUI.size(size), getTitle() == null ? "" : getTitle());

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
