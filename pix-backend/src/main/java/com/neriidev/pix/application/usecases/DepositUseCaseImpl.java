package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.DepositUseCase;
import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.enums.LedgerEntryType;
import com.neriidev.pix.domain.model.Ledger;
import com.neriidev.pix.domain.model.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DepositUseCaseImpl implements DepositUseCase {

    private final WalletRepositoryPort walletRepository;
    private final LedgerRepositoryPort ledgerRepository;

    public DepositUseCaseImpl(WalletRepositoryPort walletRepository, LedgerRepositoryPort ledgerRepository) {
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public void deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        wallet.deposit(amount);

        Ledger ledger = new Ledger(null, wallet, amount, LedgerEntryType.CREDIT, null, LocalDateTime.now());

        walletRepository.save(wallet);
        ledgerRepository.save(ledger);
    }
}