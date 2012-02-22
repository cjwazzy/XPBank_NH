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
import java.util.ArrayList;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class InfoCmd extends GenericCmd {
    
    public InfoCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permUse;
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        String name = player.getName().toLowerCase();
        //int playerXP = player.getTotalExperience();
        int playerXP = Utilities.getTotalExp(player);
        int bankXP = XPBank.getBank().getBalance(name);
        int holdXP = XPBank.getHold().getBalance(name);
        
        ArrayList<String> msgs = new ArrayList<String>();
        msgs.add("`pYou have `w" + playerXP + "`p XP.");
        
        if(player.hasPermission(Properties.permBank)){
            if(bankXP > 0)
                msgs.add("`pYour XP bank balance is `w" + bankXP + "`p XP.");
            else
                msgs.add("`pYou have not banked any experience.");
        }
        
        if(player.hasPermission(Properties.permHold)){
            if(holdXP > 0){
                msgs.add("`pYou have temporarily stored `w" + holdXP + "`p XP. ");
                msgs.add("`pType `w/xp retrieve`p to retrieve it.");
            } else
                msgs.add("`pYou do not have any experience in temporary storage.");
        }
        
        Messaging.mlSend(cs, msgs);
        return true;
    }
}
