package me.mintytc.azapi.item;

import me.mintytc.azapi.core.item.IAction;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public interface IAction_1_13 extends IAction {
    default void onMend(PlayerItemMendEvent event) {
    }

    default void onSwapHand(PlayerSwapHandItemsEvent event) {
    }
}
