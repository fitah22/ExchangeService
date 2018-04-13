package com.s305089.software.trade.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "TRADE_TRANSACTION")
public class Transaction {
    private Order orderToFullfill;
    private Collection<Order> ordersToMakeTransaction;

    public Transaction(Order orderToFullfill, Collection<Order> ordersToMakeTransaction) {
    }

    public Transaction(Order orderToFullfill, Order tradingOrder) {

    }

    public List<Order> getAllOrders() {
        List<Order> all = new ArrayList<>();
        all.add(orderToFullfill);
        all.addAll(ordersToMakeTransaction);
        return all;
    }

    public void doTrade() {

    }
}
