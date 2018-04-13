package com.s305089.software.trade.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s305089.software.trade.dao.OrderDao;
import com.s305089.software.trade.logging.OrderLogger;
import com.s305089.software.trade.logic.BuyLogic;
import com.s305089.software.trade.logic.SellLogic;
import com.s305089.software.trade.logic.TradeLogic;
import com.s305089.software.trade.model.Market;
import com.s305089.software.trade.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.s305089.software.trade.model.TransactionType.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TradeController {

    @Autowired
    private OrderDao dao;


    @GetMapping()
    public Iterable<Order> getOrders() {

        Order order = new Order("hello", 15d, 1d, Market.BTC_USD, BUY);
        order.setActive(true);
        order.calculateTotal();
        dao.save(order);
        return dao.findAll();
    }



    @PostMapping
    public HttpStatus makeOrder(@RequestBody Order order) throws JsonProcessingException {

        order.setTimestamp(new Date());
        order = dao.save(order);

        //TODO: CHeck with login that funds are available
        //If buy: Withdraw money right away
        //If sell: Don't transfer money until transaction is made
        if(order.getTransactionType() == BUY){
            //Reserve money on account
            BuyLogic.sendBuyOrder(order.getUserID(), order.getTotal());

            //Preform transaction to match with sales
            TradeLogic.performTransaction(dao.findByActiveTrue(), order);

            //Post the new valuta from trade to account
            BuyLogic.exchangeValuta(order.getUserID(), order.getTradedAmount(), order.getMarket().getSecondCurrency());
        }else {
            //Preform transaction to match with bids/buys
            TradeLogic.performTransaction(dao.findByActiveTrue(), order);

            //Post the total that is traded to account
            SellLogic.sendSellOrder(order.getUserID(), order.getTradedTotal());
        }

        OrderLogger.logToLogService(order);
        return HttpStatus.OK;
    }


    @GetMapping(value = "/markets")
    public List<Market> getMarkets() {
        return Arrays.asList(Market.values());
    }

}
