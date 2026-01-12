package me.mintytc.azapi.events.format;

import me.mintytc.azapi.events.Evt;
import me.mintytc.azapi.interfaces.Cancellable;

import java.util.regex.Matcher;

/**
 * @since 1.0.0-R0.1
 *
 */
public class EvtFGradientApplied extends Evt implements Cancellable {

    boolean isCancelled;

    String input;
    Matcher matcher;

    public EvtFGradientApplied(String input, Matcher matcher) {
        this.isCancelled = false;
        this.input = input;
        this.matcher = matcher;
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
