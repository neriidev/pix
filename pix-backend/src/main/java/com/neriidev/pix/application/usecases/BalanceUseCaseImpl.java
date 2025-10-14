package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.BalanceUseCase;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceUseCaseImpl implements BalanceUseCase {

    private final WalletRepositoryPort walletRepository;

    public BalanceUseCaseImpl(WalletRepositoryPort walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public BigDecimal getBalance(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
        return wallet.getBalance();
    }
}