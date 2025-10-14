package com.neriidev.pix.application.ports.in;

import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;

import java.time.LocalDateTime;

public interface WalletUseCase {
    WalletEntity create(WalletRequest walletRequest);

    BalanceResponse getBalance(Long walletId, LocalDateTime at);

    void withdraw(Long walletId, TransferRequest.AmountRequest request);
}
