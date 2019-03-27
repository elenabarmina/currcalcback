package com.pech.currcalcback.model.ratesapi;

public enum CurrencySymbols {

    RUB ("RUB"),
    USD ("USD");

    CurrencySymbols(String symbol){
        this.name = symbol;
    }

    private String name;

    public String getSymbol(){
        return this.name;
    }
}