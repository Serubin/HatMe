package net.serubin.hatme;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HatExecutor {

    private HatMe plugin;

    public HatExecutor(HatMe plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public boolean hatOn(Player player) {
        ItemStack itemHand = player.getItemInHand();

        if (itemHand == null || itemHand.getType().getId() == 0) {
            if (player.getInventory().getHelmet() == null) {
                plugin.sendMessage(player, ChatColor.RED,
                        plugin.airHeadMessage());
                return true;
            } else {
                if (hatOff(player)) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {

            if (!headEmpty(player)) {
                swapHeadHand(player);
                return true;
            }

            if (moveOneToHead(itemHand, player)) {
                plugin.sendMessage(player, ChatColor.YELLOW,
                        plugin.hatOnMessage());
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean hatOnAll(Player player) {
        ItemStack itemHand = player.getItemInHand();

        if (itemHand == null) {
            if(!headEmpty(player)){
                hatOff(player);
                return true;
            }
            plugin.sendMessage(player, ChatColor.RED, plugin.airHeadMessage());
            return true;
        } else {

            if (!headEmpty(player)) {
               swapHeadHandAll(player);
                return true;
            }

            if (moveAllToHead(itemHand, player)) {
                plugin.sendMessage(player, ChatColor.YELLOW,
                        plugin.hatOnMessage());
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean giveHat(Player player, int itemID, short itemData) {
        if (itemID == 0) {
            plugin.sendMessage(player, ChatColor.RED, plugin.airHeadMessage());
            return true;
        } else {

            if (!headEmpty(player)) {
                plugin.sendMessage(player, ChatColor.RED,
                        plugin.hatAlreadyOnMessage());
                return true;
            }

            if (setHead(itemID, itemData, player)) {
                plugin.sendMessage(player, ChatColor.YELLOW,
                        plugin.hatOnMessage());
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean hatOff(Player player) {
        PlayerInventory inventory = player.getInventory();
        int empty = inventory.firstEmpty();
        ItemStack itemHead = inventory.getHelmet();
        if (empty == -1) {
            plugin.sendMessage(player, ChatColor.RED, plugin.noSpaceMessage());
            return true;
        } else {
            inventory.setHelmet(null);
            inventory.setItem(empty, itemHead);
            plugin.sendMessage(player, ChatColor.YELLOW, plugin.hatOffMessage());
            return true;
        }
    }

    private boolean moveOneToHead(ItemStack itemHand, Player player) {
        PlayerInventory playerInv = player.getInventory();

        // if hand has only 1 item
        if (itemHand.getAmount() == 1) {
            playerInv.setHelmet(itemHand);
            playerInv.setItemInHand(null);
            return true;
        } else {
            ItemStack newHelmet = new ItemStack(itemHand.getType(), 1,
                    itemHand.getDurability());
            plugin.debug("itemHand Original: " + itemHand.getAmount());
            newHelmet.setAmount(1);
            playerInv.setHelmet(newHelmet);
            itemHand.setAmount((itemHand.getAmount() - 1));
            plugin.debug("itemHand New: " + itemHand.getAmount());
            return true;
        }
    }

    private boolean swapHeadHand(Player player) {
        hatOff(player);
        moveOneToHead(player.getItemInHand(), player);
        return true;
    }
    private boolean swapHeadHandAll(Player player) {
        hatOff(player);
        moveAllToHead(player.getItemInHand(), player);
        return true;
    }
    private boolean moveAllToHead(ItemStack itemHand, Player player) {
        PlayerInventory playerInv = player.getInventory();
        playerInv.setHelmet(itemHand);
        playerInv.setItemInHand(null);
        return true;
    }

    @SuppressWarnings("deprecation")
    private boolean setHead(int itemID, short itemData, Player player) {
        PlayerInventory playerInv = player.getInventory();
        playerInv.setHelmet(new ItemStack(itemID, 1, itemData));
        return true;
    }

    private boolean headEmpty(Player player) {
        return (player.getInventory().getHelmet() == null) ? true : false;
    }
}
