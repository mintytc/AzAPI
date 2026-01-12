package me.mintytc.azapi.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1
 *
 */
public class Stack extends ItemBuilder<Stack> {

    private final Material material;

    public Stack(JavaPlugin plugin, Material material) {
        super(plugin);
        this.material = material;
        this.lore = new String[]{};
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack stack = new ItemStack(this.material);
        applyDefaults(stack, player);
        this.stacks.add(stack);
        return stack;
    }
}
