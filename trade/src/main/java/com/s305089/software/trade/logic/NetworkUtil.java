package com.s305089.software.trade.logic;

import com.s305089.software.trade.model.Market;
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

import static com.s305089.software.trade.model.TransactionType.*;

@Component
public class NetworkUtil {
    private static String userURL;

    public static boolean sendPayRecordsToUserAndHistoryService(List<PayRecord> records) {
        HttpEntity<Object> requestBody = new HttpEntity<>(records, createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword"));
        return doRESTCall(userURL + "/payrecords", HttpMethod.POST, requestBody);
    }

    public static boolean checkFunds(String username, String password, Order order) {
        String currency;

        if (order.getTransactionType() == BUY) {
            currency = order.getMarket().getSecondCurrency(); //Ex: If we buy BTC, check that we have enough USD
        } else {
            currency = order.getMarket().getMainCurrency(); //EX: If we sell BTC, check that we have enough BTC
        }

        String url = userURL + "/user/funds/" + currency + "/" + order.getTotal();


        HttpEntity<Object> requestBody = new HttpEntity<>(createHeadersAsUser(username, password));
        return doRESTCall(url, HttpMethod.GET, requestBody);
    }


    public static boolean sendReserveOrder(String userId, Market market, BigDecimal total, TransactionType transactionType) {
        String url = userURL + "/reservefunds/";
        String currency;
        if (transactionType == BUY) {
            //url += "buy/";
            currency = market.getSecondCurrency();
        } else {
            //url += "sell/";
            currency = market.getMainCurrency();
        }


        OrderDTO buyOrder = new OrderDTO(userId, currency, total);
        HttpHeaders header = createHeadersAsUser("tradeuser@s305089.com", "superSecretPassword");


        return doRESTCall(url, HttpMethod.POST, new HttpEntity<>(buyOrder, header));
    }

    private static HttpHeaders createHeadersAsUser(String username, String password) {
        String auth = username + ":" + password;

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", authHeader);

        return httpHeaders;
    }

    private static boolean doRESTCall(String url, HttpMethod method, HttpEntity entity) {
        try {
            //Will cast exception if statuscode > 400
            new RestTemplate().exchange(url, method, entity, String.class);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Value("${userservice.url}")
    public void setUserUrl(String url) {
        NetworkUtil.userURL = url;
    }
}
