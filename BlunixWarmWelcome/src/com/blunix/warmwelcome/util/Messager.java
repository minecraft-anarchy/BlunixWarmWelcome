package com.blunix.warmwelcome.util;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.blunix.warmwelcome.BlunixWarmWelcome;
import com.blunix.warmwelcome.commands.WelcomeCommand;

public class Messager {
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendSuccessMessage(CommandSender sender, String message) {
		sendMessage(sender, message);
		SFXManager.playSuccessSound(sender);
	}
	
	public static void sendErrorMessage(CommandSender sender, String message) {
		sendMessage(sender, message);
		SFXManager.playErrorSound(sender);
	}
	
	public static void sendHelpMessage(CommandSender sender) {
		BlunixWarmWelcome plugin = BlunixWarmWelcome.getPlugin(BlunixWarmWelcome.class);
		String finalMessage = "&a&lCommands\n";
		Iterator<WelcomeCommand> iterator = plugin.getSubcommands().values().iterator();
		
		while (iterator.hasNext()) {
			WelcomeCommand subcommand = iterator.next();
			if (!sender.hasPermission(subcommand.getPermission()))
				continue;
			
			finalMessage += "&6" + subcommand.getUsageMessage() + " &a- &b" + subcommand.getHelpMessage();
			if (iterator.hasNext())
				finalMessage += "\n";
			
		}
		Messager.sendSuccessMessage(sender, finalMessage);
	}
	
	public static void sendNoPermissionMessage(CommandSender sender) {
		sendErrorMessage(sender, "&cYou do not have permissions to use this command!");
	}
}
