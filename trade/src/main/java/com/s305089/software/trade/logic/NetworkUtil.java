package com.s305089.software.trade.logic;

import com.s305089.software.trade.model.Order;
import com.s305089.software.trade.model.PayRecord;
import com.s305089.software.trade.model.TransactionType;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class NetworkUtil {
    private static String historyURL;
    private static String userURL;

    private static HttpHeaders createHeadersAsUser(String username, String password) {
        String auth = username + ":" + password;

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", authHeader);

        return httpHeaders;
    }

    public static boolean sendPayRecordsToUserAndHistoryService(List<PayRecord> records) {


        HttpEntity<Object> requestBody = new HttpEntity<>(records, createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword"));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseUser = restTemplate.exchange(userURL + "/payrecords", HttpMethod.POST, requestBody, String.class);
        ResponseEntity<String> responseHistory = restTemplate.exchange(historyURL + "/payrecords", HttpMethod.POST, requestBody, String.class);
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


        HttpEntity<Object> requestBody = new HttpEntity<>(createHeadersAsUser(username, password));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseHistory = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class);

        return responseHistory.getStatusCode().is2xxSuccessful();
    }

    public static boolean sendBuyOrder(String userId, String currency, BigDecimal total) {
        String url = userURL + "/user/buy/";
        BuyOrderDTO buyOrder = new BuyOrderDTO(userId, currency, total);
        HttpHeaders header = createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword");


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> s = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(buyOrder, header), String.class);

        return s.getStatusCode().is2xxSuccessful();
    }

    @Value("${historyservice.url}")
    public void setHistoryUrl(String url) {
        NetworkUtil.historyURL = url;
    }

    @Value("${userservice.url}")
    public void setUserUrl(String url) {
        NetworkUtil.userURL = url;
    }
}
