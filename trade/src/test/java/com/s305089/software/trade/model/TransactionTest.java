package com.s305089.software.trade.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private Transaction buyTransaction;
    private Order order1, order2, toFulfiill;

    @Before
    public void setUp() throws Exception {
        order1 = new Order("John", 15d, 1d, Market.BTC_USD, TransactionType.SELL);
        order2 = new Order("Kasper", 15d, 2.5d, Market.BTC_USD, TransactionType.SELL);

        toFulfiill = new Order("Svergja", 15d, 3d, Market.BTC_USD, TransactionType.BUY);

        buyTransaction = new Transaction(toFulfiill, Arrays.asList(order1, order2));
    }

    @Test
    public void generatePayrecords() {

        List<PayRecord> actual = buyTransaction.generatePayrecords();
        int expectedSize = 3;
        assertEquals(expectedSize, actual.size());

        assertEquals(0d, order1.getRemainingAmount().doubleValue(), 0.0003);
        assertEquals(0.5d, order2.getRemainingAmount().doubleValue(), 0.0003);
        assertEquals(0d, toFulfiill.getRemainingAmount().doubleValue(), 0.0003);

        assertEquals(3d, toFulfiill.getTradedAmount().doubleValue(), 0.0003);
        assertEquals(45d, toFulfiill.getTradedTotal().doubleValue(), 0.0003);


        assertEquals("John", actual.get(0).getUserID());
        assertEquals("Kasper", actual.get(1).getUserID());
        assertEquals("Svergja", actual.get(2).getUserID());

        assertEquals(1d, actual.get(0).getAmount().doubleValue(), 0.0003);
        assertEquals(2d, actual.get(1).getAmount().doubleValue(), 0.0003);
        assertEquals(3d, actual.get(2).getAmount().doubleValue(), 0.0003);

    }
}