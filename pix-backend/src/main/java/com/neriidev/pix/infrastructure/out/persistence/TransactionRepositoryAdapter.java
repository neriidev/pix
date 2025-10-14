package com.neriidev.pix.infrastructure.out.persistence;

import com.neriidev.pix.application.ports.out.TransactionRepositoryPort;
import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.out.persistence.entity.TransactionEntity;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionRepository transactionJpaRepository;

    public TransactionRepositoryAdapter(TransactionRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transaction.getId());
        transactionEntity.setEndToEndId(transaction.getEndToEndId());
        transactionEntity.setAmount(transaction.getAmount());
        transactionEntity.setStatus(transaction.getStatus());
        transactionEntity.setCreatedAt(transaction.getCreatedAt());

        WalletEntity fromWalletEntity = new WalletEntity();
        fromWalletEntity.setId(transaction.getFromWallet().getId());
        fromWalletEntity.setBalance(transaction.getFromWallet().getBalance());
        transactionEntity.setFromWallet(fromWalletEntity);

        WalletEntity toWalletEntity = new WalletEntity();
        toWalletEntity.setId(transaction.getToWallet().getId());
        toWalletEntity.setBalance(transaction.getToWallet().getBalance());
        transactionEntity.setToWallet(toWalletEntity);

        TransactionEntity savedEntity = transactionJpaRepository.save(transactionEntity);

        Wallet fromWallet = new Wallet(savedEntity.getFromWallet().getId(), savedEntity.getFromWallet().getBalance());
        Wallet toWallet = new Wallet(savedEntity.getToWallet().getId(), savedEntity.getToWallet().getBalance());

        return new Transaction(savedEntity.getId(), savedEntity.getEndToEndId(), fromWallet, toWallet, savedEntity.getAmount(), savedEntity.getStatus(), savedEntity.getCreatedAt());
    }

    @Override
    public Optional<Transaction> findByEndToEndId(String endToEndId) {
        return transactionJpaRepository.findByEndToEndId(endToEndId)
                .map(entity -> {
                    Wallet fromWallet = new Wallet(entity.getFromWallet().getId(), entity.getFromWallet().getBalance());
                    Wallet toWallet = new Wallet(entity.getToWallet().getId(), entity.getToWallet().getBalance());
                    return new Transaction(entity.getId(), entity.getEndToEndId(), fromWallet, toWallet, entity.getAmount(), entity.getStatus(), entity.getCreatedAt());
                });
    }
}
