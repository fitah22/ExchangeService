package com.s305089.software.history.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com"})
@RestController
public class OrderController {

    @Autowired
    OrderMessageDao orderMessageDao;
    private static final Logger log = LogManager.getLogger();

    @PostMapping(value = "/order")
    public void logOrder(@RequestBody OrderMessage order) {
        orderMessageDao.save(order);
        log.info("{} order saved", order);
    }

    @GetMapping(value = "/order/{username}")
    public List<OrderMessage> getAllUserEvents(@PathVariable String username) {
        return orderMessageDao.findAllByUserID(username);
    }

    @GetMapping(value = "/order")
    public Iterable<OrderMessage> getAllUserEvents() {
        return orderMessageDao.findAll();
    }
}
