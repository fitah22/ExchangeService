package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Account;
import com.s305089.software.login.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    ClientService service;

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

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<Client> getall(){
        return  service.findAll();
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
