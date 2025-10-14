package com.neriidev.pix.infrastructure.out.persistence.repository;

import com.neriidev.pix.infrastructure.out.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
