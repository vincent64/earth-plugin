package com.vincent64.earthplugin.command;

import com.vincent64.earthplugin.earth.Region;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RegionTabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        //Create position list
        List<String> propositions = new ArrayList<>();
        //Add every region ids
        for(Region region : Region.values()) {
            propositions.add(region.getId());
        }
        return propositions;
    }
}
