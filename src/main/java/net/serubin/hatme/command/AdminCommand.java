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

    public AdminCommand(List<?> rbBlocks, boolean rbAllow2,
            String notAllowedMsg, boolean rbOp2) {
        // TODO Auto-generated constructor stub
    }

    public boolean onCommand(CommandSender sender, Command cmd,
            String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("hadmin")) {
            if (args.length == 0) {
                return false;
            } else {
                if (args[0].equalsIgnoreCase("list")) {
                    if (checkPermissionAdmin(sender)) {
                        if (args.length > 1) {
                            sender.sendMessage("/hadmin list - lists allowed block list");
                            return true;
                        } else {
                            String blocks = plugin.rbBlocks.toString();
                            sender.sendMessage("Allowed Blocks: " + blocks);
                            return true;
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED
                                + "You do not have permission");
                        return true;
                    }

                } else {
                }
            }
        }
        return false;
    }

    public boolean checkPermissionAdmin(CommandSender sender) {
        if (sender.hasPermission("hatme.admin"))
            return true;
        if (plugin.rbOp = true && sender.isOp())
            return true;
        if (!(sender instanceof Player))
            return true;
        return false;
    }
}
