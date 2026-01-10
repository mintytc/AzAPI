package me.mintytc.azapi.item;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBuilder_1_13 implements Listener {

    private JavaPlugin plugin;
    private List<ItemStack> stacks;
    private IAction_1_13 iAction;

    public ItemBuilder_1_13(JavaPlugin plugin, List<ItemStack> stacks, IAction_1_13 iAction) {
        this.plugin = plugin;
        this.stacks = stacks;
        this.iAction = iAction;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMend(PlayerItemMendEvent event) {
        if (this.stacks.contains(event.getItem())) {
            if (iAction != null)
                iAction.onMend(event);
        }
    }

    @EventHandler
    public void onSwapHand(@NotNull PlayerSwapHandItemsEvent event) {
        if (this.stacks.contains(event.getMainHandItem()) || this.stacks.contains(event.getOffHandItem()))
            if (iAction != null)
                iAction.onSwapHand(event);
    }
}
