package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.PixUseCase;
import com.neriidev.pix.application.ports.out.IdempotencyKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PixServiceImpl implements PixUseCase {

    private final PixKeyRepositoryPort pixKeyRepository;
    private final WalletRepositoryPort walletRepository;
    private final IdempotencyKeyRepositoryPort idempotencyKeyJpaRepository;

    public PixServiceImpl(PixKeyRepositoryPort pixKeyRepository, WalletRepositoryPort walletRepository, IdempotencyKeyRepositoryPort idempotencyKeyJpaRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.walletRepository = walletRepository;
        this.idempotencyKeyJpaRepository = idempotencyKeyJpaRepository;
    }

    @Override
    @Transactional
    public void internalTransfer(String idempotencyKey, TransferRequest request) {
        idempotencyKeyJpaRepository.findByKeyAndScope(idempotencyKey, "internal-transfer").ifPresent(key -> {
            throw new RuntimeException("Chave de idempotência já utilizada");
        });

        PixKey sourcePixKey = pixKeyRepository.findByKey(request.sourcePixKey())
                .orElseThrow(() -> new RuntimeException("Chave de origem do PIX não encontrada"));

        PixKey targetPixKey = pixKeyRepository.findByKey(request.targetPixKey())
                .orElseThrow(() -> new RuntimeException("Chave de PIX de destino não encontrada"));

        var sourceWallet = sourcePixKey.getWallet();
        var targetWallet = targetPixKey.getWallet();

        sourceWallet.withdraw(request.amount());
        targetWallet.deposit(request.amount());

        walletRepository.save(sourceWallet);
        walletRepository.save(targetWallet);
    }
}
