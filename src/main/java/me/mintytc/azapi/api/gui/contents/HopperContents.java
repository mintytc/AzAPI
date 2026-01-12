package me.mintytc.azapi.api.gui.contents;

import me.mintytc.azapi.api.gui.GUIContentsBuilder;
import me.mintytc.azapi.api.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @since 1.0.0-R0.1
 *
 */
public class HopperContents implements GUIContentsBuilder {

    private ItemBuilder first, second, third, fourth, fifth;

    public HopperContents first(ItemBuilder item) {
        this.first = item;
        return this;
    }

    public HopperContents second(ItemBuilder item) {
        this.second = item;
        return this;
    }

    public HopperContents third(ItemBuilder item) {
        this.third = item;
        return this;
    }

    public HopperContents fourth(ItemBuilder item) {
        this.fourth = item;
        return this;
    }

    public HopperContents fifth(ItemBuilder item) {
        this.fifth = item;
        return this;
    }

    /**
     * Builds the initial item layout into the provided inventory.
     *
     * @param inventory
     * @param viewer
     */
    @Override
    public void populate(Inventory inventory, Player viewer) {
        if (first != null) inventory.setItem(0, first.build(viewer));
        if (second != null) inventory.setItem(1, second.build(viewer));
        if (third != null) inventory.setItem(2, third.build(viewer));
        if (fourth != null) inventory.setItem(3, fourth.build(viewer));
        if (fifth != null) inventory.setItem(4, fifth.build(viewer));
    }

    /**
     * Called to refresh the layout, e.g., animations or dynamic states.
     *
     * @param inventory
     * @param viewer
     */
    @Override
    public void update(Inventory inventory, Player viewer) {
        inventory.clear();
        if (first != null) inventory.setItem(0, first.build(viewer));
        if (second != null) inventory.setItem(1, second.build(viewer));
        if (third != null) inventory.setItem(2, third.build(viewer));
        if (fourth != null) inventory.setItem(3, fourth.build(viewer));
        if (fifth != null) inventory.setItem(4, fifth.build(viewer));
    }

    /**
     * Returns total usable slots for this layout.
     */
    @Override
    public int getSize() {
        return 5;
    }
}
