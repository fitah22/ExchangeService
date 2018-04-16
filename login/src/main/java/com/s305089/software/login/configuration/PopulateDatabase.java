package com.s305089.software.login.configuration;

import com.s305089.software.login.dao.ClientService;
import com.s305089.software.login.model.Account;
import com.s305089.software.login.model.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PopulateDatabase {
    private static final Logger log = LogManager.getLogger();

    private final ClientService service;

    @Autowired
    public PopulateDatabase(ClientService service) {
        this.service = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateWithTestData() {
        Client client = new Client();
        client.setEmail("hello");
        client.setPassword("123");
        Account btcAccount = Account.newBTCAccount();
        Account usdAccount = Account.newUSDAccount();
        btcAccount.deposit(100);
        usdAccount.deposit(100);
        client.addAccount(btcAccount);
        client.addAccount(usdAccount);
        service.save(client);
        log.info("Database is now populated with user 'hello'");

        Client tradeModule = new Client();
        tradeModule.setEmail("tradeuser@s305089.com");
        tradeModule.setPassword("superSecretPassword");
        tradeModule.setAddress("OsloMet P35");
        service.save(tradeModule);
        log.info("Database is now populated with user '{}'", tradeModule.getEmail());
    }

}
