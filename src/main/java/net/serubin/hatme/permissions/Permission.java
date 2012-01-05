package net.serubin.hatme.permissions;

import java.util.List;

import org.bukkit.entity.Player;

public class Permission {

	public static boolean rbOp;
	
	public Permission(List<Integer> rbBlocks, boolean rbAllow,
			String notAllowedMsg, boolean rbOp) {
		// TODO Auto-generated constructor stub
		this.rbOp = rbOp;
	}

	public boolean checkPermissionBasic(Player player) {
		if (player.hasPermission("hatme.hat")
				|| player.hasPermission("hatme.hat."
						+ player.getItemInHand().getTypeId()))
			return true;
		if (rbOp = true && player.isOp())
			return true;
		return false;
	}

	public boolean checkPermissionGive(Player player, String[] args) {
		if (player.hasPermission("hatme.hat.give")
				|| player.hasPermission("hatme.hat.give."
						+ Integer.parseInt(args[0])))
			return true;
		if (rbOp = true && player.isOp())
			return true;
		return false;
	}

	public boolean checkPermissionNoRestrict(Player player) {
		if (player.hasPermission("hatme.norestrict"))
			return true;
		if (rbOp = true && player.isOp())
			return true;
		return false;
	}

	public boolean checkPermissionAdmin(Player player) {
		if (player.hasPermission("hatme.admin"))
			return true;
		if (rbOp = true && player.isOp())
			return true;
		return false;
	}
	
}
