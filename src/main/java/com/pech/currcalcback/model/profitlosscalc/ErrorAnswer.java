package com.pech.currcalcback.model.profitlosscalc;

public enum ErrorAnswer {

    PARAMETER_INVALID("One or more parameters are invalid."),
    DATE_INVALID("Date cannot be more than current date."),
    CALCULATION_ERROR("Calculation error.");

    String errorDescription;

    ErrorAnswer(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}