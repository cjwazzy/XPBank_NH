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
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class DepositCmd extends GenericCmd {
    
    Economy economy;
    
    public DepositCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permBank;
        bank = XPBank.getBank();
        minArg = 2;
        economy = XPBank.getEconomy();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        // /xp deposit <amount|all>
        
        String name = player.getName().toLowerCase();
        //int currentXP = player.getTotalExperience();
        int currentXP = Utilities.getTotalExp(player);
        int depositXP;
        EconomyResponse er;
        
        //Parse arguments
        if(args[1].equalsIgnoreCase("all"))
            depositXP = currentXP;
        else {
            try {
                depositXP = Integer.parseInt(args[1]);
                if (depositXP > currentXP)
                    depositXP = currentXP;
            } catch (NumberFormatException nfe){
                throw new MissingOrIncorrectArgumentException();
            }
        }
        
        //Do finances
        double price = Properties.flatFeeDeposit + (Properties.perXPDeposit * depositXP);
        if(!economy.has(name, price))
            throw new InsufficientPermissionException("Insufficient funds to make this withdrawl.");
        
        er = economy.withdrawPlayer(name, price);
        if(!er.transactionSuccess())
            throw new InsufficientPermissionException("Economy failure: " + er.errorMessage); 
        
        //Do XP deposit
        bank.add(name, depositXP);
        Utilities.resetExp(player); //zero out xp.
        player.giveExp(currentXP - depositXP); //give back the remainder.
        Messaging.send(player, "`GYou have deposited `w" + depositXP 
                + " `Gexperience for the price of `w" + economy.format(price) + "`G.");
        Messaging.send("`GYour XPBank balance is `w" + bank.getBalance(name));
        
        if(Properties.logTransactions){
            XPBankTransaction event = new XPBankTransaction(TransactionType.DEPOSIT, player, depositXP, bank.getBalance(name));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        
        return true;
    }
}
