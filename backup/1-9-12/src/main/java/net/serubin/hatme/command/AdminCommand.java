package net.serubin.hatme.command;

import java.util.List;

import net.serubin.hatme.HatMe;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

	HatMe plugin;

	public AdminCommand(List<Integer> rbBlocks2, boolean rbAllow2,
			String notAllowedMsg, boolean rbOp2) {
		// TODO Auto-generated constructor stub
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("hadmin")) {
			Player player = (Player) sender;
			if (args.length == 0) {
				return false;
			} else {
				if (args[0].equalsIgnoreCase("add")) {

					if (checkPermissionAdmin(player)) {
						if (args.length == 1) {
							player.sendMessage("/hadmin add <item id> - Adds item to allowed block list");
							return true;
						}
						if (args.length > 2) {
							player.sendMessage("/hadmin add <item id> - Adds item to allowed block list");
							return true;
						} else {
							plugin.getConfig().getList("hatMe.allowed").add(
									Integer.parseInt(args[1]));
							plugin.saveConfig();
							plugin.reloadConfig();
							plugin.rbBlocks = plugin.getConfig().getList("hatMe.allowed");
							player.sendMessage(ChatColor.GREEN
									+ "Block "
									+ Integer.parseInt(args[1])
									+ " has been added to the allowed block list!");
							plugin.log.warning("[" + plugin.name + "]: " + player.getName()
									+ " has added " + Integer.parseInt(args[1])
									+ "to the allowed blocks list!");

							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED
								+ "You do not have permission");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("rem")) {

					if (checkPermissionAdmin(player)) {
						if (args.length == 1) {
							player.sendMessage("/hadmin rem <item id> - Adds item to allowed block list");
							return true;
						}
						if (args.length > 2) {
							player.sendMessage("/hadmin rem <item id> - Adds item to allowed block list");
							return true;
						} else {
							plugin.getConfig().getList("hatMe.allowed")
									.remove(Integer.parseInt(args[1]));
							plugin.saveConfig();
							plugin.reloadConfig();
							plugin.rbBlocks = plugin.getConfig().getList("hatMe.allowed");
							player.sendMessage(ChatColor.GREEN
									+ "Block "
									+ Integer.parseInt(args[1])
									+ " has been removed from the allowed block list!");
							plugin.log.warning("[" + plugin.name + "]: " + player.getName()
									+ " has removed "
									+ Integer.parseInt(args[1])
									+ "from the allowed blocks list!");

							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED
								+ "You do not have permission");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (checkPermissionAdmin(player)) {
						if (args.length > 1) {
							player.sendMessage("/hadmin list> - lists allowed block list");
							return true;
						} else {
							plugin.rbBlocks = plugin.getConfig().getList("hatMe.allowed");
							String blocks = plugin.rbBlocks.toString();
							player.sendMessage("Allowed Blocks: " + blocks);
							return true;
						}
					} else {
						player.sendMessage(ChatColor.RED
								+ "You do not have permission");
						return true;
					}

				} else {
				}
			}
		}
		return false;
	}

	public boolean checkPermissionAdmin(Player player) {
		if (player.hasPermission("hatme.admin"))
			return true;
		if (plugin.rbOp = true && player.isOp())
			return true;
		return false;
	}
}
