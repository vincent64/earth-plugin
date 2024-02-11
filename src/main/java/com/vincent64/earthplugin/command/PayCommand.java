package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.MoneyUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {
    private PlayerData playerData;

    public PayCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Check if the command contains two arguments
            if(args.length == 2) {
                //Get target player and amount
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                String amountString = args[1];

                //Check if the player exists
                if(targetPlayer != null) {
                    //Check if the amount is valid
                    if(MoneyUtil.isValidAmountString(amountString)) {
                        //Get the amount as a double
                        double amount = Double.parseDouble(amountString);

                        //Check if the player has enough
                        if(playerData.hasEnoughMoney(player, amount)) {
                            //Transfer the money
                            playerData.decreaseMoney(player, amount);
                            playerData.increaseMoney(targetPlayer, amount);
                            //Send message to both players
                            player.sendMessage(String.format(Messages.transferSuccess, amount, Config.CURRENCY, targetPlayer.getName()));
                            targetPlayer.sendMessage(String.format(Messages.transferSuccessTarget, amount, Config.CURRENCY, player.getName()));
                        } else {
                            player.sendMessage(Messages.transferNotEnoughMoney);
                        }
                    } else {
                        player.sendMessage(Messages.transferInvalidAmount);
                    }
                } else {
                    player.sendMessage(Messages.transferPlayerInexistant);
                }
            } else {
                player.sendMessage(Messages.transferInvalidCommand);
            }
        }

        return true;
    }
}
