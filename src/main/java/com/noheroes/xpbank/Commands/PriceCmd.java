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
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class PriceCmd extends GenericCmd {
    
    Economy economy;
    
    public PriceCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permBank;
        minArg = 2;
        economy = XPBank.getEconomy();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        // /xp price <amount|bank|me>
        
        String name = player.getName().toLowerCase();
        //int currentXP = player.getTotalExperience();
        int currentXP = Utilities.getTotalExp(player);
        int bankXP = XPBank.getBank().getBalance(name);
        int xp;
        EconomyResponse er;
        
        //Parse arguments
        if(args[1].equalsIgnoreCase("me"))
            xp = currentXP;
        else if(args[1].equalsIgnoreCase("bank"))
            xp = bankXP;
        else {
            try {
                xp = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe){
                throw new MissingOrIncorrectArgumentException();
            }
        }
        
        double dPrice = Properties.flatFeeDeposit + (Properties.perXPDeposit * xp);
        double wPrice = Properties.flatFeeWithdrawl + (Properties.perXPWithdrawl * xp);
        
        Messaging.send(player, "`pIt would cost `w" + economy.format(dPrice) + "`p to deposit `w" + xp + "`p XP.");
        Messaging.send(player, "`pIt would cost `w" + economy.format(wPrice) + "`p to withdraw `w" + xp + "`p XP.");
        
        return true;
    }
}
