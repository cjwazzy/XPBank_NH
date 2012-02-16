/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Interfaces.Cmd;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
import com.noheroes.xpbank.Properties.TransactionType;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class ConfirmCMD extends GenericCmd {
    
    public ConfirmCMD(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permHold;
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        Cmd command = null;
        
        if(XPBank.getConfirm().containsKey(player)){
            if(XPBank.getConfirm().get(player).equals(TransactionType.HOLD))
                command = new StoreCmd(cs, args, true);
            else if(XPBank.getConfirm().get(player).equals(TransactionType.RETRIEVE))
                command = new RetrieveCmd(cs, args, true);
        }
        
        if(command != null)
            command.execute();
        else
            Messaging.send(player, "`pNothing to confirm.");
            
        XPBank.getConfirm().remove(player); //just remove if possible.
        return true;
    }
}
