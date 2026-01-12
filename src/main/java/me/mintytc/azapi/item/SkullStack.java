package me.mintytc.azapi.item;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1
 *
 */
public class SkullStack extends ItemBuilder<SkullStack> {

    private OfflinePlayer owner;

    public SkullStack(JavaPlugin plugin, OfflinePlayer owner) {
        super(plugin);
        this.owner = owner;
    }

    public SkullStack owner(OfflinePlayer owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        applyDefaults(head, player);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (owner != null) {
            meta.setOwningPlayer(owner);
        }

        head.setItemMeta(meta);
        this.stacks.add(head);
        return head;
    }
}
