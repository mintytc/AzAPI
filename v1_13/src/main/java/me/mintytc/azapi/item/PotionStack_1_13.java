package me.mintytc.azapi.item;

import me.mintytc.azapi.core.item.BaseItemBuilder;
import me.mintytc.azapi.core.item.IPotionAction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

public class PotionStack_1_13 extends BaseItemBuilder<PotionStack_1_13> implements IPotionAction, Listener {

    private final PotionEffect effect;
    private PotionData potionData;
    private PotionEffect[] customEffects;

    public PotionStack_1_13(JavaPlugin plugin, PotionEffect effect) {
        this.effect = effect;
    }

    public PotionStack_1_13 setCustomEffects(PotionEffect... effects) {
        this.customEffects = effects;
        return this;
    }

    public PotionStack_1_13 setPotionData(PotionData data) {
        this.potionData = data;
        return this;
    }

    @Override
    public ItemStack build(Player player) {
        ItemStack stack = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) stack.getItemMeta();
        if (meta == null) return stack;

        if (potionData != null) meta.setBasePotionData(potionData);
        if (customEffects != null)
            for (PotionEffect pe : customEffects) meta.addCustomEffect(pe, true);

        meta.addCustomEffect(effect, true);
        stack.setItemMeta(meta);
        stacks.add(stack);
        return stack;
    }

    public void handleEvent(EntityPotionEffectEvent event) {
        switch (event.getAction()) {
            case ADDED:
                onEffectAdded(event);
            case CHANGED:
                onEffectChanged(event);
            case REMOVED:
                onEffectRemoved(event);
            case CLEARED:
                onEffectCleared(event);
        }
    }
}
