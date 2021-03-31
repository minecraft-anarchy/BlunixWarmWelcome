package com.blunix.warmwelcome.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.blunix.warmwelcome.BlunixWarmWelcome;
import com.blunix.warmwelcome.util.StringUtil;

public class ConfigManager {
	private BlunixWarmWelcome plugin;

	public ConfigManager(BlunixWarmWelcome plugin) {
		this.plugin = plugin;
	}

	public String getWelcomeMessage() {
		return plugin.getConfig().getString("welcome-message");
	}

	public ItemStack[] getWelcomeItems() {
		List<ItemStack> welcomeItems = new ArrayList<>();
		ConfigurationSection itemSection = plugin.getConfig().getConfigurationSection("welcome-items");
		if (itemSection == null) {
			sendErrorLog("There was an error reading 'welcome-items' in config.yml");
			return null;
		}
		itemSection.getKeys(false).forEach(itemName -> {
			Material material = Material.matchMaterial(itemName);
			if (material == null) {
				sendErrorLog("There was an error reading '" + itemName + "' in config.yml");
				return;
			}
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			int amount = itemSection.getInt(itemName + ".amount");
			List<String> configLore = itemSection.getStringList(itemName + ".lore");
			ConfigurationSection enchantmentSection = itemSection.getConfigurationSection(itemName + ".enchantments");

			item.setAmount(amount);
			applyLore(configLore, meta);
			applyColor(itemName, meta, itemSection);
			item.setItemMeta(meta);
			applyEnchantments(enchantmentSection, item);

			welcomeItems.add(item);
		});

		return welcomeItems.toArray(new ItemStack[0]);
	}

	private void sendErrorLog(String message) {
		Bukkit.getLogger().warning("[BlunixWarmWelcome] " + message);
	}

	private void applyLore(List<String> configLore, ItemMeta meta) {
		if (configLore == null)
			return;

		List<String> lore = new ArrayList<>();
		for (String line : configLore)
			lore.add(StringUtil.formatColor(line));
		meta.setLore(lore);
	}

	private void applyEnchantments(ConfigurationSection enchantmentSection, ItemStack item) {
		if (enchantmentSection == null)
			return;

		enchantmentSection.getKeys(false).forEach(enchantmentName -> {
			@SuppressWarnings("deprecation")
			Enchantment enchantment = Enchantment.getByName(enchantmentName);
			int level = enchantmentSection.getInt(enchantmentName);
			if (enchantment == null)
				return;

			item.addUnsafeEnchantment(enchantment, level);
		});
	}

	private void applyColor(String configName, ItemMeta meta, ConfigurationSection itemSection) {
		if (!(meta instanceof LeatherArmorMeta))
			return;

		LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;
		String hexColor = itemSection.getString(configName + ".color");
		Color color = getColorFromHex(hexColor);
		if (color == null) {
			sendErrorLog("There was an error reading '" + hexColor + "' in config.yml");
			return;
		}
		leatherMeta.setColor(color);
	}

	private Color getColorFromHex(String hexCode) {
		try {
			return Color.fromRGB(Integer.valueOf(hexCode.substring(1, 3), 16),
					Integer.valueOf(hexCode.substring(3, 5), 16), Integer.valueOf(hexCode.substring(5, 7), 16));

		} catch (Exception e) {
			return null;
		}
	}
}
