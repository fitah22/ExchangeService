package com.s305089.software.trade.logic;

import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.PayRecord;
import com.s305089.software.trade.model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TradeLogic {

    /**
     * Tries to fulfill the order given, with those orders who already are active. The users accounts will be notified and charged/payed accordingly.
     * @param allActiveOrders All current active orders (most likely from the database)
     * @param orderToFullfill The order we want to trade
     * @return A list of orders with updated values ready to be saved to database. T
     */
    public static List<Order> performTransaction(List<Order> allActiveOrders, Order orderToFullfill) {
        List<Order> ordersThatCanBeTradedWith = allActiveOrders
                .stream()
                .filter(order -> order.getTotal().compareTo(orderToFullfill.getTotal()) == 0)
                .filter(order -> order.getMarket().equals(orderToFullfill.getMarket()))
                .filter(order -> order.getTransactionType().isOpposit(orderToFullfill.getTransactionType()))
                .collect(Collectors.toList());

        Transaction transaction = makeTransaction(orderToFullfill, ordersThatCanBeTradedWith);

        List<PayRecord> payRecords = transaction.generatePayrecords();
        sendPayRecordsToUserAndHistoryService(payRecords);

        return transaction.getAllOrders();
    }

    /**
     * Construct a transaction that contains orders it can be traded with
     * @param orderToFullfill
     * @param ordersThatCanBeTradedWith
     * @return A transaction ready to fulfill.
     */
    private static Transaction makeTransaction(Order orderToFullfill, List<Order> ordersThatCanBeTradedWith) {
        Transaction transaction;

        Optional<Order> matchingOrder = ordersThatCanBeTradedWith
                .stream()
                .filter(order ->
                        //If an order matches the amount exact
                        order.getRemainingAmount().compareTo(orderToFullfill.getAmount()) == 0)
                .findAny();
        if (matchingOrder.isPresent()) {
            Order order = matchingOrder.get();
            transaction = new Transaction(orderToFullfill, order);
        } else {
            List<Order> ordersToMakeTransaction = new ArrayList<>();
            BigDecimal amount = new BigDecimal(0);
            //NB: We might add orders so we get above the amount to fulfill, this is OK and taken care of when we fullfill the transaction.
            for (int i = 0; amount.compareTo(orderToFullfill.getAmount()) < 0 || i < ordersThatCanBeTradedWith.size(); i++) {
                Order order = ordersThatCanBeTradedWith.get(i);
                amount = amount.add(order.getRemainingAmount());
                ordersToMakeTransaction.add(order);
            }
            transaction = new Transaction(orderToFullfill, ordersToMakeTransaction);
        }

        return transaction;
    }


    private static void sendPayRecordsToUserAndHistoryService(List<PayRecord> records) {


    }

}
