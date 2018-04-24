package com.s305089.software.history.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com"})
@RestController
public class UserController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    APIMessageDao APIMessageDao;

    @PostMapping(value = "/user")
    public void logUser(@RequestBody @Validated APIMessage apiMessage){
        APIMessageDao.save(apiMessage);
        log.info("APIMessage saved {}", apiMessage);
    }

    @GetMapping(value = "/user/{username}")
    public List<APIMessage> getAllUserEvents(@PathVariable String username){
        return APIMessageDao.findAllByUsername(username);
    }

    @GetMapping(value = "/user")
    public Iterable<APIMessage> getAllUserEvents(){
        return APIMessageDao.findAll();
    }

}
