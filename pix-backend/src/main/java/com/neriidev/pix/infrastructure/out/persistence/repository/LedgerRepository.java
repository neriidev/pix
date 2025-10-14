package com.neriidev.pix.infrastructure.out.persistence.repository;

import com.neriidev.pix.infrastructure.out.persistence.entity.LedgerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<LedgerEntity, Long> {
    List<LedgerEntity> findByWalletId(Long walletId);
}
