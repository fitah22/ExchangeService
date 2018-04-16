package com.s305089.software.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.math.BigDecimal;

import static com.s305089.software.login.model.Currency.BTC;
import static com.s305089.software.login.model.Currency.USD;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty
    private Currency currency;
    @JsonProperty
    private BigDecimal balance;

    private Account() {
    }

    public Account(Currency currency, long balance) {
        this.currency = currency;
        this.balance = new BigDecimal(balance);
    }


    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void deposit(double amount) {
        deposit(new BigDecimal(amount));
    }

    public void withdraw(BigDecimal amount) throws IllegalAccountTransactionException {
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalAccountTransactionException("Balance can not be negative");
        }
        balance = newBalance;
    }

    public void withdraw(double amount) throws IllegalAccountTransactionException {
        withdraw(new BigDecimal(amount));
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", currency=" + currency +
                ", balance=" + balance +
                '}';
    }


    public static Account newBTCAccount() {
        return new Account(BTC, 0);
    }

    public static Account newUSDAccount() {
        return new Account(USD, 0);
    }

    public static Account newFromCurrency(Currency currency) {
        return new Account(currency, 0);
    }

}
