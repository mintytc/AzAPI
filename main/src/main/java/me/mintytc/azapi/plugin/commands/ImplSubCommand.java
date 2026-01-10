package me.mintytc.azapi.plugin.commands;

import me.mintytc.azapi.core.classes.arrays.Enumerator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public abstract class ImplSubCommand {

    public abstract boolean execute(CommandSender sender, Command command, String label, Enumerator<String> args);
}
