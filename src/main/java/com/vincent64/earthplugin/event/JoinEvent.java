package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinEvent implements Listener {
    private PlayerData playerData;

    public JoinEvent(PlayerData playerData) {
        this.playerData = playerData;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Change join message
        event.setJoinMessage(String.format(Messages.playerJoin, player.getName()));
        //Set player gamemode to survival
        player.setGameMode(GameMode.SURVIVAL);

        //Check if the player is joining for the first time
        if(!playerData.hasData(player)) {
            //Send welcome message
            Bukkit.broadcastMessage(String.format(Messages.playerWelcome, player.getName()));
            player.sendMessage(Messages.playerWelcomePrivate);
            //Create player data
            playerData.createData(player);
        }

        //Get player name
        String playerName = player.getName();
        playerName += player.isOp() ? ChatColor.GOLD + " ★" : "";
        //Get player rank
        String playerRank = playerData.getRank(player).getName();

        //Set player's display name
        String displayName = ChatColor.DARK_GREEN + "[%s] " + ChatColor.GREEN + "%s";
        player.setDisplayName(String.format(displayName, playerRank, playerName));
        player.setPlayerListName(String.format(displayName, playerRank, playerName));
        //Set tablist header and footer
        String header = ChatColor.YELLOW + "Have fun on " + ChatColor.GOLD + Config.SERVER_NAME + ChatColor.YELLOW + " !";
        String footer = ChatColor.GREEN + "If you need help, go on the Discord server !";
        player.setPlayerListHeaderFooter(header, footer);

        //Check if the player need daily pay
        long lastDailyPay = playerData.getLastDailyPay(player);
        long now = System.currentTimeMillis();
        if(now - lastDailyPay >= 86_400_000) {
            //Give player its daily pay
            playerData.increaseMoney(player, Config.PLAYER_DAILY_MONEY);
            playerData.setLastDailyPay(player, now);
            player.sendMessage(String.format(Messages.moneyDaily, Config.PLAYER_DAILY_MONEY, Config.CURRENCY));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Change leave message
        event.setQuitMessage(String.format(Messages.playerLeave, player.getName()));

        //Check if the player was a transactor
        if(playerData.isCurrentTransactor(player)) {
            playerData.removeTransactor(player);
        }

        //Check if the player was sat on a chair
        if(playerData.isPlayerOnChair(player)) {
            //Remove the arrow and the player
            playerData.removeChairByPlayer(player);
        }
    }
}
