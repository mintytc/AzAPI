package me.mintytc.azapi.api.gui.contents;

import lombok.Getter;
import me.mintytc.azapi.api.classes.arrays.Dictionary;
import me.mintytc.azapi.api.gui.GUIContentsBuilder;
import me.mintytc.azapi.api.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @since 1.0.0-R0.1
 *
 */
public class DropperContent implements GUIContentsBuilder {

    @Getter
    private final Dictionary<Integer, ItemBuilder> items = new Dictionary<>();

    public DropperContent() {
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
            if (item != null)
                inventory.setItem(slot, item.build(viewer));
        }
    }

    @Override
    public void update(Inventory inventory, Player viewer) {
        // simple re-populate, could be optimized later
        populate(inventory, viewer);
    }

    @Override
    public int getSize() {
        return 9;
    }
}