package com.vincent64.earthplugin;

import com.vincent64.earthplugin.command.*;
import com.vincent64.earthplugin.earth.MysteryCrate;
import com.vincent64.earthplugin.event.*;
import com.vincent64.earthplugin.file.PlayerData;
import com.vincent64.earthplugin.file.ShopData;
import com.vincent64.earthplugin.file.TownData;
import com.vincent64.earthplugin.file.YamlLoader;
import com.vincent64.earthplugin.log.Log;
import com.vincent64.earthplugin.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EarthPlugin extends JavaPlugin {
    private MysteryCrate mysteryCrate;
    private PlayerData playerData;
    private TownData townData;
    private ShopData shopData;
    private Map map;
    private int autosaveTask;
    private int mysteryCrateTask;

    @Override
    public void onEnable() {
        Log.println("Starting...");
        initializeData();
        initializeMap();
        initializeEvents();
        initializeCommands();
        initializeGamerules();
        initializeTasks();
        Log.println("Starting successfully completed.");
    }

    @Override
    public void onDisable() {
        Log.println("Shutting down...");
        Bukkit.getScheduler().cancelTask(autosaveTask);
        Bukkit.getScheduler().cancelTask(mysteryCrateTask);
        if(mysteryCrate != null) mysteryCrate.remove();
        save();
        Log.println("Shut down completed.");
    }

    private void initializeData() {
        Log.println("Initializing data...");
        //Initialize configuration
        Config.initialize(YamlLoader.loadConfig());
        Log.println("Configuration loaded.");
        //Initialize player data, shop data, etc.
        playerData = new PlayerData(YamlLoader.loadYaml("players"));
        townData = new TownData(YamlLoader.loadYaml("towns"));
        shopData = new ShopData(YamlLoader.loadYaml("shops"));
        //Create crate object
        mysteryCrate = new MysteryCrate(playerData);
        Log.println("Data initialized.");
    }

    private void initializeCommands() {
        Log.println("Initializing commands...");
        //Register commands
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("l").setExecutor(new LocalCommand(playerData));
        getCommand("region").setExecutor(new RegionCommand(playerData));
        getCommand("region").setTabCompleter(new RegionTabCompletion());
        getCommand("money").setExecutor(new MoneyCommand(playerData));
        getCommand("pay").setExecutor(new PayCommand(playerData));
        getCommand("rank").setExecutor(new RankCommand(playerData));
        getCommand("home").setExecutor(new HomeCommand(playerData));
        getCommand("town").setExecutor(new TownCommand(playerData, townData));
        Log.println("Commands initialized.");
    }

    private void initializeEvents() {
        Log.println("Initializing events...");
        //Initialize plugin manager
        PluginManager pluginManager = getServer().getPluginManager();
        //Register events
        pluginManager.registerEvents(new JoinEvent(playerData), this);
        pluginManager.registerEvents(new ChatEvent(playerData, shopData, this), this);
        pluginManager.registerEvents(new PlayerEvent(playerData, shopData, mysteryCrate), this);
        pluginManager.registerEvents(new BlockEvent(playerData, shopData), this);
        pluginManager.registerEvents(new MoveEvent(playerData), this);
        pluginManager.registerEvents(new EntityEvent(), this);
        Log.println("Event initialized.");
    }

    private void initializeTasks() {
        Log.println("Initializing tasks...");
        //Initialize autosave data every 15 minutes
        autosaveTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::save,
                Config.AUTOSAVE_INTERVAL_TICKS, Config.AUTOSAVE_INTERVAL_TICKS);
        //Initialize mystery crates every 2 hours starting in 10 minutes
        mysteryCrateTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, this::newCrate,
                Config.MYSTERY_CRATE_START_TICKS, Config.MYSTERY_CRATE_INTERVAL_TICKS);
        Log.println("Tasks initialized.");
    }

    private void initializeGamerules() {
        Log.println("Initializing gamerules...");
        //Get main world
        World world = Bukkit.getWorld(Config.MAIN_WORLD_NAME);
        //Set gamerules
        world.setGameRule(GameRule.PROJECTILES_CAN_BREAK_BLOCKS, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        Log.println("Gamerules initialized.");
    }

    private void initializeMap() {
        Log.println("Initializing map...");
        map = new Map(townData);
        map.updateTownMarkers(townData);
        Log.println("Map initialized.");
    }

    private void save() {
        Log.println("Saving server data...");
        playerData.save();
        shopData.save();
        townData.save();
        map.updateTownMarkers(townData);
        Log.println("Server data saved.");
    }

    private void newCrate() {
        //Remove the previous crate if there was one
        mysteryCrate.remove();
        //Create a new mystery crate
        MysteryCrate.create(mysteryCrate);
    }
}
