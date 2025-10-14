package com.neriidev.pix.application.ports.in;

import com.neriidev.pix.domain.model.Transaction;

import java.math.BigDecimal;

public interface InternalTransferUseCase {
    Transaction internalTransfer(String idempotencyKey, String fromPixKey, String toPixKey, BigDecimal amount);
}
