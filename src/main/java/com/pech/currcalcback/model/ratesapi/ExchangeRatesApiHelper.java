package com.pech.currcalcback.model.ratesapi;

import com.pech.currcalcback.utils.RequestSender;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeRatesApiHelper {

    private final String BASE_CURRENCY_PARAM_NAME = "base";
    private final String ACHEVED_CURRENCY_PARAM_NAME = "symbols";

    private String goRequestToExchangeRatesApi(String path, Map<String,String> params) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("api.exchangeratesapi.io");

        builder.setPath(path);

        for (Map.Entry<String, String> entry : params.entrySet()){
            builder.addParameter(entry.getKey(), entry.getValue());
        }

        URL url = builder.build().toURL();

        return RequestSender.sendRequest(url);
    }

    // /2010-01-12?base=USD&symbols=RUB
    public String getHistoricalRubRatesByDate (LocalDate date,
                                               String baseCurrencySymbol,
                                               String achievedCurrencySymbol) throws IOException, URISyntaxException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateForRequest = formatter.format(date);

        String path = "/" + dateForRequest;

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(BASE_CURRENCY_PARAM_NAME, baseCurrencySymbol);
        queryParams.put(ACHEVED_CURRENCY_PARAM_NAME, achievedCurrencySymbol);

        String resultRate = goRequestToExchangeRatesApi(path, queryParams);

        //{"base":"USD","rates":{"RUB":64.2958108228},"date":"2019-03-26"}

        return resultRate;
    }
}
