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
import org.bukkit.command.CommandSender;


/**
 * @author Sorklin <sorklin at gmail.com>
 */
public class HelpCmd extends GenericCmd {
    
    public HelpCmd(CommandSender cs, String args[]){
        super(cs, args);
        this.mustBePlayer = false;
        this.permission = Properties.permUse;
    }
    
    @Override
    public boolean execute() throws MissingOrIncorrectArgumentException, InsufficientPermissionException {
        if(errorCheck())
            return true;

        String page;
        if(args.length < 2)
            page = "1";
        else
            page = args[1].toLowerCase();
        
        List<String> msg = new ArrayList<String>();
        
        if(page.equalsIgnoreCase("admin")){
            if(XPBank.hasPermission(cs, Properties.permAdmin)){
                msg.add("`B<|-----------  `bShow`YCase`RStandalone `gAdmin Help  `B----------|>");//msg: helpAdminTitle
                msg.add("`g/scs clear `G- Temporarily clear the floating shop items.");//helpAdmin1
            } else
                throw new InsufficientPermissionException();
        } else {
            msg.add("`B<|------------  `bShow`YCase`RStandalone `gHelp 1/2 `B-----------|>");//helpTitle %1%
            msg.add("`YShowCase management:");//help1
            msg.add("`GUse the keyword `ythis `Gto indicate the item in your hand.");//help2
            msg.add("`g/scs buy {item/this} {amount} {price} `G- create a showcase.");//help3
            if(XPBank.hasPermission(cs, Properties.permAdmin))
                msg.add("`g/scs help admin `G- show admin commands.");//help23
            msg.add("`g/scs help 2 `G- show second page.");//help24
        }
        
        Messaging.mlSend(cs, msg);
        return true;
    }
}
