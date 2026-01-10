package me.mintytc.azapi.core.events.format;

import me.mintytc.azapi.core.events.Evt;
import me.mintytc.azapi.core.interfaces.Cancellable;

import java.util.regex.Matcher;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class EvtFRgbApplied extends Evt implements Cancellable {

    boolean isCancelled;

    String input;
    Matcher matcher;


    public EvtFRgbApplied(String input, Matcher matcher) {
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
