package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.util.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleEvent implements Listener {
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if(event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();
            Location playerLocation = player.getLocation();
            Location vehicleLocation = event.getVehicle().getLocation();

            //Check if the player is entering a vehicle outside the world
            if(isGoingOutOfWorld(playerLocation.getX(), playerLocation.getZ(), vehicleLocation.getX(), vehicleLocation.getZ())) {
                //Prevent player from going out of the world
                event.setCancelled(true);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Messages.playerWorldBorder));
            }
        }
    }

    private boolean isGoingOutOfWorld(double fromX, double fromZ, double toX, double toZ) {
        return (fromX > Config.MAIN_WORLD_MIN_X && toX < Config.MAIN_WORLD_MIN_X) ||
                (fromX < Config.MAIN_WORLD_MAX_X && toX > Config.MAIN_WORLD_MAX_X) ||
                (fromZ > Config.MAIN_WORLD_MIN_Z && toZ < Config.MAIN_WORLD_MIN_Z) ||
                (fromZ < Config.MAIN_WORLD_MAX_Z && toZ > Config.MAIN_WORLD_MAX_Z);
    }
}
