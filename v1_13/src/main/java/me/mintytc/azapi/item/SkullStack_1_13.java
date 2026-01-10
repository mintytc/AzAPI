package me.mintytc.azapi.item;

import me.mintytc.azapi.core.item.ISkullStack;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullStack_1_13 implements ISkullStack {

    @Override
    public ItemStack createSkull(OfflinePlayer owner) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD); // New enum in 1.13+
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null && owner != null) meta.setOwningPlayer(owner);
        skull.setItemMeta(meta);
        return skull;
    }
}
