package me.mintytc.azapi.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.Nullable;

/**
 * @since 1.0.0-R0.1
 *
 */
public class EvtLog extends Evt implements Cancellable {

    boolean isCancelled;

    @Getter
    @Setter
    String message;

    @Getter
    @Setter
    Object[] args;

    public EvtLog(String message, @Nullable Object[] args) {
        this.isCancelled = false;
        this.message = message;
        this.args = args;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
