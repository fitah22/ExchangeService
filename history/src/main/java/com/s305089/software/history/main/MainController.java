package com.s305089.software.history.main;

import com.s305089.software.history.api.UserController;
import com.s305089.software.history.error.ErrorContoller;
import com.s305089.software.history.payrecord.PayRecordContoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MainController {

    @Autowired
    UserController userController;
    @Autowired
    PayRecordContoller payRecordContoller;

    //Return all user and payrecords. NOT errors.
    @GetMapping(value = "/all")
    public UserPayRecordWrapper getAll(){
        return new UserPayRecordWrapper(userController.getAllUserEvents(), payRecordContoller.getAllUserEvents());
    }


    @GetMapping(value = "/all/{email}")
    public UserPayRecordWrapper getAll(@PathVariable String email){
        return new UserPayRecordWrapper(userController.getAllUserEvents(email), payRecordContoller.getAllUserEvents(email));
    }

}
