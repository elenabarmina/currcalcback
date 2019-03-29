package com.pech.currcalcback.model.ratesapi;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.pech.currcalcback.AppConfig;
import com.pech.currcalcback.utils.RequestSender;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExchangeRatesApiHelper {

    private final String API_SCHEME_PROPERTY_NAME = "api.exchangeratesapi.scheme";
    private final String API_HOST_PROPERTY_NAME = "api.exchangeratesapi.host";

    private final String LATEST_PATH = "/latest";

    private final String BASE_CURRENCY_PARAM_NAME = "base";
    private final String QUOTED_CURRENCY_PARAM_NAME = "symbols";

    public ExchangeRatesApiResponse getHistoricalExchangeRate (LocalDate date,
                                               String baseCurrencySymbol,
                                               String quotedCurrencySymbol) throws IOException, URISyntaxException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateForRequest = formatter.format(date);

        String path = "/" + dateForRequest;
        return getExchangeRate(baseCurrencySymbol, quotedCurrencySymbol, path);
    }

    public ExchangeRatesApiResponse getLatestExchangeRate(String baseCurrencySymbol,
                                                      String quotedCurrencySymbol) throws IOException, URISyntaxException {

        String path = LATEST_PATH;
        return getExchangeRate(baseCurrencySymbol, quotedCurrencySymbol, path);
    }

    private ExchangeRatesApiResponse getExchangeRate(String baseCurrencySymbol,
                                                     String quotedCurrencySymbol,
                                                     String path) throws URISyntaxException, IOException {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(BASE_CURRENCY_PARAM_NAME, baseCurrencySymbol);
        queryParams.put(QUOTED_CURRENCY_PARAM_NAME, quotedCurrencySymbol);

        String resultRate = doRequestToExchangeRatesApi(path, queryParams);

        if(resultRate.isEmpty()){
            return null;
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<ExchangeRatesApiResponse>(){}.getType();
        try {
            ExchangeRatesApiResponse response = gson.fromJson(resultRate, listType);
            return response;
        }catch (JsonSyntaxException e){
            return null;
        }
    }

    private String doRequestToExchangeRatesApi(String path,
                                               Map<String,String> params) throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme(AppConfig.getProperty(API_SCHEME_PROPERTY_NAME));
        builder.setHost(AppConfig.getProperty(API_HOST_PROPERTY_NAME));
        builder.setPath(path);

        for (Map.Entry<String, String> entry : params.entrySet()){
            builder.addParameter(entry.getKey(), entry.getValue());
        }

        URL url = builder.build().toURL();

        return RequestSender.sendRequest(url);
    }
}
