package me.mintytc.azapi.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

/**
 * @since 1.0.0-R0.1
 *
 */
@Setter
public class PotionStack extends ItemBuilder<PotionStack> implements Listener {

    private PotionEffect effect;
    private PotionStackType potionType;
    private Action action;
    private PotionData potionData;
    private PotionEffect[] customPotionEffect;
    private Color color;

    public PotionStack(JavaPlugin plugin, PotionEffect effect, PotionStackType potionType) {
        super(plugin);
        this.effect = effect;
        this.potionType = potionType;
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack stack = new ItemStack(potionType.material);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();

        applyDefaults(stack, player);

        meta.setBasePotionData(potionData);
        meta.setColor(color);
        for (PotionEffect potionEffect : customPotionEffect)
            meta.addCustomEffect(potionEffect, true);
        meta.addCustomEffect(effect, true);

        stack.setItemMeta(meta);
        stacks.add(stack);
        return stack;
    }

    @EventHandler
    public void onEffectEvent(EntityPotionEffectEvent event) {
        if (!event.getCause().equals(EntityPotionEffectEvent.Cause.POTION_DRINK)
                && !event.getCause().equals(EntityPotionEffectEvent.Cause.POTION_SPLASH)) return;

        if (action == null) return;

        if (event.getAction().equals(EntityPotionEffectEvent.Action.ADDED)) action.onEffectAdded(event);
        else if (event.getAction().equals(EntityPotionEffectEvent.Action.CHANGED)) action.onEffectChanged(event);
        else if (event.getAction().equals(EntityPotionEffectEvent.Action.CLEARED)) action.onEffectCleared(event);
        else if (event.getAction().equals(EntityPotionEffectEvent.Action.REMOVED)) action.onEffectRemoved(event);
    }

    @Getter
    public enum PotionStackType {
        POTION(Material.POTION),
        POTION_LINGERING(Material.LINGERING_POTION),
        POTION_SPLASH(Material.SPLASH_POTION);

        private final Material material;

        PotionStackType(Material material) {
            this.material = material;
        }

    }

    public interface Action {
        void onEffectAdded(EntityPotionEffectEvent event);

        void onEffectChanged(EntityPotionEffectEvent event);

        void onEffectRemoved(EntityPotionEffectEvent event);

        void onEffectCleared(EntityPotionEffectEvent event);
    }
}
