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
public class RetrieveCmd extends GenericCmd {
    
    private boolean confirmed;
    
    public RetrieveCmd(CommandSender cs, String args[], boolean confirmed){
        super(cs, args);
        this.permission = Properties.permHold;
        bank = XPBank.getHold();
        this.confirmed = confirmed;
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        int xp;
        String name = player.getName().toLowerCase();
        String msg = "";
        
        if(bank.hasBalance(name)){
            xp = bank.getBalance(name);
            
            if((Utilities.getTotalExp(player) > 0) && !confirmed) {
                Messaging.send(player, "`RWarning: `rRetrieving experience from temporary"); 
                Messaging.send(player, "`rholding will overwrite the XP you are currently holding.");
                Messaging.send(player, "`rType `w/xp confirm `rto confirm the retrieval.");
                XPBank.getConfirm().put(player, TransactionType.RETRIEVE);
                return true;
            }
            
            Utilities.resetExp(player);
            player.giveExp(xp);
            
            bank.remove(name);
            msg = "`pYou have retrieved `w" + xp + " `pexperience.";
            
            if(Properties.logTransactions){
                XPBankTransaction event = new XPBankTransaction(TransactionType.RETRIEVE, player, xp, bank.getBalance(name));
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        } else {
            msg = "`rYou have not placed any experience in the temp bank.";
        }
        
        Messaging.send(player, msg);
        return true;
    }
}
