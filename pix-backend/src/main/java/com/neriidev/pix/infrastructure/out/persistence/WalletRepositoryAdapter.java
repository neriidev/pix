package com.neriidev.pix.infrastructure.out.persistence;

import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WalletRepositoryAdapter implements WalletRepositoryPort {

    private final WalletRepository walletJpaRepository;

    public WalletRepositoryAdapter(WalletRepository walletJpaRepository) {
        this.walletJpaRepository = walletJpaRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(wallet.getId());
        walletEntity.setBalance(wallet.getBalance());

        WalletEntity savedEntity = walletJpaRepository.save(walletEntity);

        return new Wallet(savedEntity.getId(), savedEntity.getBalance());
    }

    @Override
    public Optional<Wallet> findById(Long id) {
        return walletJpaRepository.findById(id)
                .map(entity -> new Wallet(entity.getId(), entity.getBalance()));
    }
}
