package com.neriidev.pix.infrastructure.in.dto.response;

import java.math.BigDecimal;

public record WalletResponse(Long id, BigDecimal balance) { }
