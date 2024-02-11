package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.EarthPlugin;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.ShopData;
import com.vincent64.earthplugin.shop.ChestShop;
import com.vincent64.earthplugin.shop.Shop;
import com.vincent64.earthplugin.shop.ShopType;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {
    private PlayerData playerData;
    private ShopData shopData;
    private EarthPlugin plugin;

    public ChatEvent(PlayerData playerData, ShopData shopData, EarthPlugin plugin) {
        this.playerData = playerData;
        this.shopData = shopData;
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //Get chat message
        String message = event.getMessage();

        //Check if the player is sending a message or making a transaction
        if(!playerData.isCurrentTransactor(player)) {
            //Get player name
            String playerName = player.getName();
            //Get player rank
            String playerRank = playerData.getRank(player).getName();

            //Cancel chat sending
            event.setCancelled(true);
            //Resend formatted message
            Bukkit.broadcastMessage(String.format(Messages.chatMessage, playerRank, playerName, message));

            //Increase player's statistics
            playerData.getStatistics().increaseMessagesSent(player, 1);
        } else {
            //Cancel chat message
            event.setCancelled(true);
            //Handle transaction synchronously
            Bukkit.getScheduler().callSyncMethod(plugin, () -> handleTransaction(player, message));
        }
    }

    private boolean handleTransaction(Player player, String message) {
        if(message.equals("stop")) {
            //The player stopped his current transaction
            playerData.removeTransactor(player);
            player.sendMessage(Messages.shopCancelled);
            return false;
        } else {

            //Get the shop
            Block chestBlock = playerData.getTransactorChest(player);
            Shop shop = shopData.getShopAt(chestBlock.getX(), chestBlock.getY(), chestBlock.getZ());

            //Check shop type
            boolean wasSuccessful;
            if(shop.getType() == ShopType.BUY) {
                wasSuccessful = ChestShop.buyFromShop(shopData, playerData, chestBlock, player, message);
            } else {
                wasSuccessful = ChestShop.sellToShop(shopData, playerData, chestBlock, player, message);
            }

            if(wasSuccessful) {
                //Remove player from transactor
                playerData.removeTransactor(player);
            }
            return wasSuccessful;
        }
    }
}
