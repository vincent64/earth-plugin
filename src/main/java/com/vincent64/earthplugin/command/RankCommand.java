package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.earth.Rank;
import com.vincent64.earthplugin.earth.Requirement;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RankCommand implements CommandExecutor {
    private PlayerData playerData;

    public RankCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                listRank(player);
            } else if(args.length == 2) {
                if(args[0].equals("set")) {
                    setRank(player, args[1]);
                } else {
                    player.sendMessage(Messages.rankInvalidCommand);
                }
            } else {
                player.sendMessage(Messages.rankInvalidCommand);
            }
        }

        return true;
    }

    private void listRank(Player player) {
        List<String> ranks = playerData.getUnlockedRanks(player);
        //Set top of the list
        String message = String.format(Messages.rankListTop, ranks.size());
        //Loop through each rank
        for(String rank : ranks) {
            //Get rank name
            Rank unlockedRank = Rank.getRank(rank);
            Requirement requirement = unlockedRank.getRequirement();
            String rankName = unlockedRank.getName();
            //Add rank to the list
            message += ChatColor.DARK_GREEN + rankName + ChatColor.GREEN + " (✔ " + requirement.getType().getName() + ": " + requirement.getLevel() + ")\n";
        }
        //Add bottom of the list
        message += Messages.rankListBottom;
        //Send list to player
        player.sendMessage(message);
    }

    private void setRank(Player player, String rankId) {
        //Get rank from id
        Rank rank = Rank.getRank(rankId);

        //Make sure the rank exists
        if(rank != null) {
            //Check if the player has unlocked this rank
            if(playerData.hasUnlockedRank(player, rank)) {
                String playerName = player.getName();
                playerName += player.isOp() ? ChatColor.GOLD + " ★" : "";
                //Set rank to player
                playerData.setRank(player, rank);
                //Set player's display name
                String displayName = ChatColor.DARK_GREEN + "[%s] " + ChatColor.GREEN + "%s";
                player.setDisplayName(String.format(displayName, rank.getName(), playerName));
                player.setPlayerListName(String.format(displayName, rank.getName(), playerName));
                player.sendMessage(String.format(Messages.rankSuccess, rank.getName()));
            } else {
                player.sendMessage(Messages.rankNotUnlocked);
            }
        } else {
            player.sendMessage(Messages.rankInvalidName);
        }
    }
}
