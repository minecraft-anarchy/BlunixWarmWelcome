package com.blunix.warmwelcome.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.blunix.warmwelcome.BlunixWarmWelcome;
import com.blunix.warmwelcome.models.ConfigManager;
import com.blunix.warmwelcome.util.Messager;

public class PlayerJoin implements Listener {
	private ConfigManager config;
	
	public PlayerJoin(BlunixWarmWelcome plugin) {
		config = new ConfigManager(plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPlayedBefore())
			return;
		
		Messager.sendMessage(player, config.getWelcomeMessage());
		ItemStack[] welcomeItems = config.getWelcomeItems();
		if (welcomeItems == null)
			return;
		
		player.getInventory().setContents(welcomeItems);
	}
}
