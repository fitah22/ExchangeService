package com.s305089.software.exchange.login.model;

import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.s305089.software.exchange.login.model.Currency.BTC;
import static com.s305089.software.exchange.login.model.Currency.USD;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Currency currency;
    private long balance;

    private Account() {
    }

    public Account(Currency currency, long balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public static Account newBTCAccount() {
        return new Account(BTC, 0);
    }

    public static Account newUSDAccount() {
        return new Account(USD, 0);
    }

}
