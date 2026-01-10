package me.mintytc.azapi.core.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IPotionStack<T> {

    T effect(Object effect); // effect type generic, actual version will cast

    T action(Object action);

    ItemStack build(Player player);
}
