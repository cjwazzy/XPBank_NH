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
        int playerXP = player.getTotalExperience();
        int bankXP = XPBank.getBank().getBalance(name);
        int holdXP = XPBank.getHold().getBalance(name);
        
//        double priceDeposit = Properties.flatFeeDeposit + (Properties.perXPDeposit * playerXP);
//        double priceWithdrawl = Properties.flatFeeWithdrawl + (Properties.perXPWithdrawl * playerXP);
        
        ArrayList<String> msgs = new ArrayList<String>();
        msgs.add("`yYou have `w" + playerXP + "`y XP.");
        
        if(bankXP > 0)
            msgs.add("`yYour XP bank balance is `w" + bankXP + "`y XP.");
        else
            msgs.add("`yYou have not banked any experience.");
        
        if(holdXP > 0){
            msgs.add("`yYou have temporarily stored `w" + holdXP + "`y XP. ");
            msgs.add("`yType `w/xp retrieve`y to retrieve it.");
        } else
            msgs.add("`yYou do not have any experience in temporary storage.");
        
        Messaging.mlSend(cs, msgs);
        return true;
    }
}
