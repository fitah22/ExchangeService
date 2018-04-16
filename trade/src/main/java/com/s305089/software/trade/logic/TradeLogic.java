package com.s305089.software.trade.logic;

import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.PayRecord;
import com.s305089.software.trade.model.Transaction;
import com.s305089.software.trade.model.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TradeLogic {

    private static String historyURL;
    private static String userURL;

    /**
     * Tries to fulfill the order given, with those orders who already are active. The users accounts will be notified and charged/payed accordingly.
     *
     * @param allActiveOrders All current active orders (most likely from the database)
     * @param orderToFullfill The order we want to trade
     * @return A list of orders with updated values ready to be saved to database. T
     */
    public static Transaction performTransaction(List<Order> allActiveOrders, Order orderToFullfill) {
        List<Order> ordersThatCanBeTradedWith = allActiveOrders
                .stream()
                .filter(order -> order.getMarket().equals(orderToFullfill.getMarket()))
                .filter(order -> order.getTransactionType().isOpposit(orderToFullfill.getTransactionType()))
                .filter(order -> order.getRemainingAmount().compareTo(new BigDecimal(0)) > 0)
                .collect(Collectors.toList());

        return makeTransaction(orderToFullfill, ordersThatCanBeTradedWith);

    }

    /**
     * Construct a transaction that contains orders it can be traded with
     *
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
            //NB: We might add orders so we get above the amount to fulfill, this is OK and taken care of when we fulfill the transaction.
            for (int i = 0; amount.compareTo(orderToFullfill.getAmount()) < 0 && i < ordersThatCanBeTradedWith.size(); i++) {
                Order order = ordersThatCanBeTradedWith.get(i);
                amount = amount.add(order.getRemainingAmount());
                ordersToMakeTransaction.add(order);
            }
            transaction = new Transaction(orderToFullfill, ordersToMakeTransaction);
        }

        return transaction;
    }


    public static boolean sendPayRecordsToUserAndHistoryService(List<PayRecord> records) {


        HttpEntity<Object> requestBody = new HttpEntity<>(records, Util.createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword"));
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<String> responseUser = restTemplate.exchange(userURL + "/payrecords", HttpMethod.POST, requestBody, String.class);
        //ResponseEntity<String> responseHistory = restTemplate.exchange(historyURL + "/payrecords", HttpMethod.POST, requestBody, String.class);
        return true;
    }


    public static boolean checkFunds(String username, String password, Order order) {
        String currency;

        if (order.getTransactionType() == TransactionType.BUY) {
            currency = order.getMarket().getSecondCurrency(); //Ex: If we buy BTC, check that we have enough USD
        } else {
            currency = order.getMarket().getSecondCurrency(); //EX: If we sell BTC, check that we have enough BTC
        }

        String url = userURL + "/user/funds/" + currency + "/" + order.getTotal();


        HttpEntity<Object> requestBody = new HttpEntity<>(Util.createHeadersAsUser(username, password));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseHistory = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class);

        return responseHistory.getStatusCode().is2xxSuccessful();
    }

    public static boolean sendBuyOrder(String userId, String currency, BigDecimal total) {
        String url = userURL + "/user/buy/";
        BuyOrderDTO buyOrder = new BuyOrderDTO(userId, currency, total);
        HttpHeaders header = Util.createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword");


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> s = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(buyOrder, header), String.class);

        return s.getStatusCode().is2xxSuccessful();
    }


    @Value("${historyservice.url}")
    public void setHistoryUrl(String url) {
        TradeLogic.historyURL = url;
    }

    @Value("${userservice.url}")
    public void setUserUrl(String url) {
        TradeLogic.userURL = url;
    }
}
