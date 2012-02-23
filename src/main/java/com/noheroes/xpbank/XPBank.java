/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.noheroes.xpbank.Commands.XPBCommandExecutor;
import com.noheroes.xpbank.Properties.TransactionType;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Sorklin <sorklin at gmail.com> & Morthis
 */


/*
 * TODO: add /xp confirm for /xp retrieve when player has experience.
 * TODO: /xp store should have a confirm if you try to deposit over something that is already there.
 * TODO: /xp clear (clear temp storage).
 */
public class XPBank extends JavaPlugin {

    private static XPBank xpb;
    private static XPBStorage xpBankStorage;
    private static XPBStorage xpHoldStorage;
    private static Economy econ = null;
    public static XPLogger transactionLog = null;
    
    private static HashMap<Player, TransactionType> playerConfirm = new HashMap<Player, TransactionType>();
    
//    public final TransactionListener tl = new TransactionListener(this);
    
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
        
        XPBank.log("Enabling listeners");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        
        if(Properties.logTransactions) {
            XPBank.log("Enabling transaction logging");
            transactionLog = new XPLogger("XPBank", getDataFolder() + File.separator + "transactions.log");
            getServer().getPluginManager().registerEvents(new TransactionListener(this), this);
        }
        
        populateExpTable();
        debugDisplayTable();  // Displays the full exp table being used, simply used to check the formula was correct
        
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
    
    public static HashMap<Player, TransactionType> getConfirm(){
        return playerConfirm;
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
    
    private void populateExpTable() {
        int currentTotal = 0;
        for (int i = 0; i < Properties.expTable.length; i++) {
            Properties.expTable[i] = currentTotal;
            currentTotal += 7 + (i * 7 >> 1);
        }
    }
    
    private void debugDisplayTable() {
        StringBuilder sb = new StringBuilder();
        log("Experience Table generation:");
        for (int i = 0; i < Properties.expTable.length; i++) {
            sb.append(Properties.expTable[i]).append(", ");
        }
        log(sb.toString());
    }
}
