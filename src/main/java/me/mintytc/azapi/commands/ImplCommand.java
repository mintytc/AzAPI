package me.mintytc.azapi.commands;

import me.mintytc.azapi.classes.arrays.Enumerator;
import me.mintytc.azapi.events.EvtOnCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * @since 1.0.0-R0.1
 */
public abstract class ImplCommand implements CommandExecutor {

    CommandSender sender;
    Command command;
    String label;
    Enumerator<String> args;

    private ImplCommand() {
    }

    abstract boolean execute(CommandSender sender, Command command, String label, Enumerator<String> args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = Enumerator.fromArray(args);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CommandHandler.onCommand(player, command);
        }
        EvtOnCommand evt = new EvtOnCommand(sender, command, label, args);
        if (evt.isCancelled()) return false;

        return execute(sender, command, label, this.args);
    }

    public boolean subcommand(Class<? extends ImplSubCommand> subcommand) {
        try {
            return subcommandthrows(subcommand);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ignored) {
        }
        return false;
    }

    public boolean subcommandthrows(Class<? extends ImplSubCommand> subcommand) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (sender == null) throw new IllegalArgumentException("'sender' is null.");
        if (command == null) throw new IllegalArgumentException("'command' is null.");
        if (label == null) throw new IllegalArgumentException("'label' is null.");
        if (args == null) throw new IllegalArgumentException("'args' is null.");
        return subcommand.getDeclaredConstructor().newInstance().execute(sender, command, label, args);
    }
}
