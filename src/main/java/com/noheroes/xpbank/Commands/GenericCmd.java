/*
 * Copyright (C) 2011 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Interfaces.Cmd;
import com.noheroes.xpbank.XPBStorage;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Sorklin <sorklin at gmail.com>
 */
abstract class GenericCmd implements Cmd {
    
    CommandSender cs; //Always populated
    String[] args; //Original Args from CommandListener
    Player player; //Only populated if cs is a player.  Only throws an error if mustBeAPlayer is true.
    XPBStorage bank = null;
    
    //Default the generic to must be executed by a player, and no minimum arguments.
    String permission = "";
    boolean mustBePlayer = true;
    int minArg = 0;
    
    XPBank xpb;
    
    public GenericCmd(CommandSender cs, String args[]){
        this.cs = cs;
        this.args = args;
        this.xpb = XPBank.get();
    }
    
    protected boolean errorCheck() 
            throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        
        //Try to cast it and only throw a problem if command must be executed as player.
        try {
            this.player = (Player)cs;
        } catch (Exception ex) {
            if(mustBePlayer){
                cs.sendMessage("This command must be executed as a player.");
                return true;
            }
        }
        
        if(!XPBank.hasPermission(cs, permission))
            throw new InsufficientPermissionException();
        
        if(args.length < minArg){
            throw new MissingOrIncorrectArgumentException();
        }
        
        return false;
    }
}
