package com.neriidev.pix.domain.model;

import com.neriidev.pix.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private Long id;

    private String endToEndId;

    private Wallet fromWallet;

    private Wallet toWallet;

    private BigDecimal amount;

    private TransactionStatus status;

    private LocalDateTime createdAt;


    public Transaction(Long id, String endToEndId, Wallet fromWallet, Wallet toWallet, BigDecimal amount, TransactionStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.endToEndId = endToEndId;
        this.fromWallet = fromWallet;
        this.toWallet = toWallet;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public Wallet getFromWallet() {
        return fromWallet;
    }

    public Wallet getToWallet() {
        return toWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void confirm() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("A transação não está pendente");
        }
        this.status = TransactionStatus.CONFIRMED;
    }

    public void reject() {
        if (this.status != TransactionStatus.PENDING) {
            throw new IllegalStateException("A transação não está pendente");
        }
        this.status = TransactionStatus.REJECTED;
    }
}
