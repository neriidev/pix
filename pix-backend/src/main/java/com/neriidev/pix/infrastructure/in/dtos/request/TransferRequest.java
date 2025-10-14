package com.neriidev.pix.infrastructure.in.dtos.request;

import java.math.BigDecimal;

public record TransferRequest(
    String sourcePixKey,
    String targetPixKey,
    BigDecimal amount
) {}