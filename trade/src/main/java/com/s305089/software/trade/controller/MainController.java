package com.s305089.software.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.logging.OrderLogger;
import com.s305089.software.trade.model.Market;
import com.s305089.software.trade.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MainController {

    @Autowired
    private OrderDao dao;


    @GetMapping()
    public Iterable<Order> getOrders(){
        return dao.findAll();
    }

    @PostMapping
    public HttpStatus makeOrder(@RequestBody Order order) throws JsonProcessingException {
        order.setActive(true);
        order.calculateTotal();
        order = dao.save(order);
        OrderLogger.logToLogService(order);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/markets")
    public List<Market> getMarkets(){
        return Arrays.asList(Market.values());
    }

}
