package com.pech.currcalcback.model.ratesapi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExchangeRatesApiResponse {

    String base;
    private Map<String, BigDecimal> rates;
    Date date;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRate(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public void addRate(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
