package com.s305089.software.trade.model;

public enum TransactionType {
    BUY,
    SELL;

    public boolean isOpposit(TransactionType transactionType) {
        return  this != transactionType;
    }
}
