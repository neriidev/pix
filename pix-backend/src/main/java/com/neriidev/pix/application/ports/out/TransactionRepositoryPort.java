package com.neriidev.pix.application.ports.out;

import com.neriidev.pix.domain.model.Transaction;

import java.util.Optional;

public interface TransactionRepositoryPort {
    Transaction save(Transaction transaction);
    Optional<Transaction> findByEndToEndId(String endToEndId);
}
