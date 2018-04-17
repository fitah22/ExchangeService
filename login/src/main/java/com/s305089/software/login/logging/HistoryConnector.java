package com.s305089.software.login.logging;

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
public class HistoryConnector {
    private static final Logger log = LogManager.getLogger();
    private static String url;

    public static void logToLogService(Loggable logMessage, String loggingPath) {
        new Thread(() -> {
            String charset = "UTF-8";
            try {
                URLConnection connection = new URL(url + loggingPath).openConnection();
                connection.setDoOutput(true); // Triggers POST.
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

                try (OutputStream output = connection.getOutputStream()) {
                    output.write(logMessage.getMessageUTF8());
                }

                connection.getInputStream();
                log.info("Log message sent of type: {}", logMessage.getClass().getSimpleName());
            } catch (IOException e) {
                log.error("Could not connect to logging server or error. Message of type {}", logMessage.getClass().getSimpleName());
            }
        }).start();
    }

    @Value("${logginservice.url}")
    public void setUrl(String url) {
        HistoryConnector.url = url;
    }
}
