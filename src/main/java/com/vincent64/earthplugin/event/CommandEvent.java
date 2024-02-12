package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.util.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;
import java.util.List;

public class CommandEvent implements Listener {
    private List<String> allowedUserCommands;

    public CommandEvent() {
        allowedUserCommands = Arrays.asList("help", "l", "region", "money", "pay", "rank", "home", "town", "map");
    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent event) {
        if(!event.getPlayer().isOp()) {
            event.getCommands().retainAll(allowedUserCommands);
        }
    }

    @EventHandler
    public void onCommandExecution(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        //Check if the player is not an operator
        if(!player.isOp()) {
            //Get the command from the message
            String command = event.getMessage().split(" ")[0];

            //Check if the command is in the allowed commands list
            if(!isAllowedCommand(command)) {
                //Cancel the event
                event.setCancelled(true);
                player.sendMessage(Messages.playerCommandNotAllowed);
            }
        }
    }

    private boolean isAllowedCommand(String message) {
        boolean isAllowed = false;
        for(String allowedCommand : allowedUserCommands) {
            if(message.startsWith("/" + allowedCommand)) {
                isAllowed = true;
                break;
            }
        }

        return isAllowed;
    }
}
