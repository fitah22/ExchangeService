package com.s305089.software.trade.logic;

import java.math.BigDecimal;

//Maps to ClientOrderDTO in login module
class BuyOrderDTO {
    private String email;
    private String currency;
    private BigDecimal amount;

    public BuyOrderDTO(String email, String currency, BigDecimal amount) {
        this.email = email;
        this.currency = currency;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
