package com.s305089.software.login.model;


public class ClientOrderDTO {
    private String email;
    private Currency currency;
    private Double amount;

    public ClientOrderDTO() {
    }

    public ClientOrderDTO(String email, Currency currency, double amount) {
        this.email = email;
        this.currency = currency;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClientOrderDTO{ currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
