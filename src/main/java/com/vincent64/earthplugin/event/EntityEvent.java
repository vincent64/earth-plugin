package com.vincent64.earthplugin.event;

import com.vincent64.earthplugin.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityEvent implements Listener {
    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        //Disable every explosions
        event.setCancelled(Config.DISABLE_EXPLOSION);
    }
}
