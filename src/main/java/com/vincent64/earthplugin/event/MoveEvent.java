package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
    private PlayerData playerData;

    public MoveEvent(PlayerData playerData) {
        this.playerData = playerData;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location locationFrom = event.getFrom();
        Location locationTo = event.getTo();

        //Check if the player is going out of the world
        if(isGoingOutOfWorld(locationFrom.getX(), locationFrom.getZ(), locationTo.getX(), locationTo.getZ())) {
            //Prevent player from going out of the world
            event.setCancelled(true);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Messages.playerWorldBorder));

            //Force the player to leave the boat/minecart when reaching the world border
            if(player.isInsideVehicle()) {
                player.leaveVehicle();
            }
        } else {
            //Increase player's statistics
            double distance = distance(locationFrom.getX(), locationFrom.getZ(), locationTo.getX(), locationTo.getZ());
            playerData.getStatistics().increaseDistanceWalked(player, distance);
        }
    }

    private boolean isGoingOutOfWorld(double fromX, double fromZ, double toX, double toZ) {
        return (fromX > Config.MAIN_WORLD_MIN_X && toX < Config.MAIN_WORLD_MIN_X) ||
                (fromX < Config.MAIN_WORLD_MAX_X && toX > Config.MAIN_WORLD_MAX_X) ||
                (fromZ > Config.MAIN_WORLD_MIN_Z && toZ < Config.MAIN_WORLD_MIN_Z) ||
                (fromZ < Config.MAIN_WORLD_MAX_Z && toZ > Config.MAIN_WORLD_MAX_Z);
    }

    private double distance(double x1, double z1, double x2, double z2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2));
    }
}
