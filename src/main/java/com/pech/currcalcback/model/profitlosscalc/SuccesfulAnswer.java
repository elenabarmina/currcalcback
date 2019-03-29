package com.pech.currcalcback.model.profitlosscalc;

import java.math.BigDecimal;

public class SuccesfulAnswer {

    BigDecimal result;

    public SuccesfulAnswer(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}
