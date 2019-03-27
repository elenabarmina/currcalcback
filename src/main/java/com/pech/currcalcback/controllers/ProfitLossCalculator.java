package com.pech.currcalcback.controllers;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/plcalculator")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfitLossCalculator {

    @GET
    @Path("/calculate")
    public String calculateProfitLossByDate(
            @QueryParam("date") String date,
            @QueryParam("usdAmount") String usdAmount){

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("calcResult", 11);

        return objectBuilder.toString();
    }
}