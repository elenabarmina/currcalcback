package com.pech.currcalcback.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

class ProfitLossCalculatorTest {

    private final Map<MethodParams, String> testData = new HashMap<>();

    @Before
    protected void setUp() {
        testData.put(new MethodParams("", ""), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");

        //amount
        testData.put(new MethodParams("2019-01-01", ""), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("2019-01-01", "amount"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("2019-01-01", null), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("2019-01-01", "-1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("2019-01-01", "0"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");

        //date
        testData.put(new MethodParams("", "1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("date", "1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams(null, "1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("2019-35-01", "1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");
        testData.put(new MethodParams("01.05.2019", "1"), "{\"error\":\"PARAMETER_INVALID\",\"errorDescription\":\"one or more parameters are invalid\"}");

        Date today = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(today);
        testData.put(new MethodParams(strDate, "1"), "{\"error\":\"DATE_INVALID\",\"errorDescription\":\"date cannot be equals or more than current date\"}");

        Date tomorrow = Calendar.getInstance().getTime();
        strDate = formatter.format(tomorrow);
        testData.put(new MethodParams(strDate, "1"), "{\"error\":\"DATE_INVALID\",\"errorDescription\":\"date cannot be equals or more than current date\"}");
    }

    @After
    protected void tearDown() {
        testData.clear();
    }

    @Test
    void calculateProfitLossByDate() {

        ProfitLossCalculator profitLossCalculator = new ProfitLossCalculator();

        for (Map.Entry<MethodParams, String> e : testData.entrySet()) {
            final String expected = e.getValue();
            final String actual = profitLossCalculator.calculateProfitLossByDate(e.getKey().getDate(),
                    e.getKey().getUsdAmount()).getEntity().toString();

            assertEquals(expected, actual);
        }

    }

    class MethodParams{
        String date;
        String usdAmount;

        public MethodParams(String date, String usdAmount){
            this.date = date;
            this.usdAmount = usdAmount;
        }

        public String getDate(){
            return this.date;
        }

        public String getUsdAmount(){
            return this.usdAmount;
        }
    }
}