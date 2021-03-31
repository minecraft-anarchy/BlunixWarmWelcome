package com.blunix.warmwelcome.commands;

import org.bukkit.command.CommandSender;

import com.blunix.warmwelcome.util.Messager;

public class CommandHelp extends WelcomeCommand {
	public CommandHelp() {
		setName("help");
		setHelpMessage("Displays this list.");
		setPermission("blunixwarmwelcome.help");
		setUsageMessage("/ww help");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Messager.sendHelpMessage(sender);
	}
}
