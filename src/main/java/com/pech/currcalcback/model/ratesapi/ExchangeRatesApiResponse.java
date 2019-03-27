package com.pech.currcalcback.model.ratesapi;

import java.util.Map;

public class ExchangeRatesApiResponse {

    String base;
    private Map<String, String> rates;
    String date;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRate(Map<String, String> rates) {
        this.rates = rates;
    }

    public void addRate(Map<String, String> rates) {
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
