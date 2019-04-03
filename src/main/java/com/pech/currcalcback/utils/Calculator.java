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

    private final BigDecimal SPREAD_VALUE = new BigDecimal(0.005);

    @Inject
    private ExchangeRatesApiHelper exchangeRatesApiHelper;

    public Calculator(){

    }

    public BigDecimal profitLossByDateForNow(LocalDate historicalDate,
                                             BigDecimal amount,
                                             CurrencySymbols baseCurrency,
                                             CurrencySymbols quotedCurrency){
        try {

            ExchangeRatesApiResponse askResponse = exchangeRatesApiHelper.getHistoricalExchangeRate(historicalDate,
                    baseCurrency.getSymbol(),
                    quotedCurrency.getSymbol());

            if (LocalDate.now().compareTo(historicalDate) == 0){
                return askResponse.getRates().get(quotedCurrency.getSymbol()).multiply(SPREAD_VALUE).negate();
            }

            ExchangeRatesApiResponse bidResponse = exchangeRatesApiHelper.getLatestExchangeRate(baseCurrency.getSymbol(),
                        quotedCurrency.getSymbol());

            BigDecimal ask = askResponse.getRates().get(quotedCurrency.getSymbol());
            BigDecimal bid = bidResponse.getRates().get(quotedCurrency.getSymbol());

            BigDecimal askIncludingSpread = ask.add(SPREAD_VALUE.divide(new BigDecimal(2)).multiply(ask));
            BigDecimal bidIncludingSpread = bid.subtract(SPREAD_VALUE.divide(new BigDecimal(2)).multiply(bid));

            return amount.multiply(bidIncludingSpread.subtract(askIncludingSpread));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
