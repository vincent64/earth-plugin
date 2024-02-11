package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocalCommand implements CommandExecutor {
    private PlayerData playerData;

    public LocalCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Check if the command contains a message
            if(args.length >= 1) {
                //Get message from command
                String message = getMessage(args);
                //Get player name
                String playerName = player.getName();
                //Get player rank
                String playerRank = playerData.getRank(player).getName();

                int playersNearby = 0;
                //Loop through every online player
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if(getDistanceBetween(player, onlinePlayer) <= 60) {
                        //Send message to player nearby
                        onlinePlayer.sendMessage(String.format(Messages.localChatMessage, playerRank, playerName, message));
                        //Increase amount of player nearby
                        playersNearby++;
                    }
                }

                //Check if the player who sent it is the only one who received the message
                if(playersNearby == 1) player.sendMessage(Messages.localNobodyNearby);
                //Increase player's statistics
                playerData.getStatistics().increaseMessagesSent(player, 1);
            } else {
                player.sendMessage(Messages.localInvalidCommand);
            }
        }

        return true;
    }

    private String getMessage(String[] args) {
        //Create message string builder
        StringBuilder message = new StringBuilder();
        //Append every argument and place a space between them
        for(int i = 0; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        return message.toString();
    }

    private double getDistanceBetween(Player player1, Player player2) {
        return player1.getLocation().distance(player2.getLocation());
    }
}
