package me.mintytc.azapi.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mintytc.azapi.api.classes.arrays.Enumerator;
import me.mintytc.azapi.api.commands.ImplTabCompleter;

public class AzAPITabs extends ImplTabCompleter {

	@Override
	public Enumerator<String> execute(CommandSender sender, Command command, String label, Enumerator<String> args0) {

		String[] args = args0.array(String.class);

		if (args.length == 1) {
			complete("help");
			complete("versions");
			complete("addons");
		}

		return tabs;
	}
}
