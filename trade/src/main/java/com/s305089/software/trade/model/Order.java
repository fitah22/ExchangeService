package com.s305089.software.trade.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//To avoid naming conflict with SQL-keyword 'order'
@Entity(name = "TRADE_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userID;
    private Double price;
    private Double amount;
    private Market market;
    private TransactionType transactionType;

    private Order(OrderBuilder builder) {
        this.userID = builder.userID;
        this.price = builder.price;
        this.amount = builder.amount;
        this.market = builder.market;
        this.transactionType = builder.transactionType;
    }

    public static class OrderBuilder{
        private Integer userID;
        private Double price;
        private Double amount;
        private Market market;
        private TransactionType transactionType;

        public Order build()
        {
            return new Order(this);
        }

        public OrderBuilder setUserID(Integer userID) {
            this.userID = userID;
            return this;
        }

        public OrderBuilder setPrice(Double price) {
            this.price = price;
            return this;
        }

        public OrderBuilder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public OrderBuilder setMarket(Market market) {
            this.market = market;
            return this;
        }

        public OrderBuilder setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }
    }


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
