package com.neriidev.pix.domain.service.impl;

import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.out.repository.WalletRepository;
import com.neriidev.pix.domain.service.WalletService;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet create(WalletRequest walletRequest) {
        Wallet wallet = new Wallet();
        wallet.setBalance(walletRequest.balance());
        return walletRepository.save(wallet);
    }

    @Override
    public BalanceResponse getBalance(Long walletId, LocalDateTime at) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // Simplified balance calculation
        BigDecimal balance = wallet.getBalance();

        return new BalanceResponse(balance, LocalDateTime.now());
    }

    @Override
    public void withdraw(Long walletId, TransferRequest.AmountRequest request) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.withdraw(request.amount());

        walletRepository.save(wallet);
    }
}
