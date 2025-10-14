package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.InternalTransferUseCase;
import com.neriidev.pix.application.ports.out.*;
import com.neriidev.pix.domain.enums.LedgerEntryType;
import com.neriidev.pix.domain.enums.TransactionStatus;
import com.neriidev.pix.domain.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InternalTransferUseCaseImpl implements InternalTransferUseCase {

    private final IdempotencyKeyRepositoryPort idempotencyKeyRepository;
    private final PixKeyRepositoryPort pixKeyRepository;
    private final WalletRepositoryPort walletRepository;
    private final TransactionRepositoryPort transactionRepository;
    private final LedgerRepositoryPort ledgerRepository;

    public InternalTransferUseCaseImpl(IdempotencyKeyRepositoryPort idempotencyKeyRepository, PixKeyRepositoryPort pixKeyRepository, WalletRepositoryPort walletRepository, TransactionRepositoryPort transactionRepository, LedgerRepositoryPort ledgerRepository) {
        this.idempotencyKeyRepository = idempotencyKeyRepository;
        this.pixKeyRepository = pixKeyRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public Transaction internalTransfer(String idempotencyKey, String fromPixKey, String toPixKey, BigDecimal amount) {
        idempotencyKeyRepository.findByKeyAndScope(idempotencyKey, "internal-transfer").ifPresent(key -> {
            throw new RuntimeException("Chave de idempotência já utilizada");
        });

        PixKey from = pixKeyRepository.findByKey(fromPixKey)
                .orElseThrow(() -> new RuntimeException("Chave de pix não encontrada"));

        PixKey to = pixKeyRepository.findByKey(toPixKey)
                .orElseThrow(() -> new RuntimeException("Chave de pix não encontrada"));

        Wallet fromWallet = from.getWallet();
        Wallet toWallet = to.getWallet();

        fromWallet.withdraw(amount);
        toWallet.deposit(amount);

        String endToEndId = UUID.randomUUID().toString();

        Transaction transaction = new Transaction(null, endToEndId, fromWallet, toWallet, amount, TransactionStatus.PENDING, LocalDateTime.now());

        Ledger fromLedger = new Ledger(null, fromWallet, amount, LedgerEntryType.DEBIT, endToEndId, LocalDateTime.now());
        Ledger toLedger = new Ledger(null, toWallet, amount, LedgerEntryType.CREDIT, endToEndId, LocalDateTime.now());

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
        transactionRepository.save(transaction);
        ledgerRepository.save(fromLedger);
        ledgerRepository.save(toLedger);

        idempotencyKeyRepository.save(new IdempotencyKey(idempotencyKey, "internal-transfer", LocalDateTime.now(), null));

        return transaction;
    }
}
