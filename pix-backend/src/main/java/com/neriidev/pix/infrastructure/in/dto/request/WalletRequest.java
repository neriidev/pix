package com.neriidev.pix.infrastructure.in.dto.request;

import java.math.BigDecimal;

public record WalletRequest(Long id, BigDecimal balance) {}
