/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class MeCmd extends GenericCmd {
    
    public MeCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.permission = Properties.permUse;
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;

        Messaging.send(player, "You have " + player.getTotalExperience() + " experience.");
        return true;
    }
}
