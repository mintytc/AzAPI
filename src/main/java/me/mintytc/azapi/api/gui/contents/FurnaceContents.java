package me.mintytc.azapi.api.gui.contents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.mintytc.azapi.api.gui.GUIContentsBuilder;
import me.mintytc.azapi.api.item.ItemBuilder;

/**
 * @since 1.0.0-R0.1
 *
 */
public class FurnaceContents implements GUIContentsBuilder {

	private ItemBuilder input, fuel, result;

	public FurnaceContents input(ItemBuilder item) {
		this.input = item;
		return this;
	}

	public FurnaceContents fuel(ItemBuilder item) {
		this.fuel = item;
		return this;
	}

	public FurnaceContents result(ItemBuilder item) {
		this.result = item;
		return this;
	}

	@Override
	public void populate(Inventory inventory, Player viewer) {
		if (input != null) inventory.setItem(0, input.build(viewer));
		if (fuel != null) inventory.setItem(1, fuel.build(viewer));
		if (result != null) inventory.setItem(2, result.build(viewer));
	}

	@Override
	public void update(Inventory inventory, Player viewer) {
		populate(inventory, viewer);
	}

	@Override
	public int getSize() {
		return 3;
	}
}