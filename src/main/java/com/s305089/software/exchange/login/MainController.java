package com.s305089.software.exchange.login;

import com.s305089.software.exchange.login.dao.UserService;
import com.s305089.software.exchange.login.model.Account;
import com.s305089.software.exchange.login.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    UserService service;

    @RequestMapping("/")
    public String getIndex(){
        Client client = new Client();
        client.setEmail("hello");
        client.setPassword("123");
        client.addAccount(Account.newBTCAccount());
        client.addAccount(Account.newUSDAccount());
        service.save(client);
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
