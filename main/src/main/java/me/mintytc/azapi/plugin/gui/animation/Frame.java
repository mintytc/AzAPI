package me.mintytc.azapi.plugin.gui.animation;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Frame {

    private final int durationTicks;
    private final Map<Integer, ItemStack> items;

    public Frame(int durationTicks, Map<Integer, ItemStack> items) {
        this.durationTicks = durationTicks;
        this.items = items;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }
}
