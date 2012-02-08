/*
 * Copyright (C) 2011 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBank extends JavaPlugin {

    private static XPBank xpb;
    
    public void onDisable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEnable() {
        XPBank.xpb = this;
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static XPBank get(){
        return xpb;
    }
    
    public static boolean hasPermission(CommandSender cs, String perm){
        return (cs.hasPermission(perm) || cs.hasPermission(Properties.permAdmin));
    }
}
