package com.s305089.software.trade.logic;

import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TradeLogic {

    /**
     *
     * @param allActiveOrders All current active orders from the database
     * @param orderToFullfill The order we want to trade
     * @return A list of orders with updated values ready to be saved to database.
     */
    public static List<Order> preformTransaction(List<Order> allActiveOrders, Order orderToFullfill) {
        List<Order> ordersThatCanBeTradedWith = allActiveOrders
                .stream()
                .filter(order -> order.getTotal().compareTo(orderToFullfill.getTotal()) == 0)
                .filter(order -> order.getMarket().equals(orderToFullfill.getMarket()))
                .filter(order -> order.getTransactionType().isOpposit(orderToFullfill.getTransactionType()))
                .collect(Collectors.toList());

        Optional<Order> matchingOrder = ordersThatCanBeTradedWith.stream().filter(order -> order.getRemainingAmount().compareTo(orderToFullfill.getAmount()) == 0).findAny();
        Transaction transaction;
        if (matchingOrder.isPresent()) {
            Order order = matchingOrder.get();
            transaction = new Transaction(orderToFullfill, order);
        } else {
            List<Order> ordersToMakeTransaction = new ArrayList<>();
            BigDecimal amount = new BigDecimal(0);
            for (int i = 0; amount.compareTo(orderToFullfill.getAmount()) < 0 && i < ordersThatCanBeTradedWith.size(); i++) {
                Order order = ordersThatCanBeTradedWith.get(i);
                amount = amount.add(order.getRemainingAmount());
                ordersToMakeTransaction.add(order);
            }
            transaction = new Transaction(orderToFullfill, ordersToMakeTransaction);
        }


        transaction.doTrade();

        return transaction.getAllOrders();
    }
    
}
