package com.neriidev.pix.domain.service;

import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dto.request.AmountRequest;
import com.neriidev.pix.infrastructure.in.dto.response.BalanceResponse;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;

import java.time.LocalDateTime;

public interface WalletService {
    Wallet create(WalletRequest walletRequest);

    BalanceResponse getBalance(Long walletId, LocalDateTime at);

    void withdraw(Long walletId, AmountRequest request);
}
