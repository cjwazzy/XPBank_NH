/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import org.bukkit.entity.Player;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class Utilities {
    
    public static void resetExp(Player player){
        if(player == null)
            return;
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
    }
}
