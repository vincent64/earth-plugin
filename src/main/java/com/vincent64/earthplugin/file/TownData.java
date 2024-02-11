package com.vincent64.earthplugin.file;

import com.vincent64.earthplugin.log.Log;
import com.vincent64.earthplugin.town.Town;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TownData {
    private FileConfiguration configuration;
    private List<Town> townData;

    public TownData(FileConfiguration configuration) {
        this.configuration = configuration;

        //Get towns data
        townData = getTowns();
    }

    public void save() {
        //Initialize string list of shops
        List<String> townString = new ArrayList<>();

        //Loop through each shop
        for(Town town : townData) {
            townString.add(town.toString());
        }

        //Save back into file
        configuration.set("towns", townString);
        YamlLoader.saveYaml(configuration, "towns");
    }

    private List<Town> getTowns() {
        //Get towns as a list of string
        List<String> townList = configuration.getStringList("towns");
        //Create list of towns
        List<Town> towns = new ArrayList<>();

        //Loop through each element of the list
        for(String townString : townList) {
            //Get the components of the element
            String[] components = townString.split(",");
            String name = components[0];
            UUID owner = UUID.fromString(components[1]);
            int x = Integer.parseInt(components[2]);
            int y = Integer.parseInt(components[3]);
            int z = Integer.parseInt(components[4]);

            //Create town object
            Town town = new Town(name, owner, x, y, z);
            //Add the town to the list
            towns.add(town);
        }

        return towns;
    }

    public List<Town> getTownData() {
        return townData;
    }

    public Town getTown(String name) {
        //Check through the list of towns
        for(Town town : townData) {
            if(town.getName().equals(name)) {
                return town;
            }
        }

        return null;
    }

    public void addTown(Town town) {
        //Add shop to the list
        townData.add(town);
        Log.println("Town added: " + town.toString());
    }

    public void removeTown(String name) {
        //Get town at coordinates
        Town town = getTown(name);
        //Remove it from the list
        townData.remove(town);
        Log.println("Town removed: " + town.toString());
    }
}
