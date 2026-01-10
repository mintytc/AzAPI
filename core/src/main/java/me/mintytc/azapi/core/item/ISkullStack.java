package me.mintytc.azapi.core.item;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public interface ISkullStack {

    ItemStack createSkull(OfflinePlayer owner);
}
