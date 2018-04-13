package com.s305089.software.trade.logic;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;

public class BuyLogic {


    public static void exchangeValuta(String userID, BigDecimal tradedAmount, String secondCurrency) {
    }



    public static void sendBuyOrder(String userID, BigDecimal total) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> s = restTemplate.exchange
                ("http://localhost:8080", HttpMethod.POST, new HttpEntity<>(TradeLogic.createHeaders("tradeuser@s305089.com", "superSecretPassword")), String.class);

    }

}
