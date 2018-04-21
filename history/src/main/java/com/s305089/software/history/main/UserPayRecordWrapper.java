package com.s305089.software.history.main;

import com.s305089.software.history.api.APIMessage;
import com.s305089.software.history.payrecord.PayRecordMessage;

import java.util.ArrayList;
import java.util.List;

public class UserPayRecordWrapper {

    private Iterable<APIMessage> apiMessages;
    private Iterable<PayRecordMessage> payrecords;

    public UserPayRecordWrapper(Iterable<APIMessage> allUserEvents, Iterable<PayRecordMessage> payRecordMessages) {

        this.apiMessages = allUserEvents == null ? new ArrayList<>() : allUserEvents;
        this.payrecords = payRecordMessages == null ? new ArrayList<>() : payRecordMessages;
    }

    public Iterable<APIMessage> getApiMessages() {
        return apiMessages;
    }

    public Iterable<PayRecordMessage> getPayrecords() {
        return payrecords;
    }
}
