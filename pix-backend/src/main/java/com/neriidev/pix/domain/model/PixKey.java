package com.neriidev.pix.domain.model;

import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;

public class PixKey {

    private Long id;

    private String key;

    private PixKeyType type;

    private Wallet wallet;


    public PixKey(Long id, String key, PixKeyType type, Wallet wallet) {
        this.id = id;
        this.key = key;
        this.type = type;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public PixKeyType getType() {
        return type;
    }

    public Wallet getWallet() {
        return wallet;
    }
}