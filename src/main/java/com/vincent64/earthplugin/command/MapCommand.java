package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.map.Map;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {
    private Map map;

    public MapCommand(Map map) {
        this.map = map;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Check if the map is enabled
            if(map.isEnabled()) {
                if(args.length == 0) {
                    player.sendMessage(String.format(Messages.mapLink, Config.SERVER_MAP_URL));
                } else if(args.length == 1) {
                    if(args[0].equals("hidems")) {
                        //Hide player on the map
                        map.hidePlayer(player);
                        player.sendMessage(Messages.mapHide);
                    } else if(args[0].equals("showms")) {
                        //Show player on the map
                        map.showPlayer(player);
                        player.sendMessage(Messages.mapShow);
                    } else {
                        player.sendMessage(Messages.mapInvalidCommand);
                    }
                } else {
                    player.sendMessage(Messages.mapInvalidCommand);
                }
            } else {
                player.sendMessage(Messages.mapNotEnabled);
            }
        }

        return true;
    }
}
