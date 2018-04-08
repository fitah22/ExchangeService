package com.s305089.software.trade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.UnsupportedEncodingException;

//To avoid naming conflict with SQL-keyword 'order'
@Entity(name = "TRADE_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private Integer userID;
    @NonNull
    @Min(value = 0)
    private Double price;
    @NonNull
    @Min(value = 0)
    private Double amount;
    private Double total;
    private Market market;
    private TransactionType transactionType;
    private Boolean active = true;

    private Order() {
    }

    public Order(Integer userID, Double price, Double amount, Market market, TransactionType transactionType) {
        this.userID = userID;
        this.price = price;
        this.amount = amount;
        this.total = price * amount;
        this.market = market;
        this.transactionType = transactionType;
        this.active = true;
    }

    public Integer getUserID() {
        return userID;
    }

    public Double getPrice() {
        return price;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getTotal() {
        return total;
    }

    public Market getMarket() {
        return market;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void calculateTotal() {
        this.total = price * amount;
    }

}
