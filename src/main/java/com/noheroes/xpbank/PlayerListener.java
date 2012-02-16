/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class PlayerListener implements Listener {
    
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event){
        XPBank.getConfirm().remove(event.getPlayer());
    }
}
