package me.mintytc.azapi.events.format;

import lombok.Getter;
import lombok.Setter;
import me.mintytc.azapi.events.Evt;
import me.mintytc.azapi.interfaces.Cancellable;

/**
 * @since 1.0.0-R0.1
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
