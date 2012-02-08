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
 * Copyright (C) 2011 Kellerkindt <kellerkindt@miykeal.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
