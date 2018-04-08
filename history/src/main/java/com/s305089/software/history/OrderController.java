package com.s305089.software.history;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping(value = "/order")
    public void logOrder(@RequestBody String order){
        System.out.println(order);
    }
}
