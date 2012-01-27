package net.serubin.hatme.permissions;

import java.util.List;

import net.serubin.hatme.HatMe;

import org.bukkit.entity.Player;

public class Permission extends HatMe{

	public static boolean rbOp;
	
	public Permission(List<Integer> rbBlocks, boolean rbAllow,
			String notAllowedMsg, boolean rbOp) {
		// TODO Auto-generated constructor stub
		this.rbOp = rbOp;
	}



	
}
