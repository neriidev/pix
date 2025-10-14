package com.neriidev.pix.application.ports.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BalanceHistoryUseCase {

    BigDecimal getBalanceAt(Long walletId, LocalDateTime at);
}
