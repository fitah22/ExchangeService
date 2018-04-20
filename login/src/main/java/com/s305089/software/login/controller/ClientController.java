package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//User profile clientService
public class ClientController {

    @Autowired
    ClientService clientService;

    //a. User can change their password
    @PatchMapping(value = "/user/password")
    public ResponseEntity updatePassword(@RequestBody String password, Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setPassword(password);
            return ResponseEntity.ok(clientService.save(client));
        }
        return ResponseEntity.notFound().build();
    }

    //b. Address information update
    @PatchMapping(value = "/user/address")
    public ResponseEntity updateAddress(@RequestBody String address, Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setAddress(address);
            return ResponseEntity.ok(clientService.saveWithoutPassword(client));
        }
        return ResponseEntity.notFound().build();
    }

    //c. Information about the balance info(crypto)
    @GetMapping(value = "/user/balance")
    public ResponseEntity accountBalance(Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client.getAccounts());
        }
        return ResponseEntity.notFound().build();
    }

}
