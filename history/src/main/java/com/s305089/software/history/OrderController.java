package com.s305089.software.history;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {

    @PostMapping(value = "/order")
    public void logOrder(@RequestBody String order){
        System.out.println(order);
    }
}
