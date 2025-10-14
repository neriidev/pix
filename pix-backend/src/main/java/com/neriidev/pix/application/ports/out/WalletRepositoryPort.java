package com.neriidev.pix.application.ports.out;



import com.neriidev.pix.domain.model.Wallet;

import java.util.Optional;

public interface WalletRepositoryPort {
    Wallet save(Wallet wallet);
    Optional<Wallet> findById(Long id);
}
