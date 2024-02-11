package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.home.Home;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.NameUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomeCommand implements CommandExecutor {
    private PlayerData playerData;

    public HomeCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                listHome(player);
            } else if(args.length == 1) {
                teleportToHome(player, args[0]);
            } else if(args.length == 2) {
                if(args[0].equals("create")) {
                    createHome(player, args[1]);
                } else if(args[0].equals("delete")) {
                    deleteHome(player, args[1]);
                } else {
                    player.sendMessage(Messages.homeInvalidCommand);
                }
            } else {
                player.sendMessage(Messages.homeInvalidCommand);
            }
        }

        return true;
    }

    private void listHome(Player player) {
        List<Home> homes = playerData.getHomes(player);
        //Set top of the list
        String message = String.format(Messages.homeListTop, homes.size());
        //Loop through each home
        for(Home home : homes) {
            //Get home name
            String homeName = home.getName();

            //Add home to the list
            message += ChatColor.GREEN + homeName + "\n";
        }
        //Add bottom of the list and show how many homes are left
        message += String.format(Messages.homeListBottom, Config.PLAYER_HOME_LIMIT, Config.PLAYER_HOME_LIMIT - homes.size());
        //Send list to player
        player.sendMessage(message);
    }

    private void teleportToHome(Player player, String homeName) {
        //Get home from name
        Home home = playerData.getHome(player, homeName);

        //Check if the home exists
        if(home != null) {
            //Get main world
            World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
            //Get home location
            Location playerLocation = player.getLocation();
            Location location = new Location(world, home.getX(), home.getY(), home.getZ(), playerLocation.getYaw(), playerLocation.getPitch());
            //Teleports player to the home
            player.teleport(location);
            player.sendMessage(String.format(Messages.homeTeleported, home.getName()));
        } else {
            player.sendMessage(Messages.homeHomeInexistant);
        }
    }

    private void createHome(Player player, String homeName) {
        //Check if the player has not reached the home limit
        if(playerData.getHomeAmount(player) < Config.PLAYER_HOME_LIMIT) {
            //Check if the home name is valid
            if (NameUtil.isValidName(homeName)) {
                //Check if the home name has already been used
                if(playerData.getHome(player, homeName) == null) {
                    //Get player's current location
                    Location location = player.getLocation();
                    //Create home at player's location
                    Home home = new Home(homeName, location.getBlockX(), location.getBlockY(), location.getBlockZ());

                    //Add home to the home list
                    playerData.addHome(player, home);
                    player.sendMessage(Messages.homeCreated);
                } else {
                    player.sendMessage(Messages.homeNameUsed);
                }
            } else {
                player.sendMessage(Messages.homeInvalidName);
            }
        } else {
            player.sendMessage(Messages.homeLimitReached);
        }
    }

    private void deleteHome(Player player, String homeName) {
        //Get home from name
        Home home = playerData.getHome(player, homeName);

        //Check if the home exists
        if(home != null) {
            playerData.removeHome(player, homeName);
            player.sendMessage(Messages.homeDeleted);
        } else {
            player.sendMessage(Messages.homeHomeInexistant);
        }
    }
}
