package com.blunix.warmwelcome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.warmwelcome.BlunixWarmWelcome;
import com.blunix.warmwelcome.util.Messager;

public class CommandRunner implements CommandExecutor {
	private BlunixWarmWelcome plugin;

	public CommandRunner(BlunixWarmWelcome plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("warmwelcome"))
			return true;
		if (args.length == 0) {
			Messager.sendHelpMessage(sender);
			return true;
		}
		if (!plugin.getSubcommands().containsKey(args[0].toLowerCase())) {
			Messager.sendErrorMessage(sender, "&cUnknown command. Type &l/ww help &cto see the full command list.");
			return true;
		}

		WelcomeCommand subcommand = plugin.getSubcommands().get(args[0].toLowerCase());
		if (!sender.hasPermission(subcommand.getPermission())) {
			Messager.sendNoPermissionMessage(sender);
			return true;
		}
		if (subcommand.isPlayerCommand() && !(sender instanceof Player)) {
			Messager.sendErrorMessage(sender, "&cNot available for consoles.");
			return true;
		}
		if (subcommand.isConsoleCommand() && sender instanceof Player) {
			Messager.sendErrorMessage(sender, "&cNot available for players.");
			return true;
		}
		if (args.length < subcommand.getArgumentLength()) {
			Messager.sendErrorMessage(sender, "&cUsage: &l" + subcommand.getUsageMessage());
			return true;
		}
		subcommand.execute(sender, args);
		return true;
	}
}
