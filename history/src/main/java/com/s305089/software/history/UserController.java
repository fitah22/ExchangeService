package com.s305089.software.history;

import com.s305089.software.history.dao.UserEventDao;
import com.s305089.software.history.model.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserEventDao userEventDao;

    @PostMapping(value = "/user")
    public HttpStatus logUser(@RequestBody @Validated UserEvent userEvent){
        userEvent.setTimestamp(new Date());
        userEventDao.save(userEvent);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/user/{username}")
    public List<UserEvent> getAllUserEvents(@PathVariable String username){
        return userEventDao.findAllByUsername(username);
    }

    @GetMapping(value = "/user")
    public Iterable<UserEvent> getAllUserEvents(){
        return userEventDao.findAll();
    }

}
