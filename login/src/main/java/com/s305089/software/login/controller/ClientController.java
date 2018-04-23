package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Client;
import com.s305089.software.login.model.Currency;
import com.s305089.software.login.model.SingleValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//User profile clientService
public class ClientController {

    @Autowired
    ClientService clientService;

    //a. User can change their password
    @PatchMapping(value = "/user/password")
    public ResponseEntity updatePassword(@RequestBody SingleValueDTO password, Principal principal) {
        Objects.requireNonNull(password);
        if(password.value.trim().equals("")) return  ResponseEntity.notFound().build();

        String email = principal.getName();
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setPassword(password.value);
            return ResponseEntity.ok(clientService.save(client));
        }
        return ResponseEntity.notFound().build();
    }

    //b. Address information update
    @PatchMapping(value = "/user/address")
    public ResponseEntity updateAddress(@RequestBody SingleValueDTO address, Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setAddress(address.value);
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

    @PostMapping(value = "user/newAccountAndClaim")
    public ResponseEntity newAccountAndClaim(@RequestBody SingleValueDTO currencyDTO, Principal principal){
        Currency currency = Currency.valueOf(currencyDTO.value);
        return clientService.newAccountAndClaim(principal.getName(), currency, 50d);
    }

    @GetMapping(value = "currencies")
    public ResponseEntity getCurrencies() {
        return ResponseEntity.ok(Currency.values());
    }

}
