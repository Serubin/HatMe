package com.shloms.serubin.hatme;

import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class HatMe extends JavaPlugin {

	public static HatMe plugin;
	public static Logger log = Logger.getLogger("Minecraft");
	private String name;
	private String version;
	public static boolean rbAllow;
	public static boolean rbOp;
	public static String notAllowedMsg;
	public static List<Integer> rbBlocks;
	public static List<Integer> allowID;

	@Override
	public void onDisable() {
		reloadConfig();
		saveConfig();
		log.info(name + " has been disabled");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {

		version = this.getDescription().getVersion();
		name = this.getDescription().getName();

		log.info(name + " version " + version + " has started...");
		PluginManager pm = getServer().getPluginManager();
		getConfig().options().copyDefaults(true);
		saveConfig();
		log.info("[" + name + "]: " + "Loading config... ");
		rbBlocks = getConfig().getList("hatMe.allowed");
		rbAllow = getConfig().getBoolean("hatMe.enable");
		notAllowedMsg = getConfig().getString("hatMe.notAllowedMsg");
		rbOp = getConfig().getBoolean("hatMe.opnorestrict");
		log.info("[" + name + "]: " + "has loaded config! ");
		if (rbAllow != false) {
			log.info("[" + name + "]: " + "is restricting blocks");
		}
		log.info(name + " version " + version + " has been enabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command requires an instance of a player.");
            return false;
        }
		  Player player = (Player) sender;
		  ItemStack itemHand = player.getItemInHand();
		  PlayerInventory inventory = player.getInventory();
		  int itemHandId = itemHand.getTypeId();
		 if (commandLabel.equalsIgnoreCase("hat")){
			 if (checkPermissionBasic(player)) {
				 if(args.length == 0){
					allowID = rbBlocks;
					if(rbAllow != false){
						//if restrict is true
						if(!checkPermissionNoRestrict(player)){
							//if op or has perm no restrict
						if((!allowID.contains(itemHandId)) && (itemHandId != 0)){
							//checks for allowed blocks
							player.sendMessage(ChatColor.RED + notAllowedMsg);
							return true;
						}else{hatOn(sender); return true;}
					}else{hatOn(sender); return true;}
					}else{hatOn(sender); return true;}
					//if restrict is false
				 }if (args.length == 1) {
					 if (checkPermissionGive(player, args)) {
						 allowID = rbBlocks;
							if(rbAllow != false){
								//if restrict is true
								if(!checkPermissionNoRestrict(player)){
									//if op or has perm no restrict
								if((!allowID.contains(Integer.parseInt(args[0]))) && (Integer.parseInt(args[0]) != 0)){
									//checks for allowed blocks
									player.sendMessage(ChatColor.RED + notAllowedMsg);
									return true;
								}else{giveHat(sender, cmd, commandLabel, args); return true;}
							}else{giveHat(sender, cmd, commandLabel, args); return true;}
							}else{giveHat(sender, cmd, commandLabel, args); return true;}
					 }else{
							player.sendMessage(ChatColor.RED + "You do not have permission");
							return true;
						}
					 }	
				}else{
					player.sendMessage(ChatColor.RED + "You do not have permission");
					return true;
				}

				 /*
					Material item;
					int itemId = Integer.parseInt(args[0]);
					try {
						//int id = Integer.parseInt(args[0]);
						item = Material.getMaterial(itemId);
					} catch (NumberFormatException e) {
						item = Material.matchMaterial(args[0]);
					}
						
				}*/
						//return true;
				//return true;
		 }


		  if (commandLabel.equalsIgnoreCase("unhat")){
				  if (player.getInventory().getHelmet().getTypeId() == 0) {                          //If helmet is empty do nothing
	                  player.sendMessage(ChatColor.RED + "You have no hat to take off!");
	                  return true;
				  }else{
					//ItemStack itemHand = player.getItemInHand();
                    //PlayerInventory inventory = player.getInventory();
                    int empty = inventory.firstEmpty();
                    ItemStack itemHead = inventory.getHelmet();                        //Get item in helmet
                    if(empty == -1){
                    	player.sendMessage(ChatColor.RED + "You have no space to take of your hat!");
                    }else{
                    inventory.setHelmet(null);                     // removes item from helmet
                    inventory.setItem(empty, itemHead);              //Sets item from helmet to first open slot
                    player.sendMessage(ChatColor.YELLOW + "You have taken off your hat!");
                    return true;
                    }
				  	}  
		  }
		return true;
	  }
	
	private boolean checkPermissionBasic(Player player) {
		if (player.hasPermission("hatme.hat") || player.hasPermission("hatme.*") || player.hasPermission("hatme.hat." + player.getItemInHand().getTypeId())) return true;
		if(rbOp = true && player.isOp()) return true;
		return false;
	}

	 private boolean checkPermissionGive(Player player, String[] args) {
	 if(player.hasPermission("hatme.give") || player.hasPermission("hatme.*") || player.hasPermission("hatme.give." + Integer.parseInt(args[0])) ) return true;
	 if(rbOp = true && player.isOp()) return true;
	 return false;
	 }
	 private boolean checkPermissionNoRestrict(Player player) {
			if (player.hasPermission("hatme.norestrict") || player.hasPermission("hatme.*")) return true;
			if(rbOp = true && player.isOp()) return true;
			return false;
		}

	public boolean hatOn(CommandSender sender) {
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
			} else {
				player.sendMessage(ChatColor.YELLOW + "You now have a hat!");
				return true;
			}
		}
		return false;
	}
	public boolean giveHat (CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		PlayerInventory inventory = player.getInventory();
		ItemStack itemHead = inventory.getHelmet();
		int empty = inventory.firstEmpty();
		ItemStack item;
		Material itemId;
		int itemIdint = Integer.parseInt(args[0]);
		//gets id
			try {
				//int id = Integer.parseInt(args[0]);
				itemId = Material.getMaterial(itemIdint);
				//gets item from id
			} catch (NumberFormatException e) {
				itemId = Material.matchMaterial(args[0]);
			}
			//int to Material
			item = new ItemStack(itemId, 1);
			if (itemHead.getTypeId() != 0) {
				 if(empty == -1){
                 	player.sendMessage(ChatColor.RED + "You already have a hat!");
				 }else{
				inventory.setHelmet(item);
				inventory.setItem(empty, itemHead);
				player.sendMessage(ChatColor.YELLOW + "You now have been given a hat!");
				 }
			} else {
				inventory.setHelmet(item);
				player.sendMessage(ChatColor.YELLOW + "You now have been given a hat!");
				return true;
			}
		return true;
	}

}