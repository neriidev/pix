package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.PixKeyUseCase;
import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import org.springframework.stereotype.Service;

@Service
public class PixKeyServiceImpl implements PixKeyUseCase {

    private final PixKeyRepositoryPort pixKeyRepository;
    private final WalletRepositoryPort walletRepository;

    public PixKeyServiceImpl(PixKeyRepositoryPort pixKeyRepository, WalletRepositoryPort walletRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public PixKey registerKey(Long walletId, PixKeyRequest pixKeyRequest) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        PixKey pixKey = new PixKey(null, pixKeyRequest.key(), pixKeyRequest.type(), wallet);
        return pixKeyRepository.save(pixKey);
    }
}
