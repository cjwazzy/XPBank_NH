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
    
    public XPBStorage () {
        this.xpb = XPBank.get();
    }
    
    public boolean initStorage(String fileName) {
        try {
            miniFile = new File(xpb.getDataFolder(), fileName);
            
            if(!miniFile.exists()){
                miniFile.createNewFile();
            }
        
            miniXP = new Mini(miniFile.getParent(), miniFile.getName());
        } catch (IOException ioe) {
            XPBank.log("Could not connect to " + fileName + ".");
            ioe.printStackTrace();
        }
        return (miniXP != null);
    }
    
    public int getBalance(String player){
        int xp = 0;
        try {
            xp = balance(player.toLowerCase());
        } catch (NumberFormatException nfe){
            XPBank.log("NFE on getbalance");
            nfe.printStackTrace();
        }
        return xp;
    }
    
    public void setBalance(String player, Integer xp){
        Arguments entry = new Arguments(player.toLowerCase());
        entry.setValue("xp", xp.toString());
        miniXP.addIndex(entry.getKey(), entry);
        miniXP.update();
    }
    
    public void add(String player, Integer xp){
        int currentXP;
        
        try {
            currentXP = balance(player.toLowerCase());
        } catch (NumberFormatException nfe){
            XPBank.log("NFE on add");
            nfe.printStackTrace();
            return;
        }
        
        currentXP += xp;
        setBalance(player, currentXP);
    }
    
    public void subtract(String player, Integer xp){
        int currentXP;
        
        try {
            currentXP = balance(player.toLowerCase());
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
    
    public void remove(String player){
        if(miniXP.hasIndex(player.toLowerCase()))
            miniXP.removeIndex(player.toLowerCase());
    }
    
    private int balance(String player) throws NumberFormatException {
        //Arguments entry = null;
        return (miniXP.hasIndex(player)) ?  miniXP.getArguments(player).getInteger(player) : 0;
//        if(miniXP.hasIndex(player))
//            entry = miniXP.getArguments(player);
//        return entry.getInteger(player);
    }
}
