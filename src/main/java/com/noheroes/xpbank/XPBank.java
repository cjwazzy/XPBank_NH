/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

import com.noheroes.xpbank.Commands.XPBCommandExecutor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class XPBank extends JavaPlugin {

    private static XPBank xpb;
    private static final XPBStorage xpStore = new XPBStorage();
    private static final XPBStorage xpHold = new XPBStorage();
    public static Economy econ = null;
    
    
    public void onDisable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEnable() {
        XPBank.xpb = this;
        
        XPBank.log("Loading configuration");
        loadXPConfig(this.getConfig());
        this.saveConfig();
        
        XPBank.log("Registering with Vault");
        if(!setupEconomy()){
            getLogger().warning("Failed to hook Vault, or Vault failed to hook economy.");
            getLogger().warning("Disabling XPBank.");
            getPluginLoader().disablePlugin(this);
        }
        
        XPBank.log("Initializing commands");
        getCommand("xpb").setExecutor(new XPBCommandExecutor(this));
        
    }
    
    public static XPBank get(){
        return xpb;
    }
    
    public static XPBStorage getBank() {
        return xpStore;
    }
    
    public static XPBStorage getHold() {
        return xpHold;
    }
    
    public static boolean hasPermission(CommandSender cs, String perm){
        return (cs.hasPermission(perm) || cs.hasPermission(Properties.permAdmin));
    }
    
    public static void log(String msg){
        xpb.getLogger().info(msg);
    }
    
    private void loadXPConfig(Configuration xpConfig){
        xpConfig.options().copyDefaults(true);
        
        Properties.flatFeeDeposit = xpConfig.getDouble("Fees.Deposit.Flat", 0.0);
        Properties.flatFeeWithdrawl = xpConfig.getDouble("Fees.Withdrawl.Flat", 0.0);
        Properties.perXPDeposit = xpConfig.getDouble("Fees.Deposit.PerXP", 0.0);
        Properties.perXPWithdrawl = xpConfig.getDouble("Fees.Withdrawl.PerXP", 0.0);
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
        return econ != null;
    }
}
