package com.neriidev.pix.application.ports.in;

import java.math.BigDecimal;

public interface BalanceUseCase {
    BigDecimal getBalance(Long walletId);
}
