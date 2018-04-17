package com.s305089.software.login.logging;

import com.s305089.software.login.model.PayRecordDTO;

import java.util.List;

public class PayRecordMessage implements Loggable {
    private List<PayRecordDTO> payRecords;

    public PayRecordMessage(List<PayRecordDTO> payRecords) {
        this.payRecords = payRecords;
    }

    public List<PayRecordDTO> getPayRecords() {
        return payRecords;
    }
}
