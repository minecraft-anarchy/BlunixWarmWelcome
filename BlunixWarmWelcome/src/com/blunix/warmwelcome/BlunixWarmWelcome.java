package com.blunix.warmwelcome;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.warmwelcome.commands.CommandCompleter;
import com.blunix.warmwelcome.commands.CommandHelp;
import com.blunix.warmwelcome.commands.CommandReload;
import com.blunix.warmwelcome.commands.CommandRunner;
import com.blunix.warmwelcome.commands.WelcomeCommand;
import com.blunix.warmwelcome.events.PlayerJoin;

public class BlunixWarmWelcome extends JavaPlugin {
	private Map<String, WelcomeCommand> subcommands = new LinkedHashMap<>();

	@Override
	public void onEnable() {
		saveDefaultConfig();

		getCommand("warmwelcome").setExecutor(new CommandRunner(this));
		getCommand("warmwelcome").setTabCompleter(new CommandCompleter(this));
		subcommands.put("help", new CommandHelp());
		subcommands.put("reload", new CommandReload(this));

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
	}

	@Override
	public void onDisable() {

	}

	public Map<String, WelcomeCommand> getSubcommands() {
		return subcommands;
	}
}
