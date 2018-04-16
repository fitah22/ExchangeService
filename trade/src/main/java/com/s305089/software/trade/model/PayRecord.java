package com.s305089.software.trade.model;

import java.math.BigDecimal;

public class PayRecord {
    private String userID;
    private TransactionType transactionType;
    private Market market;
    private BigDecimal amount;

    public PayRecord(String userID, TransactionType transactionType, Market market, BigDecimal amount) {
        this.userID = userID;
        this.transactionType = transactionType;
        this.market = market;
        this.amount = amount;
    }

    public String getUserID() {
        return userID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Market getMarket() {
        return market;
    }
}
