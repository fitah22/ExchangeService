package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//User profile clientService
public class ClientController {

    @Autowired
    ClientService clientService;

    //a. User can change their password
    @PostMapping(value = "/user/{email}/password")
    public Object updatePassword(@PathVariable String email, @RequestBody String password) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setPassword(password);
            return clientService.save(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    //b. Address information update
    @PostMapping(value = "/user/{email}")
    public Object updateAddress(@PathVariable String email, @RequestBody String address) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setAddress(address);
            return clientService.saveWithoutPassword(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    //c. Information about the balance info(crypto)
    @GetMapping(value = "/user/{email}/balance")
    public Object accountBalance(@PathVariable String email) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            return client.getAccounts();
        }
        return HttpStatus.NOT_FOUND;
    }

}
