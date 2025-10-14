package com.neriidev.pix.domain.model;

public class PixKey {


    private Long id;

    private String key;


    private PixKeyType type;


    private Wallet wallet;

    public enum PixKeyType {
        EMAIL,
        PHONE,
        EVP
    }

    public PixKey() {
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setType(PixKeyType type) {
        this.type = type;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
