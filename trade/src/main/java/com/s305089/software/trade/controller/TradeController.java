package com.s305089.software.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.logging.OrderLogger;
import com.s305089.software.trade.model.Market;
import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TradeController {

    @Autowired
    private OrderDao dao;


    @GetMapping()
    public Iterable<Order> getOrders(){
        return dao.findAll();
    }

    @PostMapping
    public HttpStatus makeOrder(@RequestBody Order order) throws JsonProcessingException {
        order = new Order("hello", 15d, 1d, Market.BTC_USD, TransactionType.BUY);
        order.setActive(true);
        order.calculateTotal();
        order = dao.save(order);
        OrderLogger.logToLogService(order);
        preformTransaction(order);
        return HttpStatus.OK;
    }

    private void preformTransaction(Order orderToFullfill){
        List<Order> ordersThatMatch = dao.findByActiveTrue()
                .stream()
                .filter(order -> order.getTotal().compareTo(orderToFullfill.getTotal()) == 0)
                .filter(order -> order.getMarket().equals(orderToFullfill.getMarket()))
                .filter(order ->  order.getTransactionType().isOpposit(orderToFullfill.getTransactionType()))
                .collect(Collectors.toList());


    }

    @GetMapping(value = "/markets")
    public List<Market> getMarkets(){
        return Arrays.asList(Market.values());
    }

}
