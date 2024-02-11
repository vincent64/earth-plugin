package com.vincent64.earthplugin;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static String SERVER_NAME = "Earth Server";
    public static String MAIN_WORLD_NAME = "world";
    public static int MAIN_WORLD_WIDTH = 30_719;
    public static int MAIN_WORLD_LENGTH = 15_871;
    public static int MAIN_WORLD_MAX_X = 30_719;
    public static int MAIN_WORLD_MIN_X = 0;
    public static int MAIN_WORLD_MAX_Z = 15_871;
    public static int MAIN_WORLD_MIN_Z = 0;
    public static int AUTOSAVE_INTERVAL_TICKS = 18_000;
    public static String CURRENCY = "$";
    public static double PLAYER_STARTING_MONEY = 100d;
    public static double PLAYER_DAILY_MONEY = 25d;
    public static int PLAYER_HOME_LIMIT = 4;
    public static int REGION_RANDOM_RADIUS = 4096;
    public static int REGION_COOLDOWN_SECONDS = 21_600;
    public static int TOWN_COOLDOWN_SECONDS = 172_800;
    public static double TOWN_CREATION_FEE = 40d;
    public static double MYSTERY_CRATE_PRIZE_MONEY = 20d;
    public static int MYSTERY_CRATE_INTERVAL_TICKS = 144_000;
    public static int MYSTERY_CRATE_START_TICKS = 12_000;
    public static boolean DISABLE_EXPLOSION = true;
    public static boolean DISABLE_ADVANCEMENT_MESSAGE = false;

    public static void initialize(FileConfiguration configuration) {
        SERVER_NAME = configuration.getString("SERVER_NAME", SERVER_NAME);
        MAIN_WORLD_NAME = configuration.getString("MAIN_WORLD_NAME", MAIN_WORLD_NAME);
        MAIN_WORLD_WIDTH = configuration.getInt("MAIN_WORLD_WIDTH", MAIN_WORLD_WIDTH);
        MAIN_WORLD_LENGTH = configuration.getInt("MAIN_WORLD_LENGTH", MAIN_WORLD_LENGTH);
        MAIN_WORLD_MAX_X = configuration.getInt("MAIN_WORLD_MAX_X", MAIN_WORLD_MAX_X);
        MAIN_WORLD_MIN_X = configuration.getInt("MAIN_WORLD_MIN_X", MAIN_WORLD_MIN_X);
        MAIN_WORLD_MAX_Z = configuration.getInt("MAIN_WORLD_MAX_Z", MAIN_WORLD_MAX_Z);
        MAIN_WORLD_MIN_Z = configuration.getInt("MAIN_WORLD_MIN_Z", MAIN_WORLD_MIN_Z);
        AUTOSAVE_INTERVAL_TICKS = configuration.getInt("AUTOSAVE_INTERVAL_TICKS", AUTOSAVE_INTERVAL_TICKS);
        CURRENCY = configuration.getString("CURRENCY", CURRENCY);
        PLAYER_STARTING_MONEY = configuration.getDouble("PLAYER_STARTING_MONEY", PLAYER_STARTING_MONEY);
        PLAYER_HOME_LIMIT = configuration.getInt("PLAYER_HOME_LIMIT", PLAYER_HOME_LIMIT);
        REGION_RANDOM_RADIUS = configuration.getInt("REGION_RANDOM_RADIUS", REGION_RANDOM_RADIUS);
        REGION_COOLDOWN_SECONDS = configuration.getInt("REGION_COOLDOWN_SECONDS", REGION_COOLDOWN_SECONDS);
        TOWN_COOLDOWN_SECONDS = configuration.getInt("TOWN_COOLDOWN_SECONDS", TOWN_COOLDOWN_SECONDS);
        TOWN_CREATION_FEE = configuration.getDouble("TOWN_CREATION_FEE", TOWN_CREATION_FEE);
        MYSTERY_CRATE_PRIZE_MONEY = configuration.getDouble("MYSTERY_CRATE_PRIZE_MONEY", MYSTERY_CRATE_PRIZE_MONEY);
        MYSTERY_CRATE_INTERVAL_TICKS = configuration.getInt("MYSTERY_CRATE_INTERVAL_TICKS", MYSTERY_CRATE_INTERVAL_TICKS);
        MYSTERY_CRATE_START_TICKS = configuration.getInt("MYSTERY_CRATE_START_TICKS", MYSTERY_CRATE_START_TICKS);
        DISABLE_EXPLOSION = configuration.getBoolean("DISABLE_EXPLOSION", DISABLE_EXPLOSION);
        DISABLE_ADVANCEMENT_MESSAGE = configuration.getBoolean("DISABLE_ADVANCEMENT_MESSAGE", DISABLE_ADVANCEMENT_MESSAGE);
    }
}
