package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.application.ports.in.PixKeyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PixKeyServiceImpl implements PixKeyUseCase {

    @Autowired
    private PixKeyRepositoryPort pixKeyRepository;

    @Autowired
    private WalletRepositoryPort walletRepository;

    @Override
    public PixKey registerKey(Long walletId, PixKeyRequest pixKeyRequest) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        PixKey pixKey = new PixKey(null, pixKeyRequest.key(), pixKeyRequest.type(), wallet);
        return pixKeyRepository.save(pixKey);
    }
}
