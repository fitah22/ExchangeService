package com.s305089.software.login.dao;

import com.s305089.software.login.logging.ErrorMessage;
import com.s305089.software.login.logging.HistoryConnector;
import com.s305089.software.login.logging.PayRecordMessage;
import com.s305089.software.login.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private static final Logger log = LogManager.getRootLogger();

    private ClientDao dao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity checkFunds(Client client, Currency currency, BigDecimal amount) {
        Optional<Account> account = client.getAccounts().stream().filter(a -> a.getCurrency() == currency).findFirst();
        if (account.isPresent()) {
            BigDecimal balance = account.get().getBalance();
            if (balance.compareTo(amount) >= 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Withdraws the given amount from clients account
     * @param client The client from whom the amount should be withdrawn
     * @param currency The currency of the amount
     * @param amount
     * @return
     */
    public ResponseEntity withdrawFromAccountBasedOnOrder(Client client, Currency currency, Double amount) {
        Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(currency)).findFirst();
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            try {
                account.withdraw(amount);
                this.saveWithoutPassword(client);
            } catch (IllegalAccountTransactionException e) {
                HistoryConnector.logToLogService(new ErrorMessage(client.getEmail(), e.getMessage()), "/error");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public boolean payPayRecords(List<PayRecordDTO> payRecords) {
        List<Client> clients = new ArrayList<>(payRecords.size());
        for (PayRecordDTO payRecord : payRecords) {
            Client client = this.findByEmail(payRecord.getEmail());
            if (client == null) {
                //This should really not happen, as this means the user doesn't exist
                HistoryConnector.logToLogService(new ErrorMessage(payRecord.getEmail(), "Client not found when executing pay records"), "/error");
                return false;
            }
            clients.add(client);
            depositAmountBasedOnPayroll(client, payRecord.getCurrency(), payRecord.getTotal());
        }

        HistoryConnector.logToLogService(new PayRecordMessage(payRecords), "/payrecords");
        this.saveAll(clients);
        return true;
    }

    private void depositAmountBasedOnPayroll(Client client, Currency currency, BigDecimal amount) {
        Optional<Account> accountOpt = client.getAccounts().stream().filter(a -> a.getCurrency().equals(currency)).findFirst();
        Account account = accountOpt.orElseGet(() -> Account.newFromCurrency(currency));
        account.deposit(amount);
    }


    public Client findByEmail(String email) {
        return dao.findByEmail(email);
    }


    public <S extends Client> S save(S entity) {
        String password = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(password);
        return dao.save(entity);
    }

    public <S extends Client> S saveWithoutPassword(S entity) {
        return dao.save(entity);
    }

    public <S extends Client> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        for (S entity : entities) {
            savedEntities.add(saveWithoutPassword(entity));
        }

        return savedEntities;
    }

    public Optional<Client> findById(Integer integer) {
        return dao.findById(integer);
    }


    public boolean existsById(Integer integer) {
        return dao.existsById(integer);
    }


    public Iterable<Client> findAll() {
        return dao.findAll();
    }

    public long count() {
        return dao.count();
    }


    public void deleteById(Integer integer) {
        dao.deleteById(integer);
    }


    public void delete(Client entity) {
        dao.delete(entity);
    }


    public void deleteAll(Iterable<? extends Client> entities) {
        dao.deleteAll(entities);
    }


    public void deleteAll() {
        dao.deleteAll();
    }
}
