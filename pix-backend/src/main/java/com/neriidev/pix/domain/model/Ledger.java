package com.neriidev.pix.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ledger {

    private Long id;

    private Wallet wallet;

    private BigDecimal amount;

    private LedgerEntryType type;

    private String transactionId;

    private LocalDateTime createdAt;

    public enum LedgerEntryType {
        CREDIT,
        DEBIT
    }

    public Ledger(Long id, Wallet wallet, BigDecimal amount, LedgerEntryType type, String transactionId, LocalDateTime createdAt) {
        this.id = id;
        this.wallet = wallet;
        this.amount = amount;
        this.type = type;
        this.transactionId = transactionId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LedgerEntryType getType() {
        return type;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}