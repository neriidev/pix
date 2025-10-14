package com.neriidev.pix.infrastructure.in.dtos.request;

import java.math.BigDecimal;

public record WithdrawRequest(BigDecimal amount) {}