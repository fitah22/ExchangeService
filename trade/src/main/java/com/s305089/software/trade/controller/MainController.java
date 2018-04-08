package com.s305089.software.trade.controller;

import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.model.Market;
import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.s305089.software.trade.model.Market.*;
import static com.s305089.software.trade.model.TransactionType.*;

@RestController
public class MainController {

    @Autowired
    private OrderDao dao;

    @PostMapping
    public Order makeOrder(@RequestBody Order order){
        order = new Order.OrderBuilder()
                .setUserID(1)
                .setAmount(15.0)
                .setPrice(6358.0)
                .setMarket(BTC_USD)
                .setTransactionType(BUY)
                .build();
        return dao.save(order);
    }

    @GetMapping()
    public List<Market> getMarkets(){
        return Arrays.asList(Market.values());
    }

}
