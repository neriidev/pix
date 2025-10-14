package com.neriidev.pix.infrastructure.out.persistence.repository;

import com.neriidev.pix.domain.model.Transaction;
import com.neriidev.pix.infrastructure.out.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByEndToEndId(String endToEndId);
}