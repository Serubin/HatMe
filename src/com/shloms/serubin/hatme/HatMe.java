package com.shloms.serubin.hatme;

import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class HatMe extends JavaPlugin{

	public static HatMe plugin;
	public static Logger log = Logger.getLogger("Minecraft");
	private String name;
	private String version;
	public static boolean rbAllow;
	public static boolean rbOp;
	public static List<Integer> rbBlocks;
	public static List<Integer> allowID;

	@Override
	public void onDisable() {
		reloadConfig();
		saveConfig();
		log.info(name + "has been disabled");
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onEnable(){

		version = this.getDescription().getName();
		name = this.getDescription().getVersion();

		log.info(name + " version " + version + " has started...");
		PluginManager pm = getServer().getPluginManager();
		getConfig().options().copyDefaults(true);
		saveConfig();
		log.info("[" + name + "]: " + "Loading config... ");
		rbBlocks = getConfig().getList("hatMe.allowed");
		rbAllow = getConfig().getBoolean("hatMe.enable");
		rbOp = getConfig().getBoolean("hatMe.opnorestrict");
		log.info("[" + name + "]: " + "has loaded config! ");
		if(rbAllow != false){
			log.info("[" + name + "]: " + "is restricting blocks");
		}
		log.info(name + " version " + version + " has been enabled!");
		}




	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		  Player player = (Player) sender;
		  ItemStack itemHand = player.getItemInHand();
		  int itemHandId = itemHand.getTypeId();
		 if (commandLabel.equalsIgnoreCase("hat") || commandLabel.equalsIgnoreCase("hatme") || commandLabel.equalsIgnoreCase("hm")){
				if (checkPermissionBasic(player)) {
					allowID = rbBlocks;
					if(rbAllow != false){
						//if restrict is true
						if(!allowID.contains(itemHandId) || itemHandId != 0){
							//checks for allowed blocks
							player.sendMessage(ChatColor.RED + "Invalid item");
							return true;
						}else{hatOn(sender); return true;}
					}else{hatOn(sender); player.sendMessage(Boolean.toString(rbAllow));  return true;}
					//if restrict is false
				}else{
					player.sendMessage(ChatColor.RED + "You do not have permission");
					return true;
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
		  }
		return true;
	  }


	 private boolean checkPermissionBasic(Player player) {
	     if(player.hasPermission("hatme.basic") || player.hasPermission("hatme.*") || player.isOp()) return true;
	     return false;
	    }
	// private boolean checkPermissionGive(Player player) {
	  //   if(player.hasPermission("hatme.give") || player.hasPermission("hatme.*") || player.isOp()) return true;
	    // return false;
	// }


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
             }else{
             player.sendMessage(ChatColor.YELLOW + "You now have a hat!"); 
             return true;
             }
	  	}
	 return false;
	}

}