 package net.serubin.hatme.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.serubin.hatme.HatMe;
import net.serubin.hatme.permissions.Permission;

public class AdminCommand implements CommandExecutor {

	Permission perm;
	HatMe hat;
	public AdminCommand(List<Integer> rbBlocks, boolean rbAllow,
			String notAllowedMsg, boolean rbOp) {
		// TODO Auto-generated constructor stub
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("hadmin")) {
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("add")) {

			if (perm.checkPermissionAdmin(player)) {
				hat.addblock(player, args);
				return true;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission");
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("rem")) {

			if (perm.checkPermissionAdmin(player)) {
				hat.remblock(player, args);
				return true;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission");
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("list")) {

			if (perm.checkPermissionAdmin(player)) {
				hat.listblock(player, args);
				return true;
			} else {
				player.sendMessage(ChatColor.RED
						+ "You do not have permission");
				return true;
			}
		} else {
	}
		}
		return false;
	}
}
