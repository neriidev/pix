package com.neriidev.pix.application.ports.in;

import java.math.BigDecimal;

public interface DepositUseCase {
    void deposit(Long walletId, BigDecimal amount);
}
