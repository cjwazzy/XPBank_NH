/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.Properties;
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
        
//        if(page.equalsIgnoreCase("admin")){
//            if(XPBank.hasPermission(cs, Properties.permAdmin)){
//                msg.add("`gThere is no admin menu.");//helpAdmin1
//            } else
//                throw new InsufficientPermissionException();
//        } else {
            msg.add("`G[] are optional arguments, <> are mandatory arguments.");
            msg.add("`GTerms separated by a | are interchangeable.");
            msg.add("`g/xp info `G- See info about your personal and banked XP.");
            msg.add("`g/xp balance|bal `G- See your XP bank balance.");
            msg.add("`g/xp deposit|dep [all|amount] `G- Deposit some or all of your XP.");
            msg.add("`g/xp withdraw|wd [all|amount] `G- Withdraw some or all of your XP.");
            msg.add("`g/xp store `G- Temporarily stores your XP.");
            msg.add("`g/xp retrieve `G- Retrieves your XP from temp storage.");
            msg.add("`g/xp rate `G- Shows the current transaction rates.");
            msg.add("`g/xp price <bank|me|amount> `G- Shows how much it costs to");
            msg.add("`Gwithdraw an amount.");
            
//            if(XPBank.hasPermission(cs, Properties.permAdmin))
//                msg.add("`g/scs help admin `G- show admin commands.");//help23
//            msg.add("`g/scs help 2 `G- show second page.");//help24
//        }
        
        Messaging.mlSend(cs, msg);
        return true;
    }
}
