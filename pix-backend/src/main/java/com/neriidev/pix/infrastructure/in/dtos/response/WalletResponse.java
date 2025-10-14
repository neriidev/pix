package com.neriidev.pix.infrastructure.in.dtos.response;

import java.math.BigDecimal;

public record WalletResponse(Long id, BigDecimal balance) { }
