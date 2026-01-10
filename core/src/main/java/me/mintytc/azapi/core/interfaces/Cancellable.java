package me.mintytc.azapi.core.interfaces;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}