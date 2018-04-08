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
    private Double total;
    private Market market;
    private TransactionType transactionType;
    private Boolean active = true;

    private Order(OrderBuilder builder) {
        this.userID = builder.userID;
        this.price = builder.price;
        this.amount = builder.amount;
        this.total = price*amount;
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
}
