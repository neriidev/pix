package com.neriidev.pix.infrastructure.out.persistence.repository;

import com.neriidev.pix.infrastructure.out.persistence.entity.PixKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKeyEntity, Long> {
    Optional<PixKeyEntity> findByKey(String key);
}
