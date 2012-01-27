package net.serubin.hatme.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.serubin.hatme.HatMe;
import net.serubin.hatme.permissions.Permission;

public class HatCommand implements CommandExecutor {

	private String notAllowedMsg1;
	private boolean rbAllow1;
	private List<Integer> rbBlocks1;
	private List<Integer> allowID;
	private boolean rbOp1;
	private HatMe plugin;
	Permission perm1;

	public HatCommand(List<Integer> rbBlocks, boolean rbAllow,
			String notAllowedMsg, boolean rbOp) {
		// TODO Auto-generated constructor stub
		this.rbBlocks1 = rbBlocks;
		this.rbAllow1 = rbAllow;
		this.notAllowedMsg1 = notAllowedMsg;
		this.rbOp1 = rbOp;
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command requires an instance of a player.");
			return false;
		}
		Player player = (Player) sender;
		ItemStack itemHand = player.getItemInHand();
		PlayerInventory inventory = player.getInventory();
		int itemHandId = itemHand.getTypeId();
		if (commandLabel.equalsIgnoreCase("hat")) {
			if (checkPermissionBasic(player)) {
				if (args.length == 0) {
					allowID = rbBlocks1;
					String allowIDS = allowID.toString();
					String allow = rbBlocks1.toString();
					player.sendMessage("AllowID_" + allowIDS);
					player.sendMessage("rbBlocks_" + allow);
					if (rbAllow1 != false) {
						player.sendMessage("Restricting = true");
						// if restrict is true
						if (!checkPermissionNoRestrict(player)) {
							player.sendMessage("NoRestrict = false");
							// if op or has perm no restrict
							if ((!allowID.contains(itemHandId))
									&& (itemHandId != 0)) {
								player.sendMessage("itemhand allowed = false");
								// checks for allowed blocks
								player.sendMessage(ChatColor.RED
										+ notAllowedMsg1);
								return true;
							} else {
								player.sendMessage("itemhand allowed = true");
								hatOn(sender);
								return true;
							}
						} else {
							player.sendMessage("NoRestrict = true");
							hatOn(sender);
							return true;
						}
					} else {
						player.sendMessage("Restricting = false");
						hatOn(sender);
						return true;
					}
					// if restrict is false
				} else {
					if (args.length == 1) {
						if (checkPermissionGive(player, args)) {
							allowID = rbBlocks1;
							if (rbAllow1 != false) {
								// if restrict is true
								if (!checkPermissionNoRestrict(player)) {
									// if op or has perm no restrict
									if ((!allowID.contains(Integer
											.parseInt(args[0])))
											&& (Integer.parseInt(args[0]) != 0)) {
										// checks for allowed blocks
										player.sendMessage(ChatColor.RED
												+ notAllowedMsg1);
										return true;
									} else {
										giveHat(sender, cmd, commandLabel, args);
										return true;
									}
								} else {
									giveHat(sender, cmd, commandLabel, args);
									return true;
								}
							} else {
								giveHat(sender, cmd, commandLabel, args);
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED
									+ "You do not have permission");
							return true;
						}
					}else{
						return false;
					}
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission");
				return true;
			}
		}

		if (commandLabel.equalsIgnoreCase("unhat")) {
			if (args.length >= 1) {
				return false;
			} else {
				if (player.getInventory().getHelmet().getTypeId() == 0) { // If
																			// helmet
																			// is
																			// empty
																			// do
																			// nothing
					player.sendMessage(ChatColor.RED
							+ "You have no hat to take off!");
					return true;
				} else {
					// ItemStack itemHand = player.getItemInHand();
					// PlayerInventory inventory = player.getInventory();
					int empty = inventory.firstEmpty();
					ItemStack itemHead = inventory.getHelmet(); // Get item in
																// helmet
					if (empty == -1) {
						player.sendMessage(ChatColor.RED
								+ "You have no space to take of your hat!");
					} else {
						inventory.setHelmet(null); // removes item from helmet
						inventory.setItem(empty, itemHead); // Sets item from
															// helmet
															// to first open
															// slot
						player.sendMessage(ChatColor.YELLOW
								+ "You have taken off your hat!");
						return true;
					}
				}
			}
		}
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

	public boolean giveHat(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player player = (Player) sender;
		PlayerInventory inventory = player.getInventory();
		ItemStack itemHead = inventory.getHelmet();
		int empty = inventory.firstEmpty();
		ItemStack item;
		Material itemId;
		int itemIdint = Integer.parseInt(args[0]);
		// gets id
		try {
			// int id = Integer.parseInt(args[0]);
			itemId = Material.getMaterial(itemIdint);
			// gets item from id
		} catch (NumberFormatException e) {
			itemId = Material.matchMaterial(args[0]);
		}
		// int to Material
		item = new ItemStack(itemId, 1);
		if (itemHead.getTypeId() != 0) {
			if (empty == -1) {
				player.sendMessage(ChatColor.RED + "You already have a hat!");
			} else {
				inventory.setHelmet(item);
				inventory.setItem(empty, itemHead);
				player.sendMessage(ChatColor.YELLOW
						+ "You now have been given a hat!");
			}
		} else {
			inventory.setHelmet(item);
			player.sendMessage(ChatColor.YELLOW
					+ "You now have been given a hat!");
			return true;
		}
		return true;
	}

	public boolean checkPermissionBasic(Player player) {
		if (player.hasPermission("hatme.hat")
				|| player.hasPermission("hatme.hat."
						+ player.getItemInHand().getTypeId()))
			return true;
		if (rbOp1 = true && player.isOp())
			return true;
		return false;
	}

	public boolean checkPermissionGive(Player player, String[] args) {
		if (player.hasPermission("hatme.hat.give")
				|| player.hasPermission("hatme.hat.give."
						+ Integer.parseInt(args[0])))
			return true;
		if (rbOp1 = true && player.isOp())
			return true;
		return false;
	}

	public boolean checkPermissionNoRestrict(Player player) {
		// if (player.hasPermission("hatme.norestrict"))
		// return true;
		// if (rbOp1 = true && player.isOp())
		// return true;
		return false;
	}
}
