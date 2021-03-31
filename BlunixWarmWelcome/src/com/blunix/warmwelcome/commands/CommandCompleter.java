package com.blunix.warmwelcome.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.blunix.warmwelcome.BlunixWarmWelcome;

public class CommandCompleter implements TabCompleter {
	private BlunixWarmWelcome plugin;

	public CommandCompleter(BlunixWarmWelcome plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<>();
		if (arguments.isEmpty()) {
			for (WelcomeCommand subcommand : plugin.getSubcommands().values()) {
				if (!sender.hasPermission(subcommand.getPermission()))
					continue;

				arguments.add(subcommand.getName());
			}
		}
		if (args.length >= 0 && args.length < 2)
			return getCompletion(arguments, args, 0);

		arguments.clear();
		return arguments;
	}

	private ArrayList<String> getCompletion(ArrayList<String> arguments, String[] args, int index) {
		ArrayList<String> results = new ArrayList<>();
		for (String argument : arguments) {
			if (!argument.toLowerCase().startsWith(args[index].toLowerCase()))
				continue;

			results.add(argument);
		}
		return results;
	}
}
