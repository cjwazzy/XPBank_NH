/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Properties;
import com.noheroes.xpbank.Utilities;
import com.noheroes.xpbank.XPBStorage;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class RetrieveCmd extends GenericCmd {
    
    private XPBStorage hold;
    
    public RetrieveCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permHold;
        hold = XPBank.getHold();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        int xp;
        String name = player.getName().toLowerCase();
        String msg = "";
        
        if(hold.hasBalance(name)){
            xp = hold.getBalance(name);
            
            Utilities.resetExp(player);
            player.giveExp(xp);
            
            hold.remove(name);
            msg = "`YYou have retrieved `G" + xp + " `Yexperience.";
        } else {
            msg = "`rYou have not placed any experience in the temp bank.";
        }
        
        return true;
    }
}
