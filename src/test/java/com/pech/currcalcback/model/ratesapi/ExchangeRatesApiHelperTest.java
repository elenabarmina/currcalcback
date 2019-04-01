package com.pech.currcalcback.model.ratesapi;

import com.pech.currcalcback.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExchangeRatesApiHelperTest {

    @BeforeEach
    void loadProps(){
        new AppConfig();
    }

    @Test
    void getHistoricalExchangeRate() throws IOException, URISyntaxException, ParseException {

        ExchangeRatesApiHelper exchangeRatesApiHelper = new ExchangeRatesApiHelper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate historicalDate = LocalDate.parse("2010-01-01", formatter);

        final String actual = exchangeRatesApiHelper.getHistoricalExchangeRate(historicalDate,
                    "USD", "RUB").toString();

        assertEquals(actual, "ExchangeRatesApiResponse{base='USD', rates={RUB=29.9555740664}, date=Thu Dec 31 00:00:00 MSK 2009}");

    }

    @Test
    void getLatestExchangeRate() throws IOException, URISyntaxException {

        ExchangeRatesApiHelper exchangeRatesApiHelper = new ExchangeRatesApiHelper();
            final ExchangeRatesApiResponse actual = exchangeRatesApiHelper.getLatestExchangeRate(
                    "USD", "RUB");

            assertTrue(actual.getBase().equals("USD")
                    && actual.getRates().entrySet().size() > 0);

    }
}