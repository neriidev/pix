package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.WalletUseCase;
import com.neriidev.pix.infrastructure.in.dtos.request.TransferRequest;
import com.neriidev.pix.infrastructure.in.dtos.request.WalletRequest;
import com.neriidev.pix.infrastructure.in.dtos.response.BalanceResponse;
import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class WalletServiceImpl implements WalletUseCase {

    private static final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletEntity create(WalletRequest walletRequest) {
        log.info("Criando carteira com saldo {}", walletRequest.balance());
        WalletEntity wallet = new WalletEntity();
        wallet.setBalance(walletRequest.balance());
        WalletEntity savedWallet = walletRepository.save(wallet);
        log.info("Carteira criada com sucesso com o id {}", savedWallet.getId());
        return savedWallet;
    }

    @Override
    public BalanceResponse getBalance(Long walletId, LocalDateTime at) {
        log.info("Buscando saldo para a carteira de id {}", walletId);
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        BigDecimal balance = wallet.getBalance();
        log.info("O saldo para a carteira de id {} é {}", walletId, balance);
        return new BalanceResponse(balance, LocalDateTime.now());
    }

    @Override
    public void withdraw(Long walletId, TransferRequest.AmountRequest request) {
        log.info("Sacando {} da carteira de id {}", request.amount(), walletId);
        WalletEntity wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));
        wallet.withdraw(request.amount());
        walletRepository.save(wallet);
        log.info("Saque da carteira de id {} realizado com sucesso", walletId);
    }
}
