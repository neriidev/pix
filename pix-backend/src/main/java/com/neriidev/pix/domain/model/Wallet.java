package com.neriidev.pix.domain.model;

import java.math.BigDecimal;

public class Wallet {

    private Long id;

    private BigDecimal balance;

    public Wallet(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Fundos insuficientes");
        }
        this.balance = this.balance.subtract(amount);
    }
}
