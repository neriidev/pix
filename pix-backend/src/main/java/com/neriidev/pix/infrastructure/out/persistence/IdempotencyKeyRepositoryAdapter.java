package com.neriidev.pix.infrastructure.out.persistence;

import com.neriidev.pix.application.ports.out.IdempotencyKeyRepositoryPort;
import com.neriidev.pix.domain.model.IdempotencyKey;
import com.neriidev.pix.infrastructure.out.persistence.entity.IdempotencyKeyEntity;
import com.neriidev.pix.infrastructure.out.persistence.repository.IdempotencyKeyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IdempotencyKeyRepositoryAdapter implements IdempotencyKeyRepositoryPort {

    private final IdempotencyKeyJpaRepository idempotencyKeyJpaRepository;

    public IdempotencyKeyRepositoryAdapter(IdempotencyKeyJpaRepository idempotencyKeyJpaRepository) {
        this.idempotencyKeyJpaRepository = idempotencyKeyJpaRepository;
    }

    @Override
    public IdempotencyKey save(IdempotencyKey idempotencyKey) {
        IdempotencyKeyEntity idempotencyKeyEntity = new IdempotencyKeyEntity();
        idempotencyKeyEntity.setKey(idempotencyKey.getKey());
        idempotencyKeyEntity.setScope(idempotencyKey.getScope());
        idempotencyKeyEntity.setCreatedAt(idempotencyKey.getCreatedAt());
        idempotencyKeyEntity.setResponse(idempotencyKey.getResponse());

        IdempotencyKeyEntity savedEntity = idempotencyKeyJpaRepository.save(idempotencyKeyEntity);

        return new IdempotencyKey(savedEntity.getKey(), savedEntity.getScope(), savedEntity.getCreatedAt(), savedEntity.getResponse());
    }

    @Override
    public Optional<IdempotencyKey> findByKeyAndScope(String key, String scope) {
        return idempotencyKeyJpaRepository.findByKeyAndScope(key, scope)
                .map(entity -> new IdempotencyKey(entity.getKey(), entity.getScope(), entity.getCreatedAt(), entity.getResponse()));
    }
}
