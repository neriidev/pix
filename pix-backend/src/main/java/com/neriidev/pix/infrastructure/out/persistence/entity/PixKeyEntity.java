package com.neriidev.pix.infrastructure.out.persistence.entity;


import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.out.persistence.enums.PixKeyType;
import jakarta.persistence.*;

@Entity
public class PixKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pix_key")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PixKeyType type;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private WalletEntity wallet;

    public PixKeyEntity() {
    }

    public PixKeyEntity(Long id, String key, PixKeyType type, WalletEntity wallet) {
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

    public WalletEntity getWallet() {
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

    public void setWallet(WalletEntity wallet) {
        this.wallet = wallet;
    }
}
