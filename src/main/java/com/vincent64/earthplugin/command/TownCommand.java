package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.TownData;
import com.vincent64.earthplugin.town.Town;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.NameUtil;
import com.vincent64.earthplugin.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TownCommand implements CommandExecutor {
    private PlayerData playerData;
    private TownData townData;

    public TownCommand(PlayerData playerData, TownData townData) {
        this.playerData = playerData;
        this.townData = townData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                listTown(player);
            } else if(args.length == 1) {
                teleportToTown(player, args[0]);
            } else if(args.length == 2) {
                if(args[0].equals("create")) {
                    createTown(player, args[1]);
                } else if(args[0].equals("delete")) {
                    deleteTown(player, args[1]);
                } else {
                    player.sendMessage(Messages.townInvalidCommand);
                }
            } else {
                player.sendMessage(Messages.townInvalidCommand);
            }
        }

        return true;
    }

    private void listTown(Player player) {
        List<Town> towns = townData.getTownData();
        //Set top of the list
        String message = String.format(Messages.townListTop, towns.size());
        //Loop through each town
        for(Town town : towns) {
            //Get town name and owner
            String townName = town.getName();
            String townOwner = Bukkit.getPlayer(town.getOwner()).getName();

            //Add town to the list
            message += ChatColor.DARK_GREEN + townName + ChatColor.GREEN + " (" + townOwner + ")\n";
        }
        //Add bottom of the list
        message += Messages.townListBottom;
        //Send list to player
        player.sendMessage(message);
    }

    private void teleportToTown(Player player, String townName) {
        //Get town from name
        Town town = townData.getTown(townName);

        //Check if the town exists
        if(town != null) {
            //Get main world
            World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
            //Get town location
            Location playerLocation = player.getLocation();
            Location location = new Location(world, town.getX(), town.getY(), town.getZ(), playerLocation.getYaw(), playerLocation.getPitch());
            //Teleports player to the town
            player.teleport(location);
            player.sendMessage(String.format(Messages.townTeleported, town.getName()));
        } else {
            player.sendMessage(Messages.townTownInexistant);
        }
    }

    private void createTown(Player player, String townName) {
        //Get the last time the player created a town
        long lastUsedTimestamp = playerData.getTownCooldown(player);
        long now = System.currentTimeMillis();

        //Check if the player can create a new town now
        if(now - lastUsedTimestamp >= Config.TOWN_COOLDOWN_SECONDS * 1000L) {
            //Check if the town name is valid
            if(NameUtil.isValidName(townName)) {
                //Check if the town name is not already used
                if(townData.getTown(townName) == null) {
                    //Check if the player has enough money
                    if(playerData.hasEnoughMoney(player, Config.TOWN_CREATION_FEE)) {
                        //Get player's current location
                        Location location = player.getLocation();
                        //Create town at player's location
                        Town town = new Town(townName, player.getUniqueId(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
                        //Add town to the town list
                        townData.addTown(town);
                        player.sendMessage(Messages.townCreated);

                        //Notify every player
                        Bukkit.broadcastMessage(String.format(Messages.townCreatedPublic, player.getName(), townName));

                        //Set town cooldown
                        playerData.setTownCooldown(player, System.currentTimeMillis());
                        playerData.decreaseMoney(player, Config.TOWN_CREATION_FEE);
                    } else {
                        player.sendMessage(String.format(Messages.townNotEnoughMoney, Config.TOWN_CREATION_FEE, Config.CURRENCY));
                    }
                } else {
                    player.sendMessage(Messages.townNameUsed);
                }
            } else {
                player.sendMessage(Messages.townInvalidName);
            }
        } else {
            int canCreateIn = (int) (Config.TOWN_COOLDOWN_SECONDS - (double) (now - lastUsedTimestamp) / 1000d);
            String timeLeft = TimeUtil.secondsToHMS(canCreateIn);
            player.sendMessage(String.format(Messages.townDenied, timeLeft));
        }
    }

    private void deleteTown(Player player, String townName) {
        //Get town from name
        Town town = townData.getTown(townName);

        //Check if the town exists
        if(town != null) {
            //Check if the town owner is self
            if(town.getOwner().equals(player.getUniqueId())) {
                //Remove the town from the list
                townData.removeTown(townName);
                player.sendMessage(Messages.townDeleted);

                //Notify every player
                Bukkit.broadcastMessage(String.format(Messages.townDeletedPublic, townName));
            } else {
                player.sendMessage(Messages.townDeleteAttempt);
            }
        } else {
            player.sendMessage(Messages.townTownInexistant);
        }
    }
}
