package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.logging.ErrorLog;
import com.s305089.software.login.logging.HistoryConnector;
import com.s305089.software.login.model.*;
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

    @PostMapping(value = "/user/buy")
    public ResponseEntity withdrawIfHSufficientFunds(@RequestBody ClientOrderDTO clientOrderDTO, Principal principal) {
        if (principal.getName().equals("tradeuser@s305089.com")) {

            Client client = service.findByEmail(clientOrderDTO.getEmail());
            if (client != null) {
                Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(clientOrderDTO.getCurrency())).findFirst();
                if (accountOpt.isPresent()) {
                    Account account = accountOpt.get();
                    try {
                        account.withdraw(clientOrderDTO.getAmount());
                    } catch (IllegalAccountTransactionException e) {
                        HistoryConnector.logToLogService(new ErrorLog(client.getEmail(), e.getMessage()), "/error");
                        e.printStackTrace();
                    }
                    return new ResponseEntity(HttpStatus.OK);
                }
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping(value = "/user/sell")
    public ResponseEntity depositBecauseOfSell(@RequestBody ClientOrderDTO clientOrderDTO, Principal principal) {
        if (principal.getName().equals("tradeuser@s305089.com")) {

            Client client = service.findByEmail(clientOrderDTO.getEmail());
            if (client != null) {
                Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(clientOrderDTO.getCurrency())).findFirst();
                Account account = accountOpt.orElseGet(() -> Account.newFromCurrency(clientOrderDTO.getCurrency()));
                account.deposit(clientOrderDTO.getAmount());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

}
