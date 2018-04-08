package com.s305089.software.login.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

import java.io.UnsupportedEncodingException;

public class LogMessage {
    private String username;
    private boolean success;

    public LogMessage(String username, boolean authenticated) {
        this.username = username;
        this.success = authenticated;
    }

    public byte[] getMessageUTF8() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(this);
            System.out.println(s);

            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
