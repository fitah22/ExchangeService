package com.s305089.software.trade.logic;

import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.s305089.software.trade.model.Market.BTC_USD;
import static com.s305089.software.trade.model.TransactionType.BUY;
import static com.s305089.software.trade.model.TransactionType.SELL;
import static org.junit.Assert.assertEquals;

public class TradeLogicTest {
    List<Order> activeOrders;


    @Before
    public void setUp() throws Exception {
        activeOrders = new ArrayList<>();
        Order order1 = new Order("John", 15d, 1d, BTC_USD, SELL);
        Order order2 = new Order("Kasper", 15d, 2.5d, BTC_USD, SELL);
        Order order3 = new Order("Svergja", 15d, 3d, BTC_USD, BUY);
        Order order4 = new Order("Svergja", 1d, 1d, BTC_USD, BUY);

        activeOrders.add(order1);
        activeOrders.add(order2);
        activeOrders.add(order4);
    }

    @Test
    public void should_be_non_if_same_user() {
        Order toFulfill = new Order("Svergja", 1d, 1d, BTC_USD, SELL);
        Transaction transaction = TradeLogic.performTransaction(toFulfill, activeOrders);
        List<Order> allOrders = transaction.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals(toFulfill, allOrders.get(0));
    }

    @Test
    public void transaction_with_matching_opposite_order() {
        Order toFulfill = new Order("s305089", 15d, 1d, BTC_USD, BUY);
        Transaction transaction = TradeLogic.performTransaction(toFulfill, activeOrders);
        List<Order> allOrders = transaction.getAllOrders();
        assertEquals(2, allOrders.size());
        assertEquals(toFulfill, allOrders.get(0));
        assertEquals(activeOrders.get(0), allOrders.get(1));
    }

    @Test
    public void multiple_orders_to_fulfill() {
        Order toFulfill = new Order("s305089", 15d, 10d, BTC_USD, BUY);
        Transaction transaction = TradeLogic.performTransaction(toFulfill, activeOrders);
        List<Order> allOrders = transaction.getAllOrders();
        assertEquals(3, allOrders.size());
        assertEquals(toFulfill, allOrders.get(0));
        assertEquals(activeOrders.get(0), allOrders.get(1));
        assertEquals(activeOrders.get(1), allOrders.get(2));
    }
    @Test
    public void no_orders_should_be_added() {
        Order toFulfill = new Order("s305089", 10d, 10d, BTC_USD, SELL);
        Transaction transaction = TradeLogic.performTransaction(toFulfill, activeOrders);
        List<Order> allOrders = transaction.getAllOrders();
        assertEquals(1, allOrders.size());
        assertEquals(toFulfill, allOrders.get(0));
    }
}