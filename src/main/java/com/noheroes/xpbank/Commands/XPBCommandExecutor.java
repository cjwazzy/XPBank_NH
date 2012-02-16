/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank.Commands;

import com.noheroes.xpbank.Exceptions.InsufficientPermissionException;
import com.noheroes.xpbank.Exceptions.MissingOrIncorrectArgumentException;
import com.noheroes.xpbank.Interfaces.Cmd;
import com.noheroes.xpbank.Messaging;
import com.noheroes.xpbank.XPBank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBCommandExecutor implements CommandExecutor {
    private Cmd cmd;
    private XPBank xpb;
    
    public XPBCommandExecutor(XPBank xpb) {
        this.xpb = xpb;
    }
    
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
            else if (mainArg.equalsIgnoreCase("balance") || mainArg.equalsIgnoreCase("info"))
                this.cmd = new InfoCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("store") || mainArg.equalsIgnoreCase("hold"))
                this.cmd = new StoreCmd(sender, args, false);
            else if (mainArg.equalsIgnoreCase("retrieve") || mainArg.equalsIgnoreCase("return"))
                this.cmd = new RetrieveCmd(sender, args, false);
            else if (mainArg.equalsIgnoreCase("deposit") || mainArg.equalsIgnoreCase("dep"))
                this.cmd = new DepositCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("withdrawl") || mainArg.equalsIgnoreCase("withdraw") || mainArg.equalsIgnoreCase("wd"))
                this.cmd = new WithdrawlCMD(sender, args);
            else if (mainArg.equalsIgnoreCase("rate") || mainArg.equalsIgnoreCase("rates"))
                this.cmd = new RateCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("price"))
                this.cmd = new PriceCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("setrate"))
                this.cmd = new PriceCmd(sender, args);
            else if (mainArg.equalsIgnoreCase("confirm"))
                this.cmd = new ConfirmCMD(sender, args);
                    
            //Unknown
            else
                throw new MissingOrIncorrectArgumentException("Unknown command. See /xp help."); //msg: unknownCommand

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
