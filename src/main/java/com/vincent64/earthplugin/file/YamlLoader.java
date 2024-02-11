package com.vincent64.earthplugin.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlLoader {
    public static final String PATH = "plugins/EarthPluginData/";
    public static final String CONFIG_PATH = "plugins/";

    public static FileConfiguration loadYaml(String name) {
        //Load the plugin's data folder first
        loadFolder();

        //Load yaml file
        File file = new File(PATH + name + ".yml");
        //Create the yaml configuration
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        //Check if the file already exist or create it
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return configuration;
    }

    public static FileConfiguration loadConfig() {
        //Load the plugin's data folder first
        loadFolder();

        //Load yaml file
        File file = new File(CONFIG_PATH + "earthplugin-config.yml");
        //Create the yaml configuration
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        return configuration;
    }

    public static void saveYaml(FileConfiguration configuration, String name) {
        try {
            configuration.save(new File(PATH + name + ".yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadFolder() {
        //Get folder to yaml
        File folder = new File(PATH);
        //Create folder if it doesn't exist
        if(!folder.exists()) {
            try {
                folder.mkdirs();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
