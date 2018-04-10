package com.s305089.software.history;

import com.s305089.software.history.dao.UserEventDao;
import com.s305089.software.history.model.APIEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    UserEventDao userEventDao;

    @PostMapping(value = "/user")
    public HttpStatus logUser(@RequestBody @Validated APIEvent apiEvent){
        System.out.println("Recived:" + apiEvent);
        apiEvent.setTimestamp(new Date());
        userEventDao.save(apiEvent);
        return HttpStatus.OK;
    }

    @GetMapping(value = "/user/{username}")
    public List<APIEvent> getAllUserEvents(@PathVariable String username){
        return userEventDao.findAllByUsername(username);
    }

    @GetMapping(value = "/user")
    public Iterable<APIEvent> getAllUserEvents(){
        return userEventDao.findAll();
    }

}
