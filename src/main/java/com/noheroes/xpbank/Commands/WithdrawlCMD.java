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
import com.noheroes.xpbank.XPBank;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class WithdrawlCMD extends GenericCmd {
    
    Economy economy;
    
    public WithdrawlCMD(CommandSender cs, String args[]){
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
        
        String name = player.getName().toLowerCase();
        int bankXP = 0;
        int withdrawlXP;
        EconomyResponse er;
        
        if(bank.hasBalance(name))
           bankXP = bank.getBalance(name);
        
        //Parse arguments
        if(args[1].equalsIgnoreCase("all"))
            withdrawlXP = bankXP;
        else {
            try {
                withdrawlXP = Integer.parseInt(args[1]);
                if (withdrawlXP > bankXP)
                    withdrawlXP = bankXP;
            } catch (NumberFormatException nfe){
                throw new MissingOrIncorrectArgumentException();
            }
        }
        
        //sanity check
        if(withdrawlXP == 0)
            throw new MissingOrIncorrectArgumentException("No experience to withdrawl.");
        
        //Do finances
        double price = Properties.flatFeeWithdrawl + (Properties.perXPWithdrawl * withdrawlXP);
        if(!economy.has(name, price))
            throw new InsufficientPermissionException("Insufficient funds to make this deposit.");
        
        er = economy.withdrawPlayer(name, price);
        if(!er.transactionSuccess())
            throw new InsufficientPermissionException("Economy failure: " + er.errorMessage);        
        
        //Do XP withdrawl
        if(bank.hasBalance(name)){
            bank.subtract(name, withdrawlXP);
            player.giveExp(withdrawlXP);

            Messaging.send(player, "`GYou have withdrawn `w" + withdrawlXP 
                + " `Gexperience for the price of `w" + economy.format(price) + "`G.");
            Messaging.send("`GYour XPBank balance is `w" + bank.getBalance(name));
        } else
            throw new MissingOrIncorrectArgumentException("No experience to withdrawl.");
        
        if(Properties.logTransactions){
            XPBankTransaction event = new XPBankTransaction(TransactionType.WITHDRAWL, player, withdrawlXP, bank.getBalance(name));
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
        
        return true;
    }
}
