package com.neriidev.pix.infrastructure.out.persistence;

import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.domain.model.Ledger;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.out.persistence.entity.LedgerEntity;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.LedgerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LedgerRepositoryAdapter implements LedgerRepositoryPort {
    private final LedgerRepository ledgerRepository;

    public LedgerRepositoryAdapter(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public Ledger save(Ledger ledger) {
        LedgerEntity ledgerEntity = new LedgerEntity();
        ledgerEntity.setId(ledger.getId());
        ledgerEntity.setAmount(ledger.getAmount());
        ledgerEntity.setType(ledger.getType());
        ledgerEntity.setTransactionId(ledger.getTransactionId());
        ledgerEntity.setCreatedAt(ledger.getCreatedAt());

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(ledger.getWallet().getId());
        walletEntity.setBalance(ledger.getWallet().getBalance());
        ledgerEntity.setWallet(walletEntity);

        LedgerEntity savedEntity = ledgerRepository.save(ledgerEntity);

        Wallet wallet = new Wallet(savedEntity.getWallet().getId(), savedEntity.getWallet().getBalance());

        return new Ledger(savedEntity.getId(), wallet, savedEntity.getAmount(), savedEntity.getType(), savedEntity.getTransactionId(), savedEntity.getCreatedAt());
    }

    @Override
    public List<Ledger> findByWalletId(Long walletId) {
        return ledgerRepository.findByWalletId(walletId).stream()
                .map(entity -> {
                    Wallet wallet = new Wallet(entity.getWallet().getId(), entity.getWallet().getBalance());
                    return new Ledger(entity.getId(), wallet, entity.getAmount(), entity.getType(), entity.getTransactionId(), entity.getCreatedAt());
                })
                .collect(Collectors.toList());
    }
}
