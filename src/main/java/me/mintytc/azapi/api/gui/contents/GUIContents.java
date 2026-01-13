package me.mintytc.azapi.api.gui.contents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import me.mintytc.azapi.api.classes.arrays.Dictionary;
import me.mintytc.azapi.api.gui.GUIContentsBuilder;
import me.mintytc.azapi.api.item.ItemBuilder;
import me.mintytc.azapi.api.util.UGUI;

/**
 * @since 1.0.0-R0.1
 *
 */
public class GUIContents implements GUIContentsBuilder {

	private final int size;
	@Getter
	private final Dictionary<Integer, ItemBuilder> items = new Dictionary<>();

	public GUIContents(int size) {
		this.size = UGUI.size(size);
	}

	public boolean setItem(int slot, ItemBuilder item) {
		return items.put(slot, item);
	}

	public boolean removeItem(int slot) {
		return items.remove(slot);
	}

	@Override
	public void populate(Inventory inventory, Player viewer) {
		inventory.clear();
		for (Integer slot : items.keySet()) {
			ItemBuilder item = items.get(slot);
			if (item != null) {
				if (slot >= size || slot < 0) continue;
				inventory.setItem(slot, item.build(viewer));
			}
		}
	}

	@Override
	public void update(Inventory inventory, Player viewer) {
		// simple re-populate, could be optimized later
		populate(inventory, viewer);
	}

	@Override
	public int getSize() {
		return size;
	}
}