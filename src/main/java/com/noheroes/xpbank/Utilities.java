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
    
    public static int getTotalExp(Player player) {
        if (player == null)
            return 0;
        
        int level = player.getLevel();
        if (level >= Properties.MAX_LEVEL) {
            XPBank.log("Level overflow.  Player " + player.getName() + " is level " + level + " while max supported is " + Properties.MAX_LEVEL);
            return 0;
        }
        int currentLevelProgress = Math.round(player.getExp() * (Properties.expTable[level + 1] - Properties.expTable[level])); // Amount of exp earned in current level
        
        return Properties.expTable[level] + currentLevelProgress;
    }
}
