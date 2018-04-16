package com.s305089.software.login.model;

import java.math.BigDecimal;

//Could possible merge with ClientOrderDTO
public class PayRecordDTO {

    private String userId;
    private String transactionType;
    private BigDecimal amount;
    private Currency currency;

    public String getUserID() {
        return userId;
    }


    public String getTransactionType() {
        return transactionType;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public Currency getCurrency() {
        return currency;
    }
}
