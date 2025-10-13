package com.neriidev.pix.infrastructure.in.dto.request;

import java.math.BigDecimal;

public class DepositRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
