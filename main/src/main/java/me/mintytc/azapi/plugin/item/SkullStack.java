package me.mintytc.azapi.plugin.item;

import lombok.Setter;
import me.mintytc.azapi.core.ServerVersion;
import me.mintytc.azapi.core.item.ISkullStack;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 1.0.0-R0.1-BASE
 * <p>
 * Version-aware skull stack builder
 */
@Setter
public class SkullStack extends ItemBuilder<SkullStack> {

    private final ISkullStack skullImpl;
    private OfflinePlayer owner;

    public SkullStack(JavaPlugin plugin, OfflinePlayer owner) {
        super(plugin);
        this.owner = owner;

        if (ServerVersion.atLeast(13, 0)) {
            skullImpl = new me.mintytc.azapi.item.SkullStack_1_13();
        } else {
            skullImpl = new me.mintytc.azapi.item.SkullStack_1_8();
        }
    }

    public SkullStack owner(OfflinePlayer owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public ItemStack build(Player player) {
        // Use the version-specific implementation
        ItemStack skull = skullImpl.createSkull(owner);

        // Apply all default ItemBuilder stuff (name, lore, enchants, flags, etc.)
        applyDefaults(skull, player);

        // Track the stack for events
        this.stacks.add(skull);
        return skull;
    }
}
