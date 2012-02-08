/*
 * Copyright (C) 2011 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Interfaces.Cmd;
import com.noheroes.xpbank.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBCommandExecutor implements CommandExecutor {
    private Cmd cmd;
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String mainArg;
            
        if(args.length < 1)
            mainArg = "help";
        else
            mainArg = args[0];

        try {
            //General commands
            if (mainArg.equalsIgnoreCase("help") || mainArg.equalsIgnoreCase("?"))
                this.cmd = new HelpCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("help"))
                this.cmd = new HelpCmd(sender, args);

            //Unknown
            else
                throw new MissingOrIncorrectArgumentException("Unknown command. See /xpb help."); //msg: unknownCommand

            return cmd.execute();

        } catch (MissingOrIncorrectArgumentException miae) {
            Messaging.send(sender, "`r" + miae.getMessage());
            return true;
        } catch (InsufficientPermissionException nperm) {
            Messaging.send(sender, "`r" + nperm.getMessage());
            return true;
        }
    }
}
