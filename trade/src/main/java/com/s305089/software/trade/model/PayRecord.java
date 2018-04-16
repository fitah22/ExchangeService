package com.s305089.software.trade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class PayRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    @ManyToOne
    private Order order;
    private BigDecimal amount;

    public PayRecord(Order order, BigDecimal tradedAmount) {
        this.order = order;
        this.amount = tradedAmount;
    }

    @JsonProperty(value = "userId")
    public String getUserID() {
        return order.getUserID();
    }

    @JsonProperty(value = "transactionType")
    public TransactionType getTransactionType() {
        return order.getTransactionType();
    }

    @JsonProperty(value = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty(value = "market")
    public Market getMarket() {
        return order.getMarket();
    }
}
