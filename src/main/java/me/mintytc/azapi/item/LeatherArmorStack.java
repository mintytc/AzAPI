package me.mintytc.azapi.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @since 1.0.0-R0.1
 *
 */
public class LeatherArmorStack extends ItemBuilder<LeatherArmorStack> {

    private final LeatherArmorType armorType;
    @Getter
    @Setter
    private Color color;

    LeatherArmorStack(JavaPlugin plugin, LeatherArmorType armorType) {
        super(plugin);
        this.armorType = armorType;
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack stack = new ItemStack(armorType.getMaterial());
        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();

        if (color != null)
            meta.setColor(color);

        applyDefaults(stack, player);

        stacks.add(stack);
        return stack;
    }

    @Getter
    public enum LeatherArmorType {
        HELMET(Material.LEATHER_HELMET), CHESTPLATE(Material.LEATHER_CHESTPLATE), LEGGINGS(Material.LEATHER_LEGGINGS), BOOTS(Material.LEATHER_BOOTS);

        private final Material material;

        LeatherArmorType(Material material) {
            this.material = material;
        }
    }
}
