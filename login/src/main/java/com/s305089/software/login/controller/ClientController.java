package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Account;
import com.s305089.software.login.model.Client;
import com.s305089.software.login.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//User profile service
public class ClientController {

    @Autowired
    ClientService service;


    @GetMapping("/claim")
    public ResponseEntity claimMoney(Principal principal) {
        Client client = service.findByEmail(principal.getName());
        if (client != null && !client.hasHasClamiedReward()) {

            Optional<Account> usd = client.getAccounts().stream()
                    .filter(account -> account.getCurrency() == Currency.USD)
                    .findFirst();

            Optional<Account> btc = client.getAccounts().stream()
                    .filter(account -> account.getCurrency() == Currency.BTC)
                    .findFirst();

            if (usd.isPresent() && btc.isPresent()) {
                usd.get().deposit(100);
                btc.get().deposit(100);
                client.setHasClamiedReward(true);
                return new ResponseEntity<>(service.save(client), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/user/{email}/password")
    //a. User can change their password
    public Object updatePassword(@PathVariable String email, @RequestBody String password) {
        Client client = service.findByEmail(email);
        if (client != null) {
            client.setPassword(password);
            return service.save(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping(value = "/user/{email}")
    //b. Address information update
    public Object updateAddress(@PathVariable String email, @RequestBody String address) {
        Client client = service.findByEmail(email);
        if (client != null) {
            client.setAddress(address);
            return service.saveWithoutPassword(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping(value = "/user/{email}/balance")
    //c. Information about the balance info(crypto)
    public Object accountBalance(@PathVariable String email) {
        Client client = service.findByEmail(email);
        if (client != null) {
            return client.getAccounts();
        }
        return HttpStatus.NOT_FOUND;
    }
}
