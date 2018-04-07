package com.s305089.software.exchange.login;

import com.s305089.software.exchange.login.dao.ClientService;
import com.s305089.software.exchange.login.model.Account;
import com.s305089.software.exchange.login.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/claim")
    public String claimMoney(Principal principal){
        Client client = service.findByEmail(principal.getName());
        if (client != null && !client.hasHasClamiedReward()) {
            client.getAccounts().forEach(account -> account.deposit(100));
            client.setHasClamiedReward(true);
            service.save(client);
        }
        return "redirect:";
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
