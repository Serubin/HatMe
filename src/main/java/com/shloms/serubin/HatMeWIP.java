package com.shloms.serubin.hatme;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
//import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
//import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.configuration.*;
import org.bukkit.util.config.Configuration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HatMe extends JavaPlugin{

	public static HatMe plugin;
	public static Logger log = Logger.getLogger("Minecraft");
	Configuration config;
	public static String version = 0.4 + "RC1";
	public static String name = "HatMe";
	boolean rbAllow;
	Array rbBlocks;
	
	  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		  Player player = (Player) sender;
		  ItemStack itemHand = player.getItemInHand();
		  if (commandLabel.equalsIgnoreCase("hat") || commandLabel.equalsIgnoreCase("hatme") || commandLabel.equalsIgnoreCase("hm")){
				if (checkPermissionBasic(player)) {
					if(rbAllow = true){
						if(itemHand != rbBlocks[]){
							player.sendMessage(ChatColor.RED + "Invalid item");
							return true;
						}else{hatOn(sender); return true;}
					}else{hatOn(sender); return true;}
				}
			  }
					 	  
		  if (commandLabel.equalsIgnoreCase("unhat") || commandLabel.equalsIgnoreCase("unhatme") || commandLabel.equalsIgnoreCase("unhm")){
			  if(checkPermissionBasic(player)){
				  if (player.getInventory().getHelmet().getTypeId() == 0) {                          //If helmet is empty do nothing
	                  player.sendMessage(ChatColor.RED + "You have no hat to take off!");
	                  return true;
				  }else{
					//ItemStack itemHand = player.getItemInHand();
                    PlayerInventory inventory = player.getInventory();
                    int empty = inventory.firstEmpty();
                    ItemStack none = inventory.getItem(empty);
                    ItemStack itemHead = inventory.getHelmet();                        //Get item in helmet
                    inventory.setHelmet(null);                     // removes item from helmet
                    inventory.setItem(empty, itemHead);              //Sets item from helmet to first open slot
                    return true;
				  	}  
		  	}
		  }
		return false;
	  }
	 
	  
	 private boolean checkPermissionBasic(Player player) {
	     if(player.hasPermission("hatme.basic") || player.hasPermission("hatme.*") || player.isOp()) return true;
	     return false;
	    }
	// private boolean checkPermissionGive(Player player) {
	  //   if(player.hasPermission("hatme.give") || player.hasPermission("hatme.*") || player.isOp()) return true;
	    // return false;
	// }
	    
	 
	@Override
	public void onDisable() {
		//PluginDescriptionFile pdfFile =this.getDescription();
		//this.logger.info(pdfFile.getName() + " is now disabled ");
		log.info(name + "has been disabled");
	}
	@Override
	public void onEnable(){
		log.info(name + "version" + version + "has been enabled");
		PluginManager pm = getServer().getPluginManager();
		loadConfig();

		if(config.getBoolean("restrictblocks.enable", true)){
			boolean rbAllow = true;
			log.info("[" + name + "]: " + "Restricted Blocks enabled!");
			
		}
		List<Integer> rbBlocks = config.getIntegerList("restrictblocks.allowed", 1,2,3,4,5,12,13,14,15,17,18,20,22,23,24,25,35,41,42,44,45,46,47,48,49,52,54,57,58,80,81,82,87,88,89,91,98,103,112);
		
		config.load();
		//this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " is enabled ");
		}
	
	private void loadConfig(){
		File f = new File(getDataFolder(),"config.yml");
		config = new Configuration(f);
		if(!f.exists()){
			config.setProperty("restrictblocks.enable", true);
			config.setProperty("restrictblocks.allowed", "1, 2, 3, 4, 5, 12, 13, 14, 15, 17, 18, 20, 22, 23, 24, 25, 35, 41, 42, 44, 45, 46, 47, 48, 49, 52, 54, 57, 58, 80, 81, 82, 87, 88, 89, 91, 98, 103, 112");
			config.setProperty("restrictblocks.opnorestrict", true);

			config.save();
		}
	}
		
	public boolean hatOn(CommandSender sender){
		Player player = (Player) sender;
	 if (player.getItemInHand().getTypeId() == 0) {
         player.sendMessage(ChatColor.RED + "Please pick a valid item!");
         return true;
	  } else {
             ItemStack itemHand = player.getItemInHand();
             PlayerInventory inventory = player.getInventory();
             ItemStack itemHead = inventory.getHelmet();
             inventory.removeItem(itemHand);
             inventory.setHelmet(itemHand);
             if (itemHead.getTypeId() != 0) {
                 inventory.setItemInHand(itemHead);
             player.sendMessage(ChatColor.YELLOW + "You now have a hat!");
             return true;
             }
	  	}
	 return false;
	}
	
}

