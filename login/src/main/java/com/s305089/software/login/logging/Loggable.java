package com.s305089.software.login.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

public interface Loggable {
    @JsonIgnore
    default byte[] getMessageUTF8() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(this);

            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
