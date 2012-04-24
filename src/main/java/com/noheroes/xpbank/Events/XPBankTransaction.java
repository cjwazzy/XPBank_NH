/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Events;

import com.noheroes.xpbank.Properties.TransactionType;
import com.noheroes.xpbank.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBankTransaction extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    private final TransactionType type;
    private final String player;
    private final int playersRemainingXP;
    private final int amount;
    private final int newBalance;
    
    public XPBankTransaction(TransactionType type, Player player, int amount, int newBalance) {
        this.type = type;
        this.player = player.getName();
        //this.playersRemainingXP = player.getTotalExperience();
        this.playersRemainingXP = Utilities.getTotalExp(player);
        this.amount = amount;
        this.newBalance = newBalance;
    }
 
    @Override
    public String toString(){
        return String.format("Name: %s, Type: %s, Amount: %d, Balance: %d, Left on player: %d", 
                player, type, amount, newBalance, playersRemainingXP);
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }   
}
