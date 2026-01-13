package me.mintytc.azapi.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mintytc.azapi.api.classes.arrays.Enumerator;
import me.mintytc.azapi.api.commands.ImplCommand;
import me.mintytc.azapi.api.output.OutputStream;
import me.mintytc.azapi.api.util.UString;
import me.mintytc.azapi.plugin.AzAPI;

public class AzAPICommand extends ImplCommand {

	@Override
	public boolean execute(CommandSender sender, Command command, String label, Enumerator<String> args0) {

		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		String[] args = args0.array(String.class);

		if (args.length == 0) {
			OutputStream.send(player, "&8" + UString.center(" &eAzAPI (1/0)&8 ", "=", 64));
			OutputStream.send(player, "");
			OutputStream.send(player, "-- command");
			OutputStream.send(player, "");
			OutputStream.send(player, "&8" + UString.center(" &e" + AzAPI.INSTANCE.getDescription().getVersion() + "&8 ", "=", 64));
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("")) {

			}
		}

		return true;
	}
}
