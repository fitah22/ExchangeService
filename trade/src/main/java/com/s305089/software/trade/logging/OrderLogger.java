package com.s305089.software.trade.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s305089.software.trade.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class OrderLogger {

    private static String url;

    private static final Logger log = LogManager.getRootLogger();

    public static void logToLogService(Order order) {
        new Thread(() -> sendLogmessage(order)).start();
    }

    private static void sendLogmessage(Order order) {
        String charset = "UTF-8";
        try {
            URLConnection connection = new URL(url + "/order").openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

            ObjectMapper mapper = new ObjectMapper();
            String orderJSON = mapper.writeValueAsString(order);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(orderJSON.getBytes(charset));
            }

            InputStream response = connection.getInputStream();
        } catch (IOException e) {
            log.error("Could not connect to logging server");
        }
    }

    @Value("${logginservice.url}")
    public void setUrl(String url) {
        OrderLogger.url = url;
    }
}
