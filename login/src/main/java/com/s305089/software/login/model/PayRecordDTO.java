package com.s305089.software.login.model;

import java.math.BigDecimal;

//Could possible merge with ClientOrderDTO
public class PayRecordDTO {

    private String email;
    private String transactionType;
    private BigDecimal total;
    private Currency currency;

    public String getEmail() {
        return email;
    }


    public String getTransactionType() {
        return transactionType;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public Currency getCurrency() {
        return currency;
    }
}
