package com.blunix.warmwelcome.commands;

import org.bukkit.command.CommandSender;

import com.blunix.warmwelcome.BlunixWarmWelcome;
import com.blunix.warmwelcome.util.Messager;

public class CommandReload extends WelcomeCommand {
	private BlunixWarmWelcome plugin;

	public CommandReload(BlunixWarmWelcome plugin) {
		this.plugin = plugin;

		setName("reload");
		setHelpMessage("Reloads the plugin's config.");
		setPermission("blunixwarmwelcome.reload");
		setUsageMessage("/ww reload");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		plugin.reloadConfig();
		Messager.sendSuccessMessage(sender, "&aConfig reloaded.");
	}
}
