package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.earth.Region;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.util.Messages;
import com.vincent64.earthplugin.util.RandomUtil;
import com.vincent64.earthplugin.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionCommand implements CommandExecutor {
    private PlayerData playerData;

    public RegionCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            //Check if the command contains a destination
            if(args.length == 1) {
                //Get the last time the player used the region command
                long lastUsedTimestamp = playerData.getRegionCooldown(player);
                long now = System.currentTimeMillis();

                //Check if the player can reuse it now
                if(now - lastUsedTimestamp >= Config.REGION_COOLDOWN_SECONDS * 1000L) {
                    //Get region id
                    String regionId = args[0];
                    //Get region from region id
                    Region region = Region.getRegion(regionId);

                    //Make sure that the region is valid
                    if(region != null) {
                        //Get main world
                        World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
                        //Create region location with random radius
                        int x = region.getX() + RandomUtil.getRandomInt(-Config.REGION_RANDOM_RADIUS, Config.REGION_RANDOM_RADIUS);
                        int z = region.getZ() + RandomUtil.getRandomInt(-Config.REGION_RANDOM_RADIUS, Config.REGION_RANDOM_RADIUS);
                        //Get the highest point at coordinates
                        int y = world.getHighestBlockYAt(x, z) + 1;

                        //Check if the coordinates are good
                        boolean isGoodCoordinates = false;
                        while(!isGoodCoordinates) {
                            //Get the block at these coordinates
                            Material blockBelow = world.getBlockAt(x, y - 1, z).getType();

                            //Check if is no water below
                            if(blockBelow != Material.WATER) {
                                isGoodCoordinates = true;
                            } else {
                                //Regenerate coordinates if they are not good
                                x = region.getX() + RandomUtil.getRandomInt(-Config.REGION_RANDOM_RADIUS, Config.REGION_RANDOM_RADIUS);
                                z = region.getZ() + RandomUtil.getRandomInt(-Config.REGION_RANDOM_RADIUS, Config.REGION_RANDOM_RADIUS);
                                y = world.getHighestBlockYAt(x, z) + 1;
                            }
                        }

                        //Create region location
                        Location location = new Location(world, x, y, z);

                        //Teleport player to location
                        player.teleport(location);
                        player.sendMessage(String.format(Messages.regionTeleported, region.getName()));

                        //Set cooldown timestamp
                        playerData.setRegionCooldown(player, System.currentTimeMillis());
                    } else {
                        player.sendMessage(Messages.regionInvalidCommand);
                    }
                } else {
                    int canReuseIn = (int) (Config.REGION_COOLDOWN_SECONDS - (double) (now - lastUsedTimestamp) / 1000d);
                    String timeLeft = TimeUtil.secondsToHMS(canReuseIn);
                    player.sendMessage(String.format(Messages.regionDenied, timeLeft));
                }
            } else {
                player.sendMessage(Messages.regionInvalidCommand);
            }
        }

        return true;
    }
}
