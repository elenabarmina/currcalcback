package com.pech.currcalcback.model.profitlosscalc;

public enum ErrorAnswer {

    PARAMETER_INVALID("one or more parameters are invalid"),
    DATE_INVALID("date cannot be equals or more than current date"),
    CALCULATION_ERROR("calculation error");

    String errorDescription;

    ErrorAnswer(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}