package com.s305089.software.history.main;

import com.s305089.software.history.api.UserController;
import com.s305089.software.history.error.ErrorContoller;
import com.s305089.software.history.payrecord.PayRecordContoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    UserController userController;
    @Autowired
    PayRecordContoller payRecordContoller;

    @GetMapping(value = "/all/")
    public UserPayRecordWrapper getAll(){
        return new UserPayRecordWrapper(userController.getAllUserEvents(), payRecordContoller.getAllUserEvents());
    }


    @GetMapping(value = "/all/{email}")
    public UserPayRecordWrapper getAll(@PathVariable String email){
        return new UserPayRecordWrapper(userController.getAllUserEvents(email), payRecordContoller.getAllUserEvents(email));
    }

}
