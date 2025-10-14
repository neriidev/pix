package com.neriidev.pix.application.usecases;

import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.out.persistence.repository.WalletRepository;
import com.neriidev.pix.application.ports.in.WalletUseCase;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletUseCase {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletEntity create(WalletRequest walletRequest) {
        WalletEntity wallet = new WalletEntity();
        wallet.setBalance(walletRequest.balance());
        return walletRepository.save(wallet);
    }

    @Override
    public BalanceResponse getBalance(Long walletId, LocalDateTime at) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        // Simplified balance calculation
        BigDecimal balance = wallet.getBalance();

        return new BalanceResponse(balance, LocalDateTime.now());
    }

    @Override
    public void withdraw(Long walletId, TransferRequest.AmountRequest request) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        wallet.withdraw(request.amount());

        walletRepository.save(wallet);
    }
}
