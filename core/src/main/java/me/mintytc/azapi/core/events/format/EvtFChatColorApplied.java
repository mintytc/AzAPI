package me.mintytc.azapi.core.events.format;

import lombok.Getter;
import lombok.Setter;
import me.mintytc.azapi.core.events.Evt;
import me.mintytc.azapi.core.interfaces.Cancellable;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class EvtFChatColorApplied extends Evt implements Cancellable {

    boolean isCancelled;

    @Getter
    @Setter
    String input;

    public EvtFChatColorApplied(String input) {
        this.isCancelled = false;
        this.input = input;
    }


    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
