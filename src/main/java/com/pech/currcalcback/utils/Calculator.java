package com.pech.currcalcback.utils;

import com.pech.currcalcback.model.ratesapi.CurrencySymbols;
import com.pech.currcalcback.model.ratesapi.ExchangeRatesApiHelper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Stateless
public class Calculator {

    @Inject
    private ExchangeRatesApiHelper exchangeRatesApiHelper;

    public Calculator(){

    }

    public BigDecimal profitLossByDateForNow(LocalDate historicalDate,
                                             BigDecimal amount,
                                             CurrencySymbols baseCurrency,
                                             CurrencySymbols achievedCurrency){
        BigDecimal res = new BigDecimal(0);
        try {
            exchangeRatesApiHelper.getHistoricalRubRatesByDate(historicalDate,
                    baseCurrency.getSymbol(),
                    achievedCurrency.getSymbol());
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
