package com.s305089.software.login.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

public class LogMessage {
    private String username;
    private Boolean loginSuccess;
    private String apiEndpoint;

    public LogMessage(String username, boolean authenticated, String apiEndpoint) {
        this.username = username;
        this.loginSuccess = authenticated;
        this.apiEndpoint = apiEndpoint;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    @JsonIgnore
    byte[] getMessageUTF8() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(this);

            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
