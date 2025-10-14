package com.neriidev.pix.infrastructure.in.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BalanceResponse(BigDecimal balance, LocalDateTime retrievedAt) {
    public BalanceResponse(BigDecimal balance) {
        this(balance, LocalDateTime.now());
    }
}
