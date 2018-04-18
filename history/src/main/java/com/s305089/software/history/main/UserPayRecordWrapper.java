package com.s305089.software.history.main;

import com.s305089.software.history.api.APIMessage;
import com.s305089.software.history.payrecord.PayRecordMessage;

import java.util.List;

public class UserPayRecordWrapper {

    private List<APIMessage> apiMessages;
    private List<PayRecordMessage> payrecords;

    public UserPayRecordWrapper(Iterable<APIMessage> allUserEvents, Iterable<PayRecordMessage> userEvents) {
    }

    public UserPayRecordWrapper(List<APIMessage> allUserEvents, List<PayRecordMessage> allUserEvents1) {

        this.apiMessages = allUserEvents;
        this.payrecords = allUserEvents1;
    }

    public List<APIMessage> getApiMessages() {
        return apiMessages;
    }

    public List<PayRecordMessage> getPayrecords() {
        return payrecords;
    }
}
