package com.neriidev.pix.application.ports.out;

import com.neriidev.pix.domain.model.IdempotencyKey;

import java.util.Optional;

public interface IdempotencyKeyRepositoryPort {
    IdempotencyKey save(IdempotencyKey idempotencyKey);
    Optional<IdempotencyKey> findByKeyAndScope(String key, String scope);
}

