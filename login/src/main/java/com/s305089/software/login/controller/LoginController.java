package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Account;
import com.s305089.software.login.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = {"http://localhost:3000", "http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com"})
@RestController
public class LoginController {
    private static final Logger log = LogManager.getLogger();

    private final ClientService service;
    @Autowired
    public LoginController(ClientService service) {
        this.service = service;
    }

    @PostMapping(value = "/login")
    public Client login(Principal principal){
        //User is already authenticated at this point.
        String email = principal.getName();
        return service.findByEmail(email);
        //Dummy method to check if username and password is correct.
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Client> signup(@RequestBody Client newClient) {
        if(newClient.getEmail() == null || newClient.getPassword() == null || newClient.getAddress() == null) {
            log.info("New signup is not accepted: {}", newClient);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        Client client = service.findByEmail(newClient.getEmail());
        if (client == null) {
            Account btcAccount = Account.newBTCAccount();
            Account usdAccount = Account.newUSDAccount();
            btcAccount.deposit(100);
            usdAccount.deposit(100);
            newClient.addAccount(btcAccount);
            newClient.addAccount(usdAccount);
            newClient = service.save(newClient);
            log.info("New client has signed up: {}", newClient);
            return ResponseEntity.ok(newClient);
        }
        log.info("Client already exists: {}", client);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @RequestMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<Client> getall() {
        return service.findAll();
    }
}
