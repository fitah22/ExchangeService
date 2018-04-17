package com.s305089.software.login.controller;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.logging.ErrorMessage;
import com.s305089.software.login.logging.HistoryConnector;
import com.s305089.software.login.logging.PayRecordMessage;
import com.s305089.software.login.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
//User profile clientService
public class ClientController {

    @Autowired
    ClientService clientService;


    @GetMapping("/claim")
    public ResponseEntity claimMoney(Principal principal) {
        Client client = clientService.findByEmail(principal.getName());
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
                return new ResponseEntity<>(clientService.save(client), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/user/{email}/password")
    //a. User can change their password
    public Object updatePassword(@PathVariable String email, @RequestBody String password) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setPassword(password);
            return clientService.save(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    @PostMapping(value = "/user/{email}")
    //b. Address information update
    public Object updateAddress(@PathVariable String email, @RequestBody String address) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            client.setAddress(address);
            return clientService.saveWithoutPassword(client);
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping(value = "/user/{email}/balance")
    //c. Information about the balance info(crypto)
    public Object accountBalance(@PathVariable String email) {
        Client client = clientService.findByEmail(email);
        if (client != null) {
            return client.getAccounts();
        }
        return HttpStatus.NOT_FOUND;
    }

    @GetMapping(value = "/user/funds/{currency}/{amount}")
    public ResponseEntity checkFunds(@PathVariable Currency currency, @PathVariable BigDecimal amount, Principal principal) {
        Client client = clientService.findByEmail(principal.getName());
        if (client != null) {
            Optional<Account> account = client.getAccounts().stream().filter(a -> a.getCurrency() == currency).findFirst();
            if (account.isPresent()) {
                BigDecimal balance = account.get().getBalance();
                if (balance.compareTo(amount) > 0) {
                    return ResponseEntity.ok().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/reservefunds")
    public ResponseEntity reserveBasedOnOrder(@RequestBody ClientOrderDTO clientOrderDTO, Principal principal) {
        if (isNotTradeModule(principal)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Client client = clientService.findByEmail(clientOrderDTO.getEmail());
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return withdrawFromAccountBasedOnOrder(client, clientOrderDTO.getCurrency(), clientOrderDTO.getAmount());
    }

    private ResponseEntity withdrawFromAccountBasedOnOrder(Client client, Currency currency, Double amount) {
        Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(currency)).findFirst();
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            try {
                account.withdraw(amount);
                clientService.saveWithoutPassword(client);
            } catch (IllegalAccountTransactionException e) {
                HistoryConnector.logToLogService(new ErrorMessage(client.getEmail(), e.getMessage()), "/error");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping(value = "/payrecords")
    public ResponseEntity executePayRecords(@RequestBody List<PayRecordDTO> payRecords, Principal principal) {
        if (isNotTradeModule(principal)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<Client> clients = new ArrayList<>(payRecords.size());
        for (PayRecordDTO payRecord : payRecords) {
            Client client = clientService.findByEmail(payRecord.getEmail());
            if (client == null) {
                //This should really not happen
                HistoryConnector.logToLogService(new ErrorMessage(payRecord.getEmail(), "Not found when executing pay records"), "/error");
                return ResponseEntity.notFound().build();
            }
            clients.add(client);
            depositAmountBasedOnPayroll(client, payRecord.getCurrency(), payRecord.getAmount());
        }

        HistoryConnector.logToLogService(new PayRecordMessage(payRecords), "/payrecords");
        clientService.saveAll(clients);
        return ResponseEntity.ok().build();
    }

    private void depositAmountBasedOnPayroll(Client client, Currency currency, BigDecimal amount) {
        Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(currency)).findFirst();
        Account account = accountOpt.orElseGet(() -> Account.newFromCurrency(currency));
        account.deposit(amount);
    }

    private boolean isNotTradeModule(Principal principal) {
        return !principal.getName().equals("tradeuser@s305089.com");
    }
}
