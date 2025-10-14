package com.neriidev.pix.infrastructure.in.dtos.request;

import java.math.BigDecimal;

public record InternalTransferRequest(
        String fromPixKey,
        String toPixKey,
        BigDecimal amount
) {}