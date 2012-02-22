/*
 * Copyright (C) 2012 No Heroes.
 * See readme for license details.
 */
package com.noheroes.xpbank;

/**
 *
 * @author Sorklin <sorklin at gmail.com>
 */
public class Properties {
    
    //permissions:
    public static String permUse = "xpbank.use"; //see help, basic access
    public static String permBank = "xpbank.bank"; //bank the exp.
    public static String permHold = "xpbank.hold"; //use temp holding
    public static String permAdmin = "xpbank.admin"; //full access
    
    //storage filename.
    public static String miniFileName = "xp.mini";
    public static String miniHoldName = "xphold.mini";
    
    //XP bank:
    public static double flatFeeDeposit = 0.0;
    public static double flatFeeWithdrawl = 0.0;
    public static double perXPDeposit = 0.0;
    public static double perXPWithdrawl = 0.0;
    public static boolean logTransactions = false;
    
    public static final int MAX_LEVEL = 1000;
    public static final int expTable[] = new int[MAX_LEVEL];
    
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWL,
        HOLD,
        RETRIEVE;
    }
}
