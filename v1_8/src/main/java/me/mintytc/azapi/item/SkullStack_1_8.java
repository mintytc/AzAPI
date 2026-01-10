package me.mintytc.azapi.item;

import me.mintytc.azapi.core.item.ISkullStack;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullStack_1_8 implements ISkullStack {

    @Override
    public ItemStack createSkull(OfflinePlayer owner) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3); // 1.8 uses durability for skull type
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta != null && owner != null) meta.setOwner(owner.getName());
        skull.setItemMeta(meta);
        return skull;
    }
}
