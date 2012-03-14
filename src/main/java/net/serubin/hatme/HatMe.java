package net.serubin.hatme;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import net.serubin.hatme.command.AdminCommand;
import net.serubin.hatme.command.HatCommand;
import net.serubin.hatme.permissions.Permission;

public class HatMe extends JavaPlugin {

	public static HatMe plugin;
	public static Logger log = Logger.getLogger("Minecraft");
	public String name;
	private String version;
	public static boolean rbAllow;
	public static boolean rbOp;
	public static String notAllowedMsg;
	public static List<Integer> rbBlocks;
	public static List<Integer> allowID;

	public void onDisable() {
		reloadConfig();
		saveConfig();
		log.info(name + " has been disabled");
	}

	@SuppressWarnings("unchecked")
	public void onEnable() {

		version = this.getDescription().getVersion();
		name = this.getDescription().getName();

		log.info(name + " version " + version + " has started...");
		PluginManager pm = getServer().getPluginManager();
		getConfig().options().copyDefaults(true);
		saveConfig();
		// Loads config
		log.info("[" + name + "]: " + "Loading config... ");
		rbBlocks = getConfig().getIntegerList("hatMe.allowed");
		rbAllow = getConfig().getBoolean("hatMe.enable");
		notAllowedMsg = getConfig().getString("hatMe.notAllowedMsg");
		rbOp = getConfig().getBoolean("hatMe.opnorestrict");
		log.info("[" + name + "]: " + "has loaded config! ");
		if (rbAllow != false) {
			log.info("[" + name + "]: " + "is restricting blocks");
		}
		log.info("[" + name + "]: " + "Loading commands");
		// Loads classes
		HatCommand Hat = new HatCommand(rbBlocks, rbAllow, notAllowedMsg, rbOp);
		AdminCommand Admin = new AdminCommand(rbBlocks, rbAllow, notAllowedMsg,
				rbOp);
		// Permission Perm = new Permission(rbBlocks, rbAllow,
		// notAllowedMsg, rbOp);
		// Set command classes
		getCommand("hat").setExecutor(Hat);
		getCommand("unhat").setExecutor(Hat);
		getCommand("hadmin").setExecutor(Admin);

		log.info("[" + name + "]: " + "Loaded commands");
		log.info(name + " version " + version + " has been enabled!");
	}

	

}