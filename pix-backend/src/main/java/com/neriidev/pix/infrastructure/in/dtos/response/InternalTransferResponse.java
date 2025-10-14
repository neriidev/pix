package com.neriidev.pix.infrastructure.in.dtos.response;

import com.neriidev.pix.domain.enums.TransactionStatus;
import com.neriidev.pix.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InternalTransferResponse {
    private Long id;
    private String endToEndId;
    private BigDecimal amount;
    private TransactionStatus status;
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
