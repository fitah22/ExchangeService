package com.s305089.software.history.payrecord;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PayRecordContoller {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    PayRecordMessageDao payRecordMessageDao;

    @PostMapping(value = "/payrecords")
    public void logOrder(@RequestBody PayRecordMessageWrapper payRecordMessages) {
        List<PayRecordMessage> records = payRecordMessages.getPayRecords();
        payRecordMessageDao.saveAll(records);
        log.info("{} payrecord(s) saved", records.size());
    }

    @GetMapping(value = "/payrecords/{username}")
    public List<PayRecordMessage> getAllUserEvents(@PathVariable String username) {
        return payRecordMessageDao.findAllByEmail(username);
    }

    @GetMapping(value = "/payrecords")
    public Iterable<PayRecordMessage> getAllUserEvents() {
        return payRecordMessageDao.findAll();
    }
}
