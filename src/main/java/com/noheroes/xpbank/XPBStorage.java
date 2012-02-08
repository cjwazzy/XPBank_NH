/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.mini.Mini;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBStorage {
    private XPBank xpb;
    private Mini miniXP;
    private File miniFile;
    
    public XPBStorage () {
        this.xpb = XPBank.get();
    }
    
    public void initStorage(String fileName) {
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
    }
    
    public int getBalance(String player){
        return 0;
    }
    
    public void setBalance(String player, Integer xp){
        
    }
    
    public void add(String player, Integer xp){
        
    }
    
    public void subtract(String player, Integer xp){
        
    }
    
    public void remove(String player){
        
    }
}
