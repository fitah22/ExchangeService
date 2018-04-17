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
    private BigDecimal tradedTotal;

    public PayRecord(Order order, BigDecimal tradedAmount) {
        this.order = order;
        this.tradedTotal = tradedAmount;
    }

    @JsonProperty(value = "email")
    public String getUserID() {
        return order.getUserID();
    }

    @JsonProperty(value = "transactionType")
    public TransactionType getTransactionType() {
        return order.getTransactionType();
    }

    @JsonProperty(value = "total")
    public BigDecimal getTradedTotal() {
        return tradedTotal;
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
