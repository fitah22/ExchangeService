package com.s305089.software.trade.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

//To avoid naming conflict with SQL-keyword 'order'
@Entity(name = "TRADE_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    private String userID;
    @NonNull
    @Min(value = 0)
    private BigDecimal price;
    @NonNull
    @Min(value = 0)
    private BigDecimal amount;
    @Min(value = 0)
    private BigDecimal amountTraded;
    private BigDecimal total;
    private Market market;
    private TransactionType transactionType;
    private Boolean active = true;
    private Date timestamp = new Date();

    private Order() {
    }

    public Order(String userID, Double price, Double amount, Market market, TransactionType transactionType) {
        this.userID = userID;
        this.price = new BigDecimal(price);
        this.amount = new BigDecimal(amount);
        this.amountTraded = new BigDecimal(0);
        this.total = this.price.multiply(this.amount);
        this.market = market;
        this.transactionType = transactionType;
        this.active = true;
    }

    public String getUserID() {
        return userID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRemainingAmount() {
        return amount.subtract(amountTraded);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getRemaningTotal() {
        return getRemainingAmount().multiply(price);
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void calculateTotal() {
        this.total = price.multiply(amount);
    }

    public BigDecimal getTradedAmount() {
        return amountTraded;
    }

    public void setAmountTraded(BigDecimal amountTraded) {
        this.amountTraded = amountTraded;
    }

    public void addAmountTraded(BigDecimal amountTraded) {
        this.amountTraded = this.amountTraded.add(amountTraded);
    }

    public BigDecimal getTradedTotal() {
        return amountTraded.multiply(price);
    }

    public void tradeRemaningAmount() {
        this.amountTraded = amount;
    }
}
