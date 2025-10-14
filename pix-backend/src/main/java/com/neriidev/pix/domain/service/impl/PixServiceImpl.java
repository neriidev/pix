package com.neriidev.pix.domain.service.impl;

import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.infrastructure.out.repository.PixKeyRepository;
import com.neriidev.pix.infrastructure.out.repository.WalletRepository;
import com.neriidev.pix.domain.service.PixService;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PixServiceImpl implements PixService {

    @Autowired
    private PixKeyRepository pixKeyRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    @Transactional
    public void internalTransfer(String idempotencyKey, TransferRequest request) {
        PixKey sourcePixKey = pixKeyRepository.findByKey(request.sourcePixKey())
                .orElseThrow(() -> new RuntimeException("Source pix key not found"));

        PixKey targetPixKey = pixKeyRepository.findByKey(request.targetPixKey())
                .orElseThrow(() -> new RuntimeException("Target pix key not found"));

        var sourceWallet = sourcePixKey.getWallet();
        var targetWallet = targetPixKey.getWallet();

        sourceWallet.withdraw(request.amount());
        targetWallet.deposit(request.amount());

        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);
    }
}
