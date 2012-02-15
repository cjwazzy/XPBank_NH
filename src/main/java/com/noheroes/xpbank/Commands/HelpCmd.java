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
        
//        if(page.equalsIgnoreCase("admin")){
//            if(XPBank.hasPermission(cs, Properties.permAdmin)){
//                msg.add("`wThere is no admin menu.");//helpAdmin1
//            } else
//                throw new InsufficientPermissionException();
//        } else {
            msg.add("`Y[] are optional arguments, <> are mandatory arguments.");
            msg.add("`YTerms separated by a | are interchangeable.");
            msg.add("`Y/xp info `w- See your XP balance and info.");
            if(XPBank.hasPermission(cs, Properties.permHold)){
                msg.add("`YTemporary storage:");
                msg.add("`Y/xp store `w- Temporarily stores your XP.");
                msg.add("`Y/xp retrieve `w- Retrieves your XP from temp storage.");
            }
            if(XPBank.hasPermission(cs, Properties.permBank)){
                msg.add("`YXP Bank:");
                msg.add("`Y/xp deposit|dep [all|amount] `w- Deposit some or all of your XP.");
                msg.add("`Y/xp withdraw|wd [all|amount] `w- Withdraw some or all of your XP.");
                msg.add("`Y/xp rate `w- Shows the current transaction rates.");
                msg.add("`Y/xp price <bank|me|amount> `w-  Shows the transaction costs");
                msg.add("`wfor a specific amount.");
            }
            
//            if(XPBank.hasPermission(cs, Properties.permAdmin))
//                msg.add("`w/scs help admin `w- show admin commands.");//help23
//            msg.add("`w/scs help 2 `w- show second page.");//help24
//        }
        
        Messaging.mlSend(cs, msg);
        return true;
    }
}
