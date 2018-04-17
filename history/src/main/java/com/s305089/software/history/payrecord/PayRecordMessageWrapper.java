package com.s305089.software.history.payrecord;

import java.util.List;

class PayRecordMessageWrapper {
    private List<PayRecordMessage> payRecords;

    public List<PayRecordMessage> getPayRecords() {
        return payRecords;
    }

    public void setPayRecords(List<PayRecordMessage> payRecords) {
        this.payRecords = payRecords;
    }
}
