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
import javax.ws.rs.core.Response;
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
    public Response calculateProfitLossByDate(
            @QueryParam("date") String date,
            @QueryParam("usdAmount") String usdAmount){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new EnumErrorsAdapterFactory());
        Gson gson = builder.create();

        if(StringUtils.isEmpty(date) || StringUtils.isEmpty(usdAmount))
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorAnswer.PARAMETER_INVALID))
                    .build();

        LocalDate historicalDate;
        BigDecimal amount;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            historicalDate = LocalDate.parse(date, formatter);
            amount = new BigDecimal(usdAmount);
        }catch (DateTimeParseException | NumberFormatException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorAnswer.PARAMETER_INVALID))
                    .build();
        }

        if (LocalDate.now().compareTo(historicalDate) < 0){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorAnswer.DATE_INVALID))
                    .build();
        }

        if (amount.compareTo(new BigDecimal(0)) <= 0){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(ErrorAnswer.PARAMETER_INVALID))
                    .build();
        }

         BigDecimal result = calculator.profitLossByDateForNow(historicalDate,
                amount,
                CurrencySymbols.USD,
                CurrencySymbols.RUB);

        if (result == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(ErrorAnswer.CALCULATION_ERROR))
                    .build();

        return Response.ok(gson.toJson(new SuccesfulAnswer(result)), MediaType.APPLICATION_JSON).build();
    }
}