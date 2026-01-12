package me.mintytc.azapi.api.events.format;

import me.mintytc.azapi.api.events.Evt;
import me.mintytc.azapi.api.interfaces.Cancellable;

import java.util.regex.Matcher;

/**
 * @since 1.0.0-R0.1
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
