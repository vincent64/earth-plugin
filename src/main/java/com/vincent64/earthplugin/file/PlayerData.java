package com.vincent64.earthplugin.file;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.earth.Chair;
import com.vincent64.earthplugin.earth.Rank;
import com.vincent64.earthplugin.home.Home;
import com.vincent64.earthplugin.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerData {
    private FileConfiguration configuration;
    private StatData statData;
    private HashMap<UUID, Block> currentTransactor;
    private List<Chair> currentChairs;

    public PlayerData(FileConfiguration configuration) {
        this.configuration = configuration;

        //Load players statistics
        statData = new StatData(YamlLoader.loadYaml("players_statistics"));
        //Initialize transactor players list
        currentTransactor = new HashMap<>();
        //Initialize chair list
        currentChairs = new ArrayList<>();
    }

    public void save() {
        YamlLoader.saveYaml(configuration, "players");
        statData.save();

        //Check statistics for every player online
        for(Player player : Bukkit.getOnlinePlayers()) {
            checkStatistics(player);
        }
    }

    public void createData(Player player) {
        //Initialize player data
        setRank(player, Rank.SURVIVOR);
        addUnlockedRank(player, Rank.SURVIVOR);
        setMoney(player, Config.PLAYER_STARTING_MONEY);
        setLastDailyPay(player, System.currentTimeMillis());
        setRegionCooldown(player, 0);
        setTownCooldown(player, 0);
        statData.createData(player);
    }

    public void checkStatistics(Player player) {
        //Get player's statistics
        double totalDistanceWalked = getStatistics().getDistanceWalked(player);
        int totalBlocksPlaced = getStatistics().getBlocksPlaced(player);
        int totalMessagesSent = getStatistics().getMessagesSent(player);

        //Loop through each rank
        for(Rank rank : Rank.values()) {
            int level = rank.getRequirement().getLevel();

            switch(rank.getRequirement().getType()) {
                case DISTANCE_WALKED:
                    if(totalDistanceWalked >= level && !hasUnlockedRank(player, rank)) {
                        //Set rank as unlocked for the player
                        addUnlockedRank(player, rank);
                        player.sendMessage(String.format(Messages.rankUnlocked, player.getName(), rank.getName()));
                    }
                    break;
                case BLOCK_PLACED:
                    if(totalBlocksPlaced >= level && !hasUnlockedRank(player, rank)) {
                        //Set rank as unlocked for the player
                        addUnlockedRank(player, rank);
                        player.sendMessage(String.format(Messages.rankUnlocked, player.getName(), rank.getName()));
                    }
                    break;
                case MESSAGES_SENT:
                    if(totalMessagesSent >= level && !hasUnlockedRank(player, rank)) {
                        //Set rank as unlocked for the player
                        addUnlockedRank(player, rank);
                        player.sendMessage(String.format(Messages.rankUnlocked, player.getName(), rank.getName()));
                    }
                    break;
            }
        }
    }

    public boolean hasData(Player player) {
        return configuration.isSet(player.getUniqueId().toString());
    }

    public void setRank(Player player, Rank rank) {
        configuration.set(player.getUniqueId() + ".rank", rank.getId());
    }

    public Rank getRank(Player player) {
        return Rank.getRank(configuration.getString(player.getUniqueId() + ".rank"));
    }

    public void setMoney(Player player, double amount) {
        configuration.set(player.getUniqueId() + ".money", amount);
    }

    public double getMoney(Player player) {
        return configuration.getDouble(player.getUniqueId() + ".money");
    }

    public boolean hasEnoughMoney(Player player, double amount) {
        return getMoney(player) >= amount;
    }

    public void increaseMoney(Player player, double amount) {
        setMoney(player, getMoney(player) + amount);
    }

    public void decreaseMoney(Player player, double amount) {
        increaseMoney(player, -amount);
    }

    public void setLastDailyPay(Player player, long timestamp) {
        configuration.set(player.getUniqueId() + ".lastDailyPay", timestamp);
    }

    public long getLastDailyPay(Player player) {
        return configuration.getLong(player.getUniqueId() + ".lastDailyPay");
    }

    public List<Home> getHomes(Player player) {
        //Get player's home as a list of string
        List<String> homeList = configuration.getStringList(player.getUniqueId() + ".homes");
        //Create list of homes
        List<Home> homes = new ArrayList<>();

        //Loop through each element of the list
        for(String home : homeList) {
            String[] homeComponents = home.split(",");
            String homeName = homeComponents[0];
            int x = Integer.parseInt(homeComponents[1]);
            int y = Integer.parseInt(homeComponents[2]);
            int z = Integer.parseInt(homeComponents[3]);

            //Create home object
            Home shop = new Home(homeName, x, y, z);
            //Add the home to the list
            homes.add(shop);
        }

        return homes;
    }

    public Home getHome(Player player, String name) {
        //Check through the list of homes
        for(Home home : getHomes(player)) {
            if(home.getName().equals(name)) {
                return home;
            }
        }

        return null;
    }

    public int getHomeAmount(Player player) {
        return getHomes(player).size();
    }

    public void addHome(Player player, Home home) {
        //Get list of player's homes
        List<Home> homes = getHomes(player);
        //Add new home to it
        homes.add(home);

        //Create list of home string
        List<String> homesString = new ArrayList<>();
        //Add every home to string list
        for(Home homeElement : homes) {
            homesString.add(homeElement.toString());
        }

        //Save back home list
        configuration.set(player.getUniqueId() + ".homes", homesString);
    }

    public void removeHome(Player player, String name) {
        //Get list of player's homes
        List<Home> homes = getHomes(player);

        //Check through the list of homes
        for(Home home : homes) {
            if(home.getName().equals(name)) {
                //Remove the home from the list
                homes.remove(home);
                break;
            }
        }

        //Create list of home string
        List<String> homesString = new ArrayList<>();
        //Add every home to string list
        for(Home homeElement : homes) {
            homesString.add(homeElement.toString());
        }

        //Save back home list
        configuration.set(player.getUniqueId() + ".homes", homesString);
    }

    public List<String> getUnlockedRanks(Player player) {
        //Get player's ranks as a list of string
        return configuration.getStringList(player.getUniqueId() + ".unlockedRanks");
    }

    public void addUnlockedRank(Player player, Rank rank) {
        //Get unlocked ranks list
        List<String> unlockedRanks = getUnlockedRanks(player);
        //Add the new rank
        unlockedRanks.add(rank.getId());
        configuration.set(player.getUniqueId() + ".unlockedRanks", unlockedRanks);
    }

    public boolean hasUnlockedRank(Player player, Rank rank) {
        return getUnlockedRanks(player).contains(rank.getId());
    }

    public void setRegionCooldown(Player player, long timestamp) {
        configuration.set(player.getUniqueId() + ".regionCooldown", timestamp);
    }

    public long getRegionCooldown(Player player) {
        return configuration.getLong(player.getUniqueId() + ".regionCooldown");
    }

    public void setTownCooldown(Player player, long timestamp) {
        configuration.set(player.getUniqueId() + ".townCooldown", timestamp);
    }

    public long getTownCooldown(Player player) {
        return configuration.getLong(player.getUniqueId() + ".townCooldown");
    }

    public StatData getStatistics() {
        return statData;
    }

    public void addTransactor(Player player, Block block) {
        currentTransactor.put(player.getUniqueId(), block);
    }

    public void removeTransactor(Player player) {
        currentTransactor.remove(player.getUniqueId());
    }

    public Block getTransactorChest(Player player) {
        return currentTransactor.get(player.getUniqueId());
    }

    public HashMap<UUID, Block> getCurrentTransactor() {
        return currentTransactor;
    }

    public boolean isCurrentTransactor(Player player) {
        return currentTransactor.containsKey(player.getUniqueId());
    }

    public void addChair(Player player, Arrow arrow, Block block) {
        currentChairs.add(new Chair(player.getUniqueId(), arrow, block));
    }

    public void removeChairByBlock(Block block) {
        for(Chair chair : currentChairs) {
            Block chairBlock = chair.getChair();
            if(chairBlock.getX() == block.getX() &&
                    chairBlock.getY() == block.getY() &&
                    chairBlock.getZ() == block.getZ()) {
                //Destroy the chair and remove it
                chair.destroyChair();
                currentChairs.remove(chair);
                break;
            }
        }
    }

    public void removeChairByPlayer(Player player) {
        for(Chair chair : currentChairs) {
            if(chair.getSitter().equals(player.getUniqueId())) {
                //Destroy the chair and remove it
                chair.destroyChair();
                currentChairs.remove(chair);
                break;
            }
        }
    }

    public boolean isBlockChair(Block block) {
        for(Chair chair : currentChairs) {
            Block chairBlock = chair.getChair();
            if(chairBlock.getX() == block.getX() &&
                    chairBlock.getY() == block.getY() &&
                    chairBlock.getZ() == block.getZ()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerOnChair(Player player) {
        for(Chair chair : currentChairs) {
            if(chair.getSitter().equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }
}
