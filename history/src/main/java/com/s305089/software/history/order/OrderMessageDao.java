package com.s305089.software.history.order;

import com.s305089.software.history.payrecord.PayRecordMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OrderMessageDao extends CrudRepository<OrderMessage, Integer> {
    List<OrderMessage> findAllByUserID(String userID);
}
