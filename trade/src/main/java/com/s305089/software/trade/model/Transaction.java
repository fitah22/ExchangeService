package com.s305089.software.trade.model;

import java.math.BigDecimal;
import java.util.*;

//@Entity
//@Table(name = "TRADE_TRANSACTION")
public class Transaction {

    private Order orderToFulfill;
    private Collection<Order> ordersToMakeTransaction;

    public Transaction(Order orderToFulfill, Order tradingOrder) {
        this.orderToFulfill = orderToFulfill;
        this.ordersToMakeTransaction = Collections.singletonList(tradingOrder);
    }

    public Transaction(Order orderToFulfill, Collection<Order> ordersToMakeTransaction) {
        this.orderToFulfill = orderToFulfill;
        this.ordersToMakeTransaction = ordersToMakeTransaction;
    }


    public List<Order> getAllOrders() {
        List<Order> all = new ArrayList<>();
        all.add(orderToFulfill);
        all.addAll(ordersToMakeTransaction);
        return all;
    }

    public List<PayRecord> generatePayrecords() {
        List<PayRecord> payRecords = new ArrayList<>(ordersToMakeTransaction.size());

        for (Order order : ordersToMakeTransaction) {
            BigDecimal remainingAmount = order.getRemainingAmount();
            BigDecimal orderToFulfillRemainingAmount = orderToFulfill.getRemainingAmount();

            //if the current order has more amount then what we need
            if (orderToFulfillRemainingAmount.compareTo(remainingAmount) < 0) {
                orderToFulfill.tradeRemaningAmount();
                order.addAmountTraded(orderToFulfillRemainingAmount);

                //The amount that has been traded on this order
                remainingAmount = remainingAmount.subtract(order.getRemainingAmount());
            } else {
                orderToFulfill.addAmountTraded(remainingAmount);
                order.tradeRemaningAmount();
            }
            BigDecimal tradedToalt = remainingAmount.multiply( orderToFulfill.getPrice());
            payRecords.add(new PayRecord(order, tradedToalt));
        }
        payRecords.add(new PayRecord(orderToFulfill, orderToFulfill.getTradedTotal()));

        return payRecords;
    }
}
