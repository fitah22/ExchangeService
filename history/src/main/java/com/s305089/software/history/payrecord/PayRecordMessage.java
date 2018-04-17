package com.s305089.software.history.payrecord;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@Entity
class PayRecordMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String transactionType;
    private BigDecimal total;
    private String currency;
    private Date timestamp = new Date();

    public PayRecordMessage() {
    }

    public PayRecordMessage(String email, String transactionType, BigDecimal total, String currency) {
        this.email = email;
        this.transactionType = transactionType;
        this.total = total;
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }


    public String getTransactionType() {
        return transactionType;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
