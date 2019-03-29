package com.pech.currcalcback.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

public final class RequestSender {

    public static String sendRequest(URL url) throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url.toURI());

        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        if (StringUtils.isEmpty(result)){
            return "";
        }

        return result.toString();
    }

}
