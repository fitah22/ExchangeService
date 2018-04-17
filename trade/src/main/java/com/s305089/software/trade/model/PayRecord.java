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
    @JsonIgnore
    private BigDecimal tradedAmount;

    public PayRecord(Order order, BigDecimal tradedAmount) {
        this.order = order;
        this.tradedAmount = tradedAmount;
    }

    @JsonProperty(value = "email")
    public String getUserID() {
        return order.getUserID();
    }

    @JsonProperty(value = "transactionType")
    public TransactionType getTransactionType() {
        return order.getTransactionType();
    }

    @JsonProperty(value = "amount")
    public BigDecimal getTradedAmount() {
        return tradedAmount;
    }

    @JsonProperty(value = "currency")
    public String getCurrency() {
        if(getTransactionType() == TransactionType.BUY){
            return order.getMarket().getSecondCurrency();
        }else{
            return order.getMarket().getMainCurrency();
        }
    }
}
