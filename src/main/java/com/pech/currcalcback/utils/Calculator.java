package com.pech.currcalcback.utils;

import com.pech.currcalcback.model.ratesapi.CurrencySymbols;
import com.pech.currcalcback.model.ratesapi.ExchangeRatesApiHelper;
import com.pech.currcalcback.model.ratesapi.ExchangeRatesApiResponse;

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
                                             CurrencySymbols quotedCurrency){
        BigDecimal res = new BigDecimal(0);
        try {


            ExchangeRatesApiResponse ask = exchangeRatesApiHelper.getHistoricalExchangeRate(historicalDate,
                    baseCurrency.getSymbol(),
                    quotedCurrency.getSymbol());

            ExchangeRatesApiResponse bid = exchangeRatesApiHelper.getLatestExchangeRate(baseCurrency.getSymbol(),
                    quotedCurrency.getSymbol());

            return res;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
