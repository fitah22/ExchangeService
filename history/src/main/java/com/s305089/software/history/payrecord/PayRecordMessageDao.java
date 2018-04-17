package com.s305089.software.history.payrecord;

import com.s305089.software.history.payrecord.PayRecordMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface PayRecordMessageDao extends CrudRepository<PayRecordMessage, Integer> {
    List<PayRecordMessage> findAllByEmail(String email);
}
