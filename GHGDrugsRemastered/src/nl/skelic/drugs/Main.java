package nl.skelic.drugs;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import nl.skelic.commands.*;
import nl.skelic.events.ListenerClass;
import nl.skelic.files.PlayerData;
import nl.skelic.utils.SkullUtil;
import nl.skelic.utils.Util;

@SuppressWarnings("unused")
public class Main extends JavaPlugin {
	
	private static Economy econ = null;
	private static Main instance;
	private Util util;
	private SkullUtil skullUtil;
	private PlayerData playerData;
	
	public static final String prefix = (ChatColor.AQUA + "[GHG] " + ChatColor.RESET);
	
	@Override
	public void onEnable() {
		//Loading Vault
		if(!setupEconomy()) {
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		
		util = new Util(this);
		skullUtil = new SkullUtil(this);
		
		/*if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}*/
		
		//Loading Configs
		if(new File(getDataFolder(), "PlayerData").mkdirs()) {
			getLogger().info("Generated PlayerData folder!");
		}
		
		instance = this;
		
		//Loading Events
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new ListenerClass(), this);
		pm.registerEvents(playerData = new PlayerData(this), this);
		
		//Loading Commands
		getCommand("dshop").setExecutor(new DshopCMD());
		getCommand("reborn").setExecutor(new RebornCMD());
		
		//Finished Message
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "--------{De GHGDrugs Plugin}-------");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "       Created by: SkelicStylz   " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "           Version: v" + getDescription().getVersion() + "         " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "      Plugin Status:  Enabled    " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "-----------------------------------");
		//loadConfigManager();
		loadConfiguration();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "--------{De GHGDrugs Plugin}-------");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "       Created by: SkelicStylz   " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "           Version: v" + getDescription().getVersion() + "         " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "|" + ChatColor.YELLOW + "      Plugin Status: Disabled    " + ChatColor.GOLD + "|");
		Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "-----------------------------------");
	}
	
	public static Main getInstance() {
		return instance;
	}

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        /*getConfig().addDefault("coca�ne.addictive", true);
        getConfig().addDefault("coca�ne.after_how_many_times", 1);
        getConfig().addDefault("weed.addictive", true);
        getConfig().addDefault("weed.after_how_many_times", 5);*/
        saveConfig();
        getLogger().info("Configuratie Reloaded!");
        Bukkit.broadcastMessage(prefix + ChatColor.GOLD + "De GHGDrugs Remastered Plugin is loaded");
    }
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public Util getUtil() {
		return util;
	}
	
	public SkullUtil getSkullUtil() {
		return skullUtil;
	}
	
	public static Economy getEconomy() {
        return econ;
	}
	
}