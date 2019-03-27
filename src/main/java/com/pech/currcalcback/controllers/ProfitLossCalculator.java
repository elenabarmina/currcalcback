package com.pech.currcalcback.controllers;

import com.pech.currcalcback.model.ratesapi.CurrencySymbols;
import com.pech.currcalcback.utils.Calculator;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Path("/plcalculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfitLossCalculator {

    @Inject
    Calculator calculator;

    @GET
    @Path("/calculate")
    public String calculateProfitLossByDate(
            @QueryParam("date") String date,
            @QueryParam("usdAmount") String usdAmount){

        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(usdAmount))
            return "{}";

        LocalDate historicalDate;
        BigDecimal amount;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            historicalDate = LocalDate.parse(date, formatter);
            amount = new BigDecimal(usdAmount);

        }catch (DateTimeParseException | NumberFormatException e){
            return "{\"error\" : \"invalid parameter value\"}";
        }

        return calculator.profitLossByDateForNow(historicalDate,
                amount,
                CurrencySymbols.USD,
                CurrencySymbols.RUB).toString();
    }
}