package net.serubin.hatme;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HatPermsHandler {
    private boolean restrictionEnabled;
    private List<Integer> unrestrictBlocks;
    
    public HatPermsHandler(HatMe plugin, FileConfiguration config) {
        this.unrestrictBlocks = config.getIntegerList("plugin.hatme.allowed");
        this.restrictionEnabled = config.getBoolean("plugin.hatme.restrict");
    }
    
    @SuppressWarnings("deprecation")
    protected boolean checkHatPerms(Player player) {
        if (player.hasPermission("hatme.hat") || 
                player.hasPermission("hatme.hat." + player.getItemInHand().getType().getId()) || player.getItemInHand().getType().getId() == 0)
            return true;
        
        return false;
    }
    
    @SuppressWarnings("deprecation")
    protected boolean checkHatAllPerms(Player player) {
        if (player.hasPermission("hatme.hat.all") || 
                player.hasPermission("hatme.hat.all." + player.getItemInHand().getType().getId()))
            return true;
        
        return false;
    }
    
    protected boolean checkGivePerms(Player player, String arg) {
        if (player.hasPermission("hatme.hat.give") || 
                player.hasPermission("hatme.hat.give." + arg))
            return true;
        
        return false;
    }

    @SuppressWarnings("deprecation")
    protected boolean checkRestrict(Player player) {
        int heldItem = player.getItemInHand().getType().getId();
        if(!restrictionEnabled) return true;
        if (player.hasPermission("hatme.norestrict"))
            return true;
        if (unrestrictBlocks.contains(heldItem))
            return true;
        return false;
    }

    boolean checkItemRestrict(String arg, Player player) {
        if(!restrictionEnabled) return true;
        if (player.hasPermission("hatme.norestrict"))
            return true;
        if (unrestrictBlocks.contains(Integer.parseInt(arg)))
            return true;
        return false;
    }
}
