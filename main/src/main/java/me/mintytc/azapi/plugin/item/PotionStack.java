package me.mintytc.azapi.plugin.item;

import lombok.Setter;
import me.mintytc.azapi.core.ServerVersion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

@Setter
public class PotionStack {

    private final JavaPlugin plugin;
    private final PotionEffect effect;

    public PotionStack(JavaPlugin plugin, PotionEffect effect) {
        this.plugin = plugin;
        this.effect = effect;
    }

    public ItemStack build(Player player) {
        if (ServerVersion.atLeast(13, 0)) {
            // 1.13+ implementation
            return new me.mintytc.azapi.item.PotionStack_1_13(plugin, effect).build(player);
        } else {
            // 1.8 implementation
            return new me.mintytc.azapi.item.PotionStack_1_8(plugin, effect).build(player);
        }
    }
}
