package me.mintytc.azapi.api.events;

import lombok.Getter;
import me.mintytc.azapi.api.interfaces.Cancellable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @since 1.0.0-R0.1
 *
 */
public class EvtOnCommand extends Evt implements Cancellable {

    @Getter
    final CommandSender sender;
    @Getter
    final Command command;
    @Getter
    final String label;
    @Getter
    final String[] args;
    boolean isCancelled;

    public EvtOnCommand(CommandSender sender, Command command, String label, String[] args) {
        this.isCancelled = false;
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancelled) {

    }
}
