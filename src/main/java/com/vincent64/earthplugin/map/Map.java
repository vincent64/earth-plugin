package com.vincent64.earthplugin.map;

import com.vincent64.earthplugin.Config;
import com.vincent64.earthplugin.file.TownData;
import com.vincent64.earthplugin.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import java.util.List;

public class Map {
    private DynmapCommonAPI API;
    private boolean isEnabled;

    public Map(TownData townData) {
        //Initialize the dynmap API
        API = (DynmapCommonAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        isEnabled = API != null;

        if(isEnabled) {
            //Load towns on map
            updateTownMarkers(townData);
        }
    }

    public void updateTownMarkers(TownData townData) {
        if(isEnabled) {
            //Get marker API
            MarkerAPI markerAPI = API.getMarkerAPI();
            markerAPI.getMarkerSets().clear();

            //Get the list of towns
            List<Town> towns = townData.getTownData();

            //Initialize marker set
            markerAPI.createMarkerSet("towns", "Towns", null, false);
            MarkerIcon markerIcon = markerAPI.getMarkerIcon("greenflag");
            MarkerSet townMarkerSet = markerAPI.getMarkerSet("towns");

            //Loop through every town
            int i = 0;
            for(Town town : towns) {
                townMarkerSet.createMarker("towns.town" + i++, town.getName(), true,
                        Config.MAIN_WORLD_NAME, town.getX(), town.getY(), town.getZ(), markerIcon, false);
            }
        }
    }

    public void hidePlayer(Player player) {
        API.setPlayerVisiblity(player.getName(), false);
    }

    public void showPlayer(Player player) {
        API.setPlayerVisiblity(player.getName(), true);
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}