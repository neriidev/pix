package com.neriidev.pix.infrastructure.out.persistence.repository;


import com.neriidev.pix.infrastructure.out.persistence.entity.IdempotencyKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyKeyJpaRepository extends JpaRepository<IdempotencyKeyEntity, String> {
    Optional<IdempotencyKeyEntity> findByKeyAndScope(String key, String scope);
}
