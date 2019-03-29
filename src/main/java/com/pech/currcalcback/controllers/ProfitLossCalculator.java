package com.pech.currcalcback.controllers;

import com.google.gson.*;
import com.pech.currcalcback.model.profitlosscalc.ErrorAnswer;
import com.pech.currcalcback.model.profitlosscalc.SuccesfulAnswer;
import com.pech.currcalcback.model.ratesapi.CurrencySymbols;
import com.pech.currcalcback.utils.Calculator;
import com.pech.currcalcback.utils.EnumErrorsAdapterFactory;
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
    private Calculator calculator;

    @GET
    @Path("/calculate-usd-rub")
    public String calculateProfitLossByDate(
            @QueryParam("date") String date,
            @QueryParam("usdAmount") String usdAmount){

        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(usdAmount))
            return "{}";

        LocalDate historicalDate;
        BigDecimal amount;

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new EnumErrorsAdapterFactory());
        Gson gson = builder.create();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            historicalDate = LocalDate.parse(date, formatter);
            amount = new BigDecimal(usdAmount);
        }catch (DateTimeParseException | NumberFormatException e){
            return gson.toJson(ErrorAnswer.PARAMETER_INVALID);
        }

        if (LocalDate.now().compareTo(historicalDate) <= 0){
            return gson.toJson(ErrorAnswer.DATE_INVALID);
        }

        if (amount.compareTo(new BigDecimal(0)) <= 0){
            return gson.toJson(ErrorAnswer.PARAMETER_INVALID);
        }

         BigDecimal result = calculator.profitLossByDateForNow(historicalDate,
                amount,
                CurrencySymbols.USD,
                CurrencySymbols.RUB);

        if (result == null)
            return gson.toJson(ErrorAnswer.CALCULATION_ERROR);

        return gson.toJson(new SuccesfulAnswer(result));
    }
}