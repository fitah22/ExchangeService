package com.s305089.software.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.dao.PayRecordDao;
import com.s305089.software.trade.logging.OrderLogger;
import com.s305089.software.trade.logic.NetworkUtil;
import com.s305089.software.trade.logic.TradeLogic;
import com.s305089.software.trade.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.s305089.software.trade.model.TransactionType.BUY;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TradeController {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PayRecordDao payRecordDao;


    @GetMapping()
    public Iterable<Order> getOrders() {

        Order order = new Order("hello", 15d, 1d, Market.BTC_USD, BUY);
        order.setActive(true);
        orderDao.save(order);
        return orderDao.findAll();
    }


    @PostMapping
    public ResponseEntity makeOrder(@RequestBody OrderDTO orderDTO) {

        Order order = orderDTO.getOrder();
        order.setTimestamp(new Date());


        boolean fundsOK = NetworkUtil.checkFunds(orderDTO.getUsername(), orderDTO.getPassword(), order);
        if (!fundsOK) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        //If buy: Reserve/withdraw money right away
        //If sell: Don't transfer money until transaction is made
        if (order.getTransactionType() == BUY) {
            boolean buyOK = NetworkUtil.sendBuyOrder(order.getUserID(), order.getMarket().getSecondCurrency(), order.getTotal());
            if (!buyOK) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        //Preform transaction to match with bids (buys) and asks (sells)
        Transaction transaction = TradeLogic.performTransaction(order, orderDao.findByActiveTrue());
        List<PayRecord> payRecords = transaction.generatePayrecords();
        boolean tradeOK = NetworkUtil.sendPayRecordsToUserAndHistoryService(payRecords);

        if (tradeOK) {
            List<Order> orders = transaction.getAllOrders();
            orderDao.saveAll(orders);

            payRecordDao.saveAll(payRecords);

            OrderLogger.logToLogService(order);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping(value = "/markets")
    public List<Market> getMarkets() {
        return Arrays.asList(Market.values());
    }

}
