package com.s305089.software.trade.dao;

import com.s305089.software.trade.model.PayRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRecordDao extends CrudRepository<PayRecord, Integer> {
}
