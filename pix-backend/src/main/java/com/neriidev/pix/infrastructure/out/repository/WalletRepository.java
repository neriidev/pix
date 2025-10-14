package com.neriidev.pix.infrastructure.out.repository;

import com.neriidev.pix.domain.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
