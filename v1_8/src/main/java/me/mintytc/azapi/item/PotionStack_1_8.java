package me.mintytc.azapi.item;

import me.mintytc.azapi.core.item.BaseItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class PotionStack_1_8 extends BaseItemBuilder<PotionStack_1_8> {

    private final PotionEffect effect;

    public PotionStack_1_8(JavaPlugin plugin, PotionEffect effect) {
        this.effect = effect;
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack stack = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();

        if (meta != null) meta.addCustomEffect(effect, true);
        stack.setItemMeta(meta);
        stacks.add(stack);
        return stack;
    }
}
