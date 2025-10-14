package com.neriidev.pix.application.usecases;

import com.neriidev.pix.application.ports.in.BalanceHistoryUseCase;
import com.neriidev.pix.application.ports.out.LedgerRepositoryPort;
import com.neriidev.pix.domain.enums.LedgerEntryType;
import com.neriidev.pix.domain.model.Ledger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BalanceHistoryUseCaseImpl implements BalanceHistoryUseCase {

    private final LedgerRepositoryPort ledgerRepository;

    public BalanceHistoryUseCaseImpl(LedgerRepositoryPort ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public BigDecimal getBalanceAt(Long walletId, LocalDateTime at) {
        List<Ledger> ledgerEntries = ledgerRepository.findByWalletId(walletId);

        BigDecimal balance = BigDecimal.ZERO;

        for (Ledger entry : ledgerEntries) {
            if (entry.getCreatedAt().isBefore(at) || entry.getCreatedAt().isEqual(at)) {
                if (entry.getType() == LedgerEntryType.CREDIT) {
                    balance = balance.add(entry.getAmount());
                } else {
                    balance = balance.subtract(entry.getAmount());
                }
            }
        }

        return balance;
    }
}
