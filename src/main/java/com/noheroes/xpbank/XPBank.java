/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.noheroes.xpbank.Commands.XPBCommandExecutor;
import java.io.File;
import java.io.IOException;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */


/*
 * TODO: add /xp confirm for /xp retrieve when player has experience.
 */
public class XPBank extends JavaPlugin {

    private static XPBank xpb;
    private static XPBStorage xpBankStorage;
    private static XPBStorage xpHoldStorage;
    private static Economy econ = null;
    public static XPLogger transactionLog = null;
    
    public final TransactionListener tl = new TransactionListener(this);
    
    @Override
    public void onDisable() {
        XPBank.log("Disabled");
    }

    @Override
    public void onEnable() {
        XPBank.xpb = this;
        
        XPBank.log("Loading configuration");
        loadXPConfig(this.getConfig());
        this.saveConfig();
        
        XPBank.log("Registering with Vault");
        if(!setupEconomy()){
            getLogger().warning("Failed to hook Vault, or Vault failed to hook economy");
            getLogger().warning("Disabling XPBank");
            getPluginLoader().disablePlugin(this);
        }
        
        XPBank.log("Initializing commands");
        getCommand("xpbank").setExecutor(new XPBCommandExecutor(this));
        
        XPBank.log("Connecting to storage");
        xpBankStorage = new XPBStorage(Properties.miniFileName);
        xpHoldStorage = new XPBStorage(Properties.miniHoldName);
        try {
            xpBankStorage.initStorage();
            xpHoldStorage.initStorage();
        } catch (IOException ioe) {
            getLogger().warning("Failed to connect to storage");
            ioe.printStackTrace();
            getPluginLoader().disablePlugin(this);
        }
        
        if(Properties.logTransactions) {
            XPBank.log("Enabling transaction logging.");
            transactionLog = new XPLogger("XPBank", getDataFolder() + File.separator + "transactions.log");
            getServer().getPluginManager().registerEvents(tl, this);
        }
        
        XPBank.log("Plugin started");
    }
    
    public static XPBank get(){
        return xpb;
    }
    
    public static XPBStorage getBank() {
        return xpBankStorage;
    }
    
    public static XPBStorage getHold() {
        return xpHoldStorage;
    }
    
    public static Economy getEconomy() {
        return econ;
    }
    
    public static boolean hasPermission(CommandSender cs, String perm){
        return (cs.hasPermission(perm) || cs.hasPermission(Properties.permAdmin));
    }
    
    public static void log(String msg){
        xpb.getLogger().info(msg);
    }
    
    private void loadXPConfig(Configuration xpConfig) {
        xpConfig.options().copyDefaults(true);
        
        Properties.flatFeeDeposit = xpConfig.getDouble("Fees.Deposit.Flat", 0.0);
        Properties.flatFeeWithdrawl = xpConfig.getDouble("Fees.Withdrawl.Flat", 0.0);
        Properties.perXPDeposit = xpConfig.getDouble("Fees.Deposit.PerXP", 0.0);
        Properties.perXPWithdrawl = xpConfig.getDouble("Fees.Withdrawl.PerXP", 0.0);
        Properties.logTransactions = xpConfig.getBoolean("LogTransactions", true);
    }
    
    private Boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }
}
