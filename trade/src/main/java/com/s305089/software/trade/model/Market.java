package com.s305089.software.trade.model;

public enum Market {
    BTC_USD("BTC", "USD");

    private String mainCurrency;
    private String secondCurrency;


    Market(String mainCurrency, String secondCurrency) {
        this.mainCurrency = mainCurrency;
        this.secondCurrency = secondCurrency;
    }

    public String getMainCurrency() {
        return mainCurrency;
    }

    public String getSecondCurrency() {
        return secondCurrency;
    }

}
