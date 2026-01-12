package me.mintytc.azapi.commands;

import me.mintytc.azapi.classes.arrays.Enumerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @since 1.0.0-R0.1
 *
 */
public abstract class ImplSubCommand {

    public abstract boolean execute(CommandSender sender, Command command, String label, Enumerator<String> args);
}
