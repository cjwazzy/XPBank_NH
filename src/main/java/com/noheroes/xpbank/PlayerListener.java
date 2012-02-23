/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class PlayerListener implements Listener {
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDisconnect(PlayerQuitEvent event){
        XPBank.getConfirm().remove(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerConnect (PlayerJoinEvent event){
        XPBank.log("Player " + event.getPlayer().getName() + 
                " joined with " + Utilities.getTotalExp(event.getPlayer())
                + "xp (" + event.getPlayer().getTotalExperience() + " bukkit totalxp)");
    }
}
