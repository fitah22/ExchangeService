package com.s305089.software.login.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private long balance;

    private Account() {
    }

    public Account(Currency currency, long balance) {
        this.currency = currency;
        this.balance = balance;
    }

    public void deposit(long amount) {
        balance += amount;
    }
    public void withdraw(long amount) {
        balance -= amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
}
