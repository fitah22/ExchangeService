package com.s305089.software.login.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
public class AuthenticationLogger implements ApplicationListener<AbstractAuthenticationEvent> {

    @Value("${logginservice.url}")
    private String url;

    private static final Logger log = LogManager.getRootLogger();

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent authenticationEvent) {
        if (authenticationEvent instanceof InteractiveAuthenticationSuccessEvent) {
            // ignores to prevent duplicate logging with AuthenticationSuccessEvent
            return;
        }
        Authentication authentication = authenticationEvent.getAuthentication();

        //https://stackoverflow.com/a/43982356/
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        new Thread(() ->
                logToLogService(new LogMessage(authentication.getName(), authentication.isAuthenticated(), request.getServletPath()))
        ).start();

    }


    private void logToLogService(LogMessage logMessage) {
        String charset = "UTF-8";
        try {
            URLConnection connection = new URL(url + "/user").openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(logMessage.getMessageUTF8());
            }

            InputStream response = connection.getInputStream();
        } catch (IOException e) {
            log.error("Could not connect to logging server");
        }
    }

}
