/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.noheroes.xpbank.Events.XPBankTransaction;
import java.util.logging.Level;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class TransactionListener implements Listener {
    
    private XPBank xpb;
    
    public TransactionListener(XPBank xpb){
        this.xpb = xpb;
    }
    
    @EventHandler
    public void onTransaction(XPBankTransaction transaction){
        //XPBank.log("event received for transction: " + transaction.toString());
        if(Properties.logTransactions && XPBank.transactionLog != null)
            XPBank.transactionLog.log(Level.INFO, transaction.toString());
    }
}
