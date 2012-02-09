/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class BalanceCmd extends GenericCmd {
    
    public BalanceCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permBank;
        bank = XPBank.getBank();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        Messaging.send(player, "`GYou have `Y" 
                + bank.getBalance(player.getName()) + " `Gstored experience.");
        return true;
    }
}
