package com.neriidev.pix.infrastructure.out.persistence.entity;

import com.neriidev.pix.domain.enums.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String endToEndId;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id", nullable = false)
    private WalletEntity fromWallet;

    @ManyToOne
    @JoinColumn(name = "to_wallet_id", nullable = false)
    private WalletEntity toWallet;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public WalletEntity getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(WalletEntity fromWallet) {
        this.fromWallet = fromWallet;
    }

    public WalletEntity getToWallet() {
        return toWallet;
    }

    public void setToWallet(WalletEntity toWallet) {
        this.toWallet = toWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
