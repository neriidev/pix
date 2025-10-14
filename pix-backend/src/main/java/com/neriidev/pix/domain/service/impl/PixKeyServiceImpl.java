package com.neriidev.pix.domain.service.impl;

import com.neriidev.pix.domain.model.PixKeyEntity;
import com.neriidev.pix.domain.model.WalletEntity;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import com.neriidev.pix.infrastructure.out.repository.PixKeyRepository;
import com.neriidev.pix.infrastructure.out.repository.WalletRepository;
import com.neriidev.pix.domain.service.PixKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PixKeyServiceImpl implements PixKeyService {

    @Autowired
    private PixKeyRepository pixKeyRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public PixKeyEntity registerKey(Long walletId, PixKeyRequest pixKeyRequest) {
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        PixKeyEntity pixKey = new PixKeyEntity();
        pixKey.setKey(pixKeyRequest.key());
        pixKey.setType(pixKeyRequest.type());
        pixKey.setWallet(wallet);

        return pixKeyRepository.save(pixKey);
    }
}
