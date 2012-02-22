/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Events.XPBankTransaction;
import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
import com.noheroes.xpbank.Properties.TransactionType;
import com.noheroes.xpbank.Utilities;
import com.noheroes.xpbank.XPBank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class StoreCmd extends GenericCmd {
    
    private boolean confirmed;
    
    public StoreCmd(CommandSender cs, String args[], boolean confirmed){
        super(cs, args);
        this.confirmed = confirmed;
        this.permission = Properties.permHold;
        bank = XPBank.getHold();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        //int xp = player.getTotalExperience();
        int xp = Utilities.getTotalExp(player);
        String name = player.getName().toLowerCase();
        String msg;
        
        if((bank.getBalance(name) > 0) && !confirmed) {
            Messaging.send(player, "`RWarning: `rYou already have stored experience. Storing"); 
            Messaging.send(player, "`ragain will overwrite that experience with your current total.");
            Messaging.send(player, "`rType `w/xp confirm `rto confirm.");
            XPBank.getConfirm().put(player, TransactionType.HOLD);
            return true;
        }
        
        //Add it to the bank.
        bank.setBalance(name, xp);
        int storedXP = bank.getBalance(name);

        if(storedXP == xp) {
            Utilities.resetExp(player);
            msg = "`pYou have stored `w" + storedXP + " `pexperience.";
        } else
            msg = "`rError.  Hold balance (" + storedXP + ") doesn't equal your exp (" + xp + ").";

        Messaging.send(player, msg);
        
        if(Properties.logTransactions){
            XPBankTransaction event = new XPBankTransaction(TransactionType.HOLD, player, storedXP, bank.getBalance(name));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        
        return true;
    }
}
