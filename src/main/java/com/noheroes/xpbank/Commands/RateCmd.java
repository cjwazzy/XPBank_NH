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
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class RateCmd extends GenericCmd {
    
    Economy econ;
    
    public RateCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.mustBePlayer = false;
        this.permission = Properties.permBank;
        econ = XPBank.getEconomy();
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;
        
        List<String> msg = new ArrayList<String>();
        
        msg.add("`pDeposit: `w" + econ.format(Properties.flatFeeDeposit) + "`p flat fee, plus `w" 
                + econ.format(Properties.perXPDeposit) + "`p per XP.");
        msg.add("`pWithdrawl: `w" + econ.format(Properties.flatFeeWithdrawl) + "`p flat fee, plus `w" 
                + econ.format(Properties.perXPWithdrawl) + "`p per XP.");
            
        Messaging.mlSend(cs, msg);
        return true;
    }
}
