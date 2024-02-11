package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class MoneyCommand implements CommandExecutor {
    private PlayerData playerData;

    public MoneyCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Get the amount of money the player has
            double money = playerData.getMoney(player);
            String moneyPrint = new DecimalFormat("#.##").format(money);
            //Print message
            player.sendMessage(String.format(Messages.moneyView, moneyPrint, Config.CURRENCY));
        }

        return true;
    }
}
