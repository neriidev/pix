package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.DepositUseCase;
import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.enums.LedgerEntryType;
import com.neriidev.pix.domain.model.Ledger;
import com.neriidev.pix.domain.model.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DepositUseCaseImpl implements DepositUseCase {

    private static final Logger log = LoggerFactory.getLogger(DepositUseCaseImpl.class);

    private final WalletRepositoryPort walletRepository;
    private final LedgerRepositoryPort ledgerRepository;

    public DepositUseCaseImpl(WalletRepositoryPort walletRepository, LedgerRepositoryPort ledgerRepository) {
        this.walletRepository = walletRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public void deposit(Long walletId, BigDecimal amount) {
        log.info("Depositando {} na carteira de id {}", amount, walletId);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        wallet.deposit(amount);

        Ledger ledger = new Ledger(null, wallet, amount, LedgerEntryType.CREDIT, null, LocalDateTime.now());

        walletRepository.save(wallet);
        ledgerRepository.save(ledger);
        log.info("Depósito para a carteira de id {} realizado com sucesso", walletId);
    }
}