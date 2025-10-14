package com.neriidev.pix.application.ports.out;

import com.neriidev.pix.domain.model.Ledger;

import java.util.List;

public interface LedgerRepositoryPort {
    Ledger save(Ledger ledger);
    List<Ledger> findByWalletId(Long walletId);
}

