package com.s305089.software.trade.logic;

import com.s305089.software.trade.logging.OrderLogger;
import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.PayRecord;
import com.s305089.software.trade.model.Transaction;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


        HttpEntity<Object> requestBody = new HttpEntity<>(records, createHeaders("tradeuser@s305089.com", "superSecretPassword"));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseUser = restTemplate.exchange(userURL, HttpMethod.POST, requestBody, String.class);
        ResponseEntity<String> responseHistory = restTemplate.exchange(historyURL, HttpMethod.POST, requestBody, String.class);

    }


    public static HttpHeaders createHeaders(String username, String password) {
        String auth = username + ":" + password;

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", authHeader);

        return httpHeaders;
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
