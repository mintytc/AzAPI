package me.mintytc.azapi.core.item;

public interface IPotionAction {
    default void onEffectAdded(Object event) {
    }

    default void onEffectChanged(Object event) {
    }

    default void onEffectRemoved(Object event) {
    }

    default void onEffectCleared(Object event) {
    }
}
