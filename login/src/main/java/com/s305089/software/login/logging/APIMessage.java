package com.s305089.software.login.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class APIMessage implements Loggable{
    private String username;
    private Boolean authenticated;
    private Date timestamp;
    private String apiEndpoint;

    public APIMessage(String username, boolean authenticated, String apiEndpoint) {
        this.username = username;
        this.authenticated = authenticated;
        this.apiEndpoint = apiEndpoint;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }


}
