package me.mintytc.azapi.core.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemBuilder<T extends BaseItemBuilder<T>> {

    protected final List<ItemStack> stacks = new ArrayList<>();

    public abstract ItemStack build(Player player);

    // put any core-only defaults logic here, that doesn't rely on version-specific Bukkit API
}
