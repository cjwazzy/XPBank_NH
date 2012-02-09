/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
import com.noheroes.xpbank.Utilities;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class StoreCmd extends GenericCmd {
    
    public StoreCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permHold;
        bank = XPBank.getHold();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        int xp = player.getTotalExperience();
        String name = player.getName().toLowerCase();
        String msg;
        
        //Add it to the hold.
        bank.setBalance(name, xp);
        int storedXP = bank.getBalance(name);

        if(storedXP == xp) {
            Utilities.resetExp(player);
            msg = "`GYou have stored `y" + storedXP + " `Gexperience.";
        } else
            msg = "`rError.  Hold balance (" + storedXP + ") doesn't equal your exp (" + xp + ").";

        Messaging.send(player, msg);
        
        return true;
    }
}
