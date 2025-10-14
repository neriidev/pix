package com.neriidev.pix.infrastructure.out.persistence;

import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.PixKeyRepository;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PixKeyRepositoryAdapter implements PixKeyRepositoryPort {

    private final PixKeyRepository pixKeyJpaRepository;

    public PixKeyRepositoryAdapter(PixKeyRepository pixKeyJpaRepository) {
        this.pixKeyJpaRepository = pixKeyJpaRepository;
    }

    @Override
    public PixKey save(PixKey pixKey) {
        PixKeyEntity pixKeyEntity = new PixKeyEntity();
        pixKeyEntity.setId(pixKey.getId());
        pixKeyEntity.setKey(pixKey.getKey());
        pixKeyEntity.setType(pixKey.getType());

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(pixKey.getWallet().getId());
        walletEntity.setBalance(pixKey.getWallet().getBalance());
        pixKeyEntity.setWallet(walletEntity);

        PixKeyEntity savedEntity = pixKeyJpaRepository.save(pixKeyEntity);

        Wallet wallet = new Wallet(savedEntity.getWallet().getId(), savedEntity.getWallet().getBalance());

        return new PixKey(savedEntity.getId(), savedEntity.getKey(), savedEntity.getType(), wallet);
    }

    @Override
    public Optional<PixKey> findByKey(String key) {
        return pixKeyJpaRepository.findByKey(key)
                .map(entity -> {
                    Wallet wallet = new Wallet(entity.getWallet().getId(), entity.getWallet().getBalance());
                    return new PixKey(entity.getId(), entity.getKey(), entity.getType(), wallet);
                });
    }
}
