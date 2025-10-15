package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.PixKeyUseCase;
import com.neriidev.pix.application.ports.out.PixKeyRepositoryPort;
import com.neriidev.pix.application.ports.out.WalletRepositoryPort;
import com.neriidev.pix.domain.model.PixKey;
import com.neriidev.pix.domain.model.Wallet;
import com.neriidev.pix.infrastructure.in.dtos.request.PixKeyRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PixKeyServiceImpl implements PixKeyUseCase {

    private static final Logger log = LoggerFactory.getLogger(PixKeyServiceImpl.class);

    private final PixKeyRepositoryPort pixKeyRepository;
    private final WalletRepositoryPort walletRepository;

    public PixKeyServiceImpl(PixKeyRepositoryPort pixKeyRepository, WalletRepositoryPort walletRepository) {
        this.pixKeyRepository = pixKeyRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public PixKey registerKey(Long walletId, PixKeyRequest pixKeyRequest) {
        log.info("Registrando chave pix para a carteira de id {}", walletId);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        PixKey pixKey = new PixKey(null, pixKeyRequest.key(), pixKeyRequest.type(), wallet);
        PixKey savedPixKey = pixKeyRepository.save(pixKey);
        log.info("Chave pix registrada com sucesso com o id {}", savedPixKey.getId());
        return savedPixKey;
    }
}
