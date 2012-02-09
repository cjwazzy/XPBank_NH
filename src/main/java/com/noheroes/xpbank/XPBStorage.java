/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.mini.Arguments;
import com.mini.Mini;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBStorage {
    private XPBank xpb;
    private Mini miniXP = null;
    private File miniFile = null;
    private String fileName;
    
    public XPBStorage (String fileName) {
        this.xpb = XPBank.get();
        this.fileName = fileName;
    }
    
    public void initStorage() throws IOException {
        miniFile = new File(xpb.getDataFolder(), fileName);

        if(!miniFile.exists()){
            miniFile.createNewFile();
        }

        miniXP = new Mini(miniFile.getParent(), miniFile.getName());
    }
    
    public int getBalance(String player) {
        int xp = 0;
        try {
            xp = balance(player);
        } catch (NumberFormatException nfe){
            XPBank.log("NFE on getbalance");
            nfe.printStackTrace();
        }
        return xp;
    }
    
    public boolean hasBalance(String player) {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return false;
        }
        
        return miniXP.hasIndex(player.toLowerCase());
    }
    
    public void setBalance(String player, Integer xp) {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return;
        }
        
        Arguments entry = new Arguments(player);
        entry.setValue("xp", xp.toString());
        miniXP.addIndex(entry.getKey(), entry);
        miniXP.update();
    }
    
    public void add(String player, Integer xp) {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return;
        }
        
        int currentXP;
        
        try {
            currentXP = balance(player);
        } catch (NumberFormatException nfe){
            XPBank.log("NFE on add");
            nfe.printStackTrace();
            return;
        }
        
        currentXP += xp;
        setBalance(player, currentXP);
    }
    
    public void subtract(String player, Integer xp) {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return;
        }
        
        int currentXP;
        
        try {
            currentXP = balance(player);
        } catch (NumberFormatException nfe){
            XPBank.log("NFE on add");
            nfe.printStackTrace();
            return;
        }
        
        if(xp > currentXP)
            currentXP = 0;
        else
            currentXP -= xp;
        setBalance(player, currentXP);
    }
    
    public void remove(String player) {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return;
        }
        
        if(hasBalance(player))
            miniXP.removeIndex(player.toLowerCase());
    }
    
    private int balance(String player) throws NumberFormatException {
        if(miniXP == null){
           XPBank.log("Could not attach to mini db.");
           return 0;
        }
        
        Arguments entry = null;
        if(miniXP.hasIndex(player))
            entry = miniXP.getArguments(player);
        
        return (entry != null) ? entry.getInteger("xp") : 0;
    }
}
