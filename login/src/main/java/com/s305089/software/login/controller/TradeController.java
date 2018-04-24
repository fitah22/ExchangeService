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

@CrossOrigin(origins = {"http://localhost:3000", "http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com"})
@RestController
public class TradeController {
    @Autowired
    ClientService clientService;

    @GetMapping(value = "/user/funds/{currency}/{amount}")
    public ResponseEntity checkFunds(@PathVariable Currency currency, @PathVariable BigDecimal amount, Principal principal) {
        Client client = clientService.findByEmail(principal.getName());
        if (client != null) {
            return  clientService.checkFunds(client, currency, amount);
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

        return clientService.withdrawFromAccountBasedOnOrder(client, clientOrderDTO.getCurrency(), clientOrderDTO.getAmount());
    }

    @PostMapping(value = "/payrecords")
    public ResponseEntity executePayRecords(@RequestBody List<PayRecordDTO> payRecords, Principal principal) {
        if (isNotTradeModule(principal)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        boolean payRecordsOK = clientService.payPayRecords(payRecords);
        if (!payRecordsOK) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();
    }


    private boolean isNotTradeModule(Principal principal) {
        return !principal.getName().equals("tradeuser@s305089.com");
    }

}
