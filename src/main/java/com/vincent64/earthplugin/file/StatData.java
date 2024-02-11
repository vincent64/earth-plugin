package com.vincent64.earthplugin.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StatData {
    private FileConfiguration configuration;

    public StatData(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public void save() {
        YamlLoader.saveYaml(configuration, "players_statistics");
    }

    public void createData(Player player) {
        setDistanceWalked(player, 0d);
        setBlocksPlaced(player, 0);
        setMessagesSent(player, 0);
    }

    private void setDistanceWalked(Player player, double distance) {
        configuration.set(player.getUniqueId() + ".statDistanceWalked", distance);
    }

    private void setBlocksPlaced(Player player, int amount) {
        configuration.set(player.getUniqueId() + ".statBlocksPlaced", amount);
    }

    private void setMessagesSent(Player player, int amount) {
        configuration.set(player.getUniqueId() + ".statMessagesSent", amount);
    }

    public void increaseDistanceWalked(Player player, double distance) {
        configuration.set(player.getUniqueId() + ".statDistanceWalked", getDistanceWalked(player) + distance);
    }

    public double getDistanceWalked(Player player) {
        return configuration.getDouble(player.getUniqueId() + ".statDistanceWalked");
    }

    public void increaseBlocksPlaced(Player player, int amount) {
        configuration.set(player.getUniqueId() + ".statBlocksPlaced", getMessagesSent(player) + amount);
    }

    public int getBlocksPlaced(Player player) {
        return configuration.getInt(player.getUniqueId() + ".statBlocksPlaced");
    }

    public void increaseMessagesSent(Player player, int amount) {
        configuration.set(player.getUniqueId() + ".statMessagesSent", getMessagesSent(player) + amount);
    }

    public int getMessagesSent(Player player) {
        return configuration.getInt(player.getUniqueId() + ".statMessagesSent");
    }
}
