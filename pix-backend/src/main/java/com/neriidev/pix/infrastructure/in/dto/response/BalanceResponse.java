package com.neriidev.pix.infrastructure.in.dto.response;

import java.math.BigDecimal;

public class BalanceResponse {
    private BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
